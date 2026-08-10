<#
.SYNOPSIS
    Drives the Minecraft dev client for automated visual testing, without ever taking focus.

.DESCRIPTION
    Input is POSTED to the game window (WM_KEYDOWN / WM_CHAR / WM_MOUSEMOVE / WM_*BUTTON*),
    which GLFW's window proc handles exactly like real input. Because we never move the real
    cursor and never activate the window:
      * the user keeps their keyboard and mouse while a test run is in progress, and
      * the in-game camera does not spin (moving the OS cursor while Minecraft has the cursor
        grabbed feeds it a mouse delta and whips the view around — that wrecked earlier attempts).

    Capture is a screen-region copy of the window rect. See CLIENT-TESTING.md for why
    PrintWindow is NOT used.

.PARAMETER Steps
    Path to a JSON array of steps. See CLIENT-TESTING.md for the step vocabulary.

.PARAMETER Shot
    Capture the window to this path and exit.

.EXAMPLE
    ./mc-client-driver.ps1 -Shot look.png
    ./mc-client-driver.ps1 -Steps steps.json
#>
param(
  [string]$Steps,
  [string]$Shot,
  [double]$Wait = 0.6,
  [switch]$Place      # reposition the window for a test run, then exit
)

Add-Type -AssemblyName System.Drawing
Add-Type @"
using System;
using System.Runtime.InteropServices;
public class MCW {
  [DllImport("user32.dll")] public static extern IntPtr PostMessage(IntPtr h, uint m, IntPtr w, IntPtr l);
  [DllImport("user32.dll")] public static extern uint MapVirtualKey(uint c, uint t);
  [DllImport("user32.dll")] public static extern bool GetWindowRect(IntPtr h, out RECT r);
  [DllImport("user32.dll")] public static extern bool ClientToScreen(IntPtr h, ref POINT p);
  [DllImport("user32.dll")] public static extern bool SetWindowPos(IntPtr h, IntPtr a, int x, int y, int w, int ht, uint f);
  [DllImport("user32.dll")] public static extern bool ShowWindow(IntPtr h, int c);
  [DllImport("user32.dll")] public static extern bool IsIconic(IntPtr h);
  [StructLayout(LayoutKind.Sequential)] public struct POINT { public int X,Y; }
  [StructLayout(LayoutKind.Sequential)] public struct RECT { public int L,T,R,B; }
}
"@

$proc = Get-Process | Where-Object { $_.MainWindowTitle -like "*Minecraft*" } | Select-Object -First 1
if (-not $proc) { Write-Error "No Minecraft window. Start it with ./gradlew runClient first."; exit 1 }
$h = $proc.MainWindowHandle
if ([MCW]::IsIconic($h)) { [void][MCW]::ShowWindow($h, 4); Start-Sleep -Seconds 2 }   # SW_SHOWNOACTIVATE

if ($Place) {
  # HWND_TOPMOST (-1) + SWP_NOACTIVATE (0x10): always visible so frames composite,
  # never focused so the user keeps their input.
  [void][MCW]::SetWindowPos($h, [IntPtr](-1), 200, 60, 2000, 1250, 0x0010)
  Write-Output "window placed topmost, not activated"
  exit 0
}

$wr = New-Object MCW+RECT; [void][MCW]::GetWindowRect($h, [ref]$wr)
$co = New-Object MCW+POINT; [void][MCW]::ClientToScreen($h, [ref]$co)
$offX = $co.X - $wr.L; $offY = $co.Y - $wr.T

