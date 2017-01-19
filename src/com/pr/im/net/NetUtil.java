package com.pr.im.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.pr.im.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;


public class NetUtil {
	
	private static NetUtil netutil;
	private InetAddress inetAddress;
	
	/*
	 * ����ģʽ
	 */
	public static NetUtil getInstance() {
		if (netutil==null) {
			netutil = new NetUtil();
		}
		return netutil;
	}
	
	/*
	 * ��ȡ����IP
	 */
	public String getHostIp(Context mContext ) {
		 //��ȡwifi����  
        WifiManager wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);  
        //�ж�wifi�Ƿ���  
        if (!wifiManager.isWifiEnabled()) {  
        wifiManager.setWifiEnabled(true);   
        }  
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();      
        int ipAddress = wifiInfo.getIpAddress();  
        String ip = formatIpAddress(ipAddress);  
        return ip;  
		
	}
	
	private static String formatIpAddress(int ipAdress) {      
        
        return (ipAdress & 0xFF ) + "." +      
       ((ipAdress >> 8 ) & 0xFF) + "." +      
       ((ipAdress >> 16 ) & 0xFF) + "." +      
       ( ipAdress >> 24 & 0xFF) ;  
    }  
	
	/*
	 * �ж������Ƿ�����
	 */
	public boolean cheackNet(Context context) {
		 ConnectivityManager connManager = (ConnectivityManager)context
				 .getSystemService(Context.CONNECTIVITY_SERVICE);  
		    if(connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable() ) {  
		        return true;  
		    }  else {
		    	Toast.makeText(context, R.string.net_null, Toast.LENGTH_SHORT);
		    	return false;
		    }
		
	}

}
