package bgu.spl.a2;


import java.util.ArrayList;
import java.util.List;




public class PrintSomthing extends Action<String> {

	List<Action<String>> actions;
	
	public PrintSomthing() {
		this.setActionName("printsomthing");
		actions= new ArrayList<>();
	}
	
	protected void start() {
		Action <String> printAstring= new PrintThis();
		sendMessage(printAstring,"actor1",new PrintState());
		actions.add(printAstring);
		then(actions, ()->{
			this.complete(actions.get(0).getResult().get());
			});
		}
	}


