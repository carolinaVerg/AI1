package bgu.spl.a2.sim;

import java.util.HashMap;
import java.util.Map;

import bgu.spl.a2.Promise;

/**
 * represents a warehouse that holds a finite amount of computers
 * and their suspended mutexes.
 * releasing and acquiring should be blocking free.
 */
public class Warehouse {
	private Map<Computer,SuspendingMutex> computerMap;
	private static Warehouse instance;
	private Computer lastAdded;
	
	private Warehouse() {
		computerMap=new HashMap<Computer,SuspendingMutex>();
		instance = new Warehouse();
	}
	
	public void addComputer(Computer comp, SuspendingMutex sumu) {
		computerMap.put(comp, sumu);
		comp.setWarehouse(this);
		lastAdded=comp;
	}
	
	public static Warehouse getInstance() {
		if(instance==null)
			instance=new Warehouse();
		return instance;
	}
	
	public Computer getComp() throws NullPointerException{
		if(computerMap.size()==0)
			throw new NullPointerException("No computers exists");
		return lastAdded;
	}
	
}
