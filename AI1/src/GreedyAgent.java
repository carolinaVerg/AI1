
public class GreedyAgent extends Agents{
	private boolean Carrying;

	public GreedyAgent(int state) {
		super(state);
		Carrying=false;
	}

	@Override
	public Graph performAction() {
		int verticesToChange;
		Graph modifiedGraph=this.Percept;
		if(Carrying) {
			verticesToChange=findShelter();
			Carrying=false;
			return modifiedGraph;
		}
		else {
			verticesToChange=findPeopleVertoces();
			Carrying=true;
			modifiedGraph.modifyVertices(verticesToChange);
			return modifiedGraph;
		}
	}
	
	private int findShelter() {
		return null;
	}
	private int findPeopleVertoces() {
		return null;
	}

}
