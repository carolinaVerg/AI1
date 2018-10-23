package bgu.spl.a2.sim;
import java.awt.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import bgu.spl.a2.Promise;

/**
 * 
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * 
 * Note: this class can be implemented without any synchronization. 
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 *
 */
public class SuspendingMutex {
	private Computer computer;
	private ConcurrentLinkedQueue<Promise<Computer>> Promises;
	private AtomicBoolean flag;
	/**
	 * Constructor
	 * @param computer
	 */
	public SuspendingMutex(Computer computer){
		this.computer=computer;
		this.Promises = new ConcurrentLinkedQueue<Promise<Computer>>();
		flag = new AtomicBoolean(true);
	}
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediatly
	 * 
	 * @return a promise for the requested computer
	 */
	public Promise<Computer> down(){
		Promise<Computer> returnProm =new Promise<Computer>();
		if(flag.compareAndSet(true, false)) 
			returnProm.resolve(computer);
		else
			Promises.add(returnProm);
		return returnProm;
		
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 */
	public void up(){
		if(flag.compareAndSet(false, true)) {
			if(Promises.size()>0) {
				flag.compareAndSet(true, false);
				Promises.poll().resolve(computer);
			}
		}
			
	}
}
