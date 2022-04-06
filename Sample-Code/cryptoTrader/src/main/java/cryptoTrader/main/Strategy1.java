package cryptoTrader.main;

import java.util.HashMap;

public class Strategy1 extends Strategy{
	public Strategy1() {
		
	}
	@Override
	public TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices) {
		if ((prices.size() < 2) || !((prices.containsKey("bitcoin") && (prices.containsKey("ethereum"))))) {
			return TradeResult.FAIL;
		} if (prices.get("bitcoin") > 100) {
			System.out.println("Price of bitcoin: " + prices.get("bitcoin"));
			if (prices.get("ethereum") > 1000) {
				System.out.println("Price of eth: " + prices.get("ethereum"));
				return TradeResult.BUY;
			}
		}

		return TradeResult.FAIL;
	}
	public int getQuantity() {
		return 100;
	}
	
	public float getPrice(HashMap<String, Float> prices) {
		return prices.get("bitcoin");
	}
}