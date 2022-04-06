package Strategies;
import java.util.HashMap;

public class StrategyFactory {

	/**
	 * a factory method which calls on different strategy classes depending on the strategyname entered
	 * @param strategyname the name of the strategy the user selected
	 * @return a Strategy object according to the input strategyname
	 */
	public Strategy createStrategy(String strategyname) {
		
		if (strategyname.equals("Strategy-A")) return new Strategy1();
		else if (strategyname.equals("Strategy-B")) return new Strategy2();
		else if (strategyname.equals("Strategy-C")) return new Strategy3();
		else {
		    return new Strategy4();
		}
	}

	
}
