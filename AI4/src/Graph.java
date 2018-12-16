import java.util.Iterator;
import java.util.LinkedList;

public class Graph {
	private int VerticesNum;
	private LinkedList<Vertex> Vertices;
	private int peopleRescude;
	private int peopleNotRescude;
	private int deadLine;

	
	public Graph(int verticesNum) {
		VerticesNum=verticesNum;
		Vertices=new LinkedList<>();
		for (int i=1; i<=VerticesNum; i++){
			Vertices.add(new Vertex(i));
		}
		this.peopleNotRescude = 0;
	}
	
	
	public void addEdge(int vertice1Id,int vertice2Id, int weight) {
		Vertex v1=Vertices.get(vertice1Id);
		Vertex v2=Vertices.get(vertice2Id);
		v1.addEdge(weight, v2);
		v2.addEdge(weight, v1);
	}
	
	public void removeEdge(int vId1, int vId2){
		this.getVertexById(vId1).removeEdgeById(vId2);
		this.getVertexById(vId2).removeEdgeById(vId1);
	}

	public void addVertex(Vertex v) {
		Vertices.add(v);
	}

	public Vertex getVertexById(int vId){
		return Vertices.get(vId-1);
	}


	public int getVerticesNum() {
		return VerticesNum;
	}


	public void setVerticesNum(int verticesNum) {
		VerticesNum = verticesNum;
	}


	public LinkedList<Vertex> getVertices() {
		return Vertices;
	}


	public void setVertices(LinkedList<Vertex> vertices) {
		Vertices = vertices;
	}


	public int getPeopleRescude() {
		return peopleRescude;
	}


	public void setPeopleRescude(int peopleRescude) {
		this.peopleRescude = peopleRescude;
	}


	public int getDeadLine() {
		return deadLine;
	}


	public void setDeadLine(int deadLine) {
		this.deadLine = deadLine;
	}


	public int getPeopleNotRescude() {
		return peopleNotRescude;
	}


	public void setPeopleNotRescude(int peopleNotRescude) {
		this.peopleNotRescude = peopleNotRescude;
	}
	
	


	
}
