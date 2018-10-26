
public class TreeVertex implements Comparable<TreeVertex>{
	AgentState State;
	TreeVertex Parent;
	int Cost;

	public TreeVertex(AgentState State,TreeVertex Parent,int Cost) {
		this.State=State;
		this.Parent=Parent;
		this.Cost=Cost;
	}

	

	@Override
	public int compareTo(TreeVertex V) {
		if(this.Cost>V.Cost)
			return 1;
		else if(this.Cost<V.Cost)
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

}
