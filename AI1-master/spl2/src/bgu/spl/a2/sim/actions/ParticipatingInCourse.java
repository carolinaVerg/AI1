package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class ParticipatingInCourse extends Action<String>{
	String Stud;
	String Course;
	Integer grade=null;
	List<Action<String>>actions;
	
	

	public ParticipatingInCourse(String Stud, String Course, List<String> grade) {
		this.Stud=Stud;
		this.Course= Course;
		if(grade.get(0)!="-")
			this.grade= new Integer (grade.get(0));
		else
			this.grade= new Integer (-1);
		actions= new ArrayList<>();
	}
	
	
	protected void start() {
		List<String> Students = new LinkedList <String>();
		Students.add(Stud);
		Computer comp = Warehouse.getInstance().getComp();
		CoursePrivateState courseState= (CoursePrivateState)pool.getActors().get(Course);
		StudentPrivateState StudState= (StudentPrivateState)pool.getActors().get(Stud);
		List <String> prerequisites= courseState.getPrequisites();
		AdministrativeCheck administrativeCheck= new AdministrativeCheck("SomeDep", Students, comp, prerequisites);
		actions.add(administrativeCheck);
		sendMessage(administrativeCheck, Course, courseState);
		then(actions, ()-> {
			if(StudState.getSignature()==comp.getsuccessSig() & courseState.getAvailableSpots()>0) {
				StudState.setGrades(Course, grade);
				courseState.decavailableSpots();
				courseState.incRegistered();
				courseState.setRegStudents(Stud);
				this.complete("done");
			}	
			this.complete("Can't register");
		});
	}

}
