import java.util.Iterator;
import java.util.ListIterator;

public class Tent extends Square{

	public Tent(int x, int y) {
		super( x, y);
		this.obj='t';
	}
	public void eliminateAdj() {
		if((this.up!=null) &&(this.up.getObj()!='T')) {
			main.wold.getLeftFree().remove(this.up);
			this.up.setObj('g');
		}
		if((this.down!=null) &&(this.down.getObj()!='T')) {
			main.wold.getLeftFree().remove(this.down);
			this.down.setObj('g');
		}
		if((this.left!=null) &&(this.left.getObj()!='T')) {
			main.wold.getLeftFree().remove(this.left);
			this.left.setObj('g');
		}
		if((this.right!=null) &&(this.right.getObj()!='T')) {
			main.wold.getLeftFree().remove(this.right);
			this.right.setObj('g');
		}
		for(Square t:this.diagonals) {
			if(t.getObj()!='T') {
				main.wold.getLeftFree().remove(t);
				t.setObj('g');
			}
		}
	
	}

}
