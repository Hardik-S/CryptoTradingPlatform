package cryptoTrader.main;

import java.util.HashMap;

public class Strategy2 extends Strategy{
	public Strategy2(String strategyname, HashMap<String, Integer> prices) {
		executeStrategy(strategyname, prices);
	}
	//@override
	public TradeResult executeStrategy(String strategyname, HashMap<String, Integer> prices) {
		if ((prices.size() < 3) || !((prices.containsKey("BTC") & (prices.containsKey("ETH") & (prices.containsKey("USDT")))))) {
			return TradeResult.FAIL;
		} if (prices.get("BTC") > 10000) {
			if (prices.get("ETH") > 100) {
				if (prices.get("USDT") > 5) return TradeResult.TRADE;
			}
		}

		return TradeResult.NOTRADE;
	}
	
}