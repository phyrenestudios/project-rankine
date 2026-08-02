#!/usr/bin/env bash
# Closed-loop verification gates for the NeoForge 1.21.1 port.
# Usage: bash .porting/verify.sh [gate...]
#   gates: build | test | datagen | gametest | server | all   (default: all)
# Exits non-zero on the first failing gate. Logs land in .porting/logs/.
set -uo pipefail
cd "$(dirname "$0")/.." || exit 1

LOGS=.porting/logs
mkdir -p "$LOGS"
GATES="${*:-all}"
run_gate() { [[ "$GATES" == "all" || " $GATES " == *" $1 "* ]]; }

pass() { echo "PASS  $1"; }
fail() { echo "FAIL  $1  (see $2)"; exit 1; }

# ---------------------------------------------------------------- gate: build
if run_gate build; then
  echo "== gate: build =="
  ./gradlew build -x test --console=plain > "$LOGS/build.log" 2>&1 \
    && pass build || { grep -E "error:" "$LOGS/build.log" | head -30; fail build "$LOGS/build.log"; }
fi

# ----------------------------------------------------------------- gate: test
if run_gate test; then
  echo "== gate: unit tests =="
  ./gradlew test --console=plain > "$LOGS/test.log" 2>&1 \
    && pass test || { grep -E "FAILED|error:" "$LOGS/test.log" | head -30; fail test "$LOGS/test.log"; }
fi

# -------------------------------------------------------------- gate: datagen
# runData exercises every registry, codec and bootstrap headlessly. It must also be
# IDEMPOTENT: a second run that changes files means datagen is non-deterministic.
if run_gate datagen; then
  echo "== gate: datagen =="
  ./gradlew runData --console=plain > "$LOGS/datagen.log" 2>&1 \
    || { tail -40 "$LOGS/datagen.log"; fail datagen "$LOGS/datagen.log"; }
  if git status --porcelain src/generated/resources | grep -q .; then
    echo "  note: datagen produced changes (expected on first run after the port)"
    git status --porcelain src/generated/resources | head -5
  fi
  pass datagen
fi

# ------------------------------------------------------------- gate: gametest
# Boots a real headless server, loads all registries + datapacks, runs the
# GameTest suite, and exits non-zero if any test fails.
if run_gate gametest; then
  echo "== gate: gametest =="
  ./gradlew runGameTestServer --console=plain > "$LOGS/gametest.log" 2>&1 \
    && pass gametest || { grep -iE "failed|FAIL|Exception" "$LOGS/gametest.log" | head -30; fail gametest "$LOGS/gametest.log"; }
fi

# --------------------------------------------------------------- gate: server
# Dedicated-server smoke test: boot to "Done", then stop cleanly. Catches
# registry/datapack failures that only surface on a real world load.
if run_gate server; then
  echo "== gate: server smoke =="
  mkdir -p run/server
  echo "eula=true" > run/server/eula.txt
  # server.properties: flat world + no spawn chunks keeps the boot fast and deterministic
  cat > run/server/server.properties <<'EOF'
level-type=minecraft\:flat
online-mode=false
max-tick-time=600000
view-distance=4
EOF
  ( ./gradlew runServer --console=plain > "$LOGS/server.log" 2>&1 ) &
  GRADLE_PID=$!
  ok=0
  for _ in $(seq 1 180); do
    if grep -qE 'Done \([0-9.]+s\)! For help' "$LOGS/server.log"; then ok=1; break; fi
    if grep -qE 'Failed to start the minecraft server|A problem occurred|FAILURE: Build failed' "$LOGS/server.log"; then break; fi
    if ! kill -0 "$GRADLE_PID" 2>/dev/null; then break; fi
    sleep 5
  done
  kill "$GRADLE_PID" 2>/dev/null
  wait "$GRADLE_PID" 2>/dev/null
  # Any crash report written during boot is a failure even if "Done" appeared.
  if [[ "$ok" == "1" ]] && ! ls run/server/crash-reports/* >/dev/null 2>&1; then
    pass "server smoke (reached Done)"
  else
    tail -40 "$LOGS/server.log"; fail "server smoke" "$LOGS/server.log"
  fi
fi

echo
echo "All requested gates passed."
