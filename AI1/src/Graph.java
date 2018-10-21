
public class Graph {
	private int VerticesNum;
	private int EdgesNum;
	private int DeadLine;
	private int [][]EdgeMatrix;
	private int []Vertices;

	public Graph() {
		VerticesNum=0;
		EdgesNum=0;
		DeadLine=0;
	}
	
	
	public void modifyEdge(int vertice1,int vertice2, int weight) {
		EdgeMatrix[vertice1][vertice2]=weight;
		EdgeMatrix[vertice2][vertice1]=weight;
	}
	
	
	public void initiateGraph(int verticesNum, int edgeNum, int deadLine, int[][]edgeMatrix, int[] vertices) {
		VerticesNum=verticesNum;
		EdgesNum=edgeNum;
		DeadLine=deadLine;
		EdgeMatrix=edgeMatrix;
		Vertices=vertices;
	}


	public void modifyVertices(int verticesToChange) {
		Vertices[verticesToChange]=0;
		
	}
}
