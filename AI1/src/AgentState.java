
public class AgentState {
	protected Vertex vertex;
	protected int peopleOn;
	protected int peopleToSave;
	protected int deadLine;
	public AgentState(Vertex vertex,int deadline,int peopleToSave) {
		// update the state?
		this.vertex=vertex;
		this.deadLine = deadline;
		this.peopleToSave = peopleToSave;
	}
	
	public Vertex getVertex() {
		return vertex;
	}
	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}
	public int getPeopleOn() {
		return peopleOn;
	}
	public void setPeopleOn(int peopleOn) {
		this.peopleOn = peopleOn;
	}
	public int getPeopleToSave() {
		return peopleToSave;
	}
	public void setPeopleToSave(int peopleToSave) {
		this.peopleToSave = peopleToSave;
	}
	public int getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(int deadLine) {
		this.deadLine = deadLine;
	}

}
