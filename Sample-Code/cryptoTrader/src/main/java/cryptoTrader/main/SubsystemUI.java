package cryptoTrader.main;

import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.namespace.QName;

import java.util.HashMap;

public class SubsystemUI {
    private ArrayList<TradingClient> activeClients;
    private HashSet<String> coins;

    public SubsystemUI () {
        this.activeClients = new ArrayList<TradingClient>();
        this.coins = new HashSet<String>();
    }

    public TradingClient createClient(String clientName, String[] clientCoins, String clientStrategy) { // trading client factory

        TradingClient newClient = new TradingClient(clientName, clientCoins, clientStrategy);
        this.activeClients.add(newClient);
        return (newClient);
    }
    
    

    /**
     * Runs all trades created so far, returns ResultFactory storing all 
     */
    public ResultFactory runTrades () {
        ResultFactory results = new ResultFactory();
        // get prices of all subscribed coins
        for (TradingClient current : this.activeClients.toArray()) {
            this.coins.addAll(current.getCoins()); // add coins to accumulator list, removing duplicates
        }
        // call API and return coins to hashmap

        HashMap<String, Float> coinPrices = new HashMap<String, Float>(); // API returns here <-- (temp empty)

        for (TradingClient current : this.activeClients.toArray()) { // for every client
            ArrayList<String> clientCoinList = new ArrayList<String>();
            HashMap<String, Float> clientCoinPrices = new HashMap<String, Float>();
            for (String clientCoins : current.getCoins()) { // for every coin of a specific client
                clientCoinList.add(coinPrices.get(clientCoins));
                clientCoinPrices.put(clientCoins, coinPrices.get(clientCoins));
            }
            // pass coin prices over to trading client
            TradeResult clientResult = current.runStrategy(clientCoinPrices);
            results.createResult(current.name(), current.strategy(), "Undefined coin", clientResult, 6.9f, 100000l);
            // placeholders used for some values, will be replaced later
        }
        return (results); // returns class holding all results
        
    }
}
