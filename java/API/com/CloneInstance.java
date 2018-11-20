package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/***
 * 
 * @author autumnzest
 *
 */

public class CloneInstance {

	public static int CloneInstance(String privm, String newvm) {

		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("virt-clone --original " + privm + " --name " + newvm
					+ " --file /var/lib/libvirt/images/" + newvm + ".img");
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str = null;
				String[] strArray = null;
				while ((str = in.readLine()) != null) {
					strArray = str.split(" ");
				}
				for (String para : strArray) {
					System.out.println(para);
					
					if (para.contains("成功")) {
						return 1;
					}
				}
			} catch (Exception e) {
				//e.printStackTrace();
				return -1;
			} finally {
				in.close();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return -1;
		}
		return 0;
	}
}
