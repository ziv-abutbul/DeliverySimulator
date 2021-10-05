package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import components.Branch;
import components.MainOffice;
import components.NonStandardPackage;
import components.NonStandardTruck;
import components.Package;
import components.StandardTruck;
import components.Status;
import components.Truck;
import components.Van;
/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */


public class Menu implements ActionListener{
	/**
	 * GUI creation parameters
	 * 
	 * @param juncs-A panel that animations run on
	 * @param window,windocreate,ChoiceBranch-Windows: system, system creation, branch information selection
	 * @param bot_panel,branch_Panel-Separation of panels in the main window for animation and buttons
	 * @param tableInfoPackages,bInfo-Windows of information tables by branches and all sabotage
	 * @param spanePanelAllPacagesInfo,SB -Displays the table window
	 * @param createSystem,Start,Stop,Resume,allPackagesInfo,branchInfo,okButton,cancelButton,okBrnach,cancelBranch-System buttons
	 * @param  valBranch,valTrucks,valPack,clickOpenAndClose,clickOpenAndClose1,choicB-Variables for storing information of the number of car branches and closing and opening window packages and impressive selection
	 **/
	public static MyPanel juncs=new MyPanel();
	private JFrame window,windocreate,ChoiceBranch;
	private JPanel bot_panel,branch_Panel;
	private JTable tableInfoPackages,bInfo;
	private JScrollPane spanePanelAllPacagesInfo,SB;
	private JButton createSystem,Start,Stop,Resume,allPackagesInfo,branchInfo,okButton,cancelButton,okBrnach,cancelBranch;
	private int valBranch,valTrucks,valPack,clickOpenAndClose,clickOpenAndClose1,choicB;
	

	/**
	 * Stopping and running animation
	 **/
	
	public static boolean stop1=true,stop2;
	

	public static void main(String[] args) {
		Menu gui= new Menu();
		gui.window.setVisible(true);
	}
	
	
	public Menu()
	{
		/**
		 *Builder Creates a main window on which all the buttons and a blank screen will appear
		 */
		window = new JFrame("Post tracking system");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1200,700);
		windocreate=new JFrame("Create post system");
		windocreate.setBounds(150, 150, 200, 300);
		windocreate.setSize(600,400);
		windocreate.setVisible(false);
		ChoiceBranch=new JFrame("Choices Branch");
		ChoiceBranch.setBounds(150,150,50,50);
		ChoiceBranch.setVisible(false);
		bot_panel=new JPanel();
		window.add(bot_panel,BorderLayout.SOUTH);
		window.setVisible(true);

		
		MakeButtons();
		
		bot_panel.setLayout(new GridLayout(1,0));
		
		MakePanel();
		
