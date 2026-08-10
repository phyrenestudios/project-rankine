# Automated visual testing in the dev client

**Read this before trying to drive `./gradlew runClient` from an agent.** Every trap below cost
real time to find; none of them are guessable, and two of them produce *silent* wrong behaviour
rather than an error.

---

## Why this exists

Gates 1–5 in `verify.sh` (build, unit, datagen, gametest, server smoke) are **all headless**.
There is a whole class of port regression they structurally cannot see, because the mod loads,
registers and ticks perfectly — it just draws or behaves wrongly on screen:

- Item tints going invisible (1.21 item colours are ARGB; a `0xRRGGBB` constant has alpha 0).
- Armour rendering untinted, or an overlay layer never drawn.
- Render layers: a solid block emitted as `translucent`, or a hand-written model that never got
  a `render_type` at all.
- GUI slots drifting off their background holes, progress bars bound to the wrong `ContainerData`
  index, text drawn black-on-black.
- Item model predicates reading the wrong data component, so an item is stuck on one texture.
- JEI laying a non-square recipe out over a guessed square grid.

The 1.21.1 port shipped real instances of **every one of those**. If a change touches rendering,
GUIs, colours, models or JEI, a headless gate pass means nothing — run this.

---

## Setup (once per machine)

### 1. `run/options.txt`

```
pauseOnLostFocus:false
```

**This is not optional and it is the single highest-value line in this document.** With the
default `true`, Minecraft opens the pause menu the moment the window loses focus — and since this
harness deliberately never focuses the window, the game sits paused forever. Worse, it re-opens
the menu instantly every time you close it, which *looks exactly like "the Escape key is broken"*
and will send you chasing a phantom for an hour. It is not broken. Set this to `false`.

Minecraft rewrites `options.txt` on a clean exit, so edit it while the client is **not** running
(hard-killing the process is fine and will not clobber your edit).

`guiScale:3` is also worth setting — it keeps GUI geometry stable between runs so screenshot
coordinates stay valid.

### 2. Window placement

```bash
powershell -File .porting/tools/mc-client-driver.ps1 -Place
```

That puts the window **topmost but never activated**. Both halves matter:

- *Topmost*, because capture is a screen-region copy, which needs the window genuinely visible.
- *Never activated*, because the whole point is that you keep your keyboard while a run proceeds.

A second monitor is ideal — the game sits out of your way and still composites. Check the panel
is actually awake first; a sleeping display returns solid black and looks identical to a crash.

---

## The driver

```bash
powershell -File .porting/tools/mc-client-driver.ps1 -Steps steps.json
powershell -File .porting/tools/mc-client-driver.ps1 -Shot look.png
```

`steps.json` is a JSON array executed in order:

| Step | Meaning |
|---|---|
| `{"cmd": "/tp @s 59.5 70 -275.5 0 20"}` | run a chat command (keep the leading `/`) |
| `{"key": 27}` | press a virtual-key code |
| `{"move": [x, y]}` | move the in-game cursor (hover, for tooltips) |
| `{"click": "right", "at": [x, y]}` | right click — use / place / open a machine GUI |
| `{"click": "left", "at": [x, y]}` | left click |
| `{"wait": 1.5}` | seconds |
| `{"shot": "name.png"}` | capture the window, half scale |
| `{"shot": "name.png", "crop": [x,y,w,h]}` | capture that region at **full** resolution |

Useful key codes: `27` Escape, `69` E (inventory), `114` F3 (debug overlay), `49`–`57` hotbar
slots 1–9.

**Coordinates are in captured-image pixels**, i.e. the half-scale png you get back. At a
2000×1250 window the image is 1000×625 and the crosshair is at `(500, 312)`. The driver converts
to client pixels itself.

Use `crop` liberally — it is the only way to read tooltips, GUI titles and JEI labels, and it
costs nothing extra.

---

## How it works, and why it is built this way

Input is **posted** to the window (`WM_KEYDOWN` / `WM_CHAR` / `WM_MOUSEMOVE` / `WM_*BUTTON*`).
GLFW's window proc handles posted messages exactly like real ones, and `TranslateMessage`
synthesises the `WM_CHAR` for a posted `WM_KEYDOWN` for free. Two consequences:

- **No focus is taken.** The user keeps working.
- **The camera does not spin.** This is the big one. Minecraft grabs the cursor in-world, so
  *moving the real mouse pointer* feeds it a delta and whips the view somewhere random — which
  makes "click the block in front of you" unreproducible. Posting an absolute client coordinate
  sidesteps the grab entirely.

**Capture is `Graphics.CopyFromScreen` over the window rect, deliberately not `PrintWindow`.**
`PrintWindow` (even with `PW_RENDERFULLCONTENT`) is unreliable against Minecraft's OpenGL
surface: it returns either a **stale frame** or solid black. Stale is the dangerous failure —
you get a plausible screenshot of a state that is minutes out of date and draw confident wrong
conclusions from it. `CopyFromScreen` cannot go stale, at the cost of needing the window visible.

---

## Traps

### Escape works. Do not build a workaround for it.

`{"key": 27}` closes any screen — creative inventory, machine GUI, pause menu. If it appears not
to, you have `pauseOnLostFocus:true` (see Setup). Verified against
`KeyboardHandler.keyPress` → `Screen.keyPressed`, and by test.

