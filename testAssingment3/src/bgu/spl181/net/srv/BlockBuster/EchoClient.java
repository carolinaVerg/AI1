package bgu.spl181.net.srv.BlockBuster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class EchoClient {


    //configurable params
    private static int randomMultConstant = 15;
    private static int numOfCopies = 7;
    private static int numOfClients = 18;
    private static int numOfIterations = 30;
    private static Boolean testNumOfRentedCopies = false;

    //test vars
    private static int numOfRentedCopies=0;
    private static ArrayList<Thread> clientThreads = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        //clientThreads creation
        for(int i = 0 ; i < numOfIterations; i++){

            clientThreads.clear();
            for(int j=0;j<numOfClients;j++){
                final int num = j;
                Thread client= new Thread(() -> {
                    try {
                        normalClient("Client"+num);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                clientThreads.add(client);
            }
            adminClient("admin");

            //starting clients
            for(Thread client : clientThreads){
                client.start();
            }
            EchoClient.waitForThreads();
            EchoClient.returnAllCopies();

        }
        System.out.println("Test ended.");
    }
    //holds the client config
    private static void normalClient(String client) throws IOException {
        try (Socket sock = new Socket("localhost", 7877);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
            sleep((int)(Math.random()* randomMultConstant));
            ArrayList<String> cmds = new ArrayList<>();
            cmds.add("REGISTER "+client+" tryingagain country=\"Russia\"\n");
            cmds.add("LOGIN "+client+" tryingagain\n");
            cmds.add("REQUEST balance add 10\n");
            cmds.add("REQUEST info \"Alice in wonderland\"\n");
            cmds.add("REQUEST rent \"The Godfather\"\n");
            cmds.add("SIGNOUT\n");


            for (String cmd : cmds) {
                sleep((int) (Math.random()* EchoClient.randomMultConstant));
                out.write(cmd);
                out.flush();
                String line = in.readLine();
                if(line==null)
                	System.out.println("Check If Here");
                System.out.println(client+": " + line);
                while (true) {
                    if (line.contains("BROADCAST")) {
                        line = in.readLine();
                        System.out.println(client+": " + line);
                    } else
                        break;
                }
                if(line.equals("ACK rent \"The Godfather\" success"))
                    EchoClient.numOfRentedCopies++;
                if (line.equals("ACK signout succeeded")) {
                    sock.close();
                    System.out.println(client+": disconnected");
                    break;
                }


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waitForThreads() {
        for(Thread client: clientThreads){
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void returnAllCopies() throws Exception {
        if (testNumOfRentedCopies && numOfCopies!=numOfRentedCopies) {
            System.out.println("numOfCopies: "+numOfCopies);
            System.out.println("numOfRentedCopies: "+numOfRentedCopies);
            throw new Exception("numOfCopies!=numOfRentedCopies");
        }
        for(int j=0;j<numOfClients;j++){
            EchoClient.clientReturn("Client"+j);
        }
        //assumes that all return callouts went well
        numOfRentedCopies=0;
    }

    private static void adminClient(String client) throws IOException {
    	
        try (Socket sock = new Socket("localhost", 7877);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {

            ArrayList<String> cmds = new ArrayList<>();
            cmds.add("LOGIN admin admin\n");
            cmds.add("REQUEST balance add 1\n");
            cmds.add("REQUEST addmovie \"Matrix\" "+numOfCopies+" 40 \"Italy\"\n");
            cmds.add("REQUEST addmovie \"The Godfather\" "+numOfCopies+" 2\n");
            cmds.add("REQUEST remmovie \"Matrix\" \n");
            cmds.add("REQUEST addmovie \"Alice in wonderland\" "+numOfCopies+" 12\n");
            cmds.add("REQUEST info \"Alice in wonderland\" \n");
            cmds.add("SIGNOUT\n");;

            for (String cmd : cmds) {
                out.write(cmd);
                out.flush();
                String line = in.readLine();
                System.out.println(client+": " + line);
                while (true) {
                    if (line.contains("BROADCAST")) {
                        line = in.readLine();
                        System.out.println(client+": " + line);
                    } else
                        break;
                }

                if (line.equals("ACK signout succeeded")) {
                    sock.close();
                    System.out.println(client+": disconnected----------------------------------");
                }


            }
        }
    }

    private static void clientReturn(String client) throws IOException {
        try (Socket sock = new Socket("localhost", 7877);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
            ArrayList<String> cmds = new ArrayList<>();
            cmds.add("LOGIN "+client+" tryingagain\n");
            cmds.add("REQUEST return \"The Godfather\" \n");
            cmds.add("SIGNOUT\n");


            for (String cmd : cmds) {
                sleep((long) (Math.random()* EchoClient.randomMultConstant));
                out.write(cmd);
                out.flush();
                String line = in.readLine();
                System.out.println(client+": " + line);
                while (true) {
                    if (line.contains("BROADCAST")) {
                        line = in.readLine();
                        System.out.println(client+": " + line);
                    } else
                        break;
                }
                if (line.equals("ACK signout succeeded")) {
                    sock.close();
                    System.out.println(client+": disconnected");
                    break;
                }


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
