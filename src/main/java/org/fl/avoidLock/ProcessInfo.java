/*
 * MIT License

Copyright (c) 2017, 2023 Frederic Lefevre

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
