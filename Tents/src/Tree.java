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
	public int maybeTent() {
		int ans=0;
		if(this.up!=null && this.up.obj=='f')
			ans++;
		if(this.down!=null && this.down.obj=='f')
			ans++;
		if(this.left!=null && this.left.obj=='f')
		ans++;
		if(this.right!=null && this.right.obj=='f')
		ans++;
		return ans;
	}

	
	

	
}
