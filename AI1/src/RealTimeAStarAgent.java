
public class RealTimeAStarAgent extends Agents{
	int NumOfExp;

	public RealTimeAStarAgent(AgentState state, int numOfExp) {
		super(state);
		this.NumOfExp=numOfExp;
	}
	@Override
	public Action agentFunc(int deadLine, int peopleToSave) {
		this.getState().setDeadLine(deadLine);
		BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
		fring.add(new TreeVertex(this.State, null, 0) );
		Vertex nextV;
		this.State = TreeSearch(fring, "optimal",NumOfExp,0); // next v in shortest path
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
