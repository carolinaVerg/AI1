import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
public class main {
    public static Graph world;
    public static BN_Net bnNet;


	public static void main(String[] args) {
		File file = new File("C:\\univercity\\courses\\semester5\\intro to AI\\programming assignments\\AI4\\AI4\\tests\\test1.txt"); //graph description
        BufferedReader br = null;
		String st = "";
		initWorld(br,st,file);
        constructBayesNet();
	}

    private static void constructBayesNet() {

    }

    private static void initWorld(BufferedReader br, String st, File file){
        try {
            br = new BufferedReader(new FileReader(file));
            try {
                st = br.readLine();
                int numOfNodes=Integer.parseInt(st.split(" ")[1]);
                world = new Graph(numOfNodes);
                bnNet= new BN_Net(numOfNodes);
                while ((st = br.readLine()) != null) {
                    String[] data = st.split(" ");
                    switch(data[0]){
                        case "V":
                            updateVertex(data);
                            break;
                        case "E":
                            updateEdge(data);
                            break;
                        default:
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
        bnNet.updateCPTInNet();

    }



    public static void updateVertex(String[] data){
        int vid = Integer.parseInt(data[1]);
        double dis=Double.parseDouble(data[3]);
        ((BN_Fl)bnNet.getFls().get(vid)).setDistribution(dis);

    }

    public static void updateEdge(String[] data){
    	int edgeId=Integer.parseInt(data[1]);
    	int idN1=Integer.parseInt(data[3]);
    	int idN2=Integer.parseInt(data[4]);
        Vertex vfirst = world.getVertexById(idN1);
        Vertex vsecond = world.getVertexById(idN2);
        int weight = Integer.parseInt(data[6]);
        vfirst.addEdge(weight,vsecond);
        vsecond.addEdge(weight,vfirst);
        BN_B newB= new BN_B(edgeId, weight);
        bnNet.getBs().put(edgeId,newB);
        updateBnNet(idN1, idN2,edgeId);
     
    }
    public static void updateBnNet(int v1, int v2, int edge) {
    	BN_Node newB= bnNet.getBs().get(edge);
    	BN_Node fl1=bnNet.getFls().get(v1);
    	BN_Node fl2=bnNet.getFls().get(v2);
    	BN_Node ev1=bnNet.getEvs().get(v1);
    	BN_Node ev2=bnNet.getEvs().get(v2);
    	
    	newB.getParents().put(v1, fl1);
    	newB.getParents().put(v2, fl2);
    	newB.getChildren().put(v1, ev1);
    	newB.getChildren().put(v2, ev2);
    	
    	fl1.getChildren().put(edge, newB);
    	fl2.getChildren().put(edge, newB);
    	
    	ev1.getParents().put(edge, newB);
    	ev2.getParents().put(edge, newB);
    }


	
	}

