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
		AgentState returnAgentState = new AgentState(currentNeighbor.getVertex().getId(),deadline,peopleNotSaved,prevState.getVertices());
		returnAgentState.setPeopleOn(peopleOn);
		returnAgentState.getVertices().get(currentNeighbor.getVertex().getId()).setPeople(0);
		return returnAgentState;
	}




}
