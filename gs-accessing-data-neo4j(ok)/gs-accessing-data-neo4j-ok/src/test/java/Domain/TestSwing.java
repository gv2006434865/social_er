package Domain;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestSwing extends JFrame {
	TestSwing() {
	JLabel jlbHelloWorld = new JLabel("Hello World");
	add(jlbHelloWorld);
	this.setSize(250, 150); 
	setVisible(true); 
	}
}
