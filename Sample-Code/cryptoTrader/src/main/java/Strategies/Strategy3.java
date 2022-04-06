package Strategies;

import java.util.HashMap;
import cryptoTrader.main.TradeResult;



public class Strategy3 extends Strategy{
	public Strategy3() {
		
	}
	//@override
	/**
	 * method to determine if the trade is successful according to the specifications of the strategy, takes in the list of coins and coin prices provided
	 * the coin list must include bitcoin, the price of bitcoin must be below 1000
	 * @param strategyname the name of the strategy
	 * @param prices the list of coins and the prices for them according to the trade entry
	 * return BUY or FAIL according to the conditions of the strategy
	 */
	public TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices) {
	
		if (!prices.containsKey("bitcoin")) {
			return TradeResult.FAIL;
		} if (prices.get("bitcoin") < 1000) {
				return TradeResult.BUY;
			
		}

		return TradeResult.FAIL;
	}
	/*
	 * The quantity of coins traded associated with strategy 3 is 300
	 * @return the value 200
	 */
	public int getQuantity() {
		return 300;
	}
	/**
	 * takes in the list of coins and their prices and return the price of bitcoin
	 */
	public float getPrice(HashMap<String, Float> prices) {
		return prices.get("bitcoin");
	}
	/*
	 * Strategy 3 trades BTC
	 */
	public String getCoinName() {
		return "BTC";
	}
}