package components;

import java.util.ArrayList;

/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */
public abstract class Package {
	private static int countID=1000;
	final private int packageID;
	private Priority priority;
	private Status status;
	private Address senderAddress;
	private Address destinationAddress;
	private ArrayList<Tracking> tracking = new ArrayList<Tracking>();
	private int xCord;
	private int yCord;
	private int yEndCord;
	
	
	public Package(Priority priority, Address senderAddress,Address destinationAdress) {
		packageID = countID++;
		this.priority=priority;
		this.status=Status.CREATION;
		this.senderAddress=senderAddress;
		this.destinationAddress=destinationAdress;
		tracking.add(new Tracking( MainOffice.getClock(), null, status));
		setyCord(30);
		setyEndCord(600);
	}

	
	public Priority getPriority() {
		return priority;
	}

	
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	
	public Status getStatus() {
		return status;
	}

	
	public void setStatus(Status status) {
		this.status = status;
	}

	
	public int getPackageID() {
		return packageID;
	}
	
	
	
	public Address getSenderAddress() {
		return senderAddress;
	}

	
	public void setSenderAddress(Address senderAddress) {
		this.senderAddress = senderAddress;
	}

	
	public Address getDestinationAddress() {
		return destinationAddress;
	}

	public String getName() {
		return "package " + getPackageID(); 
	}
	
	
	public void setDestinationAddress(Address destinationAdress) {
		this.destinationAddress = destinationAdress;
	}

	
	public void addTracking(Node node, Status status) {
		tracking.add(new Tracking(MainOffice.getClock(), node, status));
	}
	
	
	public void addTracking(Tracking t) {
		tracking.add(t);
	}
	
	
	public ArrayList<Tracking> getTracking() {
		return tracking;
	}

	
	public void printTracking() {
		for (Tracking t: tracking)
			System.out.println(t);
	}
	
	
	public Branch getSenderBranch() {
		return MainOffice.getHub().getBranches().get(getSenderAddress().getZip());
	}
	
	
	public Branch getDestBranch() {
		return MainOffice.getHub().getBranches().get(getDestinationAddress().getZip());
	}
	
	
	@Override
	public String toString() {
		return "packageID=" + packageID + ", priority=" + priority + ", status=" + status + ", senderAddress=" + senderAddress + ", destinationAddress=" + destinationAddress;
	}

	
	public int getxCord() {
		return xCord;
	}


	public void setxCord(int xCord) {
		this.xCord = xCord;
	}


	public int getyCord() {
		return yCord;
	}


	public void setyCord(int yCord) {
		this.yCord = yCord;
	}


	public int getyEndCord() {
		return yEndCord;
	}


	public void setyEndCord(int yEndCord) {
		this.yEndCord = yEndCord;
	}


	public void addRecords(Status status, Node node) {
		setStatus(status);
		addTracking(node, status);
	}
	
	
}
