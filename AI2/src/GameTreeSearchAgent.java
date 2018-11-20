import java.util.LinkedList;

public class GameTreeSearchAgent extends Agents{

	public GameTreeSearchAgent(AgentState state) {
		super(state);
		// TODO Auto-generated constructor stub
	}
@Override
	 public Action agentFunc(int deadLine, int peopleToSave,AgentState otherAgent) {
	       switch(main.gameType) {
	       case 1:
	    	   return alphaBetaDecision(deadLine, peopleToSave , otherAgent);
	       case 2:
	    	   break;
	       case 3:
	    	   break;
	    	   default:
	    		   
	       }
	       return null;
	    }
public Action alphaBetaDecision(int deadLine, int peopleToSave, AgentState otherAgentState) {
	LinkedList<AgentState> moves=generateNextMoveState(this.getState());
	int MaxOfMin=Integer.MIN_VALUE;
	int currentMin=0;
	AgentState bestNextState=null;
	for(AgentState move: moves) {
		currentMin=minValue(move,otherAgentState, 0, 0);
		if(currentMin>MaxOfMin) {
			MaxOfMin=currentMin;
			bestNextState=move;
		}
	}
	
	return null;
}
private LinkedList<AgentState> generateNextMoveState(AgentState startState) {
	// TODO Auto-generated method stub
	return null;
}
private int maxValue(AgentState maxState,AgentState minState, int BestMax, int BestMin) {  //returns a heuristicValue of the cutoff
	if(cutOff(this.getState()))
		return heuristicFun(this.getState());
	int MaxOfMin=Integer.MIN_VALUE;
	LinkedList<AgentState> moves=generateNextMoveState(maxState);
	int currentMin=0;
	for(AgentState move: moves) {
		currentMin=minValue(move,minState, 0, 0);
		if(currentMin>MaxOfMin) {
			MaxOfMin=currentMin;
		}
		if(MaxOfMin>BestMin)
			return MaxOfMin;
		if(MaxOfMin>BestMax)
			BestMax=MaxOfMin;
	}
	return MaxOfMin;
	
}
private int minValue(AgentState maxState,AgentState other, int BestMax, int BestMin) {
	return 0;
}
private int heuristicFun(AgentState state) {
	// TODO Auto-generated method stub
	return 0;
}
private boolean cutOff(AgentState state) {
	// TODO Auto-generated method stub
	return false;
}


}
