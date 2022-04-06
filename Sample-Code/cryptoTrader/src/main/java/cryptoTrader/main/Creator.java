package cryptoTrader.main;

import java.util.HashMap;

public abstract class Creator {
	/**
	 * Abstract class for strategy factories
	 * @param String name of strategy
	 * @param Key-value pairs of all prices of coins subscribed
	 * @return
	 */
	public abstract Strategy factoryMethod(String strategyname, HashMap<String, Float> prices);
}
