package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dao.DBAccess;
import com.example.util.Common;

@RestController
@RequestMapping("/api")
public class APIController {

	String ipaddr = "127.0.0.1";
	int port = 8888;
	String command = "checkplace";

	@RequestMapping("/login")
	public boolean login(String username, String password) {

		DBAccess db = null;

		try {
			db = new DBAccess();

			List<LinkedHashMap<String, Object>> queryResult = db.executeQuery(Common.SQL_GET_USER_DATA,
					new String[] { username });

			if (queryResult.get(0).get("password").equals(password)) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@RequestMapping("/getserver")
	public List<LinkedHashMap<String, Object>> getserver() {

		DBAccess db = null;

		try {
//			int rev = checkplace();
//
//			if (rev < 1) {
//				return null;
//			}

			db = new DBAccess();

			List<LinkedHashMap<String, Object>> queryResult = db.executeQuery(Common.SQL_GET_SERVER_DATA,
					new String[] {});

			return queryResult;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/getinstance")
	public List<LinkedHashMap<String, Object>> getinstance() {

		DBAccess db = null;

		try {

			db = new DBAccess();

			List<LinkedHashMap<String, Object>> queryResult = db.executeQuery(Common.SQL_GET_INSTANCE_DATA,
					new String[] {});

			return queryResult;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/cloneVM")
	public int cloneVM(String oldvmname,String newvmname) {
		String rev = "";
		int result = -1;
		DBAccess db = null;

		try {

			db = new DBAccess();

			Properties prop = Common.getConfig();

			ipaddr = prop.getProperty("server1");
			port = Integer.parseInt(prop.getProperty("port1"));
			command = "clone/" + oldvmname+ "/" + newvmname;

			rev = send(ipaddr, port, command);
			
			if (!rev.equals("")) {
				result = MakeKeys.MakeKeys(newvmname, rev);
				result = db.executeUpdate(Common.SQL_UPDATE_IPADDRESS_DATA, new String[] {rev,newvmname});
			}
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@RequestMapping("/sendcmd")
	public int sendcmd(String command, String vmname) {
		String rev = "";
		int result = -1;
		DBAccess db = null;

		try {

			db = new DBAccess();

			Properties prop = Common.getConfig();

			ipaddr = prop.getProperty("server1");
			port = Integer.parseInt(prop.getProperty("port1"));
			command = command + "/" + vmname;

			rev = send(ipaddr, port, command);

			if (!rev.equals("")) {
				result = db.executeUpdate(Common.SQL_UPDATE_INSTANCE_DATA, new String[] {rev,vmname});
			}
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int checkplace() {
		DBAccess db = null;
		db = new DBAccess();
		String rev = "";
		String[] revList = null;
		int result = -1;
		Properties prop = Common.getConfig();

		for (int i = 1; i <= 1; i++) {
			try {
				ipaddr = prop.getProperty("server" + i);
				port = Integer.parseInt(prop.getProperty("port" + i));
				command = prop.getProperty("command1");

				rev = send(ipaddr, port, command);

				if (!rev.equals("error")) {
					revList = rev.split("/");
					result = db.executeUpdate(Common.SQL_UPDATE_SERVER_DATA,
							new String[] { revList[0], revList[1], revList[2], String.valueOf(i) });
				} else {
					break;
				}

			} catch (SQLException e) {
				return (result);
			} catch (Exception e) {
				return (result);
			}
		}

		return result;
	}

	public static String send(String ipaddr, int port, String command) {
		try {
			Socket socket = new Socket(ipaddr, port);

			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream);
			printWriter.print(command);
			printWriter.flush();
			socket.shutdownOutput();

			InputStream inputStream = socket.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String info = bufferedReader.readLine();

			bufferedReader.close();
			inputStream.close();
			printWriter.close();
			outputStream.close();
			socket.close();

			return (info);
		} catch (UnknownHostException e) {
			return ("error");
		} catch (IOException e) {
			return ("error");
		} catch (Exception e) {
			return ("error");
		}
	}
}
