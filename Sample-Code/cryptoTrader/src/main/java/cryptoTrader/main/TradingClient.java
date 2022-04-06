package cryptoTrader.main;

import java.util.HashMap;

public class TradingClient {
    private String clientName;
    private String[] coinList;
    private String strategy;
    
    /**
     * Stores data for one broker client
     * @param name
     * @param coinList
     * @param strategy
     */
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

}
