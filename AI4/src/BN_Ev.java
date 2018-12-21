

public class BN_Ev extends BN_Node{

	public BN_Ev(int id) {
		super(id);
	}

	@Override
	public void evalQi(double[] qiArray) {
		int count = 0;
		for (BN_Node b:this.parents.values()){
			int weight = ((BN_B)b).weight;
			if(weight >= 4){
				qiArray[count] = 1 - 0.8;
			}
			else{
				qiArray[count] = 1 - 0.4;
			}
			count++;
		}
	}

}
