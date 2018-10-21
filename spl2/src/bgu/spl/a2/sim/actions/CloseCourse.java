package bgu.spl.a2.sim.actions;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class CloseCourse extends Action<String> {
	String Department;
	String Course;
	private List<Action<?>>actions;
	
	public CloseCourse(String Department, String Course) {
		this.Department= Department;
		this.Course=Course;
		actions =new LinkedList<Action<?>>();
	}
	
	protected void start() {
		Unregiste UnregisteStud;
		DepartmentPrivateState DepState =(DepartmentPrivateState) pool.getActors().get(Department);
		CoursePrivateState CourseState= (CoursePrivateState) pool.getActors().get(Course);
		for(int i=0; i<CourseState.getRegistered(); i++) {
			UnregisteStud =new Unregiste(CourseState.getRegStudents().get(i), Course);
			actions.add(UnregisteStud);
			}
		for(int i=0; i<CourseState.getRegistered(); i++) {
			UnregisteStud=(Unregiste) actions.get(i);
			sendMessage(UnregisteStud, Department, new DepartmentPrivateState());
		}
		then(actions,()->{
			DepState.getCourseList().remove(Course);
			CourseState.setAvailableSpots(-1);
			this.complete("done");
		});
		
	}

}
