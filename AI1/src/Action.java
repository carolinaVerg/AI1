
public class Action {
	int cost;
	int peopleSaved;
	Vertex location;
	Vertex EdgeToBlock;
	
	public Action(int cost,int peopleSaved, Vertex location,Vertex EdgeToBlock) {
		this.cost=cost;
		this.peopleSaved=peopleSaved;
		this.location=location;
		this.EdgeToBlock=EdgeToBlock;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getPeopleSaved() {
		return peopleSaved;
	}

	public void setPeopleSaved(int peopleSaved) {
		this.peopleSaved = peopleSaved;
	}

	public Vertex getLocation() {
		return location;
	}

	public void setLocation(Vertex location) {
		this.location = location;
	}
	
}
