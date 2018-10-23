package bgu.spl.a2.sim.privateStates;

import java.util.LinkedList;

import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState{

	private Integer availableSpots;
	private Integer registered;
	private List<String> regStudents;
	private List<String> prequisites;
	
	/*
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public CoursePrivateState() {
		availableSpots= new Integer(0);
		registered=new Integer(0);
		regStudents = new LinkedList <String>();
		prequisites =  new LinkedList <String>();
		
		
	}

	public Integer getAvailableSpots() {
		return availableSpots;
	}
	
	public void setAvailableSpots(int i) {
		availableSpots= new Integer(i);
	}

	public Integer getRegistered() {
		return registered;
	}
	
	public void setRegistered(int i) {
		registered= new Integer(i);
	}
	
	public void incRegistered() {
		registered= registered+1;
	}
	
	public void decRegistered() {
		registered= registered-1;
	}
	
	public void incavailableSpots() {
		availableSpots= availableSpots+1;
	}
	
	public void decavailableSpots() {
		availableSpots= availableSpots-1;
	}

	public List<String> getRegStudents() {
		return regStudents;
	}
	
	public void setRegStudents(String Stud) {
		regStudents.add(Stud);
	}

	public List<String> getPrequisites() {
		return prequisites;
	}
}
