/*
 * MIT License

Copyright (c) 2017, 2025 Frederic Lefevre

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package org.fl.avoidLock;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.fl.util.swing.ApplicationTabbedPane;

public class AvoidLockGui  extends JFrame {

	private static final long serialVersionUID = -5402890739503108015L;
	
	private static final String DEFAULT_PROP_FILE = "avoidLock.properties";
	
	private static final Logger avoidLockLog = Logger.getLogger(AvoidLockGui.class.getName());
			
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AvoidLockGui window = new AvoidLockGui();
					window.setVisible(true);
				} catch (Exception e) {
					avoidLockLog.log(Level.SEVERE, "Exception during initialisation", e);
				}
			}
		});
	}

	public static String getPropertyFile() {
		return DEFAULT_PROP_FILE;
	}
	
	private AvoidLockGui() throws AWTException {

		// initialisation (property read, logs set up)
		Control.init(DEFAULT_PROP_FILE);
		
		setBounds(50, 50, 1700, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mouse move simulator");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		// Tabbed Panel for configuration, tables and controls, and history
		ApplicationTabbedPane bkpTablesPanel = new ApplicationTabbedPane(Control.getRunningContext());

		JPanel lockAppGui = new JPanel();
		lockAppGui.setLayout(new BoxLayout(lockAppGui, BoxLayout.Y_AXIS));
		
		// process control buttons
		UiControl startStop = new UiControl();
		lockAppGui.add(startStop.getProcCtrl());

		// process information display
		ProcessInfo stepsInfo = new ProcessInfo();
		lockAppGui.add(stepsInfo.getProcInfos());

		bkpTablesPanel.add(lockAppGui, "Lock control", 0);
		bkpTablesPanel.setSelectedIndex(0);

		getContentPane().add(bkpTablesPanel);

		// launch the SwingWorker
		AvoidLock sw = new AvoidLock(startStop, stepsInfo);
		sw.execute();
	}
}
