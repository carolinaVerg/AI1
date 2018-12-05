
public class World {
	private int treeNum;
	private char [][] worldMatrix;
	private int tentsInRow[];
	private int tentsInCol[];
	private Tree [] treeArray;
	

	public World(int treeNum,char [][] worldMatrix,int tentsInRow[],int tentsInCol[] ) {
		this.treeNum=treeNum;
		this.worldMatrix=worldMatrix;
		this.tentsInRow=tentsInRow;
		this.tentsInCol=tentsInCol;
		treeArray=new Tree[treeNum];
		genTreeArray();
	}
	private void genTreeArray() {
		int treeCounter = 0;
		for(int i = 0; i < tentsInRow.length ; ++i){
			for(int j = 0 ; j < tentsInCol.length; ++j) {
				if(worldMatrix[i][j]=='t') {
					this.treeArray[treeCounter++] = new Tree(i,j);
				}
			}
		}
	}
	

}
