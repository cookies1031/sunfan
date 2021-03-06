package com.sunfan.monitor.entity.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sunfan.monitor.entity.CpuInfo;
@Component
public class CpuInfoUtil extends EntityBaseUtil{
	
	/**
	 * to transfer cpuInfo Object
	 * @param result  string type mpstat cmd result  
	 * @return  List<CpuInfo>
	 */
	public List<CpuInfo> mpstatResultTransferCpuObject(String result){
		List<String[]> contentArrayList = super.transferListofStringArray(result,"nice");
		return this.transfer(contentArrayList);
	}
	
	
	
	
	/**
	 * to transfer cpuInfo Object
	 * @param contentArrayList 
	 * @return List<CpuInfo>
	 */
	private List<CpuInfo> transfer(List<String[]> contentArrayList){
		List<CpuInfo> cpuList= new ArrayList<CpuInfo>();
		List<String> headList =Arrays.asList(contentArrayList.get(0));
		contentArrayList.remove(0);
		for(String[] info:contentArrayList){
			CpuInfo cpuInfo = new CpuInfo();
			cpuInfo.setTime(info[0]);
			cpuInfo.setCpu(resolveValueByTagName(headList,info,"CPU"));
			cpuInfo.setUsr(resolveValueByTagName(headList,info,"%usr"));
			if("".equals(cpuInfo.getUsr())) cpuInfo.setUsr(resolveValueByTagName(headList,info,"%user"));
			cpuInfo.setNice(resolveValueByTagName(headList,info,"%nice"));
			cpuInfo.setSys(resolveValueByTagName(headList,info,"%sys"));
			cpuInfo.setIowait(resolveValueByTagName(headList,info,"%iowait"));
			cpuInfo.setIrq(resolveValueByTagName(headList,info,"%irq"));
			cpuInfo.setSoft(resolveValueByTagName(headList,info,"%soft"));
			cpuInfo.setSteal(resolveValueByTagName(headList,info,"%steal"));
			cpuInfo.setGuest(resolveValueByTagName(headList,info,"%guest"));
			cpuInfo.setIdle(resolveValueByTagName(headList,info,"%idle"));
			cpuList.add(cpuInfo);
		}
		return cpuList;
	}

}
