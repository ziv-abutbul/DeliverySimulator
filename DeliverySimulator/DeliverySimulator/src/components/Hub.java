package components;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */
public class Hub extends Branch{
	
	private ArrayList<Branch> branches=new ArrayList<Branch>();
	private int currentIndex=0;
	private Vector<Integer> yEnd = new Vector<Integer>();
	
	public Hub() {
		super("HUB");
	}
	

	public ArrayList<Branch> getBranches() {
		return branches;
	}

	
	public void add_branch(Branch branch) {
		branches.add(branch);
	}

	
	public void shipNonStandard(NonStandardTruck t) {
		for (Package p: packages) {
			if (p instanceof NonStandardPackage) {
//				if (((NonStandardPackage) p).getHeight() <= t.getHeight() 
//					&& ((NonStandardPackage) p).getLength()<=t.getLength()
//					&& ((NonStandardPackage) p).getWidth()<=t.getWidth()){
						t.collectPackage(p);
						t.setxStart(this.getxCord()+10);
						t.setyStart(this.getyCord()-16);
						t.setxEnd(p.getxCord()+15);
						t.setyEnd(p.getyCord()+15);
						t.setJump();
						packages.remove(p);
						return;
				//	}
			}
		}	
	}
	
	
	public Vector<Integer> getyEnd() {
		return yEnd;
	}


	public void setyEnd(Vector<Integer> yEnd) {
		this.yEnd = yEnd;
	}

	@Override
	public void localWork() {
		for (Truck t : listTrucks) {
			if (t.isAvailable()){
				if(t instanceof NonStandardTruck) 
					shipNonStandard((NonStandardTruck)t);
				else {
					sendTruck((StandardTruck)t);
				}
			}	
		}	
	}

	public void sendTruck(StandardTruck t) {
		t.setAvailable(false);
		t.setDestination(branches.get(currentIndex));
		t.load(this, t.getDestination(), Status.BRANCH_TRANSPORT);
		t.setTimeLeft(((new Random()).nextInt(10)+1)*10);
		System.out.println(t.getName() + " is on it's way to " + t.getDestination().getName() + ", time to arrive: "+t.getTimeLeft());	
		t.setxStart(this.getxCord());
		t.setyStart(getyEnd().get(currentIndex)-10);
		t.setxEnd(t.getDestination().getxCord()+40);
		t.setyEnd(t.getDestination().getyCord()+7);
		t.setJump();
		currentIndex=(currentIndex+1)%branches.size();
	}
	

}
