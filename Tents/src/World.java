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
	

	public World(int treeNum,Square [][] worldMatrix,int tentsInRow[],int tentsInCol[] ) {
		this.treeNum=treeNum;
		this.worldMatrix=worldMatrix;
		this.tentsInRow=tentsInRow;
		this.tentsInCol=tentsInCol;
		leftTreeArray=new LinkedList<>();
		this.n=worldMatrix.length;
		this.m=worldMatrix[0].length;
		genTreeList();
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
	public void onlyTentsLeft() {
			for(int i=0; i<worldMatrix.length; i++) {    //runs over calms
				if(this.tentsInCol[i]==this.freeInCol[i])
					for (int j=0; j<worldMatrix[i].length; i++)
						if((worldMatrix[i][j].getObj())=='f')
						{
							Tent tent =new Tent(worldMatrix[i][j].getX(), worldMatrix[i][j].getY());
							tent.eliminateAdj();
							this.tentsInRow[j]--;
							this.tentsInCol[i]--;
							worldMatrix[i][j]= tent;
						}
			}
			for(int i=0; i<worldMatrix[0].length; i++) {  // runs over rows
				if(this.tentsInRow[i]==this.freeInCol[i]) {
					for(Square s=worldMatrix[0][i];s!=null; s =s.getRight()) {
						if(s.getObj()=='f')
						{
							Tent tent =new Tent(s.getX(), s.getY());
							tent.eliminateAdj();
							this.tentsInRow[s.getY()]--;
							this.tentsInCol[s.getX()]--;
							worldMatrix[s.getX()][s.getY()]= tent;
						}
					}
				}			
			}	
	}
	public void onlyGrassLeft() {
		
	}
	public void oneOptionLeftToTree() {
		LinkedList<Tree> newTreeList=(LinkedList<Tree>) this.leftTreeArray.clone();
		for(Tree t:leftTreeArray){
			if(t.maybeTent()==1) {
				Tent tent;
				if(t.up!=null && t.up.obj=='f') {
					tent =new Tent(t.getUp().getX(), t.getUp().getY());
					tent.eliminateAdj();
					this.tentsInRow[t.getUp().getY()]--;
					this.tentsInCol[t.getUp().getX()]--;
					worldMatrix[t.getUp().getX()][t.getUp().getY()]= tent;
				}

				else if(t.down!=null && t.down.obj=='f'){
					tent =new Tent(t.getDown().getX(), t.getDown().getY());
					tent.eliminateAdj();
					this.tentsInRow[t.getDown().getY()]--;
					this.tentsInCol[t.getDown().getX()]--;
					worldMatrix[t.getDown().getX()][t.getDown().getY()]= tent;
				}

				else if(t.left!=null && t.left.obj=='f'){
					tent =new Tent(t.getLeft().getX(), t.getLeft().getY());
					tent.eliminateAdj();
					this.tentsInRow[t.getLeft().getY()]--;
					this.tentsInCol[t.getLeft().getX()]--;
					worldMatrix[t.getLeft().getX()][t.getLeft().getY()]= tent;
				}
			
				else {
					tent =new Tent(t.getRight().getX(), t.getRight().getY());
					tent.eliminateAdj();
					this.tentsInRow[t.getRight().getY()]--;
					this.tentsInCol[t.getRight().getX()]--;
					worldMatrix[t.getRight().getX()][t.getRight().getY()]= tent;
				}
				newTreeList.remove(t);
			}
				
		}
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
	

}
