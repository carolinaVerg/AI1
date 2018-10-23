import java.io.*; 
public class main {
    Graph world;
    int deadLine;

	public void main(String[] args) {
		File file = new File("C:\\Users\\Oro\\Desktop\\FROMLAB\\AI1\\AI1\\src\\tests\\test1.txt"); //graph description
		BufferedReader br;
		String st; 
		try {
			br = new BufferedReader(new FileReader(file));
			  try {
                  st = br.readLine();
                  world = new Graph(Integer.parseInt(st.split(" ")[1]));
                  while ((st = br.readLine()) != null) {
                        String[] data = st.split(" ");
                        switch(data[0]){
							case "V":
                                updateVertex(data);
							case "E":
                                updateEdge(data);
							case "D":
							    updateDeadline(data);
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

    public void updateVertex(String[] data){
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

    public void updateEdge(String[] data){
        Vertex vfirst = world.getVertexById(Integer.parseInt(data[1]));
        Vertex vsecond = world.getVertexById(Integer.parseInt(data[2]));
        int weight = Integer.parseInt(data[4]);
        vfirst.addEdge(weight,vsecond);
        vsecond.addEdge(weight,vfirst);
    }

    public void updateDeadline(String[] data){
        deadLine = Integer.parseInt(data[1]);
    }

	
	}

