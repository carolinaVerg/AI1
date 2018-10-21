package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class RegisterWithPreference extends Action<String>{

	String Student;
	List<String> Preferences;
	List<String> grade;
	List<Action<String>>actions;
	
	

	public RegisterWithPreference(String Student, List<String> Preferences, List<String> grade) {
		this.Student=Student;
		this.Preferences= Preferences;
		this.grade=grade;
		actions= new ArrayList<>();
	}
	
	
	protected void start() {
		ParticipatingInCourse tryToRegister;
		LinkedList<String> OneGarade;
		boolean KeepTrying=true;
		for(int i=0; i<grade.size() & KeepTrying; i++) {
			OneGarade=new LinkedList<String>();
			OneGarade.add(grade.get(i));
			tryToRegister= new ParticipatingInCourse(Student, Preferences.get(i), OneGarade);
			actions.add(tryToRegister);
			sendMessage(tryToRegister, Preferences.get(i), new CoursePrivateState());
			then(actions, ()-> {});
			if(tryToRegister.getResult().get()=="done")
				KeepTrying=false;
		}
		this.complete("done");
	}
}
