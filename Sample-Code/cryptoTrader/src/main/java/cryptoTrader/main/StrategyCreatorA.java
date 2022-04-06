package cryptoTrader.main;
import java.util.HashMap;

public class StrategyCreatorA extends Creator{

	public Strategy factoryMethod(String strategyname, HashMap<String, Float> prices) {
		return new Strategy1(strategyname, prices);
	}

	
}
