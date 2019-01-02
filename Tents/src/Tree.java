import java.util.LinkedList;

public class Tree extends Square{
	private Tent tent;
	public Tree(int x, int y) {
		 super(x, y);
		 this.tent=null;
		 this.obj='T';
	}
	public Tent getTent() {
		return tent;
	}
	public void setTent(Tent tent) {
		this.tent = tent;
	}
	

	
	

	
}
