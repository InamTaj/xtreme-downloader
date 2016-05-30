package idm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingWorker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import java.awt.Color;

public class Downloader extends SwingWorker<Void, Float> {

	//Variables for Calculation
	private long fileSize;
	static protected long chunkSize;
	static protected long startFrom;
	static protected long endRange;
	private boolean downloadFlag;
	private String filePath;

	//GUI Variables
	private JFrame frame;
	private JTextField urlField;
	private JLabel fileSizeLabel;
	private JProgressBar progressBar_1;
	private JProgressBar progressBar_2;
	private JProgressBar progressBar_3;
	private JProgressBar progressBar_4;
	private JProgressBar progressBar_5;
	private JProgressBar progressBar_6;
	private JPanel panel_1;
	private JPanel downloadDetailsPanel;
	private JLabel lblT_1;
	private JLabel lblT_2;
	private JLabel lblT_3;
	private JLabel lblT_4;
	private JLabel lblT_5;
	private JLabel lblStatus;
	private JLabel statusLabel;


	//Main Method
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Code to set look & feel of GUI to windows
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (Throwable e) {
						e.printStackTrace();
					}

					Downloader window = new Downloader();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
					//window.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//CONSTRUCTOR
	public Downloader() {

		fileSize = -1;
		downloadFlag = false;
		chunkSize = startFrom = endRange = 0;

		initialize();	//to Initialize GUI Components	
	}


