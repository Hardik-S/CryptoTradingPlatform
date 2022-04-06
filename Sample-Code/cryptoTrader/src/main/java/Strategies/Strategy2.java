package Strategies;

import java.util.HashMap;
import cryptoTrader.main.TradeResult;



public class Strategy2 extends Strategy{
	public Strategy2() {
		
	}
	//@override
	
	/**
	 * method to determine if the trade is successful according to the specifications of the strategy, takes in the list of coins and coin prices provided
	 * the coin list must include bitcoin and ethereum, the price of bitcoin must be above 10000 and ethereum must be above 100
	 * @param strategyname the name of the strategy
	 * @param prices the list of coins and the prices for them according to the trade entry
	 * return SELL or FAIL according to the conditions of the strategy
	 */
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
	/*
	 * The quantity of coins traded associated with strategy 2 is 200
	 * @return the value 200
	 */
	public int getQuantity() {
		return 200;
	}
	@Override
	/**
	 * takes in the list of coins and their prices and return the price of tether
	 */
	public float getPrice(HashMap<String, Float> prices) {
		return prices.get("tether");
	}
	/*
	 * Strategy 2 trades USDT
	 */
	public String getCoinName() {
		return "USDT";
	}
	
}