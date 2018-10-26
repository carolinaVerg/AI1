import java.util.Iterator;

public  class Agents {
	protected AgentState State;
	
	

	public Agents(AgentState state) {
		this.State=state;
		
	}

	
	public Action AgentFunc(int deadLine, int peopleToSave) {
		return null;
	}


	public AgentState getState() {
		return State;
	}


	public void setState(AgentState state) {
		State = state;
	}


	public Pair TreeSerch(BinaryHeap<TreeVertex> fringe, String goal) {
		TreeVertex current;
		Pair toReturn;
		while(!fringe.isEmpty()) {
			current= fringe.remove();
			toReturn=new Pair(current.getState().getVertex(), current.getCost());
			switch (goal) {
			case "shelter":
				if(current.getState().getVertex().isIsShelter())
					return toReturn;
				else
					Expand(current, fringe);
			case "people":
				if(current.getState().getVertex().getPeople()>0)
					return toReturn;
				else
					Expand(current, fringe);
			}
		}
		return null;
		
	}
	public BinaryHeap<TreeVertex> Expand (TreeVertex currentState,BinaryHeap<TreeVertex> fringe ){
		TreeVertex newVertex;
		Iterator<Pair> iter=currentState.getState().getVertex().getEdges().listIterator(0);
		Pair currentPair;
		AgentState newState;
		while (iter.hasNext()) {
			currentPair=iter.next();
			newVertex= new TreeVertex(new AgentState(currentPair.getVertex()),
					currentState, currentState.getCost()+currentPair.getWeight());
			fringe.add(newVertex);
			
		}
		return fringe;
	}
	
	

}
