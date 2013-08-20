package parkinglot;

public class PSRestriction {

	public final PSRestriction VIP = new PSRestriction("VIP");
	public final PSRestriction HANDICAP = new PSRestriction("HAND");
	public final PSRestriction EMERGENCY_VEHICLE = new PSRestriction("EMV");
	
	public final String name;
	public final String desc;
	
	public PSRestriction(String name) {
		this(name, "");
	}

	public PSRestriction(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
}