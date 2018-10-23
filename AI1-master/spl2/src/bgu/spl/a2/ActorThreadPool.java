package bgu.spl.a2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.CountDownLatch;

/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class ActorThreadPool {
    private LinkedList<Thread> myList;
    protected LinkedList<ConcurrentLinkedQueue<Action<?>>> actors;
    protected LinkedList< AtomicBoolean> avalibility;
    protected LinkedList<String> Id;
    private LinkedList<PrivateState> StateList;
    protected VersionMonitor VM ;
    private ReadWriteLock readWriteLock;
    

    /**
     * creates a {@link ActorThreadPool} which has nthreads. Note, threads
     * should not get started until calling to the {@link #start()} method.
     *
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     *
     * @param nthreads
     *           the number of threads that should be started by this thread
     *            pool
     */
    public ActorThreadPool(int nthreads) {
    	 readWriteLock = new ReentrantReadWriteLock();
         myList=new LinkedList<Thread> ();
         actors=new LinkedList<ConcurrentLinkedQueue<Action<?>>>();
         avalibility= new LinkedList<AtomicBoolean>();
         Id=new LinkedList<String>();
         StateList=new LinkedList<PrivateState>();
         VM = new VersionMonitor();
         int countLatch=0;
         for(int i=0; i<actors.size(); i++)
        	 countLatch=countLatch + actors.get(i).size();
        Runnable r;
        for(int i=0; i<nthreads; i++) {
            r = new Runnable() {
                public void run() {
               
                    while(!Thread.interrupted()) {
                        int olldVertion= VM.getVersion();
                        for(int i=0; i<actors.size(); i++) {
                            tryActor(i);
                        }
                        try {
                        VM.await(olldVertion);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
           
            Thread thrd = new Thread(r);
            myList.add(thrd);
        }
    }
   
    private  void tryActor(int acotorI) {
        if(avalibility.get(acotorI).compareAndSet(true, false)) {
        	readWriteLock.writeLock().lock();
        	try {
        		if(actors.get(acotorI).isEmpty())
                    return;
        		Action<?> action=actors.get(acotorI).poll();
                action.handle(this, Id.get(acotorI),StateList.get(acotorI));
        	    // only one writer can enter this section, only if no threads are currently reading.
        	} finally {
        		avalibility.get(acotorI).compareAndSet(false, true);
                VM.inc();
        	  readWriteLock.writeLock().unlock();
        	}
            
            
            
            }
    }
   
    public Map<String, PrivateState> getActors(){
        Map<String, PrivateState> mp= new HashMap<String,PrivateState> ();
        for(int i =0; i<actors.size(); i++)
            mp.put(Id.get(i), StateList.get(i));
        return mp;
           
    }
   
    /**
     * getter for actor's private state
     * @param actorId actor's id
     * @return actor's private state
     */
    public PrivateState getPrivateState(String actorId){
        return getActors().get(actorId);
    }

    /**
     * submits an action into an actor to be executed by a thread belongs to
     * this thread pool
     *
     * @param action
     *            the action to execute
     * @param actorId
     *            corresponding actor's id
     * @param actorState
     *            actor's private state (actor's information)
     */
    public void submit(Action<?> action, String actorId, PrivateState actorState) {
        if(Id.contains(actorId)) {
            int actorI =Id.indexOf(actorId);
            if(!actors.get(actorI).contains(action))
                actors.get(actorI).add(action);
            VM.inc();
        }
        else {
            actors.add(new ConcurrentLinkedQueue<Action<?>>());
            actors.getLast().add(action);
            avalibility.add(new AtomicBoolean(true));
            Id.add(actorId);
            StateList.add(actorState);
            VM.inc();
           
           
        }
    }

    /**
     * closes the thread pool - this method interrupts all the threads and waits
     * for them to stop - it is returns *only* when there are no live threads in
     * the queue.
     *
     * after calling this method - one should not use the queue anymore.
     *
     * @throws InterruptedException
     *             if the thread that shut down the threads is interrupted
     */
    public void shutdown() throws InterruptedException {
    	  for(int i=0; i<myList.size(); i++) {
              try {
              myList.get(i).interrupt();
              }
              catch(Exception e) {
                  myList.get(i).notify();   
                  myList.get(i).interrupt();
              }
          }
    }

    /**
     * start the threads belongs to this thread pool
     */
    public void start() {
        for(int i=0; i<myList.size(); i++)
            myList.get(i).start();
    }

}