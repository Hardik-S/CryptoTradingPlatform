package cryptoTrader.main;

import java.util.HashMap;

//enum TradeResult { TRADE, NOTRADE, FAIL }

public abstract class Strategy {
    private String[] usedCoins;
    
    public abstract TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices);

    @Override
    public String toString () {
        String buffer = "";
        
        return (buffer);
    }
    public abstract int getQuantity();
    public abstract float getPrice(HashMap<String, Float> prices);

}