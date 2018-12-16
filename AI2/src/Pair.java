
public class Pair {
	private Vertex vertex;
	private int weight;
	
	public Pair(Vertex newV, int weight) {
		this.vertex=newV;
		this.weight=weight;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
