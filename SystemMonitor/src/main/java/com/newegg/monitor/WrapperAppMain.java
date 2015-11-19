package com.newegg.monitor;

import org.apache.log4j.Logger;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;
import com.newegg.monitor.service.CollectService;

public class WrapperAppMain implements WrapperListener{
	private static final Logger LOGGER = Logger.getLogger(WrapperAppMain.class);
	
	public static void main(String[] args){
		WrapperManager.start(new WrapperAppMain(),args);
	}
	
	@Override
	public void controlEvent(int event) {
		 System.out.println("controlEvent(" + event + ")");
	}
	
	@Override
	public Integer start(String[] args) {
		try {
			CollectService.start();
			LOGGER.debug("Timer Started!");
		} catch (Exception e) {
			LOGGER.error("start fail",e);
			System.exit(-1);
		}
		return null;
	}
	
	@Override
	public int stop(int code) {
		System.exit(code);
		return code;
	}
}
