package com.newegg.monitor.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class RealIp {

	public static String getRealIp() throws Exception {
		Set<String> ips = new HashSet<String>();
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
            	InetAddress inetAddress = address.nextElement();
            	if(!inetAddress.isLoopbackAddress() && 
            			inetAddress.getHostAddress().indexOf(":") == -1
            			&& inetAddress.isSiteLocalAddress()){
            		String ip = inetAddress.getHostAddress();
                    ips.add(ip);
            	}
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (String ip : ips) {
        	buffer.append(ip).append(";");
		}
        return buffer.substring(0, buffer.lastIndexOf(";"));
    }
	
}
