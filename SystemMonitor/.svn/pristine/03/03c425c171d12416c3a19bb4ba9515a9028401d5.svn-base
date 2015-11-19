package com.newegg.monitor.db.impl;

import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import com.newegg.monitor.db.WritingData;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class RedisManager implements WritingData{
	private static final Logger LOGGER = Logger.getLogger(RedisManager.class);

	private Client client;
	private WebResource webResource;
	
	public RedisManager(String host){
		client = Client.create();
		webResource = client.resource(host);
		webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON_TYPE);
	}
	
	public boolean write(Map<String, Object> values) {
		Form form = new Form();
		for (String key : values.keySet()) {
			Object value = values.get(key);
			form.add(key, value);
		}
		String status = webResource.post(String.class,form);
        if(!"true".equals(status)){
        	LOGGER.error("POST ERROR");
        }
		return false;
	}

}
