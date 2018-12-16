
public class Action {
	private int deadline;
	private int peopleSaved;
	private Vertex vertexLocation;
	private Vertex EdgeToBlock;
	private int peopleOnCurrV;
	
	public Action(int deadline,int peopleSaved, Vertex vlocation) {
		this.deadline = deadline;
		this.peopleSaved = peopleSaved;
		this.vertexLocation = vlocation;
//		this.EdgeToBlock = EdgeToBlock;
//		this.peopleOnCurrV = peopleOnCurrV;
	}
///////////////////////////////// UPDATE GETERS SETERS!!!! /////////////////////////////////

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public int getPeopleSaved() {
		return peopleSaved;
	}

	public void setPeopleSaved(int peopleSaved) {
		this.peopleSaved = peopleSaved;
	}

	public Vertex getVertexLocation() {
		return vertexLocation;
	}

	public void setVertexLocation(Vertex vertexLocation) {
		this.vertexLocation = vertexLocation;
	}

	public Vertex getEdgeToBlock() {
		return EdgeToBlock;
	}

	public void setEdgeToBlock(Vertex edgeToBlock) {
		EdgeToBlock = edgeToBlock;
	}

	public int getPeopleOnCurrV() {
		return peopleOnCurrV;
	}

	public void setPeopleOnCurrV(int peopleOnCurrV) {
		this.peopleOnCurrV = peopleOnCurrV;
	}
}

