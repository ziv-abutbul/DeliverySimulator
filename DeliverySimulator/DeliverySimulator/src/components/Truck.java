package components;

import java.util.ArrayList;
import java.util.Random;

/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */
public abstract class Truck implements Node,Runnable {
	private static int countID=2000;
	final private int truckID;
	private String licensePlate;
	private String truckModel;
	private boolean available=true;
	private int timeLeft=0;
	private ArrayList<Package> packages=new ArrayList<Package>();
	private double xStart, yStart, xEnd, yEnd;
	private double xJump , yJump ;
		
	
	//default random constructor
	public Truck() {
		truckID=countID++;
		Random r= new Random();
		licensePlate=(r.nextInt(900)+100)+"-"+(r.nextInt(90)+10)+"-"+(r.nextInt(900)+100);
		truckModel="M"+r.nextInt(5);
		System.out.print("Creating ");
	}

	
	public Truck(String licensePlate,String truckModel) {
		truckID=countID++;
		this.licensePlate=licensePlate;
		this.truckModel=truckModel;
		System.out.print("Creating ");
	}
	
	
	public ArrayList<Package> getPackages() {
		return packages;
	}


	public int getTimeLeft() {
		return timeLeft;
	}

	
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}


	@Override
	public String toString() {
		return "truckID=" + truckID + ", licensePlate=" + licensePlate + ", truckModel=" + truckModel + ", available= " + available ;
	}


	@Override
	public void collectPackage(Package p) {
		setAvailable(false);
		int time=p.getSenderAddress().getStreet()%10+1;
		time *= 10;
		this.setTimeLeft(time);
		this.packages.add(p);
		p.setStatus(Status.COLLECTION);
		p.addTracking(new Tracking(MainOffice.getClock(), this, p.getStatus()));
		System.out.println(getName() + " is collecting package " + p.getPackageID() + ", time to arrive: "+ getTimeLeft()  );
	}


	public boolean isAvailable() {
		return available;
	}
	

	public int getTruckID() {
		return truckID;
	}

	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
	public void addPackage(Package p) {
		this.packages.add(p);
	}
	
	
	
	public double getxStart() {
		return xStart;
	}


	public void setxStart(int xStart) {
		this.xStart = xStart;
	}


	public double getyStart() {
		return yStart;
	}


	public void setyStart(int yStart) {
		this.yStart = yStart;
	}


	public double getxEnd() {
		return xEnd;
	}


	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}


	public double getyEnd() {
		return yEnd;
	}


	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}


	public String getName() {
		return this.getClass().getSimpleName()+" "+ getTruckID();
	}
	
	public void setJump() {
		xJump = (xEnd - xStart)/timeLeft;
		yJump = (yEnd - yStart)/timeLeft;
	}

	public void UpdateMove() {
		xStart += xJump;
		yStart += yJump;
	}
}
