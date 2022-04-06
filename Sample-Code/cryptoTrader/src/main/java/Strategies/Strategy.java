package Strategies;

import java.util.HashMap;

import cryptoTrader.main.TradeResult;


public abstract class Strategy {
 
    
    public abstract TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices);

    @Override
    public String toString () {
        String buffer = "";
        
        return (buffer);
    }
    public abstract int getQuantity();
    public abstract float getPrice(HashMap<String, Float> prices);

}