		makeAction();
		makeWindobar();
		makeCreateSystem();
		

	}
	public void BranchChoices()
	{
		/**
		 *Auxiliary function for creating a window for selecting a branch on which we want information
		 **/
		ChoiceBranch.setBounds(150, 150, 250, 150);
		ChoiceBranch.setVisible(true);
		JComboBox<String> choic;
		int temp;
		String[] ch=new String[mainOffice.getHub().getBranches().size()+1];
		ch[0]="Sorting center";
		for(int i=0;i<mainOffice.getHub().getBranches().size();i++)
		{
			temp=mainOffice.getHub().getBranches().get(i).getBranchId()+1;
			ch[i+1]="Branch "+temp;
		}
		choic = new JComboBox<String>(ch);
		choic.setBorder(BorderFactory.createEmptyBorder(25,15,0,15));
		branch_Panel=new JPanel();
		okBrnach=new JButton("OK");
		okBrnach.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		choicB=choic.getSelectedIndex();
	    		ChoiceBranch.dispose();
	    		//System.out.println("bran"+valBranch + " truck "+ valTrucks + " pack "+valPack );
	    		makeInfoBranch(choicB);
	    		   
	    	}
	    	
	    });
		cancelBranch=new JButton("Cancel");
		cancelBranch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ChoiceBranch.dispatchEvent(new WindowEvent(ChoiceBranch, WindowEvent.WINDOW_CLOSING));		
				}	
	    });
		branch_Panel.add(okBrnach);
		branch_Panel.add(cancelBranch);
		ChoiceBranch.add(branch_Panel,BorderLayout.SOUTH);
		ChoiceBranch.add(choic,BorderLayout.NORTH);
		
	}
	
	
	public void makeCreateSystem()
	{
		/**
		 * Auxiliary function Creating a window with 3 sliders of the amount of branch and truck sabotage
		 */
		//make slider
		JSlider numberOfBranches=new JSlider(JSlider.HORIZONTAL, 1,10,5);
		JSlider numberOfTrucks=new JSlider(JSlider.HORIZONTAL, 1,10,5);
		JSlider numberOfPackages=new JSlider(JSlider.HORIZONTAL, 2,20,8);
		//make labels to name of slider
		JLabel l1,l2,l3;
		//make how run of slider
		numberOfBranches.setMajorTickSpacing(1);
		numberOfBranches.setMinorTickSpacing(1);
		numberOfTrucks.setMajorTickSpacing(1);
		numberOfTrucks.setMinorTickSpacing(1);
		numberOfPackages.setMajorTickSpacing(2);
		numberOfPackages.setMinorTickSpacing(1);
		//make label name to sliders
		l1=new JLabel("Number of branches",SwingConstants.CENTER);
		l2=new JLabel("Number of trucks per branch",SwingConstants.CENTER);
		l3=new JLabel("Number of packages",SwingConstants.CENTER);

		//put the sliders to see 
		numberOfBranches.setPaintTrack(true); 
		numberOfBranches.setPaintTicks(true); 
		numberOfBranches.setPaintLabels(true); 
		
		numberOfTrucks.setPaintTrack(true); 
		numberOfTrucks.setPaintTicks(true); 
		numberOfTrucks.setPaintLabels(true);
		
		numberOfPackages.setPaintTrack(true); 
		numberOfPackages.setPaintTicks(true); 
		numberOfPackages.setPaintLabels(true); 
		//make buttons
	    okButton=new JButton("OK");
	    cancelButton=new JButton("Cancel");
	    //make action to button
	    okButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		valBranch=numberOfBranches.getValue();
	    		valTrucks=numberOfTrucks.getValue();
	    		valPack=numberOfPackages.getValue();
	    		windocreate.dispose();
	    		//System.out.println("bran"+valBranch + " truck "+ valTrucks + " pack "+valPack );
				juncs=new MyPanel(valBranch,valPack,valTrucks);
				window.add(juncs,BorderLayout.CENTER);
				window.setVisible(true);
				juncs.repaint();    
	    	}
	    	
	    });
	    cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				windocreate.dispatchEvent(new WindowEvent(windocreate, WindowEvent.WINDOW_CLOSING));		
				}	
	    });
	    //create panel to add sliders and labels
		JPanel panel=new JPanel();
		JPanel panel1=new JPanel();
		//pick layout
		panel.setLayout(new GridLayout(6,0));
		panel1.setLayout(new GridLayout(0,2));
		//add to panels
		panel.add(l1);
		panel.add(numberOfBranches);
		panel.add(l2);
		panel.add(numberOfTrucks);
		panel.add(l3);
		panel.add(numberOfPackages);
		panel1.add(okButton);
		panel1.add(cancelButton);
		//add panel to window create
		windocreate.add(panel,BorderLayout.CENTER);
		windocreate.add(panel1,BorderLayout.SOUTH);
		
	}
	public void MakePanel()
	{
		/**
		 * Create a bottom panel with the relevant buttons
		 */
		this.bot_panel.add(createSystem);
		this.bot_panel.add(Start);
		this.bot_panel.add(Stop);
		this.bot_panel.add(Resume);
		this.bot_panel.add(allPackagesInfo);
		this.bot_panel.add(branchInfo);
		
		
	}
	public void MakeButtons()
	{
		/**
		 * Create the buttons
		 */
		this.createSystem=new JButton("Create system");
		this.Start = new JButton("Start");
		this.Stop = new JButton("Stop");
		this.Resume = new JButton("Resume");
		this.allPackagesInfo = new JButton("All packages info");
		this.branchInfo = new JButton("Branch info");
	}
	
	public void makeWindobar()
	{
		/**
		 * Create a main window
		 */
		//add panel
		//cancel to user to change size
		window.add(bot_panel,BorderLayout.SOUTH);
		window.setResizable(false);
		window.setVisible(true);
	}
	public void makeAction()
	{
		/**
		 * Setting for buttons that will perform an action
		 */
		this.createSystem.addActionListener(this);
		this.Start.addActionListener(this);
		this.Stop.addActionListener(this);
		this.allPackagesInfo.addActionListener(this);
		this.Resume.addActionListener(this);
		this.branchInfo.addActionListener(this);
	}
	//make info table
	public void makeInfoBranch(int temp)
	{
		/**
		 * Create the table with the information of the selected relevant branch
		 */
		JScrollPane sPane1 = new JScrollPane();
		sPane1.setPreferredSize(new Dimension(400,170));
		JTable table1=juncs.infoBran(temp);
		if(clickOpenAndClose1%2==0) {
			bInfo=table1;
			
			SB=sPane1;
			//System.out.println("if");
			clickOpenAndClose1+=1;
			SB.getViewport().add(bInfo);
			juncs.add(SB);
			
			//sPane.setVisible(true);
			window.pack();
			window.setSize(1200,700); 
		   
		}
		
	}
	
	public void makeInfoTable()
	{
		/**
		 * Create a table with all the information about all the packages in the system
		 */
		JScrollPane sPane1 = new JScrollPane();
		int temp=mainOffice.getPackages().size();
		if(temp<5)
			sPane1.setPreferredSize(new Dimension(400,mainOffice.getPackages().size()*28));
		else
			sPane1.setPreferredSize(new Dimension(400,170));
		JTable table1=juncs.infoTable();
		if(clickOpenAndClose%2==0) {
			tableInfoPackages=table1;
			
			spanePanelAllPacagesInfo=sPane1;
			//System.out.println("if");
			clickOpenAndClose+=1;
			spanePanelAllPacagesInfo.getViewport().add(tableInfoPackages);
			juncs.add(spanePanelAllPacagesInfo);
			
			//sPane.setVisible(true);
			window.pack();
			window.setSize(1200,700);
		   
			
		}
		else {
			clickOpenAndClose+=1;
			juncs.remove(spanePanelAllPacagesInfo);
			juncs.repaint();		 
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * A function that checks which click the user selects and does the relevant action
		 */
		if(e.getSource().equals(createSystem))
		{
			windocreate.setBounds(150, 150, 600, 300);
			windocreate.setVisible(true);
			stop1=true;
			
			
		}
		if(e.getSource().equals(Start)&&stop1==true)
		{
			
			Thread T = new Thread(mainOffice);
			T.start();
			//ts.add(T);
			stop1=false;
		}
		if(e.getSource().equals(Stop))
		{
			mainOffice.setFlag(false);
			stop2=true;
			
		}
		if(e.getSource().equals(allPackagesInfo))
		{
			makeInfoTable();
		}
		if(e.getSource().equals(branchInfo))
		{
			if(this.clickOpenAndClose1%2!=0)
			{
				juncs.remove(SB);
				juncs.repaint();
				clickOpenAndClose1++;
			}
			BranchChoices();
			
		}
		if(e.getSource().equals(Resume)&&stop2==true)
		{
			stop2=false;
			mainOffice.setFlag(true);
			Thread T = new Thread(mainOffice);
			T.start();
			//ts.add(T);	
		}
		
		
	}
	public static MyPanel getPaint() {
		return juncs;
	}
	static MainOffice mainOffice;
	
	public static class MyPanel extends JPanel{
		/**
		 * Chief builder for building the animation
		 * @parm val of branch packages and truk
		 */
		private int valbracn,valPack,valTrack,x=0;
		private boolean addpack=false;
		Graphics g1;
		public MyPanel() {}
		public MyPanel(int b,int p,int v)
		{
			/**
			 * A builder who runs the system
			 */
			this.valbracn=b;
			this.valPack=p;
			this.valTrack=v;
			mainOffice = new MainOffice(valbracn,valTrack,valPack);
		
		}
		public void paintComponent(Graphics g)
		{
			
			super.paintComponent(g);

			g1=g;
				
	    	//checking status packages		
			
				//make branch
				Color b=new Color(0, 255, 255);
				for(int i=0;i<valbracn;i++)
				{
					if(mainOffice.getHub().getBranches().get(i).getPackages().size()!=0)
					{
					boolean flag=false;
					for(Package p : mainOffice.getHub().getBranches().get(i).getPackages())
					{
						if(p.getStatus()==Status.BRANCH_STORAGE || p.getStatus() == Status.DELIVERY)
						{
							flag=true;
							break;
						}
					}
						if(flag)
						{
						g.setColor(Color.blue);
						g.drawRect(mainOffice.getHub().getBranches().get(i).getxCord(),mainOffice.getHub().getBranches().get(i).getyCord(),40,30);
						g.setColor(Color.blue);
						g.fillRect(mainOffice.getHub().getBranches().get(i).getxCord(),mainOffice.getHub().getBranches().get(i).getyCord(),40, 30);
						}
						else
						{
					  	g.setColor(b);
				        g.drawRect(mainOffice.getHub().getBranches().get(i).getxCord(),mainOffice.getHub().getBranches().get(i).getyCord(),40,30);
				        g.setColor(b);
				        g.fillRect(mainOffice.getHub().getBranches().get(i).getxCord(),mainOffice.getHub().getBranches().get(i).getyCord(),40, 30);
						}
					}
					else
						{
						  	g.setColor(b);
					        g.drawRect(mainOffice.getHub().getBranches().get(i).getxCord(),mainOffice.getHub().getBranches().get(i).getyCord(),40,30);
					        g.setColor(b);
					        g.fillRect(mainOffice.getHub().getBranches().get(i).getxCord(),mainOffice.getHub().getBranches().get(i).getyCord(),40, 30);
					}
					
				}
				
				//make hub
				Color gre=new Color(0, 102, 0);
				g.setColor(gre);
		        g.drawRect(mainOffice.getHub().getxCord(),mainOffice.getHub().getyCord(),40,200);
		        g.setColor(gre);
		        g.fillRect(mainOffice.getHub().getxCord(), mainOffice.getHub().getyCord(), 40, 200);
		        
				//draw line between branch to hub
				for(int i=0;i<valbracn;i++)
				{
					
		        	g.drawLine(mainOffice.getHub().getxCord(),mainOffice.getHub().getyEnd().get(i),mainOffice.getHub().getBranches().get(i).getxCord()+40,mainOffice.getHub().getBranches().get(i).getyCord()+15);
				}
			
				checkStatus(g);
				UpdateTruck(g);
				UpdateVan(g);
					
		}


		private void checkStatus(Graphics g)
		{
			/**
			 * An auxiliary function that checks the status of the packages and draws according to the relevant status
			 */
	    	for(int i=0;i<mainOffice.getPackages().size();i++)
	    	{
	    		Color re=new Color(255, 204, 204);	
	        	//up
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.CREATION)	
	    		{
		        	g.setColor(Color.RED);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		        	g.setColor(Color.RED);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
	    		
		        	//down
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyEndCord(),30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyEndCord(),30,30);
		        	//line
		        	
		        	g.drawLine(mainOffice.getPackages().get(i).getxCord()+15,45,mainOffice.getPackages().get(i).getxCord()+15,mainOffice.getPackages().get(i).getyEndCord());
	    		}
	    		
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.COLLECTION)	
	    		{
	    			if(!(mainOffice.getPackages().get(i) instanceof NonStandardPackage))
	    			{
			        	g.setColor(re);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
			        	g.setColor(re);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		    		
			        	//down
			        	g.setColor(re);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyEndCord(),30,30);
			        	g.setColor(re);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyEndCord(),30,30);
			        	//line
			        	g.setColor(Color.blue);
			        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,60);

			        	//g.drawLine(mainOffice.getHub().getxStart(),mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getY()+15,mainOffice.getPackages().get(i).getX()+15,mainOffice.getHub().getxStart());
	    			}
			        else
			        {
			        	g.setColor(Color.RED);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
			        	g.setColor(Color.RED);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		    		
			        	//down
			        	g.setColor(re);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
			        	g.setColor(re);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
			        	g.setColor(Color.RED);
			        	g.drawLine(mainOffice.getHub().getxCord(),200,mainOffice.getPackages().get(i).getxCord()+15,60);
			        	g.drawLine(mainOffice.getPackages().get(i).getxCord()+15,60,mainOffice.getPackages().get(i).getxCord()+15,600);
			        }
		        	
	    		}
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.BRANCH_STORAGE)	
	    		{
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
	    		
		        	//down
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	//line
		        	g.setColor(Color.blue);
		        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,60);
		            	
	    		}
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.HUB_TRANSPORT)	
	    		{
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
	    		
		        	//down
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	//line
		        	g.setColor(Color.blue);
		        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,60);

	    		}
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.HUB_STORAGE)	
	    		{
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
	    		
		        	//down
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	//line
		        	g.setColor(Color.blue);
		        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,60);
		        	
	    		}
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.BRANCH_TRANSPORT)	
	    		{
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
	    		
		        	//down
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	//line
		        	g.setColor(Color.blue);
		        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,60);
	    		}
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.DELIVERY)	
	    		{
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
	    		
		        	//down
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	//line
		        	g.setColor(Color.blue);
		        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,60);
	    		}
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.DISTRIBUTION)	
	    		{
	    			if(!(mainOffice.getPackages().get(i) instanceof NonStandardPackage)) {
			        	g.setColor(re);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
			        	g.setColor(re);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		    		
			        	//down
			        	g.setColor(re);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
			        	g.setColor(re);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
			        	//line
			        	g.setColor(Color.blue);
			        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,60);
			        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getDestinationAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,600);
	    			}
			        else
			        {
			        	g.setColor(re);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
			        	g.setColor(re);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		    		
			        	//down
			        	g.setColor(re);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
			        	g.setColor(re);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
			        	g.setColor(Color.RED);
			        	g.drawLine(mainOffice.getHub().getxCord(),200,mainOffice.getPackages().get(i).getxCord()+15,60);
			        	g.drawLine(mainOffice.getPackages().get(i).getxCord()+15,60,mainOffice.getPackages().get(i).getxCord()+15,600);
			        }
	    			

	    		}
	    		if(mainOffice.getPackages().get(i).getStatus()==Status.DELIVERED)	
	    		{
	    			if(!(mainOffice.getPackages().get(i) instanceof NonStandardPackage))
	    			{
		        	g.setColor(re);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		        	g.setColor(re);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
	    		
		        	//down
		        	
		        	g.setColor(Color.RED);
		        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	g.setColor(Color.RED);
		        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
		        	//line
		        	g.setColor(Color.blue);
		        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,60);
		        	g.drawLine(60,mainOffice.getHub().getBranches().get(mainOffice.getPackages().get(i).getSenderAddress().getZip()).getyCord()+15,mainOffice.getPackages().get(i).getxCord()+15,600);
	    			}
			        else
			        {
			        	g.setColor(re);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
			        	g.setColor(re);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),mainOffice.getPackages().get(i).getyCord(),30,30);
		    		
			        	//down
			        	g.setColor(Color.RED);
			        	g.drawOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
			        	g.setColor(Color.RED);
			        	g.fillOval(mainOffice.getPackages().get(i).getxCord(),600,30,30);
			        	g.setColor(Color.RED);
			        	g.drawLine(mainOffice.getHub().getxCord(),200,mainOffice.getPackages().get(i).getxCord()+15,60);
			        	g.drawLine(mainOffice.getPackages().get(i).getxCord()+15,60,mainOffice.getPackages().get(i).getxCord()+15,600);
			        }
		        	
	    		}
	        }
		}
