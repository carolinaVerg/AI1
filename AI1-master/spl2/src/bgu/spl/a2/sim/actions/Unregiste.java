package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class Unregiste extends Action<String>{
	String Student;
	String Course;

	public Unregiste(String Student, String Course) {
		this.Student=Student;
		this.Course=Course;
	}
	
	protected void start() {
		CoursePrivateState CourseState=(CoursePrivateState) pool.getActors().get(Course);
		StudentPrivateState StudentState=(StudentPrivateState) pool.getActors().get(Student);
		if(CourseState.getRegStudents().contains(Student)) {
			CourseState.getRegStudents().remove(Student);
			CourseState.incavailableSpots();
			CourseState.decRegistered();
			StudentState.removeCourse(Course);
		}
		this.complete("done");
		
	}

}
