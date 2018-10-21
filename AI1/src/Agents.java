
public abstract class Agents {
	protected int State;
	protected Graph Percept;

	public Agents(int state) {
		this.State=state;
	}
	public void setPercept(Graph percept) {
		this.Percept=percept;

	}
	public Graph performAction() {
		return null;
	}
	

}
