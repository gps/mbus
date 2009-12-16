import edu.umich.mbus.data.MBusDataException;
import edu.umich.mbus.data.Route;
import edu.umich.mbus.data.TimeFeed;

/**
 * Test harness for Magic Bus Data package.
 */

/**
 * @author gopalkri
 */
public class Main {

	/**
	 * @param args
	 *            Command line arguments - ignored.
	 */
	public static void main(String[] args) {
		try {
			//TimeFeed.PRINT_DEBUG_OUTPUT = true;
			TimeFeed tf = new TimeFeed();
			
			for (Route route : tf.getRoutes()) {
				System.out.println(route);
			}
		} catch (MBusDataException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
