import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
public class main {
    public static int kConst = 0;
    public static int vNoOps = 0;
    // K
    //update messages in the begin
    //simulator
    //GUI

	public static void main(String[] args) {
		File file = new File("C:\\Users\\Oro\\Desktop\\FROMLAB\\AI1\\AI1\\src\\tests\\test1.txt"); //graph description
        Graph world = null;
        BufferedReader br = null;
		String st = "";
		world = initWorld(br,world,st,file);
		Agents agents[] = initializeAgents(world);
		// get k
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
        System.out.println("eneter K const:");
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
            }
        }
        return agents;
    }

    public static void simulator‬‬(Graph world ,Agents[] agents) {
		Graph state=world;
		Action newAction;
		while (world.getDeadLine() > 0) {
		    for(Agents a: agents) {
		        if(world.getDeadLine() > 0) {
                    newAction = a.agentFunc(world.getDeadLine(), world.getPeopleNotRescude(), kConst);
                    updateWorld(newAction, world);
                    //display current state
                }
			}
		}
	}

    private static void updateWorld(Action newAction, Graph world) {
		world.setPeopleNotRescude(world.getPeopleNotRescude() -newAction.peopleSaved);
		world.setPeopleRescude(world.getPeopleRescude()+ newAction.peopleSaved);
		world.setDeadLine(newAction.deadline);
        if(newAction.EdgeToBlock != null){ // vandal agent case
            world.removeEdge(newAction.vertexLocation.getId(),newAction.EdgeToBlock.getId());
        }
        else{
            world.getVertexById(newAction.vertexLocation.getId()).setPeople(0);
        }
		world.getVertexById(newAction.vertexLocation.getId()).setPeople(0);
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
    }

    public static void updateDeadline(String[] data, Graph world){
       world.setDeadLine(Integer.parseInt(data[1]));
    }

	
	}

