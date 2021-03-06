package com.sunfan.monitor.platform.linux;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sunfan.monitor.platform.IMonitorable;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;
/**
 * 
 * @author sunfan
 *
 */
@Component
public class LinuxSessionHandle implements IMonitorable,Closeable{
	Logger logger = LoggerFactory.getLogger(LinuxSessionHandle.class);
	private Session session;
	
	/**
	 * open session then execute commands on remote server and return the result of it
	 * @param command
	 * @return String 
	 * @throws IOException 
	 */
	public  String executeCommand(Connection conn,String command) throws IOException {
		String str="";
		try {
		    session = conn.openSession();
			session.execCommand(command);
			str = this.read().toString();
		} catch (Exception e) {
			session.ping();
			throw new IOException("session exception",e);
		}
		finally{
			close();
		}
		return str;
	}
	
	/**
	 * read the result of remote server execute commands
	 * @return
	 * @throws IOException
	 */
	private StringBuffer read() throws IOException{
		InputStream stdout = new StreamGobbler(session.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		String tempString = null;
		// readLine()每次调用都默认会读一行
		StringBuffer str = new StringBuffer();
		while ((tempString = br.readLine()) != null) {
			str.append(tempString+"\r\n");
		}
		br.close();
		return str;
	}
	
	/**
	 * close session
	 */
	@Override
	public void close() throws IOException {
		if (this.session != null) this.session.close();
	}

}
