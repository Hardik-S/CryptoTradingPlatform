package Results;

import java.util.ArrayList;

/**
 * 
 * @author Hunter Terpstra
 *
 */

public class ActionLogHistory {

	private ResultFactory resultListHistory;

    public ActionLogHistory() {
        this.resultListHistory = new ResultFactory();
    }
    
    public ResultFactory getHistory() {
    	return resultListHistory;
    }
    
	public void addToResultHistory(ResultFactory newResult) {
		
		for (Result result: newResult.getResults()) {
			resultListHistory.getResults().add(result);
		}
		
	}
	
}
