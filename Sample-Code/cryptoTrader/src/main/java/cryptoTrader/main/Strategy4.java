package cryptoTrader.main;

import java.util.HashMap;

public class Strategy4 extends Strategy{
	public Strategy4() {
	
	}
	//@override
	public TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices) {
		if (!prices.containsKey("ethereum")) {
			return TradeResult.FAIL;
		} if (prices.get("ethereum") < 200) {
				return TradeResult.SELL;
		}

		return TradeResult.FAIL;
	
	}
	public int getQuantity() {
		return 400;
	}
	public float getPrice(HashMap<String, Float> prices) {
		return prices.get("ethereum");
	}
}