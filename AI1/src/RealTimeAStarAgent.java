
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
		TreeVertex newVertex = new TreeVertex(this.State, null, 0);
		newVertex.setHueristicVal();
		fring.add(newVertex );
		AgentState newState=TreeSearch(fring, "optimal",NumOfExp,0,false); // next v in shortest path
		if(newState!=null && newState.getVertex()!= State.getVertex()) {
			this.State = newState;
			return new Action(State.getDeadLine(),peopleToSave-this.State.peopleToSave,this.getState().getVertex(),null,0);
		}
		State.setDeadLine(State.getDeadLine()-1);
		return new Action(State.getDeadLine(),peopleToSave-this.State.peopleToSave,this.getState().getVertex(),null,0); //noOp

    }
	
	@Override
	public  TreeVertex setEvalFanc(TreeVertex treeVertex) {
        if(treeVertex.getState().isGoalState()) {
        	treeVertex.setCost(treeVertex.getCost()+treeVertex.getState().getPeopleToSave()*main.bignum);
        }
        treeVertex.setEvalNum(treeVertex.getHueristicVal()+treeVertex.getCost());   //set f(n)=g(n)+h(n)
        	
        return treeVertex;
    }

}
