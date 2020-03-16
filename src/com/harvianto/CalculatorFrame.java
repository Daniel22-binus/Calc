package com.harvianto;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CalculatorFrame extends JFrame implements ActionListener{

	private JLabel monitor = new JLabel("0");
	
	private double old = 0.0;
	private Object operator;
	
	private final String[] buttonLabels = {
		"%", 	"sqrt", "x^2", 	"1/x",
		"CE", 	"C", 	"<-", 	"/",
		"7", 	"8", 	"9", 	"*",
		"4",	"5",	"6", 	"-",
		"1", 	"2", 	"3", 	"+",
		"+-",	"0",	".",	"="
	};
	
	private JPanel buttonPanel = new JPanel(new GridLayout(6, 4));
	
	private Map<String, Object> listOperators = new HashMap<String, Object>();
	
	public CalculatorFrame() {
		setSize(300, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		listOperators.put("+", new BinaryOperator() {
			
			@Override
			public double doOperation(double a, double b) {
				return a + b;
			}
		});
		listOperators.put("-", (BinaryOperator)((a, b) -> a - b)); // lambda
		listOperators.put("*", (BinaryOperator)((a, b) -> a * b));
		listOperators.put("/", (BinaryOperator)((a, b) -> a / b));
		
		monitor.setHorizontalAlignment(SwingConstants.RIGHT);
		monitor.setFont(new Font("Arial", Font.PLAIN, 30));
		add(monitor, BorderLayout.NORTH);
		
		for (String buttonLabel : buttonLabels) {
			JButton btn = new JButton(buttonLabel);
			btn.addActionListener(this);
			buttonPanel.add(btn);
		}
		
		add(buttonPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new CalculatorFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton btn = (JButton)e.getSource();
			String op = btn.getText();
			System.out.println(btn.getText());
			if("1234567890".contains(op)) {
				if(monitor.getText() == "0") {
					monitor.setText(op);
				} else {
					monitor.setText(monitor.getText() + op);
				}
			} else if("+-*/".contains(op)) {
				old = Double.parseDouble(monitor.getText());
				monitor.setText("0");
				operator = listOperators.get(op);
			} else if("=".equals(op)) {
				double baru = Double.parseDouble(monitor.getText());
				double result = ((BinaryOperator)operator).doOperation(old, baru);
				if(result == (int)result)
					monitor.setText("" + (int)result);
				else
					monitor.setText("" + result);
			}
		}
	}

}

interface UnaryOperator {
	double doOperation(double x);
}

interface BinaryOperator {
	double doOperation(double a, double b);
}
