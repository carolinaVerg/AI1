package bgu.spl.a2;

import java.util.ArrayList;
import java.util.List;

public class PrintThis extends Action<String>{
	
	List<Action<String>>actions;
	
	public PrintThis(){
		this.setActionName("printthis");
		actions= new ArrayList<>();
	}
	protected void start() {
		then(actions, ()->{
			this.complete("Hallo!");
			System.out.println(this.getResult().get());	
		});
			
	
	}

}
