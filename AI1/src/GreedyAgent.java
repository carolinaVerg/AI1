
public class GreedyAgent extends Agents {

	public GreedyAgent(AgentState state) {
		super(state);
	}
	@Override
	public Action AgentFunc(int deadLine, int peopleToSave) {
		int peopleSaved=0;
		BinaryHeap< TreeVertex> fring=new BinaryHeap<>();
		fring.add(new TreeVertex(this.State, null, 0) );
		Pair optimal;
		if(this.State.peopleOn>0) {
			optimal= TreeSerch(fring, "shelter");
			peopleSaved=State.getPeopleOn();
		}
		else
			optimal=TreeSerch(fring, "people");
		
		this.State.setDeadLine(deadLine-optimal.getWeight());
		this.State.setPeopleToSave(peopleToSave-optimal.getVertex().getPeople());
		this.State.setPeopleOn(optimal.getVertex().getPeople());
		
	
		
		
		return new Action(optimal.getWeight(),peopleSaved, optimal.getVertex(), null);
		
	}
	
	
	

}
