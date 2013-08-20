package parkinglot;


public class ParkingSpace {
	
	private String id;
	private Meter meter;
	private Vehicle vehicle;
	private Dimensions dimensions;
//	private List<PSRestriction> restrictions;
	private ParkingLevel level;
	
	public ParkingSpace(String id, Dimensions dimensions, ParkingLevel level) {
		this(id, dimensions, level, null);
	}

	public ParkingSpace(String id, Dimensions dimensions, ParkingLevel level, Meter meter) {
		this.id = id;
		this.dimensions = dimensions;
		this.level = level;
		this.meter = meter;
	}

	public String getId() {
		return this.id;
	}
	
	public Dimensions getDimensions() {
		return this.dimensions;
	}

	public synchronized boolean isOccupied() {
		return vehicle != null;
	}

	/**
	 * Returns true if the given vehicle was 
	 * successfully parked in this ParkingSpace.
	 * 
	 * @param v
	 * @return
	 */
	protected synchronized boolean acceptVehicle(Vehicle v) {
		
		boolean vehicleFitsInSpace = v.getDimensions().fitsInside(this.dimensions);
		if (level.acceptVehicle(v) && !isOccupied() && vehicleFitsInSpace) {
			this.vehicle = v;
			if (meter != null) {
				meter.startTimer();
			}
			return true;
		}
		
		return false;
	}

}