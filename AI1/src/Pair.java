
public class Pair {
	private Vertex vertex;
	private int Edge;
	
	public Pair(Vertex newV, int weight) {
		this.vertex=newV;
		this.Edge=weight;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public int getEdge() {
		return Edge;
	}

	public void setEdge(int edge) {
		Edge = edge;
	}
	
}
