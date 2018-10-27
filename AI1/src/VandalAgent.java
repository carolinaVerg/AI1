import java.util.Iterator;

public class VandalAgent extends Agents {
    int numOfNoOp;
    boolean block;

    public VandalAgent(AgentState state) {
        super(state);
        numOfNoOp = 0;
        block = true;
    }

    @Override
    public Action agentFunc(int deadLine, int peopleToSave, int k) {
        Vertex edgeToBlock;
        if (block = true) {
            block = false;
            if (numOfNoOp < main.vNoOps) {
                numOfNoOp++;
                return new Action(deadLine - 1, 0, null, null, 0);  //NoOp
            } else {
                numOfNoOp = 0;
                Pair minPair = getMinEdge();
                if (minPair == null)
                    return new Action(deadLine - 1, 0, null, null, 0);  //NoOp
                else
                    return new Action(deadLine - 1, 0, State.getVertex(), minPair.getVertex(), 0);
            }

        } else {
            block = true;
            Iterator<Pair> iter = this.State.getVertex().getEdges().listIterator(0);
            Pair minPair = getMinEdge();
            if (minPair == null)
                return new Action(deadLine - 1, 0, null, null, 0);  //NoOp
            else {
                this.State.setVertex(minPair.getVertex());
                return new Action(deadLine, 0, null, null, 0); //Traverse
            }
        }
    }

    public Pair getMinEdge() {
        Iterator<Pair> iter = this.State.getVertex().getEdges().listIterator(0);
        Pair currentPair = null;
        Pair minPair = null;
        while (iter.hasNext()) {
            currentPair = iter.next();
            if (minPair == null)
                minPair = currentPair;
            else {
                if (minPair.getWeight() > currentPair.getWeight())
                    minPair = currentPair;
            }
        }
        return minPair;
    }

}