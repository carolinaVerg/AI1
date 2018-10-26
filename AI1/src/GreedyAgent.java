
public class GreedyAgent extends Agents {

	public GreedyAgent(AgentState state) {
		super(state);
	}
	public Action AgentFunc(int deadLine, int peopleToSave, int k) {
		int peopleSaved=0;
		BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
		fring.add(new TreeVertex(this.State, null, 0) );
		Vertex nextV;
		if(this.State.peopleOn>0) {
			nextV = TreeSearch(fring, "shelter"); // next v in shortest path
			this.State.setDeadLine(deadLine - evalCost(this.State.vertex.getEdgeWeight(nextV.getId()),this.State.getPeopleOn(),k));
			if(!nextV.isIsShelter()){	// NOT shelter
				this.State.setPeopleOn(nextV.getPeople()+this.State.getPeopleOn()); // upload people
			}
			else{
				if(this.State.getDeadLine() >=0) {
					this.State.setPeopleToSave(peopleToSave - this.State.getPeopleOn());
					peopleSaved = this.State.getPeopleOn();
				}
				this.State.setPeopleOn(0);
			}
			this.State.vertex = nextV; // moves the agent to new vertex
		}
		else {
			nextV = TreeSearch(fring, "people");
			this.State.setDeadLine(deadLine - evalCost(this.State.vertex.getEdgeWeight(nextV.getId()),this.State.getPeopleOn(),k));
			this.State.setPeopleOn(nextV.getPeople());
			this.State.vertex = nextV; // moves the agent to new vertex
		}
		return new Action(State.getDeadLine(),peopleSaved,nextV,null,0);
	}

	public int evalCost(int edgeWeight, int k, int numOfPeople){
		return edgeWeight * ( 1 + (k * numOfPeople));
	}
	
	
	

}
