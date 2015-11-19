package com.newegg.monitor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import com.newegg.monitor.db.WritingData;
import com.newegg.monitor.db.impl.InfluxDBWrite;
import com.newegg.monitor.db.impl.RedisManager;
import com.newegg.monitor.util.PropertiesUtil;
import com.newegg.monitor.util.RealIp;

public class CollectService extends TimerTask{
	private static final Logger LOGGER = Logger.getLogger(CollectService.class);

	private Sigar sigar;
	private List<WritingData> writings = new ArrayList<WritingData>();
	
	public CollectService(){
		String redisHost = PropertiesUtil.getInstence().getProperty("RedisManager.host");
		String influxDBHost = PropertiesUtil.getInstence().getProperty("InfluxDB.host");
		sigar = new Sigar();
		if(redisHost != null && !"".equals(redisHost)){
			RedisManager redis = new RedisManager(redisHost);
			writings.add(redis);
		}
		if(influxDBHost != null && !"".equals(influxDBHost)){
			InfluxDBWrite influx = new InfluxDBWrite(influxDBHost);
			writings.add(influx);
		}
	}
	
	public static void start(){
		String tmp = PropertiesUtil.getInstence().getProperty("timer");
		Integer time = tmp == null?Integer.valueOf(tmp):60000;
		Timer timer = new Timer();
		timer.schedule(new CollectService(), 0, time);
	}
	
	@Override
	public void run() {
		try {
			CpuPerc cpuPerc = sigar.getCpuPerc();
			Mem mem = sigar.getMem();
			String host = RealIp.getRealIp();
			Map<String, Object> form = new HashMap<String, Object>();
			form.put("host", host);
			int total = (int)(mem.getTotal()/1048576);
			int used = (int)(mem.getUsed()/1048576);
			form.put("totalMem", total);
			form.put("userMem", used);
			form.put("freeMem", (int)(mem.getFree()/1048576));
			form.put("combinedMem",(used * 10000 / total) / 100.00);
			form.put("userCpu", ((int) (cpuPerc.getCombined() * 10000)) / 100.00);
			form.put("sysCpu",  ((int)(cpuPerc.getSys() * 10000))  / 100.00);
			form.put("idleCpu",  ((int)(cpuPerc.getIdle() * 10000)) / 100.00);
			form.put("combinedCpu", ((int)(cpuPerc.getCombined() * 10000)) / 100.00);
			for (WritingData writing : writings) {
				writing.write(form);
			}
		} catch (Exception e) {
			LOGGER.error("POST ERROR",e);
		}
	}
}
