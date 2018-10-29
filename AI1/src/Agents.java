import java.util.Iterator;

public class Agents {
    protected AgentState State;

    public Agents(AgentState state) {
        this.State = state;
    }

    public Action agentFunc(int deadLine, int peopleToSave) {
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
                case "optimal":
                    AgentState currState = current.getState();
                    if (currState.getPeopleToSave() == 0 || currState.getDeadLine() <= 0){
                        return  findNextVer(source,current);
                    }
                    else{
                        Expand(current, fringe);
                    }
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
        int deadline;
        AgentState newState;
        while (iter.hasNext()) {
            currentPair = iter.next();
            /* what deadline for new agent state??? */
            deadline = evalCost(currentPair.getWeight(),main.kConst,currentState.State.getPeopleOn());
            newVertex = new TreeVertex(new AgentState(currentPair.getVertex(),deadline,0),
                    currentState, currentState.getCost() + currentPair.getWeight());
            fringe.add(newVertex);

        }
        return fringe;
    }

    public static int evalCost(int edgeWeight, int k, int numOfPeople){
        return edgeWeight * ( 1 + (k * numOfPeople));
    }



}
