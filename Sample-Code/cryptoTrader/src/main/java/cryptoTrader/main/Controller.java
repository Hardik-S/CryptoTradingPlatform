package cryptoTrader.main;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.namespace.QName;

import Login.LoginServer;
import Login.LoginUI;
import Results.ResultFactory;
import Strategies.Strategy;
import Strategies.StrategyFactory;
import cryptoTrader.utils.DataFetcher;

import java.util.HashMap;

/**
 * 
 * @author Hunter Terpstra, Ryan Chan
 *
 */

public class Controller {
    private ArrayList<TradingClient> activeClients;
    private HashSet<String> coins;
    private static Controller instance;

    /**
     * Initialises class objects
     */
    public Controller () {
        this.activeClients = new ArrayList<TradingClient>();
        this.coins = new HashSet<String>();
    }
    

    /**
     * 
     * @param clientName
     * @param clientCoins
     * @param clientStrategy
     * @return
     */
    public TradingClient createClient(String clientName, String[] clientCoins, String clientStrategy) { // trading client factory

        TradingClient newClient = new TradingClient(clientName, clientCoins, clientStrategy);
        this.activeClients.add(newClient);
        return (newClient);
    }
    
    public ArrayList<TradingClient> getActiveClients() {
    	return activeClients;
    }
    
    // remove client method

    /**
     * Runs all trades created so far, returns ResultFactory storing all 
     */
    public ResultFactory runTrades () {
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

        
        DataFetcher fetcher = new DataFetcher(); //data fetcher to get coin prices
        
        
        for (int i = 0; i < this.activeClients.size(); i++) { //loops through all brokers
        	String cryptoCoin = "";
        	Strategy stratToRun;
        	
        	StrategyFactory strat = new StrategyFactory(); //for creating new strategy
        	int quantity = 0; //amount of coin to trade
        	TradeResult clientResult = null; //buy, sell, or fail trade
        	
        	
            HashMap<String, Float> clientCoinPrices = new HashMap<String, Float>();
        
            for (String clientCoins : this.activeClients.get(i).getCoins()) { // for every coin of a specific client
            	
            	//converting the Symbols into strings that the API can read
            	if (clientCoins.equals("BTC")){ 
            		clientCoins = "bitcoin";
            	}
            	else if (clientCoins.equals("ETH")) {
            		clientCoins = "ethereum";
            	}
            	else if (clientCoins.equals("USDT")) {
            		clientCoins = "tether";
            	}
          
            	//stores the coin names and prices into a hash map
            	clientCoinPrices.put(clientCoins, (float)fetcher.getPriceForCoin(clientCoins, getDate()));
            }
            
            //calls factory for strategy and gets all information based on that strategy
            stratToRun = strat.createStrategy(this.activeClients.get(i).strategy());
            cryptoCoin = stratToRun.getCoinName();
            clientResult = stratToRun.executeStrategy(this.activeClients.get(i).strategy(), clientCoinPrices);
            quantity = stratToRun.getQuantity();
            
            
            String tempCoin = "";
            //for getting the price of the coin that will be traded
            if (cryptoCoin.equals("BTC")){
            	tempCoin = "bitcoin";
        	}
        	else if (cryptoCoin.equals("ETH")) {
        		tempCoin = "ethereum";
        	}
        	else if (cryptoCoin.equals("USDT")) {
        		tempCoin = "tether";
        	}
           
            clientCoinPrices.put(tempCoin, (float)fetcher.getPriceForCoin(tempCoin, getDate()));
            results.createResult(this.activeClients.get(i).getName(), this.activeClients.get(i).strategy(), cryptoCoin, clientResult, quantity, clientCoinPrices.get(tempCoin), getDate());
            
        } return results; // returns class holding all results
    }
    
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return (formatter.format(date));
    }
    
}