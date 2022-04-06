package cryptoTrader.main;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.namespace.QName;

import Login.LoginServer;
import Login.LoginUI;
import cryptoTrader.utils.DataFetcher;

import java.util.HashMap;

public class Controller {
    private ArrayList<TradingClient> activeClients;
    private HashSet<String> coins;
    private static Controller instance;

    public Controller () {
        this.activeClients = new ArrayList<TradingClient>();
        this.coins = new HashSet<String>();
    }
    
//    public static SubsystemUI getInstance() {
//		
//		if (instance == null)
//			instance = new SubsystemUI();
//
//		return instance;
//		
//	}

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

        HashMap<String, Float> coinPrices = new HashMap<String, Float>(); // API returns here <-- (temp empty)
        
        DataFetcher fetcher = new DataFetcher();
        
        
        for (int i = 0; i < this.activeClients.size(); i++) {
        	String cryptoCoin = "";
        	Strategy stratToRun;
        	
        	StrategyFactory strat = new StrategyFactory();
        	int quantity = 0;
        	TradeResult clientResult = null;
        	
        	
            HashMap<String, Float> clientCoinPrices = new HashMap<String, Float>();
        
            for (String clientCoins : this.activeClients.get(i).getCoins()) { // for every coin of a specific client
            	
            	if (clientCoins.equals("BTC")){
            		clientCoins = "bitcoin";
            	}
            	else if (clientCoins.equals("ETH")) {
            		clientCoins = "ethereum";
            	}
            	else if (clientCoins.equals("USDT")) {
            		clientCoins = "tether";
            	}
          
          
            	clientCoinPrices.put(clientCoins, (float)fetcher.getPriceForCoin(clientCoins, getDate()));
            	System.out.println("CLient coin: " + clientCoins);
            }
            
            if (this.activeClients.get(i).strategy().equals("Strategy-A")) {
            	cryptoCoin = "BTC";
            	//creator = new StrategyCreatorA();
            	stratToRun = strat.createStrategy(this.activeClients.get(i).strategy());
            	//stratToRun = creator.factoryMethod(this.activeClients.get(i).strategy(), clientCoinPrices);
            	clientResult = stratToRun.executeStrategy(this.activeClients.get(i).strategy(), clientCoinPrices);
            	quantity = stratToRun.getQuantity();
            }
            else if (this.activeClients.get(i).strategy().equals("Strategy-B")) {
            	cryptoCoin = "USDT";
            	//creator = new StrategyCreatorB();
            	//stratToRun = creator.factoryMethod(this.activeClients.get(i).strategy(), clientCoinPrices);
            	stratToRun = strat.createStrategy(this.activeClients.get(i).strategy());
            	clientResult = stratToRun.executeStrategy(this.activeClients.get(i).strategy(), clientCoinPrices);
            	quantity = stratToRun.getQuantity();
            }
            else if (this.activeClients.get(i).strategy().equals("Strategy-C")) {
            	cryptoCoin = "BTC";
            	stratToRun = strat.createStrategy(this.activeClients.get(i).strategy());
            	//creator = new StrategyCreatorC();
            	//stratToRun = creator.factoryMethod(this.activeClients.get(i).strategy(), clientCoinPrices);
            	clientResult = stratToRun.executeStrategy(this.activeClients.get(i).strategy(), clientCoinPrices);
            	quantity = stratToRun.getQuantity();
            }
            else if ((this.activeClients.get(i).strategy().equals("Strategy-D"))) {
            	cryptoCoin = "ETH";
            	stratToRun = strat.createStrategy(this.activeClients.get(i).strategy());
            	//creator = new StrategyCreatorD();
            	//stratToRun = creator.factoryMethod(this.activeClients.get(i).strategy(), clientCoinPrices);
            	clientResult = stratToRun.executeStrategy(this.activeClients.get(i).strategy(), clientCoinPrices);
            	quantity = stratToRun.getQuantity();
            }
            
            String tempCoin = "";
            //TradeResult clientResult = this.activeClients.get(i).runStrategy(this.activeClients.get(i).strategy(), clientCoinPrices);
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