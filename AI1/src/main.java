import java.io.*; 
public class main {



	public static void main(String[] args) {
		File file = new File("home/carolina/Desktop/UntitledDocument"); //graph description
		
		BufferedReader br;
		String st; 
		try {
			br = new BufferedReader(new FileReader(file));
			  try {
					while ((st = br.readLine()) != null) 
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
		while (!stopWorld) {
			
		}
	}‬‬
	
	

}
