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


	public Vertex TreeSearch(BinaryHeap<TreeVertex> fringe, String goal) { // finds shortest path according to current goal
		TreeVertex current;
		Vertex vertexToRet;
		while(!fringe.isEmpty()) {
			current= fringe.remove(); // takes first from the priority queue
			vertexToRet = current.getState().getVertex();
			switch (goal) {
			case "shelter":
				if(vertexToRet.isIsShelter())
					return vertexToRet;
				else{
					return Expand(current, fringe);
				}

			case "people":
				if(current.getState().getVertex().getPeople()>0)
					return toReturn;
				else
					Expand(current, fringe);
			}
		}
		return null;
		
	}
	public BinaryHeap<TreeVertex> Expand (TreeVertex currentState,BinaryHeap<TreeVertex> fringe ){ // returns updated fringe
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

	public int evalCost(){
		return 0;
	}
	
	

}
