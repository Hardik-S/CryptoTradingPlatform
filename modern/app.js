const loginForm = document.getElementById("loginForm");
const loginError = document.getElementById("loginError");
const appRoot = document.getElementById("appRoot");
const seedBtn = document.getElementById("seed-login");
const addRowBtn = document.getElementById("addRow");
const runTradeBtn = document.getElementById("runTrade");
const runExportBtn = document.getElementById("runExport");
const runExportPackBtn = document.getElementById("runExportPack");
const runImportPackBtn = document.getElementById("runImportPack");
const runPackFile = document.getElementById("runPackFile");
const rowsBody = document.getElementById("clientRowsBody");
const resultsBody = document.getElementById("resultsBody");
const metricsContainer = document.getElementById("metrics");
const barsContainer = document.getElementById("actionBars");
const statusPill = document.getElementById("statusPill");
const runHistory = document.getElementById("runHistory");
const historyClear = document.getElementById("historyClear");
const trendContainer = document.getElementById("runTrend");
const marketSeedInput = document.getElementById("marketSeed");
const marketDateInput = document.getElementById("marketDate");
const preferLiveInput = document.getElementById("preferLive");

const storageKey = "legacy-rebuild-crypto-trading-history-v2";
const legacyStorageKey = "legacy-rebuild-crypto-trading-history-v1";
const rowStorageKey = "legacy-rebuild-crypto-trading-rows-v1";
const runPackManifestKey = "legacy-rebuild-crypto-trading-pack-v2";
const credentialList = new Set(["andrew1:hockeyfan1", "drew2:hoolahoop98", "justin5:footballfan4", "1:2"]);

const STRATEGIES = ["Strategy-A", "Strategy-B", "Strategy-C", "Strategy-D"];
const BASE_MARKET = Object.freeze({ BTC: 62000, ETH: 3200, USDT: 1 });

function normalisedSymbol(raw) {
  return raw.trim().toUpperCase();
}

function strategyEvaluator({ strategy, coinPrices }) {
  const hasBtc = Object.hasOwn(coinPrices, "BTC");
  const hasEth = Object.hasOwn(coinPrices, "ETH");
  const hasUsdt = Object.hasOwn(coinPrices, "USDT");
  const btc = coinPrices.BTC;
  const eth = coinPrices.ETH;
  const usdt = coinPrices.USDT;
  const result = { action: "Fail", quantity: null, price: null, cryptoCoin: "BTC" };
  if (strategy === "Strategy-A") {
    if (hasBtc && hasEth && btc > 100 && eth > 1000) {
      result.action = "Buy";
      result.quantity = 100;
      result.cryptoCoin = "BTC";
      result.price = btc;
    }
  } else if (strategy === "Strategy-B") {
    if (hasBtc && hasEth && hasUsdt && btc > 10000 && eth > 100 && usdt < 5) {
      result.action = "Sell";
      result.quantity = 200;
      result.cryptoCoin = "USDT";
      result.price = usdt;
    }
  } else if (strategy === "Strategy-C") {
    if (hasBtc && btc < 1000) {
      result.action = "Buy";
      result.quantity = 300;
      result.cryptoCoin = "BTC";
      result.price = btc;
    }
  } else if (strategy === "Strategy-D") {
    if (hasEth && eth < 200) {
      result.action = "Sell";
      result.quantity = 400;
      result.cryptoCoin = "ETH";
      result.price = eth;
    }
  }
  return result;
}

function deterministicHash(value) {
  let hash = 0;
  for (let i = 0; i < value.length; i += 1) {
    hash = (hash * 31 + value.charCodeAt(i)) >>> 0;
  }
  return hash;
}

function seededPrice(base, factorSeed, symbol) {
  const normalized = normalisedSymbol(symbol);
  const seed = String(factorSeed);
  const h1 = deterministicHash(seed + normalized);
  const h2 = deterministicHash(normalized + seed + "legacy");
  const noise = ((h1 ^ h2) % 2000) / 10000;
  const multiplier = 1 + (noise - 0.1);
  return Number((base * multiplier).toFixed(4));
}

function baseForToken(token) {
  return BASE_MARKET[token] ?? 0;
}

function getDateLabel() {
  const provided = marketDateInput.value;
  if (provided) return provided;
  const now = new Date();
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;
}

