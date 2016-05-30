package idm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JProgressBar;

public class MyThread extends Thread {

	private URL url;
	private long startFrom;
	private long range;
	private InputStream inStream;
	//private String fileName;
	private RandomAccessFile file;
	@SuppressWarnings("unused")
	private long fileSize;
	private JProgressBar progressbar;
	private long totalDownloadSize;
	@SuppressWarnings("unused")
	private float totalDownloaded;

	public MyThread(URL url, String fileName, long startFrom,  long range, JProgressBar progressbar) 	//parameterized constructor
	{

		this.url = url;

		try {
			file = new RandomAccessFile( fileName, "rw" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.startFrom = startFrom;
		this.range = range;
		this.progressbar = progressbar;
		totalDownloadSize = range - startFrom;
		totalDownloaded = 0;
	}

	public void run() {

		Thread.currentThread().setPriority(MAX_PRIORITY);

		System.setProperty("http.proxyHost", "192.168.10.50");
		System.setProperty("http.proxyPort", "8080");	

		HttpURLConnection uc = null;
		try  {

			uc = (HttpURLConnection) url.openConnection();

			uc.setRequestProperty("Range", "bytes="+startFrom+"-"+range);
			uc.connect();
			fileSize = uc.getContentLengthLong();

			inStream = ( uc.getInputStream() );
			System.out.println(Thread.currentThread().getName() + " is now Starting Download..");

			int bytesRead = 0;
			long totalRead = 0;
			byte[] buffer = new byte[ (int) totalDownloadSize ];

			file.seek(startFrom);	//adjusted start of file


			while( (bytesRead = inStream.read(buffer) ) != -1 ) {

				totalRead += bytesRead;	
				file.write(buffer, 0, bytesRead);
				
				float downloaded = 100 * (totalRead / (float) totalDownloadSize);

				progressbar.setValue((int) downloaded);
			}

			/*

			 int[] buffer = int byte[ (int) totalDownloadSize ];

			  for(int i = 0 ; i < totalDownloadSize; i++)
			{
				buffer[i] = inStream.read();
				file.write(buffer[i]);

				//Updating Progress bars
				totalDownloaded = totalDownloaded + 1;
				int downloaded = (int) (100 * ( totalDownloaded/ (float) totalDownloadSize)) ;
				progressbar.setValue(  downloaded );

			}*/
			//file.write(buffer);

			System.err.println( Thread.currentThread().getName() + "'s download is Finished!");
			uc.disconnect();
		}
		catch(IOException e) {
			System.err.println("Exception in " + Thread.currentThread().getName() + "\t Exception = " + e );

		}
		finally {
			try {
				file.close();
				if(inStream!=null)
					inStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}	
}