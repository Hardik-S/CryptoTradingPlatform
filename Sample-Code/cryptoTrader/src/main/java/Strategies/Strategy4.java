package Strategies;

import java.util.HashMap;
import cryptoTrader.main.TradeResult;


public class Strategy4 extends Strategy{
	public Strategy4() {
	
	}
	//@override
	/**
	 * method to determine if the trade is successful according to the specifications of the strategy, takes in the list of coins and coin prices provided
	 * the coin list must include ethereum, the price of ethereum must be below 200
	 * @param strategyname the name of the strategy
	 * @param prices the list of coins and the prices for them according to the trade entry
	 * return SELL or FAIL according to the conditions of the strategy
	 */
	public TradeResult executeStrategy(String strategyname, HashMap<String, Float> prices) {
		if (!prices.containsKey("ethereum")) {
			return TradeResult.FAIL;
		} if (prices.get("ethereum") < 200) {
				return TradeResult.SELL;
		}

		return TradeResult.FAIL;
	
	}
	/*
	 * The quantity of coins traded associated with strategy 4 is 400
	 * @return the value 200
	 */
	public int getQuantity() {
		return 400;
	}
	/**
	 * takes in the list of coins and their prices and return the price of ethereum
	 */
	public float getPrice(HashMap<String, Float> prices) {
		return prices.get("ethereum");
	}
	/*
	 * Strategy 4 trades ETH
	 */
	public String getCoinName() {
		return "ETH";
	}
}