//// case where agent search and cant find (null cases) --> do no-op

public class GreedyAgent extends Agents {

	public GreedyAgent(AgentState state) {
		super(state);
	}

	public Action agentFunc(int deadLine, int peopleToSave) {
		this.getState().setDeadLine(deadLine);
		BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
		fring.add(new TreeVertex(this.State, null, 0) );
		Vertex nextV;
		if(this.State.peopleOn>0) {
			this.State = TreeSearch(fring, "shelter",0,0); // next v in shortest path
		}
		else {
			this.State = TreeSearch(fring, "people",0,0);
		}
		return new Action(State.getDeadLine(),peopleToSave-this.State.peopleToSave,this.getState().getVertex(),null,0);
	}
	@Override
	public TreeVertex setEvalFanc(TreeVertex treeVertex){
		treeVertex.setEvalNum(treeVertex.getCost());
        return treeVertex;
    }



	
	

}
