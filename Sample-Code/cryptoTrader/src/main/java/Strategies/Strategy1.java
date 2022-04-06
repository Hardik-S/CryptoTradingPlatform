package Strategies;

import java.util.HashMap;
import cryptoTrader.main.TradeResult;

/**
 * 
 * @author Hunter Terpstra and Judy Zhu
 *
 */

public class Strategy1 extends Strategy{
	public Strategy1() {
		
	}
	@Override
	/**
	 * method to determine if the trade is successful according to the specifications of the strategy, takes in the list of coins and coin prices provided
	 * the coin list entry must contain bitcoin and ethereum, the price of bitcoin must be above 100 and ethereum must be above 1000
	 * @param strategyname the name of the strategy
	 * @param prices the list of coins and the prices for them according to the trade entry
	 * return BUY or FAIL according to the conditions of the strategy
	 */
	public TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices) {
		if ((prices.size() < 2) || !((prices.containsKey("bitcoin") && (prices.containsKey("ethereum"))))) {
			return TradeResult.FAIL;
		} if (prices.get("bitcoin") > 100) {
			
			if (prices.get("ethereum") > 1000) {
				
				return TradeResult.BUY;
			}
		}

		return TradeResult.FAIL;
	}
	/*
	 * The quantity of coins traded associated with strategy 1 is 100
	 * @return the value 100
	 */
	public int getQuantity() {
		return 100;
	}
	/**
	 * takes in the list of coins and their prices and return the price of bitcoin
	 */
	public float getPrice(HashMap<String, Float> prices) {
		return prices.get("bitcoin");
	}
	/*
	 * Strategy 1 trades Bitcoin
	 */
	public String getCoinName() {
		return "BTC";
	}
}