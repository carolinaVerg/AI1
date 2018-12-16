import java.util.LinkedList;

public class AgentState {
	protected int vertexId;
	protected int peopleOn;
	protected int peopleToSave;
	protected int peopleSaved;
	protected int deadLine;
	private LinkedList<Vertex> Vertices;
	
	public AgentState(int id,int deadline,int peopleToSave,LinkedList Ver) {
		LinkedList<Vertex>copyList=Vertex.verticesDeepCopy(Ver);
		this.vertexId=id;
		this.deadLine = deadline;
		this.peopleToSave = peopleToSave;
		this.peopleOn=0;
		this.Vertices=copyList;
	}
	
	public Vertex getVertex() {
		return Vertices.get(vertexId-1);
	}
	public void setVertex(int vertex) {
		this.vertexId = vertex;
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
	public boolean isGoalState() {
		if (this.getPeopleToSave() == 0 || this.getDeadLine() <= 0){
            return  true;
        }
		return false;
	}

	public int getPeopleSaved() {
		return peopleSaved;
	}

	public void setPeopleSaved(int peopleSaved) {
		this.peopleSaved = peopleSaved;
	}

	public LinkedList<Vertex> getVertices() {
		return Vertices;
	}

	public void setVertices(LinkedList<Vertex> vertices) {
		Vertices = vertices;
	}

	public void updateStateOp(AgentState move) {
		LinkedList<Vertex>copyList=Vertex.verticesDeepCopy(move.Vertices);
		this.deadLine = move.deadLine;
		this.peopleToSave = move.peopleToSave;
		this.Vertices=copyList;
		
	}
	public void updateMyState(AgentState newState) {
		LinkedList<Vertex>copyList=Vertex.verticesDeepCopy(newState.getVertices());
		this.vertexId=newState.getVertex().getId();
		this.deadLine = newState.getDeadLine();
		this.peopleToSave = newState.getPeopleToSave();
		this.peopleSaved=newState.getPeopleSaved();
		this.peopleOn=newState.getPeopleOn();
		this.Vertices=copyList;
		
	}

}
