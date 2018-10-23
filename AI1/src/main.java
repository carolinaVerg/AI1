import java.io.*; 
public class main {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\Oro\\Desktop\\FROMLAB\\AI1\\AI1\\src\\tests\\test1.txt"); //graph description
		int deadLine;
		BufferedReader br;
		String st; 
		try {
			br = new BufferedReader(new FileReader(file));
			  try {
					while ((st = br.readLine()) != null) {
						int indexOfHashtag = st.indexOf('#');
						switch(st.charAt(indexOfHashtag +1)){
							case 'V':

							case 'E':
							case 'D':
							    deadLine = Integer.parseInt(st.substring(indexOfHashtag+2));
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
	private void simulator‬‬(Graph world ,Agents[] agents) {
		boolean stopWorld=false;
		Graph state=world;
		Action newAction;
		while (!stopWorld) {
			for(Agents a: agents) {
				newAction=a.AgentFunc(world);
				
			}
		}
	}
	
		}

