package Results;

import java.util.ArrayList;

public class ActionLogHistory {

	private ArrayList<Result> resultListHistory;

    public ActionLogHistory() {
        this.resultListHistory = new ArrayList<Result>();
    }
    
    public ArrayList<Result> getHistory() {
    	return resultListHistory;
    }
    
	
	
}
