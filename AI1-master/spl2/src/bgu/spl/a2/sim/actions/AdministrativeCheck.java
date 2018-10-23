package bgu.spl.a2.sim.actions;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AdministrativeCheck extends Action<String> {

	private String Dep;
	private List<String> Students;
	private Computer comp;
	private List<String> Conditions;
	private List<Action<?>>actions;
	
	public AdministrativeCheck(String Dep, List<String> Students, Computer comp, List <String> Conditions) {
		this.Dep=Dep;
		this.Students=Students;
		this.comp=comp;
		this.Conditions=Conditions;
		actions =new LinkedList<Action<?>>();
	}
	
	protected void start() {
		AcquireComp acquireComp= new AcquireComp(comp);
		actions.add(acquireComp);
		sendMessage(acquireComp, Dep, new DepartmentPrivateState());
		then(actions, ()-> {
			StudentPrivateState StudState;
			for(int i=0; i<Students.size(); i++) {
				StudState =(StudentPrivateState)(this.pool.getActors().get(Students.get(i)));
				StudState.setSignature(comp.checkAndSign(Conditions, StudState.getGrades())); 
			}
			comp.getMutex().up();
			this.complete("done");	
		});
		
		
	}

}
