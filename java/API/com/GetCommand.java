package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/***
 * 
 * @author autumnzest
 * @vmname マシン名
 *
 */

public class GetCommand {
	static String[] statusarr = { "","実行中", "","シャットオフ","一時停止中" };

	public static int GetCommand(String cmd, String vmname) {
		String command = "";
		String line = "";
		int status = 0;
		try {
			switch (cmd) {
			case "start":
				command = "virsh start " + vmname;
				status = 1;
				break;
			case "shutdown":
				command = "virsh shutdown " + vmname;
				status = 3;
				break;
			case "suspend":
				command = "virsh suspend " + vmname;
				status = 4;
				break;
			case "resume":
				command = "virsh resume " + vmname;
				status = 1;
				break;
			case "destroy":
				command = "virsh destroy " + vmname;
				status = 3;
				break;
			}

			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(command);
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str = null;
				while ((str = in.readLine()) != null) {
					System.out.println("str：" + str);
					line += str;
				}
				if (line.length() == 0) {
					return -1;
				}
				status = checkStatus(status, vmname);

			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return status;
	}

	public static int checkStatus(int index, String vmname) {
		String st = "";
		int count = 0;
		try {
			do {
				st = com.GetStatus.GetStatus(vmname);
				count++;
				Thread.sleep(1000);
//				System.out.println("st:"+st);
//				System.out.println("count:"+count);
//				System.out.println("statusarr[index]"+statusarr[index]);

			} while (!st.equals(statusarr[index]) && count < 100);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		if (count < 100) {
			return index;
		} else {
			return -1;
		}
	}
}