//info

	    public JTable infoTable() {
			/**
			 * Auxiliary function which generates the information according to the relevant table
			 */
			String[] columnNames = { "Package ID", "Sender", "Destination" ,"Priority","Status"}; 
			String[][] data=new String[mainOffice.getPackages().size()][5];//3=col need to change size of how much col we have
			for (int i = 0; i<mainOffice.getPackages().size(); i++) {
				data[i][0]=String.valueOf(mainOffice.getPackages().get(i).getPackageID());
				data[i][1]=String.valueOf(mainOffice.getPackages().get(i).getSenderAddress());
				data[i][2] =String.valueOf(mainOffice.getPackages().get(i).getDestinationAddress());
				data[i][3] =String.valueOf(mainOffice.getPackages().get(i).getPriority());
				data[i][4]=String.valueOf(mainOffice.getPackages().get(i).getStatus());	
			}
			JTable table= new JTable(data,columnNames);
			table.setBounds(30, 40, 500, 500); 
			
			  
		        // adding it to JScrollPane 
		   
			
			
			return table;	
		}
	
	    public JTable infoBran(int temp) {
			/**
			 * Auxiliary function which generates the information according to the relevant table
			 */
			String[] columnNames = { "Package ID", "Sender", "Destination" ,"Priority","Status"}; 
			if(temp==0)
			{
				String[][] data=new String[mainOffice.getHub().getPackages().size()][5];
				for(int i=0;i<mainOffice.getHub().getPackages().size();i++)
				{
					data[i][0]=String.valueOf(mainOffice.getHub().getPackages().get(i).getPackageID());
					data[i][1]=String.valueOf(mainOffice.getHub().getPackages().get(i).getSenderAddress());
					data[i][2]=String.valueOf(mainOffice.getHub().getPackages().get(i).getDestinationAddress());
					data[i][3]=String.valueOf(mainOffice.getHub().getPackages().get(i).getPriority());
					data[i][4]=String.valueOf(mainOffice.getHub().getPackages().get(i).getStatus());
				}
				JTable table= new JTable(data,columnNames);
				table.setBounds(30, 40, 500, 500); 
				
				  
			        // adding it to JScrollPane 
				return table;	
			}
			if(temp>0)
			{
			String[][] data=new String[mainOffice.getHub().getBranches().get(temp-1).getPackages().size()][5];//3=col need to change size of how much col we have
			for (int i = 0; i<mainOffice.getHub().getBranches().get(temp-1).getPackages().size(); i++)
			{
				data[i][0]=String.valueOf(mainOffice.getHub().getBranches().get(temp-1).getPackages().get(i).getPackageID());
				data[i][1]=String.valueOf(mainOffice.getHub().getBranches().get(temp-1).getPackages().get(i).getSenderAddress());
				data[i][2]=String.valueOf(mainOffice.getHub().getBranches().get(temp-1).getPackages().get(i).getDestinationAddress());
				data[i][3]=String.valueOf(mainOffice.getHub().getBranches().get(temp-1).getPackages().get(i).getPriority());
				data[i][4]=String.valueOf(mainOffice.getHub().getBranches().get(temp-1).getPackages().get(i).getStatus());	
			}
				JTable table= new JTable(data,columnNames);
				table.setBounds(30, 40, 500, 500); 
			
			  
		        // adding it to JScrollPane 
				return table;	
			}
			JTable table= new JTable();
			table.setBounds(30, 40, 500, 500); 
		
		  
	        // adding it to JScrollPane 
			return table;	
			
		}
	private void UpdateVan(Graphics g)
	{
		/**
		 * A function that is responsible for moving VAN vehicles on the map
		 */
		Graphics2D g2 = (Graphics2D)g;
		for(Branch T:mainOffice.getHub().getBranches())
		{
			for(Truck k :T.getListTrucks())
			{
				if(!k.isAvailable())
				{
					if(k instanceof Van)
					{
						g2.setColor(Color.blue);
						g2.draw(new Rectangle2D.Double(k.getxStart(), k.getyStart(), 16, 16));
						g2.fill(new Rectangle2D.Double(k.getxStart(), k.getyStart(), 16, 16));
						g2.setColor(Color.BLACK);
						g2.draw(new Ellipse2D.Double(k.getxStart()-3, k.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(k.getxStart()-3, k.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(k.getxStart()+9, k.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(k.getxStart()+9, k.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(k.getxStart()-3, k.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(k.getxStart()-3, k.getyStart()+10, 10, 10));
						g2.draw(new Ellipse2D.Double(k.getxStart()+9, k.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(k.getxStart()+9, k.getyStart()+10, 10, 10));
						k.UpdateMove();
					}
				}
			}
		}
	}
		
	private void UpdateTruck(Graphics g) {
		/**
		 * A function that is responsible for moving Truck standart and nonstandart vehicles on the map
		 */
		Graphics2D g2 = (Graphics2D)g; 
		for(Truck T : mainOffice.getHub().getListTrucks())
			if(!T.isAvailable()) {
				if(T instanceof NonStandardTruck) {
					if(T.getPackages().get(0).getStatus() == Status.DISTRIBUTION) {
						g2.setColor(Color.RED);
						g2.draw(new Rectangle2D.Double(T.getxStart(), T.getyStart(), 16, 16));
						g2.fill(new Rectangle2D.Double(T.getxStart(), T.getyStart(), 16, 16));
						g2.setColor(Color.BLACK);
						g2.draw(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()+10, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()+10, 10, 10));
						T.UpdateMove();
					}
					else {
						Color re=new Color(255, 204, 204);
						g2.setColor(re);
						g2.draw(new Rectangle2D.Double(T.getxStart(), T.getyStart(), 16, 16));
						g2.fill(new Rectangle2D.Double(T.getxStart(), T.getyStart(), 16, 16));
						g2.setColor(Color.BLACK);
						g2.draw(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()+10, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()+10, 10, 10));
						T.UpdateMove();
					}
				}
				if(T instanceof StandardTruck) {
					if(T.getPackages().size() == 0) {
						g2.setColor(Color.green);
						g2.draw(new Rectangle2D.Double(T.getxStart(), T.getyStart(), 16, 16));
						g2.fill(new Rectangle2D.Double(T.getxStart(), T.getyStart(), 16, 16));
						g2.setColor(Color.BLACK);
						g2.draw(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()+10, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()+10, 10, 10));
						T.UpdateMove();
					}
					else {
						String amount = Integer.toString(T.getPackages().size());
						g2.setColor(Color.BLACK);
						g2.drawString(amount,(int)T.getxStart() + 4,(int)T.getyStart() - 2);
						Color gre=new Color(0, 102, 0);
						g2.setColor(gre);
						g2.draw(new Rectangle2D.Double(T.getxStart(), T.getyStart(), 16, 16));
						g2.fill(new Rectangle2D.Double(T.getxStart(), T.getyStart(), 16, 16));
						g2.setColor(Color.BLACK);
						g2.draw(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()-3, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()-3, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()-3, T.getyStart()+10, 10, 10));
						g2.draw(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()+10, 10, 10));
						g2.fill(new Ellipse2D.Double(T.getxStart()+9, T.getyStart()+10, 10, 10));
						T.UpdateMove();
					}
				}
			}
	}

}
}
