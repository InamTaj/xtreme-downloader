package idm;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MyFileChooser extends JFrame {
		
	private static final long serialVersionUID = -9197600984460445222L;
	
	protected JFileChooser fileChooser;

	MyFileChooser() //Constructor 
	{
		super("Set File Download Path");
		super.setSize(450, 300);
		
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		super.add(fileChooser);
		
		//super.setVisible(true);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	protected int ShowSaveDialog() {
		
		return fileChooser.showSaveDialog(this);
	}
}