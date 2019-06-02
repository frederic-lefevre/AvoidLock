package org.fl.avoidLock;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProcessInfo {

	private JPanel procInfos ;
	private JLabel lblStep;
	private JLabel lblStepNumber;
	private JLabel lblRemainTitle;
	private JLabel lblRemain;
	private JLabel lblStatusTitle;
	private JLabel lblStatus;
	
	
	public ProcessInfo() {
		
		Font font = new Font("Verdana", Font.BOLD, 16);
		procInfos = new JPanel() ;
		procInfos.setLayout(new BoxLayout(procInfos, BoxLayout.Y_AXIS));
		procInfos.setPreferredSize(new Dimension(900,300)) ;
		
		JPanel infoRemain = new JPanel() ;
		lblRemainTitle = new JLabel("Remaining time (minutes:seconds) :");
		lblRemain = new JLabel(Long.toString(Control.getRemainingTime()/60000));
		lblRemainTitle.setFont(font) ;
		lblRemain.setFont(font) ;
		infoRemain.add(lblRemainTitle) ;
		infoRemain.add(lblRemain) ;
		procInfos.add(infoRemain) ;
		
		JPanel infoStep = new JPanel() ;
		lblStep = new JLabel("Process step number :");
		lblStepNumber = new JLabel("0");
		lblStep.setFont(font) ;
		lblStepNumber.setFont(font) ;
		infoStep.add(lblStep) ;
		infoStep.add(lblStepNumber) ;
		procInfos.add(infoStep) ;
		
		JPanel statusSimul = new JPanel() ;
		lblStatusTitle = new JLabel("Process status: ");	
		lblStatus = new JLabel("Initializing");
		lblStatusTitle.setFont(font) ;
		lblStatus.setFont(font) ;
		statusSimul.add(lblStatusTitle) ;
		statusSimul.add(lblStatus) ;
		procInfos.add(statusSimul) ;
	}
	
	public JPanel getProcInfos() {
		return procInfos;
	}
	
	public void setStepNumber(int nb) {
		 lblStepNumber.setText(Integer.toString(nb));
	}

	public void setSimulStatus(String st) {
		 lblStatus.setText(st);
	}
	
	
	public void setRemaingTime(String rt) {
		 lblRemain.setText(rt);
	}
}
