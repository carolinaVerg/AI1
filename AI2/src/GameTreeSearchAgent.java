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
                bestNextState = alphaBetaDecision(deadLine, peopleToSave, otherAgentCopy,false);
                break;
            case 2:
                bestNextState = alphaBetaDecision(deadLine, peopleToSave, otherAgentCopy,true);
                break;
            case 3:
                bestNextState = alphaBetaDecision(deadLine, peopleToSave, otherAgentCopy,true);
                break;
            default:

        }
        actionToRet = createAction(bestNextState);
        this.getState().updateMyState(bestNextState);
        return actionToRet;

    }

    public AgentState alphaBetaDecision(int deadLine, int peopleToSave, AgentState otherAgentState,boolean maxMode) {
        LinkedList<AgentState> moves = generateNextMoveState(this.getState());
        int MaxOfMin = Integer.MIN_VALUE;
        int currentValue = 0;
        AgentState bestNextState = null;
        AgentState curentMove = new AgentState(0, 0, 0, this.getState().getVertices());
        for (AgentState move : moves) {
            otherAgentState.updateStateOp(move);
            curentMove.updateMyState(move);
            if(maxMode){
                currentValue = oppMinMax(curentMove,otherAgentState,maxMode, Integer.MIN_VALUE,
                        Integer.MIN_VALUE, 1);
            }
            else{
                currentValue = oppMinMax(curentMove,otherAgentState,maxMode, Integer.MIN_VALUE,
                        Integer.MAX_VALUE, 1);
            }
//            currentMin = minValue(curentMove, otherAgentState, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
            if (currentValue > MaxOfMin) {    // saving the greatest value of all
                MaxOfMin = currentValue;
                bestNextState = move;
            }
        }
        return bestNextState;
    }

    private int oppMinMax(AgentState curentMove, AgentState otherAgentState, boolean maxMode,
                          int bestValue1, int bestValue2, int cutoffDepth) {
        int currentValue;
        if (maxMode) {
            currentValue = maxValue(curentMove, otherAgentState,
                    bestValue1, bestValue2, cutoffDepth,maxMode);
        }
        else {
            currentValue = minValue(curentMove, otherAgentState,
                    bestValue1, bestValue2, cutoffDepth);
        }
        return currentValue;
    }

    private int maxValue(AgentState maxState, AgentState minState, int bestMax, int bestMin, int cutoffDepth, boolean maxMode) {  //returns a heuristicValue of the cutoff
        if (cutOff(cutoffDepth) || maxState.isGoalState())
            return evalFun(maxState);
        int MaxOfMin = Integer.MIN_VALUE;
        LinkedList<AgentState> moves = generateNextMoveState(maxState);
        int currentValue = 0;
        for (AgentState move : moves) {
            minState.updateStateOp(move);

            // min-max OR max-max
            if(maxMode){
                currentValue = maxValue(move, minState, bestMax,
                        bestMin, cutoffDepth + 1,maxMode);
            }
            else{
                currentValue = minValue(move, minState, bestMax,
                        bestMin, cutoffDepth + 1);
            }

            //finding max value of opp
            if (currentValue > MaxOfMin) {
                MaxOfMin = currentValue;
            }
            if (MaxOfMin > bestMin)
                return MaxOfMin;
            if (MaxOfMin > bestMax)
                bestMax = MaxOfMin;
        }
        return MaxOfMin;

    }

    private int minValue(AgentState maxState, AgentState minState, int bestMax, int bestMin, int cutoffDepth) {
        if (cutOff(cutoffDepth) || minState.isGoalState())
            return evalFun(minState);
        int MinOfMax = Integer.MAX_VALUE;
        LinkedList<AgentState> moves = generateNextMoveState(minState);
        int currentMax = 0;
        for (AgentState move : moves) {
            maxState.updateStateOp(move);
            currentMax = maxValue(maxState, move, bestMax, bestMin, cutoffDepth + 1,false);
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

    private int evalFun(AgentState state) {
        int evalVal = state.getPeopleSaved() - heuristicFun(state);
        return evalVal;
    }

    private int heuristicFun(AgentState state) {
        int hueristicVal = 0;
        if (State.isGoalState()) {
            return hueristicVal;
        } else {
            int peopleCantBeRescude = 0;
            int deadLine = state.deadLine;
            GameTreeSearchAgent agent = new GameTreeSearchAgent(state);
            AgentState newPeopleState = null;
            AgentState newShelterState = null;
            Iterator<Vertex> iter = main.world.getVertices().listIterator(0);
            Vertex currentVertex;
            BinaryHeap<TreeVertex> fring;
            while (iter.hasNext()) {
                fring = new BinaryHeap<>();
                fring.add(new TreeVertex(state, null, 0));
                newPeopleState = null;
                currentVertex = iter.next();
                if (currentVertex.getPeople() > 0) {
                    newPeopleState = agent.TreeSearch(fring, "id", currentVertex.getId());  //serch for people
                    if (newPeopleState == null)
                        peopleCantBeRescude = peopleCantBeRescude + currentVertex.getPeople();
                    else {
                        fring = new BinaryHeap<>();
                        fring.add(new TreeVertex(newPeopleState, null, 0));
                        newShelterState = agent.TreeSearch(fring, "shelter", 0);
                        if (newShelterState == null || newShelterState.getDeadLine() < 0)    // people canot be saved
                            peopleCantBeRescude = peopleCantBeRescude + currentVertex.getPeople();
                    }
                }


            }
            hueristicVal = main.bignum * peopleCantBeRescude;
            return hueristicVal;
        }
    }

    private boolean cutOff(int cutoffDepth) {
        return (cutoffDepth > 8);
    }


}
