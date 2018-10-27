//// case where agent search and cant find (null cases) --> do no-op

public class GreedyAgent extends Agents {

	public GreedyAgent(AgentState state) {
		super(state);
	}

	public Action agentFunc(int deadLine, int peopleToSave) {
		int peopleSaved=0;
		BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
		fring.add(new TreeVertex(this.State, null, 0) );
		Vertex nextV;
		if(this.State.peopleOn>0) {
			nextV = TreeSearch(fring, "shelter"); // next v in shortest path
			this.State.setDeadLine(deadLine - evalCost(this.State.vertex.getEdgeWeight(nextV.getId()),this.State.getPeopleOn(),main.kConst));
			if(!nextV.isIsShelter()){	// NOT shelter
				this.State.setPeopleOn(nextV.getPeople()+this.State.getPeopleOn()); // upload people
			}
			else{
				if(this.State.getDeadLine() >=0) {
					// set people to save?????// check calc
					this.State.setPeopleToSave(peopleToSave - this.State.getPeopleOn());
					peopleSaved = this.State.getPeopleOn();
				}
				this.State.setPeopleOn(0);
			}
			this.State.vertex = nextV; // moves the agent to new vertex
		}
		else {
			nextV = TreeSearch(fring, "people");
			this.State.setDeadLine(deadLine - evalCost(this.State.vertex.getEdgeWeight(nextV.getId()),this.State.getPeopleOn(),main.kConst));
			/* setpeopleon(nextV.getpeople() + State.getpeopleon())??????? */
			this.State.setPeopleOn(nextV.getPeople());
			this.State.vertex = nextV; // moves the agent to new vertex
		}
		return new Action(State.getDeadLine(),peopleSaved,nextV,null,0);
	}


	
	

}
