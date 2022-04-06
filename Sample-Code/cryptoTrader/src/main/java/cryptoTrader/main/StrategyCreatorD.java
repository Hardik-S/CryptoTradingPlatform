package cryptoTrader.main;
import java.util.HashMap;

public class StrategyCreatorD extends Creator{

	public Strategy factoryMethod(String strategyname, HashMap<String, Float> prices) {
		return new Strategy4(strategyname, prices);
	}

	
}