function coingeckoDate(dateLabel) {
  const parsed = new Date(dateLabel);
  if (Number.isNaN(parsed.valueOf())) {
    const now = new Date();
    return `${String(now.getDate()).padStart(2, "0")}-${String(now.getMonth() + 1).padStart(2, "0")}-${now.getFullYear()}`;
  }
  return `${String(parsed.getDate()).padStart(2, "0")}-${String(parsed.getMonth() + 1).padStart(2, "0")}-${parsed.getFullYear()}`;
}

async function fetchLiveCoinPrice(symbol, dateLabel) {
  const endpoint = `https://api.coingecko.com/api/v3/coins/${symbol.toLowerCase()}/history?date=${coingeckoDate(dateLabel)}`;
  try {
    const response = await fetch(endpoint);
    if (!response.ok) return null;
    const payload = await response.json();
    const cad = payload?.market_data?.current_price?.cad;
    return cad ? Number(cad.toFixed(4)) : null;
  } catch {
    return null;
  }
}

function createRow(trader = "", coinList = "BTC,ETH", strategy = "Strategy-A") {
  const tr = document.createElement("tr");
  tr.innerHTML = `
    <td><input value="${escapeHtml(trader)}" class="trader" aria-label="Trader name"></td>
    <td><input value="${escapeHtml(coinList)}" class="coins" aria-label="Coin list"></td>
    <td>
      <select class="strategy" aria-label="Strategy selector">
        ${STRATEGIES.map((s) => `<option ${s === strategy ? "selected" : ""}>${s}</option>`).join("")}
      </select>
    </td>
    <td class="row-actions">
      <button type="button" class="ghost remove-row" data-action="remove">Delete</button>
    </td>`;
  rowsBody.appendChild(tr);
  tr.querySelector('[data-action="remove"]').addEventListener("click", () => {
    tr.remove();
  });
}

function normalizeCoinList(rawValue) {
  const symbols = rawValue
    .split(",")
    .map((it) => normalisedSymbol(it))
    .filter(Boolean);
  if (!symbols.length) return [];
  const uniq = [...new Set(symbols)];
  return uniq;
}

function getRowsState() {
  return Array.from(rowsBody.querySelectorAll("tr")).map((row) => ({
    trader: row.querySelector(".trader").value.trim(),
    coinList: row.querySelector(".coins").value.trim(),
    strategy: row.querySelector(".strategy").value,
  }));
}

function persistRows() {
  localStorage.setItem(rowStorageKey, JSON.stringify(getRowsState()));
}

function renderMetrics(results) {
  const total = results.length;
  const buys = results.filter((it) => it.action === "Buy").length;
  const sells = results.filter((it) => it.action === "Sell").length;
  const fails = total - buys - sells;
  const successRate = total === 0 ? 0 : Math.round(((buys + sells) / total) * 100);
  const today = getDateLabel();
  metricsContainer.innerHTML = `
    <div class="metric-card"><div class="label">Runs</div><div class="value">${total}</div></div>
    <div class="metric-card"><div class="label">Buys</div><div class="value status-ok">${buys}</div></div>
    <div class="metric-card"><div class="label">Sells</div><div class="value status-warn">${sells}</div></div>
    <div class="metric-card"><div class="label">Fails</div><div class="value status-bad">${fails}</div></div>
    <div class="metric-card"><div class="label">Success rate</div><div class="value">${successRate}%</div></div>
    <div class="metric-card"><div class="label">Market date</div><div class="value">${today}</div></div>
  `;
}

function renderBars(results) {
  const grouped = { "Strategy-A": { Buy: 0, Sell: 0, Fail: 0 },
                    "Strategy-B": { Buy: 0, Sell: 0, Fail: 0 },
                    "Strategy-C": { Buy: 0, Sell: 0, Fail: 0 },
                    "Strategy-D": { Buy: 0, Sell: 0, Fail: 0 } };
  results.forEach((entry) => {
    grouped[entry.strategy][entry.action] += 1;
  });
  const max = Math.max(1, ...Object.values(grouped).flatMap((g) => [g.Buy, g.Sell]));
  barsContainer.innerHTML = Object.entries(grouped).map(([strategy, counts]) => {
    return `<article class="bar-row">
        <span>${strategy}</span>
        <div><small>${counts.Buy} buy / ${counts.Sell} sell / ${counts.Fail} fail</small></div>
        <div class="bar-shell"><div class="bar-fill buy" style="width:${(counts.Buy / max) * 100}%"></div></div>
        <div class="bar-shell"><div class="bar-fill sell" style="width:${(counts.Sell / max) * 100}%"></div></div>
      </article>`;
  }).join("");
}

