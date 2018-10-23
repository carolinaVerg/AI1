package bgu.spl.a2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Queue;

import org.junit.jupiter.api.Test;

class ActorThreadPoolTest {

	
	void testSubmit() {
		ActorThreadPool pool= new ActorThreadPool(4);
		PrintThis action1 =new PrintThis();
		PrintThis action2 =new PrintThis();
		pool.submit(action1, "actor1", new PrintState());
		pool.submit(action2, "actor2", new PrintState());
		if(pool.actors.size()!=2 | pool.Id.size()!=2)
			fail("actors not added");
		if(!pool.Id.get(0).equals("actor1"))
			fail("wrong actors");
		if(pool.actors.get(0).size()!=1 |pool.actors.get(1).size()!=1)
			fail("actions dont added");
		if(!pool.actors.get(0).peek().equals(action1))
			fail("wrong action");
		
	}
	
	void testSendMassege() {
		ActorThreadPool pool= new ActorThreadPool(1);
		PrintSomthing action1 =new PrintSomthing();
		pool.submit(action1, "actor1", new PrintState());
		Queue actor1 =pool.actors.get(0);
		pool.start();
		
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		action1.sendMessage(action1, "actor1", new PrintState());
		if(!action1.myprom.isResolved())
			fail("");
		
		
	}
	
	@Test
	void test() {
		for(int i=0; i<100; i++) {
		ActorThreadPool pool= new ActorThreadPool(2);
		PrintSomthing action1 =new PrintSomthing();
		PrintSomthing action2 =new PrintSomthing();
		pool.submit(action1, "actor1", new PrintState());
		pool.submit(action2, "actor2", new PrintState());
		Queue actor1 =pool.actors.get(0);
		Queue actor2 = pool.actors.get(1);
		VersionMonitor VM= pool.VM;
		
		pool.start();
		
		while((action1.getResult()==null | action2.getResult()==null)||(!action1.getResult().isResolved() | !action2.getResult().isResolved())) {}
		
		try {
			pool.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		if(!pool.actors.get(0).isEmpty() ) {
			fail("did not dequeue all the actions");
		}
		if(!pool.actors.get(1).isEmpty() ) {
			fail("did not dequeue all the actions");
		}
			
			
		
		if(action1.getResult().equals(null))
			fail("action not handeld");
		if(action2.getResult().equals(null))
			fail("action not handeld");
		
		if(!action1.getResult().isResolved() )
			fail("not resolved");
		if(!action2.getResult().isResolved() )
			fail("not resolved");
		
			
		
		
		
			
		
		}
	}

}
