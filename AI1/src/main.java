import java.io.*; 
public class main {


	public static void main(String[] args) {
		File file = new File("C:\\Users\\Oro\\Desktop\\FROMLAB\\AI1\\AI1\\src\\tests\\test1.txt"); //graph description
		BufferedReader br;
		String st;
        Graph world;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	public void simulator‬‬(Graph world ,Agents[] agents) {
		boolean stopWorld=false;
		Graph state=world;
		Action newAction;
		while (!stopWorld) {
			for(Agents a: agents) {
				newAction=a.AgentFunc(world);
				
			}
		}
	}

    public static void updateVertex(String[] data,Graph world){
        int vid = Integer.parseInt(data[1]);
        Boolean isShelter = ("S".equals(data[2]));
        int NumOfPeople = 0;
        if(!isShelter){
            NumOfPeople = Integer.parseInt(data[3]);
        }
        Vertex newV = world.getVertexById(vid);
        newV.setIsShelter(isShelter);
        newV.setPeople(NumOfPeople);
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

