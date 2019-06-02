package org.fl.avoidLock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WorkerInformationTest {

	@Test
	void test() {
		
		WorkerInformation wi = new WorkerInformation() ;
		
		int step = wi.getStep() ;
		
		assertEquals(0,  step);
	}

}
