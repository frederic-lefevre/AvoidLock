package org.fl.avoidLock;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class UiControl {

	private JPanel procCtrl ;
	private JPanel startResetButton ;
	private JPanel sliders ;
	private JButton pStart ;
	private JButton pReset ;
	private JSlider mDelay ;
	private JLabel delayLabel;
	private JSlider pDuration ;
	private JLabel durationLabel;
	
	private boolean paused ;
	private boolean isRunning ;
	
	public boolean isRunning() {
		return isRunning;
	}

	public boolean isPaused() {
		return paused;
	}
	
	public UiControl() {
		
		
		paused = true ;
		isRunning = true ;
		procCtrl = new JPanel() ;
		startResetButton = new JPanel() ;
		sliders = new JPanel() ;
		procCtrl.setLayout(new BoxLayout(procCtrl, BoxLayout.Y_AXIS));
		procCtrl.setPreferredSize(new Dimension(1200,500)) ;
		startResetButton.setLayout(new BoxLayout(startResetButton, BoxLayout.X_AXIS));
		startResetButton.setPreferredSize(new Dimension(400,175)) ;
		startResetButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
		sliders.setPreferredSize(new Dimension(1200,300)) ;
		sliders.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				
		pStart = new JButton("Press to start process") ;
		pReset = new JButton("Press to reset process") ;
		
		Font font = new Font("Verdana", Font.BOLD, 18);
		pStart.setFont(font) ;
		pStart.setBackground(Color.ORANGE) ;
		pStart.setPreferredSize(new Dimension(400,150)) ;
				
		pReset.setFont(font) ;
		pReset.setBackground(Color.RED) ;
		pReset.setPreferredSize(new Dimension(400,150)) ;
		
		startResetButton.add(pStart);
		startResetButton.add(Box.createRigidArea(new Dimension(50,0)));
		startResetButton.add(pReset);
		
		mDelay = new JSlider(JSlider.HORIZONTAL,0,Control.getTiming()*4,Control.getTiming()) ;
		mDelay.setMajorTickSpacing(Control.getTiming()/4);
		mDelay.setMinorTickSpacing(Control.getTiming()/40);
		mDelay.setPaintTicks(true);
		mDelay.setPaintLabels(true);
		Font fontTick = new Font("Verdana", Font.BOLD, 10);
		mDelay.setFont(fontTick) ;
		mDelay.setPreferredSize(new Dimension(1000,70)) ;
		
		pDuration = new JSlider(JSlider.HORIZONTAL,0,(int)Control.getRemainingTime()*4,(int)Control.getRemainingTime()) ;
		pDuration.setMajorTickSpacing((int)Control.getRemainingTime()/4);
		pDuration.setMinorTickSpacing((int)Control.getRemainingTime()/40);
		pDuration.setPaintTicks(true);
		pDuration.setPaintLabels(true);
		pDuration.setFont(fontTick) ;
		pDuration.setPreferredSize(new Dimension(1000,70)) ;
				
		// Label for the slider
		delayLabel = new JLabel("Adjust delay between moves (milliseconds)", JLabel.CENTER);
		delayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		delayLabel.setPreferredSize(new Dimension(1000,30)) ;
		durationLabel = new JLabel("Adjust remaining duration (milliseconds)", JLabel.CENTER);
		durationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		durationLabel.setPreferredSize(new Dimension(1000,30)) ;
		        
		sliders.add(delayLabel);
		sliders.add(mDelay);
		sliders.add(durationLabel);
		sliders.add(pDuration);
		
		procCtrl.add(startResetButton) ;
		procCtrl.add(sliders) ;
		
		StartProc sm = new StartProc() ;
		pStart.addActionListener(sm) ;
		pReset.addActionListener(sm) ;
		ChangeDelay cd = new ChangeDelay() ;
		mDelay.addChangeListener(cd);
		ChangeDuration cdur = new ChangeDuration() ;
		pDuration.addChangeListener(cdur);
	}
	
	public class StartProc implements ActionListener {
		
		public void actionPerformed(ActionEvent ae) {
			
			if (ae.getSource() == pReset) {
				isRunning = false ;
				paused = true ;
				pStart.setText("Press to start process") ;
				pStart.setBackground(Color.ORANGE) ;
			} else {
				isRunning = true ;
				paused = !paused ;
				if (paused) {
					pStart.setText("Press to start process") ;
					pStart.setBackground(Color.ORANGE) ;
				} else {
					pStart.setText("Press to pause process") ;
					pStart.setBackground(Color.GREEN) ;
				}
			}
			
		}
	}
	
	public class ChangeDelay implements ChangeListener {
		
		public void stateChanged(ChangeEvent ae) {
			
			if (ae.getSource() == mDelay) {
				Control.setTiming(mDelay.getValue());
			}
		}
	}
	
	public class ChangeDuration implements ChangeListener {
		
		public void stateChanged(ChangeEvent ae) {
			
			if (ae.getSource() == pDuration) {
				Control.setRemainingTime(pDuration.getValue());
			}
		}
	}
	
	public JPanel getProcCtrl() {
		return procCtrl;
	}
	
	public void setRemainingTime(long t) {
		Control.avoidLockLog.fine("set remaing time=" + t);
		pDuration.setValue((int)t);
	}
	
	public void deactivate() {
		
		isRunning = false ;
		paused = true ;
		pStart.setVisible(false) ;
		pReset.setVisible(false) ;
	}
}
