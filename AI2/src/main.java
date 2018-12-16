import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
public class main {
    public static int kConst = 0;
    public static int vNoOps = 0;
    public static Graph world=null;
    public static int bignum=0;
    public static int numOfExpands = 0;
    public static int gameType=0;
    public static int agentCSimSavedP = 0;
    public static int agentOSimSavedP = 0;


	public static void main(String[] args) {
		File file = new File("/home/carolina/eclipse-workspace/AI2/tests/test3.txt"); //graph description
        BufferedReader br = null;
		String st = "";
		world = initWorld(br,world,st,file);
		Agents agents[] = initializeAgents(world);
		simulator‬‬(world,agents);
	}

	private static Graph initWorld(BufferedReader br, Graph world, String st, File file){
        try {
            br = new BufferedReader(new FileReader(file));
            try {
                st = br.readLine();
                world = new Graph(Integer.parseInt(st.split(" ")[1]));
                while ((st = br.readLine()) != null) {
                    String[] data = st.split(" ");
                    switch(data[0]){
                        case "V":
                            updateVertex(data,world);
                            break;
                        case "E":
                            updateEdge(data,world);
                            break;
                        case "D":
                            updateDeadline(data, world);
                            break;
                    }
                }
                System.out.println(st);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        vNoOps = world.getVerticesNum();
        System.out.println("enter K const:");
        kConst = Integer.parseInt(reader.nextLine());
        System.out.println("press 1 for an adversarial ");
        System.out.println("press 2 for a semi-cooperative game");
        System.out.println("press 3 for a fully cooperative");
       
        gameType =Integer.parseInt(reader.nextLine());
        return world;
    }

    private static Agents[] initializeAgents(Graph world) {
    	Scanner reader = new Scanner(System.in); 
        Agents agents[] = new Agents[2];
        String[] input;
        System.out.println("press 1 for Human agent");
        System.out.println("press 2 for game tree search agent");
        int peopleToSave= world.getPeopleNotRescude();
        for (int i = 0; i < agents.length; i++) {
            System.out.format("please choose the %d'th agent followed by starting position\n", i+1);
            input = reader.nextLine().split(" ");
            Vertex startV = world.getVertexById(Integer.parseInt(input[1]));
            int deadline = world.getDeadLine();
            switch (input[0]) {
                case "1":
                    agents[i] = new HumanAgent(new AgentState(startV.getId(),deadline,peopleToSave, world.getVertices()));
                    break;
                case "2":
                    agents[i] = new GameTreeSearchAgent(new AgentState(startV.getId(),deadline,peopleToSave, world.getVertices()));
                    break;
          
                    default:
                        break;
            }
        }
        return agents;
        
    }

    public static void simulator‬‬(Graph world ,Agents[] agents) {
 		Graph state=world;
 		Action newAction;
 		while (world.getDeadLine() > 0 && world.getPeopleNotRescude()>0) {
 		    for(int i=0; i<agents.length; i++) {
 		    	Agents a=agents[i];	    	
 		        if(world.getDeadLine() > 0 ) {
 		            if(world.getDeadLine()==8){
 		                int alls = 0;
                    }
                     newAction = a.agentFunc(world.getDeadLine(), world.getPeopleNotRescude(),agents[(i+1)%2].getState(),world.getVertices());
                     updateWorld(newAction, world);
                     displayAgentInWorld(a,i+1);
                     //display current state
                 }
 			}
 		}
 		displayWorld(world,agents);
 		// print world at end of deadline
     }

    private static void displayAgentInWorld(Agents agent,int playerNum) {
        System.out.println("--------------------------------");
        System.out.format("Current deadline: %d\n", main.world.getDeadLine());
        System.out.format("Player number :%d",playerNum);
	    System.out.println("Agent State:");
	    System.out.format("current  vertex:           %d\n",agent.State.getVertex().getId());
        System.out.format("Number of people on agent: %d\n",agent.State.getPeopleOn());
        System.out.format("Number of people to save:  %d\n",agent.State.getPeopleToSave());
        System.out.format("Number of people saved:       %d\n",agent.getState().getPeopleSaved());
    }

    private static void displayWorld(Graph world,Agents[] agents) {
        System.out.println("\n--------------------------------");
        System.out.println("        GAME OVER       ");
        System.out.format("Score of Agent #1: %d\n",agents[0].State.getPeopleSaved());
        System.out.format("Score of Agent #2: %d\n",agents[1].State.getPeopleSaved());
        System.out.format("\nTotal number of people saved:       %d\n",world.getPeopleRescude());
        System.out.format("Number of people Not saved: %d\n",world.getPeopleNotRescude());
    }

    private static void updateWorld(Action newAction, Graph world) {
		world.setPeopleNotRescude(world.getPeopleNotRescude() -newAction.getPeopleSaved());
		world.setPeopleRescude(world.getPeopleRescude()+ newAction.getPeopleSaved());
		world.setDeadLine(newAction.getDeadline());
        if(newAction.getVertexLocation() != null) {
            world.getVertexById(newAction.getVertexLocation().getId()).setPeople(0);  
        }
	}

    public static void updateVertex(String[] data,Graph world){
        int vid = Integer.parseInt(data[1]);
        Boolean isShelter = ("S".equals(data[2]));
        int numOfPeople = 0;
        if(!isShelter){
            numOfPeople = Integer.parseInt(data[3]);
        }
        Vertex newV = world.getVertexById(vid);
        newV.setIsShelter(isShelter);
        newV.setPeople(numOfPeople);
        world.setPeopleNotRescude(world.getPeopleNotRescude() + numOfPeople);
    }

    public static void updateEdge(String[] data,Graph world){
        Vertex vfirst = world.getVertexById(Integer.parseInt(data[1]));
        Vertex vsecond = world.getVertexById(Integer.parseInt(data[2]));
        int weight = Integer.parseInt(data[4]);
        vfirst.addEdge(weight,vsecond);
        vsecond.addEdge(weight,vfirst);
        main.bignum=main.bignum+weight;
    }

    public static void updateDeadline(String[] data, Graph world){
       world.setDeadLine(Integer.parseInt(data[1]));
    }

	
	}

