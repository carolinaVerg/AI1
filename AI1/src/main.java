import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
public class main {
    public static int kConst = 0;
    public static int vNoOps = 0;
    public static Graph world=null;
    public static int bignum=0;
    public static int numOfExpands = 0;
    // K
    //update messages in the begin
    //simulator
    //GUI

	public static void main(String[] args) {
		File file = new File("C:\\Users\\Oro\\Desktop\\FROMLAB\\AI1\\AI1\\src\\tests\\test1_4v_170p.txt"); //graph description
        BufferedReader br = null;
		String st = "";
/*		world=new Graph(4);
		world.setDeadLine(20);
		Vertex v1=world.getVertexById(1);
		Vertex v2=world.getVertexById(2);
		Vertex v3=world.getVertexById(3);
		Vertex v4=world.getVertexById(4);
		v1.setIsShelter(true);
		v2.setPeople(1);
		v4.setPeople(2);
		world.setPeopleNotRescude(3);
		v1.addEdge(4 , v3);
		v3.addEdge(4, v1);
		
		v1.addEdge(1, v2);
		v2.addEdge(1, v1);
		
		v2.addEdge(1, v3);
		v3.addEdge(1, v2);
		
		v2.addEdge(5, v4);
		v4.addEdge(5, v2);
		
		v3.addEdge(1, v4);
		v4.addEdge(1, v3);  
		
		bignum=12;*/
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
        vNoOps = world.getVerticesNum();
        System.out.println("enter K const:");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        kConst = Integer.parseInt(reader.nextLine());
        return world;
    }

    private static Agents[] initializeAgents(Graph world) {
	    System.out.println("how many agents?");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int n = Integer.parseInt(reader.nextLine());
        Agents agents[] = new Agents[n];
        String[] input;
        System.out.println("press 1 for Human agent");
        System.out.println("press 2 for Greedy agent");
        System.out.println("press 3 for Vandal agent");
        System.out.println("press 4 for Greedy Search agent");
        System.out.println("press 5 for A* agent");
        System.out.println("press 6 for Real time A* agent");
        int peopleToSave= world.getPeopleNotRescude();
        for (int i = 0; i < agents.length; i++) {
            System.out.format("please choose the %d'th agent followed by starting position\n", i+1);
            input = reader.nextLine().split(" ");
            Vertex startV = world.getVertexById(Integer.parseInt(input[1]));
            int deadline = world.getDeadLine();
            switch (input[0]) {
                case "1":
                    agents[i] = new HumanAgent(new AgentState(startV,deadline,peopleToSave));
                    break;
                case "2":
                    agents[i] = new GreedyAgent(new AgentState(startV,deadline,peopleToSave));
                    break;
                case "3":
                    agents[i] = new VandalAgent(new AgentState(startV,deadline,0));
                    break;
                case "4":
                    agents[i] = new GreedySerchAgent(new AgentState(startV,deadline,peopleToSave));
                    break;
                case "5":
                    agents[i] = new AStarAgent(new AgentState(startV,deadline,peopleToSave));
                    break;
                case "6":
                    System.out.println("Enter the threshold expand limit");
                    int expLimit = reader.nextInt();
                    agents[i] = new RealTimeAStarAgent(new AgentState(startV,deadline,peopleToSave),expLimit);
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
		    for(Agents a: agents) {
		        if(world.getDeadLine() > 0 ) {
                    newAction = a.agentFunc(world.getDeadLine(), world.getPeopleNotRescude());
                    updateWorld(newAction, world);
                    displayAgentInWorld(a);
                    //display current state
                }
			}
		}
		displayWorld(world);
		// print world at deadline
	}

    private static void displayAgentInWorld(Agents agent) {
        System.out.println("--------------------------------");
        System.out.format("Current deadline: %d\n", main.world.getDeadLine());
	    System.out.println("Agent State:");
	    System.out.format("current  vertex:           %d\n",agent.State.getVertex().getId());
        System.out.format("Number of people on agent: %d\n",agent.State.getPeopleOn());
        System.out.format("Number of people to save:  %d\n",agent.State.getPeopleToSave());
    }

    private static void displayWorld(Graph world) {
        System.out.println("please enter the f parameter to evaluate the performance:");
        Scanner reader = new Scanner(System.in);
        int f = reader.nextInt();
        System.out.println("\n--------------------------------");
        System.out.println("Deadline is over, current world state:");
        System.out.format("Number of people saved:       %d\n",world.getPeopleRescude());
        System.out.format("Number of people Not rescued: %d\n",world.getPeopleNotRescude());
        int p = f*world.getPeopleRescude() + numOfExpands;
        System.out.format("The Agents performance: %d\n",p);
    }

    private static void updateWorld(Action newAction, Graph world) {
		world.setPeopleNotRescude(world.getPeopleNotRescude() -newAction.getPeopleSaved());
		world.setPeopleRescude(world.getPeopleRescude()+ newAction.getPeopleSaved());
		world.setDeadLine(newAction.getDeadline());
        if(newAction.getEdgeToBlock() != null){ // vandal agent case
            world.removeEdge(newAction.getVertexLocation().getId(),newAction.getEdgeToBlock().getId());
        }
        else{
            if(newAction.getVertexLocation() != null) {
                world.getVertexById(newAction.getVertexLocation().getId()).setPeople(0);
            }
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

