package bgu.spl.a2;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.util.List;

import java.util.LinkedList;

class PromiseTest {
	
	static private Promise<Integer> myProm;
	

	@Test public void Testget() {
		myProm = new Promise <Integer>();
		try {
            myProm.get();
            fail("Exception expected!");
        }
		catch(IllegalStateException e) {
			myProm.resolve(3);
			try {
				Integer result = new Integer(myProm.get());
				assertEquals(3,result.intValue());
			}
			catch (Exception e2) {
				fail("Unexpected exception: " + e.getMessage());
			}
			
		}
		catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
	}
	
	@Test public void isResolved() {
		myProm = new Promise <Integer>();
		if(myProm.isResolved()) 
			fail("Wrong isResolved value");
		myProm.resolve(3);
		assertEquals(true,myProm.isResolved());
	}
	
	@Test public void resolve() {
		myProm = new Promise <Integer>();
		try {
			myProm.resolve(3);
		}
		catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
		try {
			myProm.resolve(3);
			fail("expected exception");
		}
		catch (IllegalStateException e) {
			assertEquals(3,myProm.get().intValue());	
		}
		catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		
	}
	}
	
	@Test public void testSub() {
		myProm = new Promise <Integer>();
		LinkedList<Integer> myList= new LinkedList<>();
		for(int j=0; j<3; j++) {
			callback myCall = new callback() {
				public void call() {
					myList.add(1);
				}
			};
			myProm.subscribe(myCall);
		}
		myProm.resolve(10);
		if(myList.size()!=3)
			fail("Expected a call");
		
		
	}
	
}


