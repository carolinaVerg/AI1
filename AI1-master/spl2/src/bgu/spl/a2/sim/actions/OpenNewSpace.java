package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class OpenNewSpace extends Action <String> {
	String Course; 
	int Space;

	public OpenNewSpace(String Course, int Space) {
		this.Course= Course;
		this.Space=Space;
	}
	
	protected void start() {
		CoursePrivateState CourseState =(CoursePrivateState)this.pool.getActors().get(Course);
		CourseState.setAvailableSpots(Space);
		this.complete("done");
	}

}
