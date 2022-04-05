package cryptoTrader.main;

import java.util.HashMap;

public class Strategy4 extends Strategy{
	public Strategy4(String strategyname, HashMap<String, Integer> prices) {
		executeStrategy(strategyname, prices);
	}
	//@override
	public TradeResult executeStrategy(String strategyname, HashMap<String, Integer> prices) {
		if (!prices.containsKey("ETH")) {
			return TradeResult.FAIL;
		} if (prices.get("ETH") < 200) {
				return TradeResult.TRADE;
		}

		return TradeResult.NOTRADE;
	
	}
}