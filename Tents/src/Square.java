import java.util.LinkedList;

public class Square {
	protected int x;
	protected int y;
	protected char obj;
	protected Square up;
	protected Square down;
	protected Square left;
	protected Square right;
	protected LinkedList<Square> diagonals;  // max 8 neighbors
	public Square(int x, int y) {
		this.x=x;
		this.y=y;
		this.obj='f';
	}
	public int freeSquarse() {
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
	public void eliminateAdj() {
		if((this.up!=null) &&(this.up.getObj()!='T')) {
			main.wold.removeFree(this.up);
			this.up.setObj('g');
		}
		if((this.down!=null) &&(this.down.getObj()!='T')) {
			main.wold.removeFree(this.down);
			this.down.setObj('g');
		}
		if((this.left!=null) &&(this.left.getObj()!='T')) {
			main.wold.removeFree(this.left);
			this.left.setObj('g');
		}
		if((this.right!=null) &&(this.right.getObj()!='T')) {
			main.wold.removeFree(this.right);
			this.right.setObj('g');
		}
		for(Square t:this.diagonals) {
			if(t.getObj()!='T') {
				main.wold.removeFree(t);
				t.setObj('g');
			}
		}
	
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public char getObj() {
		return obj;
	}

	public void setObj(char obj) {
		this.obj = obj;
	}

	public Square getUp() {
		return up;
	}

	public void setUp(Square up) {
		this.up = up;
	}

	public Square getDown() {
		return down;
	}

	public void setDown(Square down) {
		this.down = down;
	}

	public Square getLeft() {
		return left;
	}

	public void setLeft(Square left) {
		this.left = left;
	}

	public Square getRight() {
		return right;
	}

	public void setRight(Square right) {
		this.right = right;
	}

	public LinkedList<Square> getDiagonals() {
		return diagonals;
	}

	public void setDiagonals(LinkedList<Square> diagonals) {
		this.diagonals = diagonals;
	}

}
