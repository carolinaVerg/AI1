import java.util.Iterator;

public class Agents {
    protected AgentState State;


    public Agents(AgentState state) {
        this.State = state;

    }


    public Action AgentFunc(int deadLine, int peopleToSave) {
        return null;
    }


    public AgentState getState() {
        return State;
    }


    public void setState(AgentState state) {
        State = state;
    }


    public Vertex TreeSearch(BinaryHeap<TreeVertex> fringe, String goal) { // finds shortest path according to current goal
        TreeVertex current;
        TreeVertex source = fringe.peek();
        while (!fringe.isEmpty()) {
            current = fringe.remove(); // takes first from the priority queue
            switch (goal) {
                case "shelter":
                    if (current.getState().getVertex().isIsShelter()) {
                        return findNextVer(source,current);
                    }
                    else {
                        Expand(current, fringe);
                    }
                    break;
                case "people":
                    if (current.getState().getVertex().getPeople() > 0)
                        return findNextVer(source,current);
                    else
                        Expand(current, fringe);
                    break;
            }
        }
        return null;

    } //returns next v for the action

    public Vertex findNextVer(TreeVertex sourceV, TreeVertex currV) {
        Vertex vertexToRet = currV.getState().getVertex();
        while (currV.getParent().getState().getVertex().getId() != sourceV.getState().getVertex().getId()) {
            currV = currV.getParent();
            vertexToRet = currV.getState().getVertex();
        }
        return vertexToRet;
    } // finds next v parents...

    public BinaryHeap<TreeVertex> Expand(TreeVertex currentState, BinaryHeap<TreeVertex> fringe) { // returns updated fringe
        TreeVertex newVertex;
        Iterator<Pair> iter = currentState.getState().getVertex().getEdges().listIterator(0);
        Pair currentPair;
        AgentState newState;
        while (iter.hasNext()) {
            currentPair = iter.next();
            newVertex = new TreeVertex(new AgentState(currentPair.getVertex()),
                    currentState, currentState.getCost() + currentPair.getWeight());
            fringe.add(newVertex);

        }
        return fringe;
    }

    public int evalCost() {
        return 0;
    }


}
