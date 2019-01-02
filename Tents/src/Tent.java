import java.util.Iterator;
import java.util.ListIterator;

public class Tent extends Square{

	public Tent(int x, int y) {
		super( x, y);
		this.obj='t';
	}
	
	public int num_trees_around() {
		int ans=0;
		if(this.up!=null && this.up.obj=='t')
			ans++;
		if(this.down!=null && this.down.obj=='t')
			ans++;
		if(this.left!=null && this.left.obj=='t')
		ans++;
		if(this.right!=null && this.right.obj=='t')
		ans++;
		return ans;
	}

}
