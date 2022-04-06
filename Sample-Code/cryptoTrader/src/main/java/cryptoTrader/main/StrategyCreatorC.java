package cryptoTrader.main;
import java.util.HashMap;

public class StrategyCreatorC extends Creator{

	public Strategy factoryMethod(String strategyname, HashMap<String, Float> prices) {
		return new Strategy3(strategyname, prices);
	}

	
}
