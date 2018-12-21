import java.util.HashMap;

public class BN_Node {
	HashMap<Integer,BN_Node> parents;
	HashMap<Integer,BN_Node> children;
	int id;
	double[] cpt;

	public BN_Node(int id) {
		this.id=id;
		parents=new HashMap<>();
		children= new HashMap<>();
	}

	public HashMap<Integer, BN_Node> getParents() {
		return parents;
	}

	public void setParents(HashMap<Integer, BN_Node> parents) {
		this.parents = parents;
	}

	public HashMap<Integer, BN_Node> getChildren() {
		return children;
	}

	public void setChildren(HashMap<Integer, BN_Node> children) {
		this.children = children;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void updateCPT(){
		int numOfParents = this.parents.size();
		this.cpt = new double[(int)Math.pow(2,numOfParents)+1];
		double[] qiArray = new double[numOfParents];
		evalQi(qiArray);
		for (int i = 1; i<cpt.length; i++){
			double qiProduct = 1;
			double startRange = 0;
			double endRange = cpt.length-1;
			boolean[] boolParents = new boolean[numOfParents];
			for (int j = 0; j<boolParents.length;j++){
				double check = ((endRange-startRange)/2);
				if ((i<= ( endRange - ((endRange-startRange)/2))) && (i >= startRange)){
					boolParents[j] = false;
					endRange = endRange - (endRange-startRange)/2;
				}
				else{
					boolParents[j] = true;
					startRange = startRange + (endRange-startRange)/2;
				}
			}
			for(int k =0; k<boolParents.length;k++){
				if(boolParents[k]){
					qiProduct = qiProduct * qiArray[k];
				}
			}
			if((1-qiProduct)==0){
				this.cpt[i] = 0.001;
			}else{
				this.cpt[i] = 1-qiProduct;
			}

		}
	}

	public void evalQi(double[] qiArray) {
	}


}
