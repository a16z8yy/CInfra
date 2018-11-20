package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/***
 * 
 * @author autumnzest
 * @vmname マシン名
 *
 */

public class GetIpaddress {

	public static String GetIpaddress(String vmname) {
		String strArray = "";
		String ipaddr = "";
		ArrayList<String> List = new ArrayList<String>(); 
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("virsh domiflist " + vmname);
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str = null;
				while ((str = in.readLine()) != null) {
					List.add(str);
				}
				strArray = List.get(2).split("      ")[2].toString();
				System.out.println("mac:"+strArray);

				int rev = com.GetCommand.GetCommand("start", vmname);
				if(rev < 0) {
					return null;
				}
				Thread.sleep(5000);
				//ipaddr = com.GetIpaddress2.GetIpaddress2(strArray);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return strArray;
	}
}
