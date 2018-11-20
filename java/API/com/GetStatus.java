package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetStatus {

	public static String GetStatus(String vmname) {
		String command = "";
		String line = "";
		try {
			command = "virsh dominfo " + vmname;

			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(command);
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str = null;
				while ((str = in.readLine()) != null) {
					line += str;
				}
				if(line.contains("実行中")) {
					line = "実行中";
				}else if(line.contains("シャットオフ")) {
					line = "シャットオフ";
				}else if(line.contains("一時停止中")) {
					line = "一時停止中";
				}

			} catch (Exception e) {
				e.printStackTrace();
				return "";
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return line;
	}
}