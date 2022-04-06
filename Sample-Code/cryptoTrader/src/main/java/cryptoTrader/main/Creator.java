package cryptoTrader.main;

import java.util.HashMap;

public abstract class Creator {
	
	public abstract Strategy factoryMethod(String strategyname, HashMap<String, Float> prices);
}
