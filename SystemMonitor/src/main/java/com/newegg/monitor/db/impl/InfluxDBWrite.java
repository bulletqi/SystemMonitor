package com.newegg.monitor.db.impl;

import java.util.Map;
import com.newegg.monitor.db.WritingData;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class InfluxDBWrite implements WritingData{

	private Client client;
	private WebResource webResource;
	
	public InfluxDBWrite(String host){
		client = Client.create();
		webResource = client.resource(host);
	}
	
	public boolean write(Map<String, Object> values) {
		String hosts = (String) values.get("host");
		Double cpu = (Double) values.get("combinedCpu");
		Double mem = (Double) values.get("combinedMem");
		for (String host : hosts.split(";")) {
			webResource.post("monitors,host="+host+" cpu="+cpu + ",mem="+mem);
		}
		return true;
	}
}
