package components;

import java.util.ArrayList;
import java.util.Random;

import GUI.Menu;
/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */
public class MainOffice implements Runnable {
	private static int clock=0;
	private static Hub hub;
	private ArrayList<Package> packages=new ArrayList<Package>();
	private int maxPack;
	boolean flag = true;
	private final int xHub = 1150;
	private final int yHub = 200;
	private int packSpace;
	private int space;
	
	public MainOffice(int branches, int trucksForBranch, int maxPack) {
		addHub(trucksForBranch);
		addBranches(branches, trucksForBranch);
		this.maxPack = maxPack;
		packSpace = 1100/(maxPack + 1);
		space = packSpace + 50;
		System.out.println("\n\n========================== START ==========================");

	}
	
	
	public static Hub getHub() {
		return hub;
	}


	public static int getClock() {
		return clock;
	}

	
	public void play() {
		while(flag) { 
			tick();
			Menu.getPaint().repaint();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void printReport() {
		for (Package p: packages) {
			System.out.println("\nTRACKING " +p);
			for (Tracking t: p.getTracking())
				System.out.println(t);
		}
	}
	
	public String clockString() {
		String s="";
		int minutes=clock/60;
		int seconds=clock%60;
		s+=(minutes<10) ? "0" + minutes : minutes;
		s+=":";
		s+=(seconds<10) ? "0" + seconds : seconds;
		return s;
	}
	
	
	public void tick() {
		System.out.println(clockString());
		if (clock%5==0 && packages.size() < maxPack) {
			addPackage();
			space += packSpace;
		}
		hub.work();
		for (Branch b: hub.getBranches()) {
			(new Thread(b)).start();
			//b.work();
		}
		clock++;
	}
		
	
	public void addHub(int trucksForBranch) {
		hub=new Hub();
		hub.setxCord(xHub);
		hub.setyCord(yHub);
		for (int i=0; i<trucksForBranch; i++) {
			hub.addTruck(new StandardTruck());
		}
		hub.addTruck(new NonStandardTruck());
	}
	
	
	public void addBranches(int branches, int trucks) {
		int spaceSize = 0;
		int space = 0;
		int hubLine = 200/(branches + 1) + 200;
		int tempLine = 200/(branches + 1);
		if(branches == 1)
			spaceSize = 200;
		else
			spaceSize = 600/(branches + 1);
		for (int i=0; i<branches; i++) {
			space += spaceSize;
			Branch branch=new Branch();
			branch.setyCord(space);
			hub.getyEnd().add(hubLine);
			hubLine += tempLine;
			for (int j=0; j<trucks; j++) {
				branch.addTruck(new Van());
			}
			hub.add_branch(branch);		
		}
	}
	
	
	public void addPackage() {
		Random r = new Random();
		Package p;
		Priority priority=Priority.values()[r.nextInt(3)];
		Address sender = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);
		Address dest = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);

		switch (r.nextInt(3)){
		case 0:
			p = new SmallPackage(priority,  sender, dest, r.nextBoolean() );
			p.getSenderBranch().addPackage(p);
			p.setxCord(space);
			break;
		case 1:
			p = new StandardPackage(priority,  sender, dest, r.nextFloat()+(r.nextInt(9)+1));
			p.getSenderBranch().addPackage(p);
			p.setxCord(space);
			break;
		case 2:
			p=new NonStandardPackage(priority,  sender, dest,  r.nextInt(1000), r.nextInt(500), r.nextInt(400));
			hub.addPackage(p);
			p.setxCord(space);
			break;
		default:
			p=null;
			return;
		}
		
		this.packages.add(p);
		
	}

	

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	


	public ArrayList<Package> getPackages() {
		return packages;
	}


	@Override
	public void run() {
		play();
		System.out.println("\n========================== STOP ==========================\n\n");
		printReport();
	}
}