function renderHistory(entries) {
  runHistory.innerHTML = "";
  if (!entries.length) {
    runHistory.innerHTML = `<li>No historical runs yet.</li>`;
    return;
  }
  entries.slice(-8).reverse().forEach((entry) => {
    const li = document.createElement("li");
    const seed = entry.seed ? `, seed=${entry.seed}` : "";
    li.textContent = `${entry.at}: total=${entry.total}, buys=${entry.buys}, sells=${entry.sells}, fails=${entry.fails}${seed}`;
    runHistory.appendChild(li);
  });
}

function renderTrend(entries) {
  trendContainer.innerHTML = "";
  if (!entries.length) {
    trendContainer.innerHTML = "<div class='trend-row'><p>No trend data available yet.</p></div>";
    return;
  }
  const recent = entries.slice(-8).reverse();
  const max = Math.max(1, ...recent.map((entry) => Math.max(entry.total, entry.buys + entry.sells + entry.fails || 1)));
  trendContainer.innerHTML = recent
    .map((entry) => {
      const rowTotal = entry.total || 1;
      const buyWidth = Math.round((entry.buys / max) * 100);
      const sellWidth = Math.round((entry.sells / max) * 100);
      const failWidth = Math.round((entry.fails / max) * 100);
      const dateLabel = entry.dateLabel || "n/a";
      return `<article class='trend-row'>
        <span class='trend-head'>${entry.runId || "Run"} · ${rowTotal} entries · ${dateLabel}</span>
        <span class='trend-meta'>Buy ${entry.buys}, Sell ${entry.sells}, Fail ${entry.fails}</span>
        <div class='trend-shell' aria-hidden='true'>
          <span class='trend-fill buy' style='width:${buyWidth}%;'></span>
          <span class='trend-fill sell' style='width:${sellWidth}%;'></span>
          <span class='trend-fill fail' style='width:${failWidth}%;'></span>
        </div>
      </article>`;
    })
    .join("");
}

function getHistoryState() {
  const raw = localStorage.getItem(storageKey);
  if (raw) {
    try {
      const parsed = JSON.parse(raw);
      if (Array.isArray(parsed)) return parsed;
    } catch {}
  }
  const legacyRaw = localStorage.getItem(legacyStorageKey);
  if (!legacyRaw) return [];
  try {
    const parsed = JSON.parse(legacyRaw);
    return Array.isArray(parsed)
      ? parsed.map((entry) => ({
          runId: `legacy-${Date.parse(entry.at || new Date().toISOString())}`,
          at: entry.at || new Date().toLocaleString(),
          total: Number(entry.total || 0),
          buys: Number(entry.buys || 0),
          sells: Number(entry.sells || 0),
          fails: Number(entry.fails || 0),
          dateLabel: entry.date || "",
          seed: "",
          entries: [],
        }))
      : [];
  } catch {
    return [];
  }
}

function setHistoryState(next) {
  localStorage.setItem(storageKey, JSON.stringify(next.slice(-40)));
}

function rowValidation(rows) {
  const seen = new Set();
  for (let i = 0; i < rows.length; i += 1) {
    const { trader, coinList, strategy } = rows[i];
    if (!trader) return `Please fill trader name on row ${i + 1}.`;
    if (seen.has(trader.toLowerCase())) return `Duplicate trader name on row ${i + 1}.`;
    seen.add(trader.toLowerCase());
    if (!coinList) return `Please fill coin list on row ${i + 1}.`;
    if (!STRATEGIES.includes(strategy)) return `Please select a strategy on row ${i + 1}.`;
  }
  return "";
}

