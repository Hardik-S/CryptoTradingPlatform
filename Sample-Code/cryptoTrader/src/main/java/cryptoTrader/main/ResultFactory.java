package cryptoTrader.main;

import java.util.ArrayList;

public class ResultFactory {

    private ArrayList<Result> resultList;

    public ResultFactory () {
        this.resultList = new ArrayList<Result>();
    }

    public Result createResult (String resultTrader, String resultStrategy, String resultCoin, TradeResult action, float price, long date) {
        Result trade = new Result(resultTrader, resultStrategy, resultCoin, action, price, date);
        this.resultList.add(trade);
        return (trade);
    }
}
