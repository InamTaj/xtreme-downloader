package idm;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

class AfterDownloadGUI extends JFrame {

	//public final String filePath;
	
	private static final long serialVersionUID = 670084969482557587L;
	private JPanel buttonsPanel;
	private JButton closeButton, openFolderButton;
	private JLabel successLabel;

	public AfterDownloadGUI(String fP)	//constructor
	{
		super("Downlad Successful!");
		super.setSize(350, 150);
		super.setLocationRelativeTo(null);
		getContentPane().setLayout( new GridLayout(2, 1) );
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final String filePath = fP;
		
		successLabel = new JLabel("                DOWNLOAD SUCCESSFULL!");
		successLabel.setFont(new Font("Centaur", Font.BOLD, 20));
		super.add( successLabel );

		closeButton = new JButton("Close");
		openFolderButton = new JButton("Open Folder");
		openFolderButton.setFont(new Font("SansSerif", Font.PLAIN, 13));

		buttonsPanel = new JPanel();
		buttonsPanel.setLayout( new FlowLayout() );
		buttonsPanel.add(openFolderButton);
		buttonsPanel.add(closeButton);
		super.add(buttonsPanel);

		///////////////////////EVENT HANDLING
		openFolderButton.addActionListener(
				new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) {
						try {
							Desktop.getDesktop().open(new File(filePath));
							System.exit(0);
						} catch (IOException e) {
							System.out.println("Exception: " + e);
						}
					}

				});
		closeButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

	}


}