function escapeHtml(value) {
  return String(value ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}

async function runSimulation() {
  const rows = getRowsState();
  const error = rowValidation(rows);
  if (error) {
    statusPill.textContent = error;
    statusPill.className = "status-bad";
    return;
  }
  if (!rows.length) {
    statusPill.textContent = "Please add at least one trading row.";
    statusPill.className = "status-bad";
    return;
  }
  persistRows();
  const seed = Number(marketSeedInput.value || "2026");
  const dateLabel = getDateLabel();
  const runAt = new Date().toLocaleString();
  const runId = `run-${Date.now()}`;
  const results = [];
  statusPill.textContent = "Running simulation...";
  statusPill.className = "";
  resultsBody.innerHTML = "";

  for (const entry of rows) {
    const normalizedCoins = normalizeCoinList(entry.coinList);
    const symbolPrices = {};
    for (const coin of normalizedCoins) {
      const p = seededPrice(baseForToken(coin), `${seed}:${entry.trader}:${dateLabel}`, coin);
      symbolPrices[coin] = p;
    }

    if (preferLiveInput.checked) {
      await Promise.all(
        normalizedCoins.map(async (coin) => {
          const mapped = coin === "BTC" ? "bitcoin" : coin === "ETH" ? "ethereum" : coin.toLowerCase();
          const live = await fetchLiveCoinPrice(mapped, dateLabel);
          if (live && Number.isFinite(live)) {
            symbolPrices[coin] = live;
          }
        })
      );
    }

    const strategyOut = strategyEvaluator({
      strategy: entry.strategy,
      coinPrices: symbolPrices,
    });
    const rowResult = {
      trader: entry.trader,
      strategy: entry.strategy,
      cryptoCoin: strategyOut.cryptoCoin,
      action: strategyOut.action,
      quantity: strategyOut.quantity,
      price: strategyOut.price,
      date: dateLabel,
    };
    results.push(rowResult);

    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${escapeHtml(entry.trader)}</td>
      <td>${escapeHtml(entry.strategy)}</td>
      <td>${escapeHtml(strategyOut.cryptoCoin)}</td>
      <td>${escapeHtml(strategyOut.action)}</td>
      <td>${strategyOut.quantity ?? "null"}</td>
      <td>${strategyOut.price !== null ? strategyOut.price.toFixed(4) : "null"}</td>
      <td>${escapeHtml(dateLabel)}</td>`;
    resultsBody.appendChild(tr);
  }

  renderMetrics(results);
  renderBars(results);
  const buys = results.filter((it) => it.action === "Buy").length;
  const sells = results.filter((it) => it.action === "Sell").length;
  const fails = results.length - buys - sells;
  statusPill.textContent = `Run complete. ${results.length} rows processed.`;
  statusPill.className = "status-ok";
  const hist = getHistoryState();
  const next = [...hist, {
    runId,
    at: runAt,
    total: results.length,
    buys,
    sells,
    fails,
    dateLabel,
    seed,
    entries: results,
  }];
  setHistoryState(next);
  renderHistory(next);
  renderTrend(next);
}

function buildRunPack() {
  return {
    schemaVersion: 2,
    exportedAt: new Date().toISOString(),
    repository: "Hardik-S/CryptoTradingPlatform",
    sourceRows: getRowsState(),
    runs: getHistoryState(),
  };
}

function downloadJson(filename, payload) {
  const blob = new Blob([JSON.stringify(payload, null, 2)], { type: "application/json;charset=utf-8;" });
  const link = document.createElement("a");
  link.href = URL.createObjectURL(blob);
  link.download = filename;
  link.click();
  URL.revokeObjectURL(link.href);
}

function exportRunPack() {
  const payload = buildRunPack();
  downloadJson(`legacy-crypto-run-pack-${Date.now()}.json`, payload);
  localStorage.setItem(runPackManifestKey, JSON.stringify({
    exportedAt: payload.exportedAt,
    runCount: payload.runs.length,
  }));
}

function readRunPack(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => {
      try {
        const payload = JSON.parse(String(reader.result || "{}"));
        if (!payload || !Array.isArray(payload.runs)) {
          reject(new Error("Invalid run pack payload: missing runs array."));
          return;
        }
        resolve(payload);
      } catch (error) {
        reject(error);
      }
    };
    reader.onerror = () => reject(new Error("Unable to read selected file."));
    reader.readAsText(file);
  });
}

async function importRunPack() {
  const file = runPackFile.files?.[0];
  if (!file) {
    statusPill.textContent = "Select a run pack JSON file first.";
    statusPill.className = "status-warn";
    return;
  }
  try {
    const payload = await readRunPack(file);
    const next = (payload.runs || []).map((entry) => ({
      runId: entry.runId || `imported-${Date.now()}`,
      at: entry.at || new Date().toLocaleString(),
      total: Number(entry.total || 0),
      buys: Number(entry.buys || 0),
      sells: Number(entry.sells || 0),
      fails: Number(entry.fails || 0),
      dateLabel: entry.dateLabel || "",
      seed: entry.seed || "",
      entries: Array.isArray(entry.entries) ? entry.entries : [],
    }));
    setHistoryState(next);
    renderHistory(next);
    renderTrend(next);
    statusPill.textContent = `Run pack imported with ${next.length} runs.`;
    statusPill.className = "status-ok";
    localStorage.setItem(runPackManifestKey, JSON.stringify({
      importedAt: new Date().toISOString(),
      runCount: next.length,
      source: payload.repository || "unknown",
    }));
  } catch (error) {
    statusPill.textContent = `Import failed: ${error.message}`;
    statusPill.className = "status-bad";
  } finally {
    runPackFile.value = "";
  }
}

function exportCSV() {
  const rows = Array.from(resultsBody.querySelectorAll("tr"))
    .map((row) => Array.from(row.children).map((cell) => cell.textContent))
    .map((cols) => cols.map((c) => `"${String(c ?? "").replaceAll('"', '""')}"`).join(","));
  const csv = [["Trader", "Strategy", "CryptoCoin", "Action", "Quantity", "Price", "Date"].join(","), ...rows].join("\n");
  const blob = new Blob([csv], { type: "text/csv;charset=utf-8;" });
  const link = document.createElement("a");
  link.href = URL.createObjectURL(blob);
  link.download = `legacy-trading-results-${Date.now()}.csv`;
  link.click();
  URL.revokeObjectURL(link.href);
}

function hydrateFromStorage() {
  try {
    const rawRows = localStorage.getItem(rowStorageKey);
    if (!rawRows) {
      createRow("Broker-1", "BTC,ETH", "Strategy-A");
      createRow("Broker-2", "BTC,ETH,USDT", "Strategy-B");
      return;
    }
    const savedRows = JSON.parse(rawRows);
    savedRows.forEach((row) => {
      createRow(row.trader, row.coinList, row.strategy);
    });
    if (!savedRows.length) {
      createRow("Broker-1", "BTC,ETH", "Strategy-A");
    }
  } catch {
    createRow("Broker-1", "BTC,ETH", "Strategy-A");
  }
  renderHistory(getHistoryState());
}

addRowBtn.addEventListener("click", () => {
  createRow("Broker-" + (rowsBody.children.length + 1), "BTC,ETH", "Strategy-A");
  persistRows();
});

runTradeBtn.addEventListener("click", runSimulation);
runExportBtn.addEventListener("click", exportCSV);
historyClear.addEventListener("click", () => {
  localStorage.removeItem(storageKey);
  localStorage.removeItem(runPackManifestKey);
  renderHistory([]);
  renderTrend([]);
  statusPill.textContent = "History cleared.";
});

seedBtn.addEventListener("click", () => {
  document.getElementById("username").value = "1";
  document.getElementById("password").value = "2";
});

rowsBody.addEventListener("input", () => persistRows());
rowsBody.addEventListener("change", () => persistRows());

loginForm.addEventListener("submit", (event) => {
  event.preventDefault();
  const user = (document.getElementById("username").value || "").trim().toLowerCase();
  const pass = (document.getElementById("password").value || "").trim();
  if (credentialList.has(`${user}:${pass}`)) {
    loginError.textContent = "";
    appRoot.hidden = false;
    document.querySelector(".auth-panel").hidden = true;
    if (!marketDateInput.value) {
      const now = new Date();
      marketDateInput.value = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;
    }
  } else {
    loginError.textContent = "Wrong username or password.";
  }
});

runExportPackBtn.addEventListener("click", exportRunPack);
runImportPackBtn.addEventListener("click", () => runPackFile.click());
runPackFile.addEventListener("change", importRunPack);

hydrateFromStorage();
const existingHistory = getHistoryState();
setHistoryState(existingHistory);
renderHistory(existingHistory);
renderTrend(existingHistory);

if (!marketSeedInput.value) {
  marketSeedInput.value = "2026";
}
