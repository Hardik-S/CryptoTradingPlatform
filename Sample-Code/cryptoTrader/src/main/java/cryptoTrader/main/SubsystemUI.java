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
    public Object[] runTrades () {
        ResultFactory results = new ResultFactory();
        // get prices of all subscribed coins
        for (int i = 0; i < this.activeClients.size(); i++) { // for all clients
        	// add coins to accumulator list, removing duplicates
        	String[] coins = this.activeClients.get(i).getCoins();
        	for (int j = 0; j < coins.length; j++) {
        		this.coins.add(coins[j]);
        	}
        }
        // call API and return coins to hashmap

        HashMap<String, Float> coinPrices = new HashMap<String, Float>(); // API returns here <-- (temp empty)

        for (int i = 0; i < this.activeClients.size(); i++) {
        	ArrayList<String> clientCoinList = new ArrayList<String>();
            HashMap<String, Float> clientCoinPrices = new HashMap<String, Float>();
            TradingClient currentClient = this.activeClients.get(i);
            for (String clientCoins : this.activeClients.get(i).getCoins()) { // for every coin of a specific client
            	String[] coins = this.activeClients.get(i).getCoins();
            	for (int j = 0; j < coins.length; j++) {
            		clientCoinList.add(coins[i]);
            		clientCoinPrices.put(clientCoins, coinPrices.get(clientCoins));
            	}
            	// pass coin prices over to trading client
                TradeResult clientResult = this.activeClients.get(i).runStrategy(clientCoinPrices);
                results.createResult(this.activeClients.get(i).name(), this.activeClients.get(i).strategy(), "Undefined coin", clientResult, 6.9f, 100000l);
                // placeholders used for some values, will be replaced later
            }
            
        } return (results.getSummary().toArray()); // returns class holding all results
    }
    
}
