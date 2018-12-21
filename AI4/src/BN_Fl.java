

public class BN_Fl extends BN_Node{
	double distribution;
	public BN_Fl(int id, double dis) {
		super(id);
		this.distribution=dis;
	}
	public double getDistribution() {
		return distribution;
	}
	public void setDistribution(double distribution) {
		this.distribution = distribution;
	}

	@Override
	public void updateCPT(){
		this.cpt = new double[2];
		this.cpt[1] = distribution;
	}


}
