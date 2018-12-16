import java.util.LinkedList;

public class World {
	private int treeNum;
	private Square [][] worldMatrix;
	private int tentsInRow[];
	private int tentsInCol[];
	private int freeInRow[];
	private int freeInCol[];
	private LinkedList<Tree> leftTreeArray;
	private int n;
	private int m;
	private LinkedList<Square> leftFree;
	

	public World(int treeNum,Square [][] worldMatrix,int tentsInRow[],int tentsInCol[] ) {
		this.treeNum=treeNum;
		this.worldMatrix=worldMatrix;
		this.tentsInRow=tentsInRow;
		this.tentsInCol=tentsInCol;
		leftTreeArray=new LinkedList<>();
		this.n=worldMatrix.length;
		this.m=worldMatrix[0].length;
		genTreeList();
		genFreeList();
	}
	public void genTreeList() {
		int treeCounter = 0;
		for(int i = 0; i < tentsInRow.length ; ++i){
			for(int j = 0 ; j < tentsInCol.length; ++j) {
				if(worldMatrix[i][j].getObj()=='T') {
					this.leftTreeArray.add((Tree) worldMatrix[i][j]);
				}
			}
		}
	}
	public void genFreeList() {
		int treeCounter = 0;
		for(int i = 0; i < tentsInRow.length ; ++i){
			for(int j = 0 ; j < tentsInCol.length; ++j) {
				if(worldMatrix[i][j].getObj()=='f') {
					this.leftFree.add((Square) worldMatrix[i][j]);
				}
			}
		}
	}
	public Boolean onlyTentsLeft() {
		boolean changed=false;
			for(int i=0; i<worldMatrix.length; i++) {    //runs over calms
				if(this.tentsInCol[i]==this.freeInCol[i])
					for (int j=0; j<worldMatrix[i].length; i++)
						if((worldMatrix[i][j].getObj())=='f')
						{
							Tent tent =new Tent(worldMatrix[i][j].getX(), worldMatrix[i][j].getY());
							tent.eliminateAdj();
							removeFree(worldMatrix[i][j]);
							this.tentsInRow[j]--;
							this.tentsInCol[i]--;
							worldMatrix[i][j]= tent;
							changed=true;
						}
			}
			for(int i=0; i<worldMatrix[0].length; i++) {  // runs over rows
				if(this.tentsInRow[i]==this.freeInCol[i]) {
					for(Square s=worldMatrix[0][i];s!=null; s =s.getRight()) {
						if(s.getObj()=='f')
						{
							Tent tent =new Tent(s.getX(), s.getY());
							tent.eliminateAdj();
							removeFree(s);
							this.tentsInRow[s.getY()]--;
							this.tentsInCol[s.getX()]--;
							worldMatrix[s.getX()][s.getY()]= tent;
							changed=true;
						}
					}
				}			
			}	
			return changed;
	}
	public Boolean onlyGrassLeft() {
		boolean changed=false;
		for(int i=0; i<worldMatrix.length; i++) {    //runs over calms
			if(this.tentsInCol[i]==0)
				for (int j=0; j<worldMatrix[i].length; i++)
					if((worldMatrix[i][j].getObj())=='f')
					{
						removeFree(worldMatrix[i][j]);
						worldMatrix[i][j].setObj('g');
						changed=true;
						
					}
		}
		for(int i=0; i<worldMatrix[0].length; i++) {  // runs over rows
			if(this.tentsInRow[i]==0) {
				for(Square s=worldMatrix[0][i];s!=null; s =s.getRight()) {
					if(s.getObj()=='f')
					{
						
						this.freeInRow[s.getY()]--;
						this.freeInCol[s.getX()]--;
						worldMatrix[s.getX()][s.getY()].setObj('g');
						this.leftFree.remove(s);
						changed=true;
					}
				}
			}			
		}
		return changed;
	}
	public Boolean oneOptionLeftToTree() {
		boolean changed =false;
		LinkedList<Tree> newTreeList=(LinkedList<Tree>) this.leftTreeArray.clone();
		for(Tree t:leftTreeArray){
			if(t.maybeTent()==1) {
				Tent tent;
				if(t.up!=null && t.up.obj=='f') {
					tent =new Tent(t.getUp().getX(), t.getUp().getY());
					tent.eliminateAdj();
					removeFree(t.getUp());
					this.tentsInRow[t.getUp().getY()]--;
					this.tentsInCol[t.getUp().getX()]--;
					worldMatrix[t.getUp().getX()][t.getUp().getY()]= tent;
					changed=true;
				}

				else if(t.down!=null && t.down.obj=='f'){
					tent =new Tent(t.getDown().getX(), t.getDown().getY());
					tent.eliminateAdj();
					removeFree(t.getDown());
					this.tentsInRow[t.getDown().getY()]--;
					this.tentsInCol[t.getDown().getX()]--;
					worldMatrix[t.getDown().getX()][t.getDown().getY()]= tent;
					changed=true;
				}

				else if(t.left!=null && t.left.obj=='f'){
					tent =new Tent(t.getLeft().getX(), t.getLeft().getY());
					tent.eliminateAdj();
					removeFree(t.getLeft());
					this.tentsInRow[t.getLeft().getY()]--;
					this.tentsInCol[t.getLeft().getX()]--;
					worldMatrix[t.getLeft().getX()][t.getLeft().getY()]= tent;
					changed=true;
				}
			
				else {
					tent =new Tent(t.getRight().getX(), t.getRight().getY());
					tent.eliminateAdj();
					removeFree(t.getRight());
					this.tentsInRow[t.getRight().getY()]--;
					this.tentsInCol[t.getRight().getX()]--;
					worldMatrix[t.getRight().getX()][t.getRight().getY()]= tent;
					changed=true;
				}
				newTreeList.remove(t);
			}
				
		}
		this.leftTreeArray=newTreeList;
		return changed;
	}
	public Boolean squareWithNoTrees() {
		boolean changed=false;
		LinkedList<Square> newLeftList=(LinkedList<Square>) this.leftFree.clone();
		for(Square s:newLeftList) {
			if((s.getUp()==null||s.getUp().getObj()!='T')&&
			(s.getDown()==null||s.getDown().getObj()!='T')&&
			(s.getLeft()==null||s.getLeft().getObj()!='T')&&
			(s.getRight()==null||s.getRight().getObj()!='T')) {
				removeFree(s);
				s.setObj('g');
				changed=true;
			}
		}
		return changed;
	}
	
