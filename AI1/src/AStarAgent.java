
public class AStarAgent extends Agents{

	public AStarAgent(AgentState state) {
		super(state);
	}
	@Override
	public Action agentFunc(int deadLine, int peopleToSave) {
		this.getState().setDeadLine(deadLine);
		BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
		TreeVertex newVertex=new TreeVertex(this.State, null, 0);
		newVertex.setHueristicVal();
		fring.add(newVertex);
		Vertex nextV;
		this.State = TreeSearch(fring, "optimal",-1,0,false); // next v in shortest path
		return new Action(State.getDeadLine(),peopleToSave-this.State.peopleToSave,this.getState().getVertex(),null,0);
    }
	
	@Override
	public  TreeVertex setEvalFanc(TreeVertex treeVertex) {
        if(treeVertex.getState().isGoalState()) {
        	treeVertex.setCost(treeVertex.getCost()+treeVertex.getState().getPeopleToSave());
        }
        treeVertex.setEvalNum(treeVertex.getHueristicVal()+treeVertex.getCost());   //set f(n)=g(n)+h(n)
        	
        return treeVertex;
    }

}
