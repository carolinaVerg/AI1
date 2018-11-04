

public class GreedySerchAgent extends Agents{

	public GreedySerchAgent(AgentState state) {
		super(state);
	}
	@Override
	public Action agentFunc(int deadLine, int peopleToSave) {
		this.getState().setDeadLine(deadLine);
		BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
		TreeVertex newVertex=new TreeVertex(this.State, null, 0);
		newVertex.setHueristicVal();
		fring.add(newVertex );
		this.State = TreeSearch(fring, "optimal",-1,0,false); // next v in shortest path
		return new Action(State.getDeadLine(),peopleToSave-this.State.peopleToSave,this.getState().getVertex(),null,0);
    }
	
	@Override
	public TreeVertex setEvalFanc(TreeVertex treeVertex) {
		treeVertex.setEvalNum(treeVertex.getHueristicVal());
        return treeVertex;
    }
	

}
