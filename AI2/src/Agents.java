import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

public class Agents {
    protected AgentState State;

    public Agents(AgentState state) {
        this.State = state;
    }

    public Action agentFunc(int deadLine, int peopleToSave,AgentState otherAgent,LinkedList<Vertex> Vertices) {
        return null;
    }

    public AgentState getState() {
        return State;
    }

    public void setState(AgentState state) {
        State = state;
    }


    public static int evalCost(int edgeWeight, int k, int numOfPeople){
        return edgeWeight * ( 1 + (k * numOfPeople));
    }
    
	protected AgentState buildState(Pair currentNeighbor, AgentState prevState) {
		if(currentNeighbor==null) {
			AgentState newState=new AgentState(prevState.getVertex().getId(),prevState.getDeadLine()-1,prevState.getPeopleToSave(),prevState.getVertices());
			newState.setPeopleOn(prevState.getPeopleOn());
			return newState;
		}
		int deadline = prevState.getDeadLine() - evalCost(currentNeighbor.getWeight(),main.kConst,prevState.getPeopleOn());
		int peopleNotSaved = prevState.getPeopleToSave();
		int peopleSaved=prevState.getPeopleSaved();
		int peopleOn = prevState.getPeopleOn();
		if(currentNeighbor.getVertex().isIsShelter()){
			peopleOn = 0;
			if(deadline>=0){
				peopleSaved=peopleSaved+prevState.getPeopleOn();
				peopleNotSaved = prevState.getPeopleToSave() - prevState.getPeopleOn();
			}
		}
		else{
			peopleOn = peopleOn + currentNeighbor .getVertex().getPeople();
		}
		AgentState returnAgentState = new AgentState(currentNeighbor.getVertex().getId(),deadline,peopleNotSaved,prevState.getVertices());
		returnAgentState.setPeopleOn(peopleOn);
		returnAgentState.setPeopleSaved(peopleSaved);
		returnAgentState.getVertices().get(currentNeighbor.getVertex().getId()-1).setPeople(0);
		return returnAgentState;
	}
	
    public  AgentState TreeSearch(BinaryHeap<TreeVertex> fringe, String goal,  int id) { // finds shortest path according to current goal
    	AgentState currState=null;
    	TreeVertex current=null;
        while (!fringe.isEmpty()) {
            current = fringe.remove(); // takes first from the priority queue  
            currState=current.getState();
            switch (goal) {
                case "shelter":
                    if (currState.getVertex().isIsShelter()) {
                        return currState;
                    }
                    else {
                        Expand(current, fringe);
                        break;
                    }
                case "id":
                    if (currState.getVertex().getId()==id){
                    	currState.setPeopleOn(current.getState().getVertex().getPeople());
                        return  currState;
                    }
                    else{
                        Expand(current, fringe);
                        break;
                    }
                    default:break;
            }
        }
        return null;
 //       return findNextVer(source,currState);

    } //returns next v for the action
    public  BinaryHeap<TreeVertex> Expand(TreeVertex currentState, BinaryHeap<TreeVertex> fringe ) { // returns updated fringe
    	main.numOfExpands ++;
        Iterator<Pair> iter = currentState.getState().getVertex().getEdges().listIterator(0);
        Pair currentPair;
        TreeVertex newVertex;
        while (iter.hasNext()) {
            currentPair = iter.next();
            newVertex=new TreeVertex(buildState(currentPair, currentState.getState()),currentState,currentState.getCost()+ currentPair.getWeight());
            fringe.add(newVertex);

        }
        return fringe;
    }

   




}
