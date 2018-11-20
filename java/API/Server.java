package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	ServerSocket server = null;
	Socket sk = null;
	BufferedReader rdr = null;
	PrintWriter wtr = null;

	public Server() {
		try {
			server = new ServerSocket(8888);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		while (true) {
			// System.out.println("Listenning...");
			try {
				sk = server.accept();
				ServerThread th = new ServerThread(sk);
				th.start();
				sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		new Server().start();
	}

	class ServerThread extends Thread {

		Socket sk = null;

		public ServerThread(Socket sk) {
			this.sk = sk;
		}

		public void run() {
			try {
				wtr = new PrintWriter(sk.getOutputStream());
				rdr = new BufferedReader(new InputStreamReader(sk.getInputStream()));
				String line = rdr.readLine();
				String rev = "";
				int creint = 0;
				String[] arr = line.split("/");
				String ipaddress = "";
				String macaddr = "";

				System.out.println("line:" + line);

				switch (arr[0]) {
				case "checkplace":
					rev = com.GetPlaceInfo.GetPlaceInfo().toString();
					break;
				// case "create":
				// creint = com.CreateInstance.CreateInstance();
				//
				// if(creint == 1) {
				// arr2 = com.GetIpaddress.GetIpaddress(arr[1]);
				// }
				//
				// break;
				case "clone":
					creint = com.CloneInstance.CloneInstance(arr[1], arr[2]);
					if (creint == 1) {
						macaddr = com.GetIpaddress.GetIpaddress(arr[2]);
					}
					if(macaddr!=null) {
						ipaddress = com.GetIpaddress2.GetIpaddress2(macaddr);
					}
					System.out.println("macaddr:"+macaddr);
					System.out.println("ipaddress:"+ipaddress);
					break;
				case "info":
					rev = com.GetStatus.GetStatus(arr[1]);
					break;
				case "start":
					rev = "" + com.GetCommand.GetCommand(arr[0], arr[1]);
					break;
				case "shutdown":
					rev = "" + com.GetCommand.GetCommand(arr[0], arr[1]);
					break;
				case "suspend":
					rev = "" + com.GetCommand.GetCommand(arr[0], arr[1]);
					break;
				case "resume":
					rev = "" + com.GetCommand.GetCommand(arr[0], arr[1]);
					break;
				case "destroy":
					rev = "" + com.GetCommand.GetCommand(arr[0], arr[1]);
					break;
				}

				System.out.println("rev:" + rev);
				wtr.println(rev + "\n");
				wtr.flush();
				sk.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}