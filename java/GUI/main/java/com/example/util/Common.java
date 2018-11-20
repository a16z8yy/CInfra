package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class Common {

//設定ファイル
public static String App_Config = "application.properties";

//login
public static String SQL_GET_USER_DATA = "select password from user_mst where user_name = ?";

//select server table
public static String SQL_GET_SERVER_DATA = "SELECT server_id,server_status,server_size,used_size,percent FROM server_mst";

//insert into server table
public static String SQL_UPDATE_SERVER_DATA = "update server_mst set server_size=?,used_size=?,percent=? where server_id = ?";

//select instance table
public static String SQL_GET_INSTANCE_DATA = "SELECT instance_id,instance_name,ip_address,instance_status,vcpus, ram, size, network_bridge, os_type , create_date FROM instance_mst";

//insert into instance table
public static String SQL_INSERT_INSTANCE_DATA = "insert into instance_mst('instance_name',\r\n" + 
		"'ip_address',\r\n" + 
		"'instance_status',\r\n" + 
		"'vcpus',\r\n" + 
		"'ram',\r\n" + 
		"'size',\r\n" + 
		"'network_bridge',\r\n" + 
		"'os_type',\r\n" + 
		"'delete_flag')values(?,?,1,?,?,?,?,?,0)";

//update instance status
public static String SQL_UPDATE_INSTANCE_DATA = "update instance_mst set instance_status=? where instance_name = ?";

//update instance ip
public static String SQL_UPDATE_IPADDRESS_DATA = "update instance_mst set ip=? where instance_name = ?";

//設定ファイルを読み込むメソード
public static Properties getConfig(){
		Properties prop = new Properties();
		try {
			//設定ファイルを読み込む
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(App_Config);

			//文字が'UTF-8'をセットする
			prop.load(new InputStreamReader(in,Charset.forName("UTF-8")));

		}catch(IOException e){

		}

		return prop;
	}


}
