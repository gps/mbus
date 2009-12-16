package edu.umich.mbus.data;

/**
 * Represents an arrival - toa, bus id pair.
 * @author gopalkri
 */
public class Arrival {
	
	/**
	 * Initializes this object with toa and id.
	 * @param toa Time of arrival.
	 * @param id Bus id.
	 */
	public Arrival(double toa, int id) {
		mToa = toa;
		mId = id;
	}
	
	/**
	 * Gets time of arrival in seconds.
	 * @return Time of arrival in seconds.
	 */
	public double getTimeOfArrival() {
		return mToa;
	}
	
	/**
	 * Gets bus id.
	 * @return Bus id.
	 */
	public int getBusId() {
		return mId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TOA: " + mToa + " | Id: " + mId;
	}
	
	private double mToa;
	private int mId;
}