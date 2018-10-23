package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class AddStudent extends Action<String>{
	String Dep;
	String Stud;

	public AddStudent(String Dep, String Stud) {
		this.Dep= Dep;
		this.Stud= Stud;
	}
	
	protected void start() {
		DepartmentPrivateState DepState =(DepartmentPrivateState)this.pool.getActors().get(Dep);
		DepState.getStudentList().add(Stud);
		this.complete("done");
	}

}
