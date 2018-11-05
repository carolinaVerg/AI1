import java.util.Iterator;

public class TreeVertex implements Comparable<TreeVertex>{
	AgentState State;
	TreeVertex Parent;
	int Cost; // g 
	int hueristicVal; // h
	int evalNum;  // f = h + g

	public TreeVertex(AgentState State,TreeVertex Parent,int Cost) {
		this.State=State;
		this.Parent=Parent;
		this.Cost=Cost;
		this.evalNum=0;
		this.hueristicVal = 0;
	}



	@Override
	public int compareTo(TreeVertex V) {
		if(this.evalNum>V.evalNum)
			return 1;
		else if(this.evalNum<V.evalNum)
			return -1;
		return 0;
	}



	public AgentState getState() {
		return State;
	}



	public void setState(AgentState state) {
		State = state;
	}



	public TreeVertex getParent() {
		return Parent;
	}



	public void setParent(TreeVertex parent) {
		Parent = parent;
	}



	public int getCost() {
		return Cost;
	}



	public void setCost(int cost) {
		Cost = cost;
	}



	public int getHueristicVal() {
		return hueristicVal;
	}



	public void setHueristicVal() {
		if(State.isGoalState()) {
			this.hueristicVal = 0;
		}
		else {
			int PeopleCantBeRescude=0;
			int deadLine=State.deadLine;
			GreedyAgent agent= new GreedyAgent(getState());
			AgentState newPeopleState = null;
			AgentState newShelterState = null;
			Iterator<Vertex> iter = main.world.getVertices().listIterator(0);
	        Vertex currentSucssesor;
	        BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
			fring.add(new TreeVertex(this.State, null, 0) );
	        while (iter.hasNext()) {
	        	currentSucssesor = iter.next();
	        	if(currentSucssesor.getPeople()>0) 
	        		newPeopleState=agent.TreeSearch(fring, "id", 0, currentSucssesor.getId(),true);  //serch for people
	        	if(newPeopleState==null)
	        		PeopleCantBeRescude=PeopleCantBeRescude+currentSucssesor.getPeople();
	        	else {
	        		fring=new BinaryHeap<>();
	    			fring.add(new TreeVertex(newPeopleState, null, 0) );
	        		newShelterState=agent.TreeSearch(fring, "shelter", 0, 0,true);
	        		if(newShelterState==null||newShelterState.getDeadLine()<0)	// people canot be saved
	        			PeopleCantBeRescude=PeopleCantBeRescude+currentSucssesor.getPeople();	
	        	}
	        }
	        this.hueristicVal=main.bignum* PeopleCantBeRescude;
		}
	}



	public int getEvalNum() {
		return evalNum;
	}



	public void setEvalNum(int evalNum) {
		this.evalNum = evalNum;
	}

}
