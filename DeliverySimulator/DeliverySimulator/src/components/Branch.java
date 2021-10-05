package components;

import java.util.ArrayList;
/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */
public class Branch implements Node,Runnable {
	private static int counter=-1;
	private int branchId;
	private String branchName;
	protected ArrayList <Package> packages = new ArrayList<Package>();
	protected ArrayList <Truck> listTrucks = new ArrayList<Truck>();
	private int xCord;
	private int yCord;
	private final int x = 20;
	
	public Branch() {
		this("Branch "+counter);
		setxCord(x);
	}
	
	public Branch(String branchName) {
		this.branchId=counter++;
		this.branchName=branchName;
		System.out.println("\nCreating "+ this);
	}
	
	public Branch(String branchName, Package[] plist, Truck[] tlist) {
		this.branchId=counter++;
		this.branchName=branchName;
		addPackages(plist);
		addTrucks(tlist);
	}
	
	
	public void printBranch() {
		System.out.println("\nBranch name: "+branchName);
		System.out.println("Packages list:");
		for (Package pack: packages)
			System.out.println(pack);
		System.out.println("Trucks list:");
		for (Truck trk: listTrucks)
			System.out.println(trk);
	}
	
	
	public void addPackage(Package pack) {
		packages.add(pack);
	}
	
	
	public void addTruck(Truck trk) {
		listTrucks.add(trk);
	}
	
	
	public void addPackages(Package[] plist) {
		for (Package pack: plist)
			packages.add(pack);
	}
	
	
	public void addTrucks(Truck[] tlist) {
		for (Truck trk: tlist)
			listTrucks.add(trk);
	}

	
	public int getBranchId() {
		return branchId;
	}
	
	
	public String getName() {
		return branchName;
	}

	
	@Override
	public String toString() {
		return "Branch " + branchId + ", branch name:" + branchName + ", packages: " + packages.size()
				+ ", trucks: " + listTrucks.size();
	}

	
	@Override
	public void collectPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				v.collectPackage(p);
				v.setxStart(p.getxCord()+7);
				v.setyStart(p.getyCord()+18);
				v.setxEnd(this.getxCord()+40);
				v.setyEnd(this.getyCord()+7);
				v.setJump();
				return;
			}
		}
	}

	@Override
	public void deliverPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				v.deliverPackage(p);
				v.setxStart(this.getxCord()+40);
				v.setyStart(this.getyCord()+7);
				v.setxEnd(p.getxCord()+7);
				v.setyEnd(p.getyEndCord());
				v.setJump();
				return;
			}
		}	
	}

	@Override
	public void work() {
		for (Truck t: listTrucks) {
			(new Thread(t)).start();
			t.work();
		}
		localWork();
	}
	
	
	public void localWork() {
		for (Package p: packages) {
			if (p.getStatus()==Status.CREATION) {
				collectPackage(p);
			}
			if (p.getStatus()==Status.DELIVERY) {
				deliverPackage(p);
			}
		}
	}
	
	
	public ArrayList<Package> getPackages() {
		return packages;
	}

	public void removePackage(Package p) {
		packages.remove(p);
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

	public ArrayList<Truck> getListTrucks() {
		return listTrucks;
	}

	@Override
	public void run() {
		work();
		
	}
	
	
}
