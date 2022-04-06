package Results;

import java.util.ArrayList;

import cryptoTrader.main.TradeResult;

/**
 * 
 * @author Hunter Terpstra and Ryan Chan
 *
 */

public class ResultFactory {

    private ArrayList<Result> resultList;
    /**
     * Initialise class variables
     */
    public ResultFactory () {
        this.resultList = new ArrayList<Result>();
    }

    /**
     * Creates instance of result with given data for storage and returns created result
     * @param resultTrader - name of trader that created the result
     * @param resultStrategy - name of strategy used for trade
     * @param resultCoin - name of coin traded
     * @param action - buy/sell/invalid
     * @param quantity - number of coins traded, if at all
     * @param price - price of coin at trade
     * @param date - epoch time of trade time
     * @return Result object
     */
    public Result createResult (String resultTrader, String resultStrategy, String resultCoin, TradeResult action, int quantity, float price, String date) {
        Result trade = new Result(resultTrader, resultStrategy, resultCoin, action, quantity, price, date);
        this.resultList.add(trade);
        return (trade);
    }
    /**
     * Creates summary of all result string arrays created so far
     * @return ArrayList of all results as string arrays
     */
    public ArrayList<String[]> getSummary () {
    	ArrayList<String[]> entries = new ArrayList<String[]>();
    	for (Result i : resultList) entries.add(i.getResult());
    	return entries;
    }
    /**
     * Creates summary of all result objects created so far
     * @return ArrayList of all result objects
     */
    public ArrayList<Result> getResults() {
    	return resultList;
    }
    
    /**
     * Turns all results into a 2d object array
     * @return array of results
     */
    public Object[][] getResultObject(){
    	
    	int size = resultList.size();
    	Object[][] resultObj = new Object[size][7]; // create array to hold all unique results, each holding all result values
    	
    	for (int i=0;i<size;i++) {
    		Result tempResult = resultList.get(i);
    		
    		
    		resultObj[i][0] = tempResult.getTrader();
    		resultObj[i][1] = tempResult.getStrategy();
    		resultObj[i][2] = tempResult.getCryptocoin();
    		resultObj[i][3] = tempResult.getAction();
    		if (tempResult.getAction().equals("Fail")) { // return null values if the trade was not made
    			resultObj[i][4] = "null";
        		resultObj[i][5] = "null";
    		}
    		else { // otherwise, return the stored value
    			resultObj[i][4] = tempResult.getQuantity();
        		resultObj[i][5] = tempResult.getPrice();
    		}
    		
    		resultObj[i][6] = tempResult.getDate();
    	}

    	return resultObj;
    }
}