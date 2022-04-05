package cryptoTrader.main;

import java.util.HashMap;

public class TradingClient {
    private String clientName;
    private String[] coinList;
    private String strategy;
    
    public TradingClient (String name, String[] coinList, String strategy) {
        this.clientName = name; this.coinList = coinList; this.strategy = strategy;
    }

    public String name () {
        return (this.clientName);
    }
    public String[] getCoins () {
        return (this.coinList);
    }
    public String strategy () {
        return (this.strategy);
    }
    public TradeResult runStrategy (HashMap<String, Float> prices) {
        // call strategy and return, must clarify with Judy
        return (TradeResult.TRADE);
    }
}
