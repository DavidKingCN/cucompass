package cn.com.davidking.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * placeholder Test
 * @author daikai
 *
 */
public class PlaceHolderTest {
	private static final Logger LOG = LoggerFactory.getLogger(PlaceHolderTest.class);
	public static void main(String[] args) {
		LOG.warn("test{}"," daikai");
		//LOG.info("My log message for %s", "Alex"); 
	}

}
