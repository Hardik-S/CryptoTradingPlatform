package cryptoTrader.main;

import java.util.HashMap;

enum TradeResult { TRADE, NOTRADE, FAIL }

public abstract class Strategy {
    private String[] usedCoins;
    
    public abstract void executeStrategy(String strategyname, HashMap<String, Integer> prices);

    @Override
    public String toString () {
        String buffer = "";
        
        return (buffer);
    }

}