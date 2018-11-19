

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
		AgentState newState=TreeSearch(fring, "optimal",-1,0,false); // next v in shortest path
		if(newState!=null && newState.getVertex()!= State.getVertex()) {
			this.State = newState;
			return new Action(State.getDeadLine(),peopleToSave-this.State.peopleToSave,this.getState().getVertex(),null,0);
		}
		this.State.setDeadLine(State.getDeadLine()-1);
		return new Action(this.State.getDeadLine(),peopleToSave-this.State.peopleToSave,this.getState().getVertex(),null,0); //noOp
		
		
    }
	
	@Override
	public TreeVertex setEvalFanc(TreeVertex treeVertex) {
		treeVertex.setEvalNum(treeVertex.getHueristicVal());
        return treeVertex;
    }
	

}
