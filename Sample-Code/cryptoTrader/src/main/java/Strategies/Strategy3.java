package Strategies;

import java.util.HashMap;
import cryptoTrader.main.TradeResult;



public class Strategy3 extends Strategy{
	public Strategy3() {
		
	}
	//@override
	public TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices) {
		System.out.println("EOAIFUJGHNEG");
		if (!prices.containsKey("bitcoin")) {
			return TradeResult.FAIL;
		} if (prices.get("bitcoin") < 1000) {
				return TradeResult.BUY;
			
		}

		return TradeResult.FAIL;
	}
	public int getQuantity() {
		return 300;
	}
	public float getPrice(HashMap<String, Float> prices) {
		return prices.get("bitcoin");
	}
}