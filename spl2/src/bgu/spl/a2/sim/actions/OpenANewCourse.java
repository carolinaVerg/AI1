package bgu.spl.a2.sim.actions;
import java.util.ArrayList;
import java.util.List;
import bgu.spl.a2.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState; 

public class OpenANewCourse extends Action<String> {
	String DepName;
	String CourseName;
	int Space;
	List<String> Prerequisites;
	List<Action<String>>actions;
	
	public OpenANewCourse(String DepName, String CourseName,int Space, List<String> Prerequisites) {
		this. DepName= DepName;
		this. CourseName= CourseName;
		this. Space= Space;
		this.Prerequisites= Prerequisites;
		actions= new ArrayList<>();
	}
	
	protected void start() {
		DepartmentPrivateState DepSate =(DepartmentPrivateState)this.pool.getActors().get(DepName);
		CoursePrivateState CourseSate =new CoursePrivateState();
		OpenNewSpace OpenSpace=new OpenNewSpace(CourseName, Space);
		sendMessage(OpenSpace, CourseName, CourseSate);
		actions.add(OpenSpace);
		then(actions, () -> {
			CourseSate.getPrequisites().addAll(Prerequisites);
			DepSate.getCourseList().add(CourseName);
			this.complete("done");
		});
		
		
		
		
		
		
		
	}


}
