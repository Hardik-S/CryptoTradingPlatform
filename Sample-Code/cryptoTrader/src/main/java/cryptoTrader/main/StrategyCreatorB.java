package cryptoTrader.main;
import java.util.HashMap;

public class StrategyCreatorB extends Creator{

	public Strategy factoryMethod(String strategyname, HashMap<String, Float> prices) {
		return new Strategy2(strategyname, prices);
	}

	
}
