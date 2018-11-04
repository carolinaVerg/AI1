

public class GreedySerchAgent extends Agents{

	public GreedySerchAgent(AgentState state) {
		super(state);
	}
	@Override
	public Action agentFunc(int deadLine, int peopleToSave) {
		this.getState().setDeadLine(deadLine);
		BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
		fring.add(new TreeVertex(this.State, null, 0) );
		Vertex nextV;
		this.State = TreeSearch(fring, "optimal",-1,0); // next v in shortest path
		return new Action(State.getDeadLine(),peopleToSave-this.State.peopleToSave,this.getState().getVertex(),null,0);
    }
	
	@Override
	public TreeVertex setEvalFanc(TreeVertex treeVertex) {
		treeVertex.setEvalNum(treeVertex.getHueristicVal());
        return treeVertex;
    }
	

}
