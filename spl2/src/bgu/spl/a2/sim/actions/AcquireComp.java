package bgu.spl.a2.sim.actions;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.SuspendingMutex;

public class AcquireComp extends Action<Computer> {
	private Computer comp;
	private List<Action<?>>actions;

	public AcquireComp(Computer comp) {
		this.comp= comp;
		actions= new LinkedList <Action<?>>();
		
	}
	
	protected void start() {
		SuspendingMutex compMutex= comp.getMutex();
		this.myprom=compMutex.down();
		then(actions, ()-> {});
	}

}
