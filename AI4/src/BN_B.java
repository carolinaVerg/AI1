

public class BN_B extends BN_Node{
    int weight;
	public BN_B(int id, int weight) {
		super(id);
		this.weight = weight;
	}

    @Override
    public void evalQi(double[] qiArray) {
	    for (int i = 0 ; i< qiArray.length;i++){
	        qiArray[i] = 1 - (0.6 / this.weight);
        }
    }
}
