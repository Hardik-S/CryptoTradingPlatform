package Strategies;

import java.util.HashMap;
import cryptoTrader.main.TradeResult;



public class Strategy2 extends Strategy{
	public Strategy2() {
		
	}
	//@override
	public TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices) {
		if ((prices.size() < 3) || !((prices.containsKey("bitcoin") && (prices.containsKey("ethereum") && (prices.containsKey("tether")))))) {
			return TradeResult.FAIL;
		} if (prices.get("bitcoin") > 10000) {
			if (prices.get("ethereum") > 100) {
				if (prices.get("tether") < 5) return TradeResult.SELL;
			}
		}

		return TradeResult.FAIL;
	}
	public int getQuantity() {
		return 200;
	}
	@Override
	public float getPrice(HashMap<String, Float> prices) {
		return prices.get("tether");
	}
	public String getCoinName() {
		return "USDT";
	}
	
}