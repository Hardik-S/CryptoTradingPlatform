package cryptoTrader.main;

import java.util.HashMap;

public class TradingClient {
    private String clientName;
    private String[] coinList;
    private String strategy;
    
    public TradingClient (String name, String[] coinList, String strategy) {
        this.clientName = name; this.coinList = coinList; this.strategy = strategy;
    }

    public String getName () {
        return (this.clientName);
    }
    public String[] getCoins () {
        return (this.coinList);
    }
    public String strategy () {
        return (this.strategy);
    }
//    public TradeResult runStrategy (String stratName, HashMap<String, Float> prices) {
//        // call strategy and return, must clarify with Judy
//    	if (stratName.equals("Strategy-B")) {
//    		Strategy stratAlg = new Strategy2(stratName, prices);
//    		return stratAlg.executeStrategy(stratName, prices);
//    	}
//    	
//        return null;
//    }
}
