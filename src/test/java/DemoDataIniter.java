import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DemoDataIniter {

	@Before
	public void setUp() throws Exception {
		DemoDataImporter.init();
		DemoDataImporter.printAll();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
