import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class Vertex {
	private int People;
	private boolean IsShelter;
	private int Id;
	private LinkedList<Pair> Edges;

	public Vertex(int people,boolean isShelter, int id) {
		this.People=people;
		this.IsShelter=isShelter;
		this.Id=id;
		this.Edges= new LinkedList<>();
	}
	
	public void setNumOfPeople(int num) {
		this.People=num;
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

	public int getPeople() {
		return People;
	}

	public void setPeople(int people) {
		People = people;
	}

	public boolean isIsShelter() {
		return IsShelter;
	}

	public void setIsShelter(boolean isShelter) {
		IsShelter = isShelter;
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
