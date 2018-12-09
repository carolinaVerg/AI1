import java.util.Iterator;
import java.util.ListIterator;

public class Tent extends Square{

	public Tent(int x, int y) {
		super( x, y);
		this.obj='t';
	}
	public void eliminateAdj() {
		if((this.up!=null) &&(this.up.getObj()!='T')) {
			this.up.setObj('g');
		}
		if((this.down!=null) &&(this.down.getObj()!='T')) {
			this.down.setObj('g');
		}
		if((this.left!=null) &&(this.left.getObj()!='T')) {
			this.left.setObj('g');
		}
		if((this.right!=null) &&(this.right.getObj()!='T')) {
			this.right.setObj('g');
		}
		for(Square t:this.diagonals) {
			if(t.getObj()!='T') {
				t.setObj('g');
			}
		}
	
	}

}