Historical note, because the wrong workaround is worse than the bug: an earlier version of this
document claimed Escape was unreachable and told agents to "click a non-search creative tab, then
press E". That advice **traps the agent typing into the Search Items box** and it will not get
out on its own. Deleted; use Escape.

### Never send `cmd` while a screen is open

`cmd` presses T to open chat. T does nothing when a screen is up, so the keystrokes leak into
whatever text field has focus — the creative search box, JEI's search, an anvil rename field.
The command silently does not run and you get a corrupted UI state.

Start any steps file containing a `cmd` with an Escape:

```json
[ {"key": 27}, {"wait": 0.4}, {"cmd": "/time set day"} ]
```

### The stray `t`

Pressing T to open chat *also* types a `t` into it, because `TranslateMessage` turns the posted
`WM_KEYDOWN` into a `WM_CHAR` that lands after the screen opens. The driver already eats it with
backspaces — just pass commands normally. Only relevant if you rewrite `RunCmd`.

### Identical screenshots mean something is wrong

If two consecutive captures are pixel-identical, do not keep going. Either the window got
occluded, the display slept, or the session locked. A **locked Windows session composites
nothing** — every capture, including a full-desktop grab, comes back solid black. Check with
`Get-Process LockApp,LogonUI`.

### On a reused world, verify WHAT you're holding via NBT, not the icon

`/give` + hotbar-select on a world with leftover items can silently test the wrong stack (QA once
spent a cycle enchanting a leftover wooden hoe instead of the freshly given pickaxe). Before any
per-item assertion, confirm with `/data get entity @s Inventory[{Slot:0b}]` that the `id` is the
item you think it is. Same principle for blockstates: prefer a definitive text probe
(`/execute if block <x> <y> <z> minecraft:composter[level=0] run say EMPTY`) over reading pixels —
fill levels, crop ages and similar states are all in the blockstate and unambiguous in chat.

### Set the time before judging anything visual

`{"cmd": "/time set day"}`. At night every colour, tint and render-layer judgement is worthless.

### Hold something unplaceable

Press `{"key": 57}` (hotbar 9) and keep a tool there, so a right-click aimed at a machine can
never place a block instead.

### Use F3 to confirm your aim

`{"key": 114}` toggles the debug overlay, which shows **Targeted Block**. Use it to prove the
crosshair is on the machine before right-clicking, instead of inferring it from a GUI that did
not open.

---

## Worked example — open a machine GUI and read its title

```json
[
  {"key": 27}, {"wait": 0.4},
  {"cmd": "/gamerule sendCommandFeedback false"},
  {"cmd": "/time set day"},
  {"cmd": "/tp @s 59.5 70 -275.5 0 20"},
  {"key": 57},
  {"wait": 1},
  {"click": "right", "at": [500, 312]},
  {"wait": 1},
  {"shot": "gui_alloy_furnace.png"},
  {"shot": "gui_alloy_furnace_zoom.png", "crop": [330, 180, 340, 260]}
]
```

Stand ~2.5 blocks away on the −Z side and look slightly down; yaw `0` faces **+Z (south)**, so
`+X is on your left` on screen. Pitch ~20 puts the crosshair on a block one level below eye height.

---

## A reusable machine test bed

```
/fill ~-10 ~-2 ~2 ~10 ~-2 ~16 minecraft:smooth_stone
/fill ~-10 ~-1 ~2 ~10 ~6  ~16 minecraft:air
/setblock ~-6 ~-1 ~5 rankine:alloy_furnace
...
```

Machines are cheapest to place with `/setblock` at fixed offsets and then visit by absolute
coordinate. Machine ids come from `RankineBlocks.java`; note `piston_crusher` and
`gyratory_crusher` have **no BlockItem** (same as 1.18.2) so `/give` will not work for them —
`/setblock` is the only way to see them.

---

## Standing checklist

Anything touching client code should be checked against this list:

1. Every machine GUI opens; title correct; slots aligned to the background; no unreadable text.
2. Creative tabs: no invisible items (the ARGB-alpha bug) and no magenta/missing textures.
3. Alloy items and blocks are tinted, in the inventory **and** worn/placed — those are separate
   code paths and 1.21 broke them independently.
4. Element / storage blocks render solid, not translucent — compare against a vanilla block.
5. Fluid buckets show a real name, not a raw `fluid_type.rankine.*` translation key.
6. Item model predicates flip state (gas vacuum full/empty, building-mode tools).
7. JEI: category text readable, and non-square recipe shapes (2×3, 1×3, 3×2) laid out correctly.
8. Enchanting table and anvil actually offer enchantments on mod gear.
9. The Patchouli journal opens with real content, not blank.

---

## Handing this to a subagent

This work is a screenshot → look → adjust loop, which is a good fit for a cheaper model. When
delegating:

- Point at this file rather than restating the rules; paraphrasing them is how the bad Escape
  advice propagated.
- Give absolute coordinates for everything it must visit.
- Tell it to batch many steps per JSON file — one action per invocation is painfully slow.
- Require it to distinguish **"I saw this bug"** from **"I could not test this"**. An agent that
  reports coverage it did not achieve is worse than one that reports less.