function LParam([int]$ix, [int]$iy) {
  $cx = ($ix * 2) - $offX; $cy = ($iy * 2) - $offY      # image px -> client px
  return [IntPtr](($cy -shl 16) -bor ($cx -band 0xFFFF))
}
function PressKey([int]$vk) {
  $sc = [MCW]::MapVirtualKey([uint32]$vk, 0)
  [void][MCW]::PostMessage($h, 0x0100, [IntPtr]$vk, [IntPtr](1 -bor ($sc -shl 16)))
  Start-Sleep -Milliseconds 45
  [void][MCW]::PostMessage($h, 0x0101, [IntPtr]$vk, [IntPtr](1 -bor ($sc -shl 16) -bor (1 -shl 30) -bor (1 -shl 31)))
  Start-Sleep -Milliseconds 45
}
function TypeText([string]$s) {
  foreach ($c in $s.ToCharArray()) {
    [void][MCW]::PostMessage($h, 0x0102, [IntPtr][int]$c, [IntPtr]1)   # WM_CHAR
    Start-Sleep -Milliseconds 8
  }
}
function RunCmd([string]$c) {
  PressKey 0x54                                   # T opens chat
  Start-Sleep -Milliseconds 350
  1..8 | ForEach-Object { PressKey 0x08 }         # TranslateMessage also types a stray 't'
  Start-Sleep -Milliseconds 100
  TypeText $c
  Start-Sleep -Milliseconds 120
  PressKey 0x0D                                   # Enter
  Start-Sleep -Milliseconds 300
}
function MoveTo([int]$ix, [int]$iy) {
  [void][MCW]::PostMessage($h, 0x0200, [IntPtr]0, (LParam $ix $iy))
  Start-Sleep -Milliseconds 120
}
function ClickAt([string]$btn, $at) {
  $lp = [IntPtr]0
  if ($at) { MoveTo ([int]$at[0]) ([int]$at[1]); $lp = LParam ([int]$at[0]) ([int]$at[1]) }
  if ($btn -eq "right") { $d = 0x0204; $u = 0x0205; $w = 2 } else { $d = 0x0201; $u = 0x0202; $w = 1 }
  [void][MCW]::PostMessage($h, $d, [IntPtr]$w, $lp)
  Start-Sleep -Milliseconds 110
  [void][MCW]::PostMessage($h, $u, [IntPtr]0, $lp)
  Start-Sleep -Milliseconds 250
}
function Grab([string]$path, $crop) {
  $r = New-Object MCW+RECT; [void][MCW]::GetWindowRect($h, [ref]$r)
  $full = New-Object System.Drawing.Bitmap(($r.R-$r.L), ($r.B-$r.T))
  $g = [System.Drawing.Graphics]::FromImage($full)
  $g.CopyFromScreen($r.L, $r.T, 0, 0, (New-Object System.Drawing.Size(($r.R-$r.L), ($r.B-$r.T))))
  $g.Dispose()
  if ($crop) {
    $rect = New-Object System.Drawing.Rectangle(([int]$crop[0]*2), ([int]$crop[1]*2), ([int]$crop[2]*2), ([int]$crop[3]*2))
    $out = $full.Clone($rect, $full.PixelFormat)
  } else {
    $out = New-Object System.Drawing.Bitmap([int]($full.Width/2), [int]($full.Height/2))
    $g2 = [System.Drawing.Graphics]::FromImage($out)
    $g2.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::HighQualityBicubic
    $g2.DrawImage($full, 0, 0, $out.Width, $out.Height); $g2.Dispose()
  }
  $out.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
  $full.Dispose(); $out.Dispose()
  Write-Output "shot -> $path"
}

if ($Steps) {
  foreach ($s in (Get-Content -Raw -Path $Steps | ConvertFrom-Json)) {
    $n = $s.PSObject.Properties.Name
    if ($n -contains 'cmd')   { RunCmd $s.cmd; Write-Output "cmd  $($s.cmd)" }
    if ($n -contains 'key')   { PressKey ([int]$s.key); Write-Output "key  $($s.key)" }
    if ($n -contains 'move')  { MoveTo ([int]$s.move[0]) ([int]$s.move[1]) }
    if ($n -contains 'click') { ClickAt $s.click $s.at; Write-Output "click $($s.click)" }
    if ($n -contains 'wait')  { Start-Sleep -Seconds ([double]$s.wait) }
    if ($n -contains 'shot')  { Grab $s.shot $s.crop }
  }
}
if ($Shot) { Start-Sleep -Seconds $Wait; Grab $Shot $null }
Write-Output "ok"