	private void initialize() //function definition
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 731, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setTitle("eXtreme DownloadeR!");

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "name_25751140116213");
		panel.setLayout(null);

		JLabel lblEnterUrlof = new JLabel("Enter File URL to Download File");
		lblEnterUrlof.setFont(new Font("Candara", Font.BOLD, 18));
		lblEnterUrlof.setBounds(238, 15, 246, 22);
		panel.add(lblEnterUrlof);

		urlField = new JTextField();
		urlField.setBounds(105, 48, 402, 31);
		panel.add(urlField);
		urlField.setColumns(10);

		JButton btnStartDownload = new JButton("Download");
		btnStartDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if( !urlField.getText().isEmpty() ) {

					JOptionPane.showMessageDialog(null, "Please Select where to download your file:", "File Path", JOptionPane.INFORMATION_MESSAGE);

					MyFileChooser obj = new MyFileChooser();
					int check = obj.ShowSaveDialog();

					if(check == JFileChooser.APPROVE_OPTION) {

						String temp = obj.fileChooser.getSelectedFile().getAbsolutePath();
						filePath = ( temp.replace('\\', '/') ) + '/';					
					}

					downloadDetailsPanel.setVisible(true);
					execute();
				}
				else {
					JOptionPane.showMessageDialog(null, "Empty URL?\nPlease Enter a Vaild URL to Start Downloading!", "ERROR - Empty URL", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnStartDownload.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 17));
		btnStartDownload.setBounds(524, 48, 114, 31);
		panel.add(btnStartDownload);

		downloadDetailsPanel = new JPanel();
		downloadDetailsPanel.setVisible(false);
		downloadDetailsPanel.setBounds(105, 95, 533, 214);
		panel.add(downloadDetailsPanel);
		downloadDetailsPanel.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBounds(289, 11, 234, 201);
		downloadDetailsPanel.add(panel_1);
		panel_1.setLayout(null);

		progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(24, 21, 20, 140);
		panel_1.add(progressBar_1);
		progressBar_1.setOrientation(SwingConstants.VERTICAL);
		progressBar_1.setMaximum(100);
		progressBar_1.setStringPainted(true);
		progressBar_1.setDoubleBuffered(true);

		progressBar_2 = new JProgressBar();
		progressBar_2.setBounds(56, 21, 20, 140);
		panel_1.add(progressBar_2);
		progressBar_2.setOrientation(SwingConstants.VERTICAL);
		progressBar_2.setStringPainted(true);

		progressBar_3 = new JProgressBar();
		progressBar_3.setBounds(88, 21, 20, 140);
		panel_1.add(progressBar_3);
		progressBar_3.setOrientation(SwingConstants.VERTICAL);
		progressBar_3.setStringPainted(true);

		progressBar_4 = new JProgressBar();
		progressBar_4.setBounds(120, 21, 20, 140);
		panel_1.add(progressBar_4);
		progressBar_4.setOrientation(SwingConstants.VERTICAL);
		progressBar_4.setStringPainted(true);

		JLabel lblT = new JLabel("T1");
		lblT.setBounds(29, 163, 20, 27);
		panel_1.add(lblT);

		progressBar_5 = new JProgressBar();
		progressBar_5.setBounds(154, 21, 20, 140);
		panel_1.add(progressBar_5);
		progressBar_5.setOrientation(SwingConstants.VERTICAL);
		progressBar_5.setStringPainted(true);

		progressBar_6 = new JProgressBar();
		progressBar_6.setBounds(184, 21, 20, 140);
		panel_1.add(progressBar_6);
		progressBar_6.setOrientation(SwingConstants.VERTICAL);
		progressBar_6.setStringPainted(true);

		lblT_1 = new JLabel("T2");
		lblT_1.setBounds(60, 163, 20, 27);
		panel_1.add(lblT_1);

		lblT_2 = new JLabel("T3");
		lblT_2.setBounds(92, 163, 20, 27);
		panel_1.add(lblT_2);

		lblT_3 = new JLabel("T4");
		lblT_3.setBounds(124, 163, 20, 27);
		panel_1.add(lblT_3);

		lblT_4 = new JLabel("T5");
		lblT_4.setBounds(158, 163, 20, 27);
		panel_1.add(lblT_4);

		lblT_5 = new JLabel("T6");
		lblT_5.setBounds(188, 163, 20, 27);
		panel_1.add(lblT_5);

		JButton btnCancel = new JButton("CANCEL");
		btnCancel.setBounds(6, 168, 96, 23);
		btnCancel.setForeground(Color.RED);
		downloadDetailsPanel.add(btnCancel);
		btnCancel.setBackground(Color.BLACK);

		fileSizeLabel = new JLabel("File Size: ");
		fileSizeLabel.setBounds(6, 13, 201, 42);
		downloadDetailsPanel.add(fileSizeLabel);
		fileSizeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblStatus.setBounds(6, 62, 66, 36);
		downloadDetailsPanel.add(lblStatus);

		statusLabel = new JLabel("Starting Download...");
		statusLabel.setForeground(new Color(220, 20, 60));
		statusLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		statusLabel.setBounds(74, 62, 172, 36);
		downloadDetailsPanel.add(statusLabel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//Terminate Thread which is downloading
				downloadFlag = false;
				cancel(true);

			}
		});
	}
	////////////////////END OF CONSTRUCTOR


	//////////////
	///////////////////////SWING-WORKER FUNCTIONS
	protected Void doInBackground() throws Exception {



		String fileURL = urlField.getText();
		URL url = new URL(fileURL);

		String fileName = filePath + ( fileURL.substring( fileURL.lastIndexOf('/')+1, fileURL.length() ) );

		System.setProperty("http.proxyHost", "192.168.10.50");
		System.setProperty("http.proxyPort", "8080");	

		HttpURLConnection uc = (HttpURLConnection) url.openConnection();
		uc.connect();
		fileSize = uc.getContentLengthLong();
		System.out.println("File Size = "+ fileSize );
		uc.disconnect();

		if(fileSize<1048576) {
			fileSizeLabel.setText("File Size: " + String.format("%.2f", ((float)fileSize)/1024)  + " KB");
		}
		else if(fileSize>=1048576 && fileSize<1073741824) {
			fileSizeLabel.setText("File Size: " + String.format("%.2f", ((float)fileSize/1024)/1024) + " MB");
		}
		else if(fileSize>=1073741824) {
			fileSizeLabel.setText("File Size: " + String.format("%.2f", ((float)(fileSize/1024)/1024)/1024) + " GB");
		}

		chunkSize = (long) Math.ceil(fileSize/6);
		System.out.println("Chunk Size = " + chunkSize);

		//-----------------------------------------
		startFrom = 0;
		endRange = (startFrom + chunkSize) - 1;
		System.out.println("Part 1 :: Start = " + startFrom + "\tEnd To = " + endRange);

		Thread t1 = new MyThread(url, fileName, startFrom, endRange, progressBar_1);
		t1.start();
		//-----------------------------------------

		//-----------------------------------------
		startFrom += chunkSize;
		endRange = endRange + chunkSize;
		System.out.println("Part 2 :: Start = " + startFrom + "\tEnd To = " + endRange );

		Thread t2 = new MyThread(url, fileName, startFrom, endRange, progressBar_2);
		t2.start();
		//-----------------------------------------

		//-----------------------------------------
		startFrom += chunkSize;
		endRange = endRange + chunkSize;
		System.out.println("Part 3 :: Start = " + startFrom + "\tEnd To = " + endRange);

		Thread t3 = new MyThread(url, fileName, startFrom, endRange, progressBar_3);
		t3.start();
		//-----------------------------------------

		//-----------------------------------------
		startFrom += chunkSize;
		endRange = endRange + chunkSize;
		System.out.println("Part 4 :: Start = " + startFrom + "\tEnd To = " + endRange );

		Thread t4 = new MyThread(url, fileName, startFrom, endRange, progressBar_4);
		t4.start();
		//-----------------------------------------

		//-----------------------------------------
		startFrom += chunkSize;
		endRange = endRange + chunkSize;
		System.out.println("Part 5 :: Start = " + startFrom + "\tEnd To = " + endRange);

		Thread t5 = new MyThread(url, fileName, startFrom, endRange, progressBar_5);
		t5.start();
		//-----------------------------------------

		//-----------------------------------------
		startFrom += chunkSize;
		long temp = endRange + chunkSize;
		endRange = temp + (fileSize - temp);	//add any remaining bits, that were rounded off in division
		System.out.println("Part 6 :: Start = " + startFrom + "\tEnd To = " + endRange);

		Thread t6 = new MyThread(url, fileName, startFrom, endRange, progressBar_6);
		t6.start();
		//-----------------------------------------
		downloadFlag = true;	//setting flag 

		while(t1.isAlive() || t2.isAlive() || t3.isAlive() || t4.isAlive() || t5.isAlive() || t6.isAlive()) {

			long total = progressBar_1.getValue() + progressBar_2.getValue() + progressBar_3.getValue() + progressBar_4.getValue() + progressBar_5.getValue() + progressBar_6.getValue();
			Float percentage = ((float) total) /6;

			process( percentage );
		}

		//When Completed
		statusLabel.setText("Completed!");

		return null;
	}

	protected void process(Float downloaded) {

		if( !(this.isCancelled()) ) 
		{	
			statusLabel.setText( String.format("%.2f", downloaded) + " %" );

		}
	}

	protected void done() {

		if(downloadFlag==true)
		{
			AfterDownloadGUI window = new AfterDownloadGUI(filePath);
			window.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Downloaded Canceled!", "Canceled!", JOptionPane.ERROR_MESSAGE);
			System.out.println("\nDownload Canceled. ..");
			System.exit(0);
		}
	}
}