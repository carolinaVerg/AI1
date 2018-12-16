import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
public class HumanAgent extends Agents {

    public HumanAgent(AgentState state) {
        super(state);
    }

    @Override
    public Action agentFunc(int deadline, int peopleToSave, AgentState otherAgent,LinkedList<Vertex> vertices){
        printHeadlineMsg(deadline);     // prints current state
        return getNextAction(deadline,peopleToSave);
    }

    public Action getNextAction(int deadline,int peopleToSave) { // gets the next move from user and updates current state
        int peopleSaved=0; /// ???
        System.out.println("Select an Action:" +'\n'
                + "For 'No-op' enter -1" + '\n' +
                "For 'move-to-vertex' enter vertex Id");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int act = Integer.parseInt(reader.nextLine());
        if(act == -1){ // return 'no-op' action
            this.State.setDeadLine(deadline - 1);
            return new Action(this.State.getDeadLine(),peopleSaved,null);
        }
        else{ // return 'move' action
            Vertex nextV = this.State.getVertex().getNeighborByVid(act);
            // state updates
            peopleSaved = updateState(deadline,peopleToSave,nextV);
            // action
            return new Action(State.getDeadLine(),peopleSaved,nextV);
        }
    }

    public int updateState(int deadline, int peopleToSave, Vertex nextV){ // returns numOfPeople saved
        int peopleSaved = 0;
        this.State.setDeadLine(deadline - evalCost(this.State.getVertex().getEdgeWeight(nextV.getId()),this.State.getPeopleOn(),main.kConst));
        this.State.setVertex(nextV.getId());
        if(nextV.isIsShelter()){
            if(this.State.getDeadLine() >= 0) {
                peopleSaved = this.State.getPeopleOn();
                this.State.setPeopleToSave(this.State.getPeopleToSave()-peopleSaved);
                this.State.setPeopleOn(0);
            }
        }
        else{
            this.State.setPeopleOn(this.State.getPeopleOn() + (nextV.getPeople()));
        }
        this.State.setPeopleToSave(peopleToSave - State.getPeopleOn());
        return peopleSaved;
    }

    public void printHeadlineMsg(int deadline){
        System.out.println("----  Human Agent  ---- \n\n" +
                "Current state:");
        System.out.format("Deadline:                   %d\n",deadline);
        System.out.format("Current vertex ID:          %d\n",this.State.getVertex().getId());
        System.out.format("Number of people on agent:  %d\n",this.State.getPeopleOn());
        System.out.format("Number of people to save:   %d\n",this.State.getPeopleToSave());
        System.out.println("Neighbors <Vertex ID , Edge's weight, Number of people, Shelter?> :");
        Iterator<Pair> edgeIter = this.State.getVertex().getEdges().listIterator(0);
        while(edgeIter.hasNext()){
            Pair edge = edgeIter.next(); Vertex v = edge.getVertex(); String shel = "Not Shelter";
            if (v.isIsShelter()) {shel = "Shelter";}
            System.out.format("ID: %d  , Weight: %d  , Number of people:  %d  , %s\n"
                    ,v.getId(),edge.getWeight(),v.getPeople(),shel);
        }
    }

}
