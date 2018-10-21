package bgu.spl.a2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VersionMonitorTest {
	
	private VersionMonitor VM;
	
	@Test
	void testgetVersion() {
		this.VM = new VersionMonitor ();
		if(VM.getVersion()!=0)
			fail("Wrong version");
		VM.inc();
		if(VM.getVersion()!=1)
			fail("Wrong version");
		
	}
	
	@Test
	void testinc() {
		this.VM = new VersionMonitor ();
		VM.inc();
		if(VM.getVersion()!=1)
			fail("Wrong version");
	}
	
	@Test
	void testAwait() {
		VM= new VersionMonitor();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
						while(true) {
							VM.await(0);
						}
					
					
				}
				catch (Exception e) {
					fail("Unexpected Exception "+e.toString());
				}
			
			}
		};
		
		Thread thrd = new Thread(r);
		thrd.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(thrd.getState()!=Thread.State.WAITING)
			fail("Thread in wrong state");
		try {
			VM.inc();
		}
		catch (Exception e) {
			fail("Unexpected Exception");
		}
		if(thrd.getState()==Thread.State.WAITING)
			fail("Wrong thread state");
	}

}
