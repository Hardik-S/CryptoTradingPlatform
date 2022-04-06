package cryptoTrader.main;
import java.util.HashMap;

public class StrategyFactory {

	public Strategy createStrategy(String strategyname) {
		
		if (strategyname.equals("Strategy-A")) return new Strategy1();
		else if (strategyname.equals("Strategy-B")) return new Strategy2();
		else if (strategyname.equals("Strategy-C")) return new Strategy3();
		else {
		    return new Strategy4();
		}
	}

	
}
