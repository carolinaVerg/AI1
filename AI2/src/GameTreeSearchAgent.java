import java.util.Iterator;
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
		currentMin=minValue(move,otherAgentState, 0, 0,1);
		if(currentMin>MaxOfMin) {	// saving the greatest value of all
			MaxOfMin=currentMin;
			bestNextState=move;
		}
	}
	
	return createAction(bestNextState);
}

	private Action createAction(AgentState state) {
		int peopleSaved = this.State.getPeopleToSave() - state.getPeopleToSave();
		return new Action(state.getDeadLine(),peopleSaved,state.getVertex());
	}

	private LinkedList<AgentState> generateNextMoveState(AgentState stateToGen) {
	LinkedList<AgentState> movesList = new LinkedList<>();
	Iterator<Pair> movesIter = stateToGen.getVertex().getEdges().listIterator(0);
	Pair currentNeighbor;
	while(movesIter.hasNext()){
		currentNeighbor = movesIter.next();
		movesList.add(buildState(currentNeighbor,stateToGen));
	}
	movesList.add(buildState(null,stateToGen)); // add noOP
	return movesList;
}

	private AgentState buildState(Pair currentNeighbor, AgentState prevState) {
		int deadline = prevState.getDeadLine() - evalCost(currentNeighbor.getWeight(),main.kConst,prevState.getPeopleOn());
		int peopleNotSaved = prevState.getPeopleToSave();
		int peopleOn = prevState.getPeopleOn();
		if(currentNeighbor.getVertex().isIsShelter()){
			peopleOn = 0;
			if(deadline>=0){
				peopleNotSaved = prevState.getPeopleToSave() - prevState.getPeopleOn();
			}
		}
		else{
			peopleOn = peopleOn + currentNeighbor .getVertex().getPeople();
		}
		AgentState returnAgentState = new AgentState(currentNeighbor.getVertex(),deadline,peopleNotSaved,prevState.getVertices());
		returnAgentState.getVertices().get(currentNeighbor.getVertex().getId()).setPeople(0);
		return returnAgentState;
	}

	private int maxValue(AgentState maxState,AgentState minState, int bestMax, int bestMin,int cutoffDepth) {  //returns a heuristicValue of the cutoff
	if(cutOff(cutoffDepth))
		return heuristicFun(maxState);
	int MaxOfMin=Integer.MIN_VALUE;
	LinkedList<AgentState> moves=generateNextMoveState(maxState);
	int currentMin=0;
	for(AgentState move: moves) {
		currentMin=minValue(move,minState, bestMax, bestMin,cutoffDepth+1);
		if(currentMin>MaxOfMin) {
			MaxOfMin=currentMin;
		}
		if(MaxOfMin>bestMin)
			return MaxOfMin;
		if(MaxOfMin>bestMax)
			bestMax=MaxOfMin;
	}
	return MaxOfMin;
	
}
private int minValue(AgentState maxState,AgentState minState, int bestMax, int bestMin, int cutoffDepth) {
	if(cutOff(cutoffDepth))
		return heuristicFun(minState);
	int MinOfMax=Integer.MAX_VALUE;
	LinkedList<AgentState> moves=generateNextMoveState(minState);
	int currentMax=0;
	for(AgentState move: moves) {
		currentMax=maxValue(maxState,move, bestMax, bestMin,cutoffDepth+1);
		if(currentMax<MinOfMax) {
			MinOfMax=currentMax;
		}
		if(MinOfMax<bestMin)
			return MinOfMax;
		if(MinOfMax<bestMax)
			bestMax=MinOfMax;
	}
	return MinOfMax;

}
private int heuristicFun(AgentState state) {
	// TODO Auto-generated method stub
	return 0;
}
private boolean cutOff(int cutoffDepth) {
	// TODO Auto-generated method stub
	return false;
}


}
