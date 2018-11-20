package com.example.demo;

import java.util.ArrayList;

/***
 * 
 * @author autumnzest
 *
 */

public class MakeKeys {

	public static int MakeKeys(String vmname, String ipaddr) {
		ArrayList<String> List = new ArrayList<String>();
		int status = 0;
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("ssh-keygen  -t rsa -P '' -f /home/zhang/sshmanager/" + vmname);
			status = p.waitFor();

			Thread.sleep(5000);
			p = rt.exec("expect sshcopy.sh " + ipaddr + " root passw0rd2018 " + vmname + ".pub");
			status = p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}
		return status;
	}
}
