import java.util.LinkedList;

public class Graph {
	private int VerticesNum;
	private LinkedList<Vertex> Vertices;
	private int peopleRescude;

	
	public Graph(int verticesNum) {
		VerticesNum=verticesNum;
		Vertices=new LinkedList<>();
		for (int i=1; i<=VerticesNum; i++){
			Vertices.add(new Vertex(0,false,i));
		}
	}
	
	
	public void addEdge(int vertice1Id,int vertice2Id, int weight) {
		Vertex v1=Vertices.get(vertice1Id);
		Vertex v2=Vertices.get(vertice2Id);
		v1.addEdge(weight, v2);
		v2.addEdge(weight, v1);
	}
	

	public void addVertex(Vertex v) {
		Vertices.add(v);
	}

	public Vertex getVertexById(int vId){
		return Vertices.get(vId);
	}
}
