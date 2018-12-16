import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class Vertex {
	private int Id;
	private LinkedList<Pair> Edges;

	public Vertex(int id) {
		this.Id=id;
		this.Edges= new LinkedList<>();
	}
	
	public static LinkedList<Vertex>verticesDeepCopy(LinkedList<Vertex> vertices){
		 LinkedList<Vertex> newVertices=new LinkedList<>();
		 Iterator<Vertex> verIter=vertices.listIterator(0);
		 Vertex curentVer;
		 Vertex newVer;
		 while(verIter.hasNext()) {
			 curentVer=verIter.next();
			 newVer=new Vertex(curentVer.getId());
			 newVertices.add(newVer);
		 }
		 Iterator<Vertex> newVerIter=newVertices.listIterator(0);
		 Iterator<Pair> edgeIter;
		 while(newVerIter.hasNext()) {
			 curentVer=newVerIter.next();
			 edgeIter=vertices.get(curentVer.getId()-1).getEdges().listIterator(0);
			 Pair currentEdge;
			 while(edgeIter.hasNext()) {
				currentEdge=edgeIter.next();
				curentVer.addEdge(currentEdge.getWeight(),newVertices.get(currentEdge.getVertex().Id-1));
			 }
		 }
		 return newVertices;
	}
	
	
	
	public void addEdge(int weight, Vertex v) {
		Edges.add(new Pair(v, weight));
	}
	
	public void removeEdgeById(int id) {
		Iterator<Pair> iter=Edges.listIterator(0);
		Pair current;
		while (iter.hasNext()) {
			current=iter.next();
			if(current.getVertex().Id==id)
				iter.remove();
			
		}
		
	}


	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public LinkedList<Pair> getEdges() {
		return Edges;
	}

	public Vertex getNeighborByVid(int vId){
		Iterator<Pair> edgeIter = Edges.listIterator(0);
		while(edgeIter.hasNext()) {
			Vertex v = edgeIter.next().getVertex();
			if(v.getId() == vId){
				return v;
			}
		}
		return null;
	}
	public int getEdgeWeight(int vId){
		Iterator<Pair> iter= getEdges().listIterator(0);
		Pair currentEdge;
		while (iter.hasNext()) {
			currentEdge = iter.next();
			if(currentEdge.getVertex().getId() == vId){
				return currentEdge.getWeight();
			}
		}
		return 0;
	}
	public void setEdges(LinkedList<Pair> edges) {
		Edges = edges;
	}
	

}
