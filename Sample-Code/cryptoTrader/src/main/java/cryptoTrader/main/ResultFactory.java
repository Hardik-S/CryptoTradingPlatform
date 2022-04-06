package cryptoTrader.main;

import java.util.ArrayList;

public class ResultFactory {

    private ArrayList<Result> resultList;

    public ResultFactory () {
        this.resultList = new ArrayList<Result>();
    }

    public Result createResult (String resultTrader, String resultStrategy, String resultCoin, TradeResult action, int quantity, float price, String date) {
        Result trade = new Result(resultTrader, resultStrategy, resultCoin, action, quantity, price, date);
        this.resultList.add(trade);
        return (trade);
    }
    
    public ArrayList<String[]> getSummary () {
    	ArrayList<String[]> entries = new ArrayList<String[]>();
    	for (Result i : resultList) entries.add(i.getResult());
    	return entries;
    }

    public ArrayList<Result> getResults() {
    	return resultList;
    }
    
    public Object[][] getResultObject(){
    	
    	int size = resultList.size();
    	Object[][] resultObj = new Object[size][7];
    	
    	for (int i=0;i<size;i++) {
    		Result tempResult = resultList.get(i);
    		
    		
    		resultObj[i][0] = tempResult.getTrader();
    		resultObj[i][1] = tempResult.getStrategy();
    		resultObj[i][2] = tempResult.getCryptocoin();
    		resultObj[i][3] = tempResult.getAction();
    		if (tempResult.getAction().equals("Fail")) {
    			resultObj[i][4] = "null";
        		resultObj[i][5] = "null";
    		}
    		else {
    			resultObj[i][4] = tempResult.getQuantity();
        		resultObj[i][5] = tempResult.getPrice();
    		}
    		
    		resultObj[i][6] = tempResult.getDate();
    	}

    	return resultObj;
    }
}

}

