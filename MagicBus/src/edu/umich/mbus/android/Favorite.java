package edu.umich.mbus.android;

/**
 * Represents a favorite. Contains a route name and a stop name.
 * 
 * @author gopalkri
 * 
 */
public class Favorite implements Comparable<Favorite> {

	private static final String ROUTE_STOP_SEPARATOR = "|";

	private String mRouteName;
	private String mStopName;

	/**
	 * Initializes this object with routeName and stopName.
	 * 
	 * @param routeName
	 *            Name of route.
	 * @param stopName
	 *            Name of stop.
	 */
	public Favorite(String routeName, String stopName) {
		mRouteName = routeName;
		mStopName = stopName;
	}

	/**
	 * Initializes this object with serialized String serialized.
	 * 
	 * @param serialized
	 *            Serialized representation of object.
	 * @throws IllegalArgumentException
	 *             If serialized String is not in right format.
	 */
	public Favorite(String serialized) throws IllegalArgumentException {
		String[] split = serialized.split(ROUTE_STOP_SEPARATOR);
		if (split.length != 2) {
			throw new IllegalArgumentException(
					"Serialized string not in the right format.");
		}
		mRouteName = split[0];
		mStopName = split[1];
	}

	/**
	 * Gets route name.
	 * 
	 * @return Route name.
	 */
	public String getRouteName() {
		return mRouteName;
	}

	/**
	 * Gets stop name.
	 * 
	 * @return Stop name.
	 */
	public String getStopName() {
		return mStopName;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mRouteName + ROUTE_STOP_SEPARATOR + mStopName;
	}

	/**
	 * Compares based on route name and then stop name.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Favorite another) {
		int cmp = mRouteName.compareTo(another.mRouteName);
		if (cmp == 0) {
			return mStopName.compareTo(another.mStopName);
		}
		return cmp;
	}
}