	public boolean solve() {
		if(this.getLeftTreeArray().isEmpty())
			return true;
		if(!correctness()) {
			return false;
		}
		else if(!onlyTentsLeft()& !onlyGrassLeft() & !oneOptionLeftToTree() & !squareWithNoTrees()) {
			World newWorld;
			Tent tent;
			Square newSquare;
			for(Square s:leftFree) {
				newWorld= new World(this.getTreeNum(), worldMatrix, tentsInRow, tentsInCol);
				newSquare=newWorld.worldMatrix[s.x][s.y];
				tent =new Tent(newSquare.getX(), newSquare.getY());
				tent.eliminateAdj();
				newWorld.removeFree(newSquare);
				newWorld.tentsInRow[newSquare.getY()]--;
				newWorld.tentsInCol[newSquare.getX()]--;
				newWorld.worldMatrix[s.x][s.y]=tent;
				if(newWorld.solve())
					return true;
			}
			return false;
		}
		else return solve();
	}
	public boolean correctness() {
		boolean correct = true;
		for(int i = 0; i<this.freeInRow.length;++i) {
			correct = correct&&(freeInRow[i]>=0);
			correct = correct &&(this.tentsInRow[i]>=0);
			if(freeInRow[i]==0) {
				correct = correct&&(tentsInRow[i]==0);
			}
		}
		for(int i = 0; i<this.freeInCol.length;++i) {
			correct = correct&&(freeInCol[i]>=0);
			correct = correct &&(this.tentsInCol[i]>=0);
			if(freeInCol[i]==0) {
				correct = correct&&(tentsInCol[i]==0);
			}
		}
		return correct;
	}
	
	
	
	public void removeFree(Square free) {
		this.freeInCol[free.getX()] --;
		this.freeInRow[free.getY()]--;
		this.getLeftFree().remove(free);
	}
	public int getTreeNum() {
		return treeNum;
	}
	public void setTreeNum(int treeNum) {
		this.treeNum = treeNum;
	}
	public int[] getTentsInRow() {
		return tentsInRow;
	}
	public void setTentsInRow(int[] tentsInRow) {
		this.tentsInRow = tentsInRow;
	}
	public int[] getTentsInCol() {
		return tentsInCol;
	}
	public void setTentsInCol(int[] tentsInCol) {
		this.tentsInCol = tentsInCol;
	}
	public int[] getFreeInRow() {
		return freeInRow;
	}
	public void setFreeInRow(int[] freeInRow) {
		this.freeInRow = freeInRow;
	}
	public int[] getFreeInCol() {
		return freeInCol;
	}
	public void setFreeInCol(int[] freeInCol) {
		this.freeInCol = freeInCol;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public Square[][] getWorldMatrix() {
		return worldMatrix;
	}
	public void setWorldMatrix(Square[][] worldMatrix) {
		this.worldMatrix = worldMatrix;
	}
	public LinkedList<Tree> getLeftTreeArray() {
		return leftTreeArray;
	}
	public void setLeftTreeArray(LinkedList<Tree> leftTreeArray) {
		this.leftTreeArray = leftTreeArray;
	}
	public LinkedList<Square> getLeftFree() {
		return leftFree;
	}
	public void setLeftFree(LinkedList<Square> leftFree) {
		this.leftFree = leftFree;
	}
	

}
