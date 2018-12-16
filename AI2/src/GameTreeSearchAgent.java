import java.util.Iterator;
import java.util.LinkedList;

public class GameTreeSearchAgent extends Agents {

    public GameTreeSearchAgent(AgentState state) {
        super(state);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Action agentFunc(int deadLine, int peopleToSave, AgentState otherAgent, LinkedList<Vertex> vertices) {
        this.getState().setDeadLine(deadLine);
        this.getState().setPeopleToSave(peopleToSave);
        this.getState().setVertices(vertices);
        AgentState bestNextState = null;
        AgentState otherAgentCopy = new AgentState(0, 0, 0, vertices);
        Action actionToRet = null;
        ;
        otherAgentCopy.updateMyState(otherAgent);
        switch (main.gameType) {
            case 1:
                bestNextState = alphaBetaDecision(deadLine, peopleToSave, otherAgentCopy,1);
                break;
            case 2:
                bestNextState = alphaBetaDecision(deadLine, peopleToSave, otherAgentCopy,2);
                break;
            case 3:
                bestNextState = alphaBetaDecision(deadLine, peopleToSave, otherAgentCopy,3);
                break;
            default:

        }
        actionToRet = createAction(bestNextState);
        this.getState().updateMyState(bestNextState);
        return actionToRet;

    }

    public AgentState alphaBetaDecision(int deadLine, int peopleToSave, AgentState otherAgentState,int mode) {
        LinkedList<AgentState> moves = generateNextMoveState(this.getState());
        int MaxOfMin = Integer.MIN_VALUE;
        int currentValue = 0;
        AgentState bestNextState = null;
        AgentState curentMove = new AgentState(0, 0, 0, this.getState().getVertices());
        for (AgentState move : moves) {
            AgentState tempOtherState = new AgentState(0,0,0,otherAgentState.getVertices());
            tempOtherState.updateMyState(otherAgentState);
            tempOtherState.updateStateOp(move);
            curentMove.updateMyState(move);
            int otherAgentSavedP = tempOtherState.getPeopleSaved();
            if(mode==1){
            	currentValue = oppMinMax(curentMove,tempOtherState,mode, Integer.MIN_VALUE,
                        Integer.MAX_VALUE, 1);
            }
            else {
            	currentValue = oppMinMax(curentMove,tempOtherState,mode, Integer.MIN_VALUE,
                        Integer.MIN_VALUE, 1);
            }
            if (currentValue >= MaxOfMin) {
                if(currentValue==MaxOfMin && mode==2){
                    if(otherAgentSavedP < main.agentOSimSavedP){
                        MaxOfMin = currentValue;
                        bestNextState = move;
                    }
                }
                else{
                    MaxOfMin = currentValue;
                    bestNextState = move;
                }

            }
//            if (currentValue > MaxOfMin) {    // saving the greatest value of all
//                MaxOfMin = currentValue;
//                bestNextState = move;
//            }
        }
        return bestNextState;
    }

    private int oppMinMax(AgentState curentMove, AgentState otherAgentState, int mode,
                          int bestValue1, int bestValue2, int cutoffDepth) {
        int currentValue;
        if (mode==1) {
        	currentValue = minValue(curentMove, otherAgentState,
                    bestValue1, bestValue2, cutoffDepth);
        }
        else {
        	currentValue = maxValue(otherAgentState,curentMove,
                    bestValue1, bestValue2, cutoffDepth,mode,true);
        }
        
        return currentValue;
    }

    private int maxValue(AgentState maxState, AgentState minState, int bestMax, int bestMin, int cutoffDepth, int mode,boolean isCurrent) {  //returns a heuristicValue of the cutoff
        if (cutOff(cutoffDepth) || maxState.isGoalState()) {
                return evalFun(maxState, minState, mode);
        }
        int MaxOfMin = Integer.MIN_VALUE;
        LinkedList<AgentState> moves = generateNextMoveState(maxState);
        int currentValue = 0;
        for (AgentState move : moves) {
        	if(move.getDeadLine()>=0) {
        		AgentState tempMinState = new AgentState(0,0,0,minState.getVertices());
        		tempMinState.updateMyState(minState);
        		tempMinState.updateStateOp(move);
        		// min-max OR max-max
        		if(mode==1){
        			currentValue = minValue(move, tempMinState, bestMax,
        					bestMin, cutoffDepth + 1);
        		}
        		else{
        			currentValue = maxValue(tempMinState, move,  bestMax,
        					bestMin, cutoffDepth + 1,mode,!isCurrent);
        		}
        	}
        	else {
        		currentValue = evalFun(maxState, minState, mode);
        	}
            //finding max value of opp
            if (currentValue >= MaxOfMin) {
                    MaxOfMin = currentValue;
            }
            if(mode==1) {
                if (MaxOfMin > bestMin)
                    return MaxOfMin;
                if (MaxOfMin > bestMax)
                    bestMax = MaxOfMin;
            }
        }
        return MaxOfMin;

    }

    private int minValue(AgentState maxState, AgentState minState, int bestMax, int bestMin, int cutoffDepth) {
        if (cutOff(cutoffDepth) || minState.isGoalState())
            return evalFun(maxState,minState,1);
        int MinOfMax = Integer.MAX_VALUE;
        LinkedList<AgentState> moves = generateNextMoveState(minState);
        int currentMax = 0;
        for (AgentState move : moves) {
        	if(move.getDeadLine()>=0) {
        		AgentState tempMaxState = new AgentState(0,0,0,maxState.getVertices());
        		tempMaxState.updateMyState(maxState);
            	tempMaxState.updateStateOp(move);
            	currentMax = maxValue(tempMaxState, move, bestMax, bestMin, cutoffDepth + 1,1,false);
        	}
        	else {
        		currentMax=evalFun(maxState,minState,1);
        	}
            if (currentMax < MinOfMax) {
                MinOfMax = currentMax;
            }
            if (MinOfMax < bestMax)
                return MinOfMax;
            if (MinOfMax < bestMin)
                bestMin = MinOfMax;
        }
        return MinOfMax;

    }

    private Action createAction(AgentState nextState) {
        int peopleSaved = this.State.getPeopleToSave() - nextState.getPeopleToSave();
        return new Action(nextState.getDeadLine(), peopleSaved, nextState.getVertex());
    }

    private LinkedList<AgentState> generateNextMoveState(AgentState stateToGen) {
        LinkedList<AgentState> movesList = new LinkedList<>();
        Iterator<Pair> movesIter = stateToGen.getVertex().getEdges().listIterator(0);
        Pair currentNeighbor;
        while (movesIter.hasNext()) {
            currentNeighbor = movesIter.next();
            movesList.add(buildState(currentNeighbor, stateToGen));
        }
        movesList.add(buildState(null, stateToGen)); // add noOP
        return movesList;
    }

    private int evalFun(AgentState stateCurrent,AgentState stateOther, int mode) {
    	int to_ret;
        if(mode==1) {
            to_ret = (2 * stateCurrent.getPeopleSaved() + peopleCanBeRescude(stateCurrent))-
            		(2 * stateOther.getPeopleSaved() + peopleCanBeRescude(stateOther));         
        }
        else if(mode==2) {
            to_ret = (200*stateCurrent.getPeopleSaved()+peopleCanBeRescude(stateCurrent)
			+2*stateOther.getPeopleSaved()+peopleCanBeRescude(stateOther));
        }
        else
        	 to_ret= (2*stateCurrent.getPeopleSaved()+peopleCanBeRescude(stateCurrent)
        			+2*stateOther.getPeopleSaved()+peopleCanBeRescude(stateOther));
        return to_ret;
    }

    private int peopleCanBeRescude(AgentState state) {
        if (State.isGoalState()) {
            return 0;
        } else {
        	AgentState stateCopy = new AgentState(0, 0, 0, state.getVertices());
        	stateCopy.updateMyState(state);
            int peopleCanBeRescude = 0;
            int deadLine = state.deadLine;
            GameTreeSearchAgent agent = new GameTreeSearchAgent(stateCopy);
            AgentState newPeopleState = null;
            AgentState newShelterState = null;
            Iterator<Vertex> iter = state.getVertices().listIterator(0);
            Vertex currentVertex;
            BinaryHeap<TreeVertex> fring;
            while (iter.hasNext()) {
                fring = new BinaryHeap<>();
                fring.add(new TreeVertex(stateCopy, null, 0));
                newPeopleState = null;
                currentVertex = iter.next();
                if (currentVertex.getPeople() > 0) {
                    newPeopleState = agent.TreeSearch(fring, "id", currentVertex.getId());  //serch for people
                    if (newPeopleState != null){
                        fring = new BinaryHeap<>();
                        fring.add(new TreeVertex(newPeopleState, null, 0));
                        newShelterState = agent.TreeSearch(fring, "shelter", 0);
                        if (newShelterState != null && !(newShelterState.getDeadLine() < 0))    // people canot be saved
                        	peopleCanBeRescude = peopleCanBeRescude + currentVertex.getPeople();
                    }
                }


            }
            return peopleCanBeRescude;
        }
    }

    private boolean cutOff(int cutoffDepth) {
        return (cutoffDepth > 10);
    }


}
