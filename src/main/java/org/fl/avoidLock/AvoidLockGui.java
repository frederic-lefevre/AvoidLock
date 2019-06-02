package org.fl.avoidLock;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

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
			
			// process control buttons
			startStop = new UiControl() ;
			getContentPane().add(startStop.getProcCtrl()) ;
			
			// process information display
			stepsInfo = new ProcessInfo() ;		
			getContentPane().add(stepsInfo.getProcInfos()) ;
			
			// launch the SwingWorker
			AvoidLock sw = new AvoidLock(startStop, stepsInfo) ;
			sw.execute() ;
			
		} else {
			System.out.println("Initialisation failed") ;
		}
		
	}
}
