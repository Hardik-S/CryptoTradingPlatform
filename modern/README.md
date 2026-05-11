# Legacy-to-Modern Surface Notes

## Selection and continuity context
- Repository selected live: `Hardik-S/CryptoTradingPlatform` (older than six months, non-archived, non-fork).
- No prior automation modernization path was found for this repository in `state/legacy-rebuild-log.md`.
- A fresh branch was created: `legacy-rebuild/CryptoTradingPlatform-20260511-0040`.
- Worktree path: `automation-runs/legacy-rebuild/worktrees/CryptoTradingPlatform-20260511-0040`.

## Old-to-new functionality map
- Old Java Swing app preserved behavior:
  - Trader-driven rows (`trader`, `coin list`, `strategy`).
  - Strategy execution over BTC/ETH/USDT constraints with fixed quantities.
  - Result output with `Trader`, `Strategy`, `CryptoCoin`, `Action`, `Quantity`, `Price`, `Date`.
  - Login gate using static credentials file (`LoginDB.txt` style pairs).
- New web surface preserves each of these and extends UI/UX:
  - Browser-first workflow with inline validation and clear row-level actions.
  - Rebuild chart-less visual summary with strategy-by-strategy bars.
  - Deterministic simulation seeds and date label to make repeated runs reproducible.
  - Local persistence of rows and run history in `localStorage`.
  - CSV export for result auditability.
  - Optional live coin price fetch attempt with deterministic fallback.

## Rejected approaches
- Did not port Java Swing classes directly (would preserve UI tech mismatch and block lightweight preview).
- Did not attempt to remove original Maven app; preserved untouched in `Sample-Code/cryptoTrader`.

## Stack choice rationale
- Used plain HTML/CSS/vanilla JS in a `modern/` directory to keep deployable previewability high and implementation lightweight.
- The original stack is Java desktop (Swing/JFreeChart), so a browser stack reduces maintenance risk and enables faster UI iteration.

## Run notes
- Login:
  - Use demo pair `1` / `2` (from repo credentials).
- Simulation:
  - Add rows, choose strategy, and press `Run legacy trade simulation`.
- Verification:
  - Syntax check via `node --check modern/app.js` and `git diff --check`.
  - Local smoke by serving this folder and opening `modern/index.html`.

## Next logical upgrade
- Add a persistent `runId` and downloadable JSON bundle for each saved run.
- Add light chart (Canvas/WebGL-free) for cumulative action trends across runs.
