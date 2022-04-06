package Strategies;

import java.util.HashMap;

import cryptoTrader.main.TradeResult;

/**
 * 
 * @author Hunter Terpstra and Judy Zhu
 *
 */

public abstract class Strategy {
 
    /**
     * create an abstract parent class called strategy and create an abstract method called 
     * executeStrategy which will be present and different in all child classes
     * @param strategyname specifies which strategy is called by the user
     * @param prices a list of coin names and their prices fetched from the API
     * @return one of three trade results: BUY, SELL or FAIL
     */
    public abstract TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices);

    @Override
    public String toString () {
        String buffer = "";
        
        return (buffer);
    }
    /*
     * abstract methods which the quantity of the coin trades, the price of the coin when executed
     * and the name of the coin traded
     */
    public abstract int getQuantity();
    public abstract float getPrice(HashMap<String, Float> prices);
    public abstract String getCoinName();

}