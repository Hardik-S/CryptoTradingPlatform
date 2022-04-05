package cryptoTrader.main;

import java.util.HashMap;

public class Strategy1 extends Strategy{
	public Strategy1(String strategyname, HashMap<String, Integer> prices) {
		executeStrategy(strategyname, prices);
	}
	//@override
	public TradeResult executeStrategy(String strategyname, HashMap<String, Integer> prices) {
		if ((prices.size() < 2) || !((prices.containsKey("BTC") & (prices.containsKey("ETH"))))) {
			return TradeResult.FAIL;
		} if (prices.get("BTC") > 100) {
			if (prices.get("ETH") > 1000) {
				return TradeResult.TRADE;
			}
		}

		return TradeResult.NOTRADE;
	}
}