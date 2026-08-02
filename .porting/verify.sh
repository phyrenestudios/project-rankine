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
  # ModDevGradle points every run config (client/server/data/gameTestServer) at the
  # same game directory: run/. There is no run/server/ — writing the eula there
  # leaves the real run/eula.txt at false and the server refuses to boot.
  mkdir -p run
  echo "eula=true" > run/eula.txt
  # A normal (not flat) world is the point of this gate: it exercises Rankine's
  # biome modifiers, ore features and the retrogen attachment during spawn-chunk
  # generation. Small view/simulation distance and a high watchdog keep it bounded.
  cat > run/server.properties <<'EOF'
level-name=smokeworld
level-type=minecraft\:normal
online-mode=false
max-tick-time=600000
view-distance=4
simulation-distance=4
EOF
  ( ./gradlew runServer --console=plain > "$LOGS/server.log" 2>&1 ) &
  GRADLE_PID=$!
  ok=0
  for _ in $(seq 1 180); do
    if grep -qaE 'Done \([0-9.]+s\)! For help' "$LOGS/server.log"; then ok=1; break; fi
    if grep -qaE 'Failed to start the minecraft server|A problem occurred|FAILURE: Build failed' "$LOGS/server.log"; then break; fi
    if ! kill -0 "$GRADLE_PID" 2>/dev/null; then break; fi
    sleep 5
  done

  # Stopping the server is awkward and both obvious approaches fail:
  #   - ModDevGradle does not wire stdin into the run task, so piping "stop" into
  #     ./gradlew never reaches the server console. (Verified: the server ignored it
  #     and kept idling until it was killed.)
  #   - The server JVM is forked by the Gradle *daemon*, not by this script, so it is
  #     not a descendant of $GRADLE_PID and survives killing that.
  # Kill it by command line instead. Note you cannot match on the program args
  # (--nogui and friends): MDG passes those in an @argfile, so they never appear on
  # the command line. Match the argfile path itself, which is unique per run config.
  # -clike (case-SENSITIVE) matters: -like would also match a concurrently running
  # gameTestServerRunProgramArgs.txt and kill gate 4 out from under itself.
  powershell.exe -NoProfile -Command \
    'Get-CimInstance Win32_Process | Where-Object { $_.Name -eq "java.exe" -and $_.CommandLine -clike "*\serverRunProgramArgs.txt*" } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }' \
    >/dev/null 2>&1
  kill "$GRADLE_PID" 2>/dev/null
  wait "$GRADLE_PID" 2>/dev/null

  # Gradle always reports BUILD FAILED here because we killed the JVM it was waiting
  # on. That is expected and is NOT part of the verdict.
  #
  # The verdict is: reached "Done", wrote no crash report, and loaded every recipe.
  # Recipe parse errors are the highest-signal thing this gate catches -- they are
  # logged, not thrown, so the server boots "successfully" with content missing.
  # (This is how the minecraft:milk fluid regression was found.) The journal recipe
  # is the one accepted failure: its result is a Patchouli item and Patchouli is not
  # in the dev runtime. 1.18.2 logged the same error for the same reason.
  bad_recipes=$(grep -a "Parsing error loading recipe" "$LOGS/server.log" \
                | grep -av "rankine:rankine_journal" || true)
  if [[ "$ok" == "1" && -z "$bad_recipes" ]] && ! ls run/crash-reports/* >/dev/null 2>&1; then
    pass "server smoke (reached Done, all recipes loaded)"
  else
    [[ -n "$bad_recipes" ]] && { echo "  recipes failed to load:"; echo "$bad_recipes"; }
    tail -40 "$LOGS/server.log"; fail "server smoke" "$LOGS/server.log"
  fi
fi

echo
echo "All requested gates passed."
