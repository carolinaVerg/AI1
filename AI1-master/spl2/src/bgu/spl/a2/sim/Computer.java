package bgu.spl.a2.sim;

import java.util.List;
import java.util.Map;

public class Computer {

	String computerType;
	long failSig;
	long successSig;
	private Warehouse warehouse;
	private SuspendingMutex mutex;
	
	public Computer(String computerType) {
		this.computerType = computerType;
		mutex= new SuspendingMutex(this);
	}
	
	/**
	 * this method checks if the courses' grades fulfill the conditions
	 * @param courses
	 * 							courses that should be pass
	 * @param coursesGrades
	 * 							courses' grade
	 * @return a signature if couersesGrades grades meet the conditions
	 */
	public long checkAndSign(List<String> courses, Map<String, Integer> coursesGrades){
		for(int i=0; i<courses.size(); i++) {
			if(coursesGrades.get(courses.get(i))<56)
				return failSig;
		}
		return successSig;
	}
	
	public long getsuccessSig() {
		return successSig;
	}
	public long getfailSig() {
		return failSig;
	}
	
	public void setsuccessSig(long sig) {
		successSig=sig;
	}
	
	public void setfailSig(long sig) {
		failSig=sig;
	}
	
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse =warehouse;
	}
	
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	public SuspendingMutex getMutex() {
		return mutex;
	}
}
