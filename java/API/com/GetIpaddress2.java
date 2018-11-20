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

public class GetIpaddress2 {

	public static String GetIpaddress2(String macaddr) {
		String strArray = "";
		ArrayList<String> List = new ArrayList<String>(); 
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("virsh net-dhcp-leases default " + macaddr);
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str = null;
				while ((str = in.readLine()) != null) {
					List.add(str);
				}
				strArray = List.get(2).split("      ")[1].toString().substring(0, 15);
				System.out.println("ip:"+strArray);
				
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
