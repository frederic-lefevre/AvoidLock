package org.fl.avoidLock;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ibm.lge.fl.util.swing.ApplicationTabbedPane;

public class AvoidLockGui  extends JFrame {

	private static final long serialVersionUID = -5402890739503108015L;
	
	private UiControl startStop ;
	private ProcessInfo stepsInfo;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AvoidLockGui window = new AvoidLockGui();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AvoidLockGui() {
				
		//initialisation (property read, logs set up)
		if (Control.init()) {
			setBounds(50, 50, 1250, 900);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("Mouse move simulator") ;
			getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			
			// Tabbed Panel for configuration, tables and controls, and history
			ApplicationTabbedPane bkpTablesPanel = new ApplicationTabbedPane(Control.getRunningContext()) ;
			
			JPanel lockAppGui = new JPanel() ;
			lockAppGui.setLayout(new BoxLayout(lockAppGui, BoxLayout.Y_AXIS));
			
			// process control buttons
			startStop = new UiControl() ;
			lockAppGui.add(startStop.getProcCtrl()) ;
			
			// process information display
			stepsInfo = new ProcessInfo() ;		
			lockAppGui.add(stepsInfo.getProcInfos()) ;
			
			bkpTablesPanel.add(lockAppGui, "Lock control", 0) ;
			bkpTablesPanel.setSelectedIndex(0) ;

			getContentPane().add(bkpTablesPanel) ;
			
			// launch the SwingWorker
			AvoidLock sw = new AvoidLock(startStop, stepsInfo) ;
			sw.execute() ;
			
		} else {
			System.out.println("Initialisation failed") ;
		}
		
	}
}
