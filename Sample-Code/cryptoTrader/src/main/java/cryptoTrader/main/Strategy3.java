package cryptoTrader.main;

import java.util.HashMap;

public class Strategy3 extends Strategy{
	public Strategy3(String strategyname, HashMap<String, Integer> prices) {
		executeStrategy(strategyname, prices);
	}
	//@override
	public TradeResult executeStrategy(String strategyname, HashMap<String, Integer> prices) {
		if (!prices.containsKey("BTC")) {
			return TradeResult.FAIL;
		} if (prices.get("BTC") < 1000) {
				return TradeResult.TRADE;
			
		}

		return TradeResult.NOTRADE;
	}
	
}