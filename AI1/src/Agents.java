import java.util.Iterator;
import java.util.ArrayList;

public class Agents {
    protected AgentState State;

    public Agents(AgentState state) {
        this.State = state;
    }

    public Action agentFunc(int deadLine, int peopleToSave) {
        return null;
    }

    public AgentState getState() {
        return State;
    }

    public void setState(AgentState state) {
        State = state;
    }

    public  AgentState TreeSearch(BinaryHeap<TreeVertex> fringe, String goal, int numOfExp, int id,Boolean isHuristic) { // finds shortest path according to current goal
    	ArrayList<Vertex> visited= new ArrayList<>();
    	AgentState currState=null;
    	TreeVertex current;
        int ExpCounter=numOfExp;
        TreeVertex source = fringe.peek();
        while (!fringe.isEmpty()) {
            current = fringe.remove(); // takes first from the priority queue         
            switch (goal) {
                case "shelter":
                    if (current.getState().getVertex().isIsShelter()) {
                        return findNextVer(source,current);
                    }
                    else {
                        Expand(current, fringe,isHuristic);
                    }
                    break;
                case "people":
                    if (current.getState().getVertex().getPeople() > 0)
                        return findNextVer(source,current);
                    else
                        Expand(current, fringe,isHuristic);
                    break;
                case "optimal":
                	if(visited.contains(current.getState().getVertex())) { // check for loops
                		break;
                	}
                	visited.add(current.getState().getVertex());
                    currState = current.getState();
                    if (currState.isGoalState()||ExpCounter==0){
                        return  findNextVer(source,current);
                    }
                    else{
                        Expand(current, fringe,isHuristic);
                        ExpCounter--;
                    }
                    break;
                case "id":
                    if (current.getState().getVertex().getId()==id){
                        return  current.getState();
                    }
                    else{
                        Expand(current, fringe,isHuristic);
                    }
            }
        }
        return currState;

    } //returns next v for the action

    public  AgentState findNextVer(TreeVertex sourceV, TreeVertex currV) {
        AgentState stateToRet = currV.getState();
        if(currV.getParent() == null){
            return stateToRet;
        }
        while (currV.getParent().getState().getVertex().getId() != sourceV.getState().getVertex().getId()) {
            currV = currV.getParent();
            stateToRet = currV.getState();
        }
        return stateToRet;
    } // finds next v parents...

    public  BinaryHeap<TreeVertex> Expand(TreeVertex currentState, BinaryHeap<TreeVertex> fringe, Boolean isHuristic) { // returns updated fringe
    	
        Iterator<Pair> iter = currentState.getState().getVertex().getEdges().listIterator(0);
        Pair currentPair;
        TreeVertex newVertex;
        while (iter.hasNext()) {
            currentPair = iter.next();
            newVertex=buildVertexState(currentPair, currentState);
            if(!isHuristic) {
                newVertex.setHueristicVal();
            }
            fringe.add(newVertex);

        }
        return fringe;
    }

    public static int evalCost(int edgeWeight, int k, int numOfPeople){
        return edgeWeight * ( 1 + (k * numOfPeople));
    }
    
    public  TreeVertex buildVertexState(Pair currentPair, TreeVertex currentState) {
    	int deadline;
    	int peopleNotSaved=currentState.getState().getPeopleToSave();
    	int peopleOn;
    	TreeVertex newVertex;
    	deadline =currentState.getState().getDeadLine()- evalCost(currentPair.getWeight(),main.kConst,currentState.State.getPeopleOn());
    	if(currentPair.getVertex().isIsShelter()) {
    		peopleOn=0;
    		if(deadline>=0)
    			peopleNotSaved=currentState.getState().getPeopleToSave()-currentState.getState().getPeopleOn();
    	}
    	else {
    		peopleOn=currentState.getState().getPeopleOn()+currentPair.getVertex().getPeople();
    		peopleNotSaved=currentState.getState().getPeopleToSave();
    	}
        newVertex = new TreeVertex(new AgentState(currentPair.getVertex(),deadline,peopleNotSaved),  //build new vertex and state
                currentState, currentState.getCost() + currentPair.getWeight());
        newVertex.getState().setPeopleOn(peopleOn);
        return setEvalFanc(newVertex);
    }

    public  TreeVertex setEvalFanc(TreeVertex treeVertex) {
    	treeVertex.setEvalNum(treeVertex.getCost());
        return treeVertex;
    }



}
