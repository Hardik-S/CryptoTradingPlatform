package Results;

import java.util.Date;

import Strategies.Strategy;
import cryptoTrader.main.TradeResult;



public class Result {
    private String trader; // broker associated with trade
    private String strategy; // strategy used for trade
    private String cryptocoin; // coin traded (or not traded)
    private TradeResult action; // direction of trade: 0 for sell, 1 for buy, -1 for no action taken
    private float price; // price of cryptocoin at time of trade
    private int quantity;
    private String date; // millisecond epoch time of trade signature

    public Result (String trader, String strategy, String cryptocoin, TradeResult action, int quantity, float price, String date) {
        this.trader = trader;
        this.strategy = strategy;
        this.cryptocoin = cryptocoin;
        this.action = action;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public String[] getResult () {
        String[] buffer = new String[7];
        buffer[0] = this.trader;
        buffer[1] = this.strategy.toString();
        buffer[2] = this.cryptocoin;
        switch (this.action) {
            case BUY:
                buffer[3] = "Buy";
                break;
            case SELL:
                buffer[3] = "Sell";
                break;
            case FAIL:
                buffer[3] = "Fail";
                break;
        }
        buffer[4] = Integer.toString(quantity);
        buffer[5] = Float.toString(this.price);
        buffer[6] = date;
        return (buffer);
    }
    /**
     * getTrader method
     * @return trader name
     */
    public String getTrader() {
        return this.trader;
    }

    /**
     * getStrategy method
     * @return strategy name
     */
    public String getStrategy() {
        return this.strategy;
    }

    /**
     * getCryptocoin method
     * @return coin name
     */
    public String getCryptocoin() {
        return this.cryptocoin;
    }

    /**
     * getAction method
     * @return action status
     */
    public String getAction() {
    	switch (this.action) {
        case BUY:
            return "Buy";
            
        case SELL:
        	return "Sell";
      
        case FAIL:
        	return "Fail";
    }
    	return "Fail";
    }

    /**
     * getQuantity method
     * @return number of coins traded
     */
    public String getQuantity() {
 
        return Integer.toString(this.quantity);
    }

    /**
     * getPrice method
     * @return price of coin
     */
    public String getPrice() {
        return Float.toString(this.price);
    }

    /**
     * getDate method
     * @return date of trade
     */
    public String getDate() {
        return this.date;
    }
 

}