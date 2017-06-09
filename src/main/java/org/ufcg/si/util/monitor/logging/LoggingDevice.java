package org.ufcg.si.util.monitor.logging;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LoggingDevice {

	private BufferedWriter logger;

	public LoggingDevice() {
	}

	public void createLogger(String filePath) {
		FileOutputStream fs;
		try {
			fs = new FileOutputStream(filePath, true);
			OutputStreamWriter os = new OutputStreamWriter(fs);
			this.logger = new BufferedWriter(os);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public  void logData(String data){
		try {
			this.logger.write(data);
			this.logger.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void closeLogger(){
		try {
			this.logger.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
