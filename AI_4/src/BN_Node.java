import java.util.HashMap;

public class BN_Node {
	HashMap<Integer,BN_Node> parents;
	HashMap<Integer,BN_Node> children;
	int id;

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



}
