package cryptoTrader.main;

import cryptoTrader.main.Strategy;
import java.util.Date;

enum TradeResult { TRADE, NOTRADE, FAIL }

public class Result {
    private String trader; // broker associated with trade
    private Strategy strategy; // strategy used for trade
    private String cryptocoin; // coin traded (or not traded)
    private TradeResult action; // direction of trade: 0 for sell, 1 for buy, -1 for no action taken
    private float price; // price of cryptocoin at time of trade
    private long date; // millisecond epoch time of trade signature

    public Result (String trader, Strategy strategy, String cryptocoin, TradeResult action, float price, long date) {
        this.trader = trader;
        this.strategy = strategy;
        this.cryptocoin = cryptocoin;
        this.action = action;
        this.price = price;
        this.date = date;
    }

    public String[] getResult () {
        String[] buffer = new String[6];
        buffer[0] = this.trader;
        buffer[1] = this.strategy.toString();
        buffer[2] = this.cryptocoin;
        switch (this.action) {
            case TRADE:
                buffer[3] = "Traded";
                break;
            case NOTRADE:
                buffer[3] = "Not traded";
                break;
            case FAIL:
                buffer[3] = "Coins N/A";
                break;
        }
        buffer[4] = Float.toString(this.price);
        buffer[5] = Long.toString(date);
        return (buffer);
    }

}
