package com.sunfan.monitor.entity.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sunfan.monitor.entity.MemoryInfo;
import com.sunfan.monitor.entity.Swap;
/**
 * 
 * @author sunfan
 *
 */
@Component
public class MemoryInfoUtil extends EntityBaseUtil{
	/**
	 * String type free result Transfer to MemoryInfo object
	 * @param result
	 * @return
	 */
	public MemoryInfo freeResultTransferMemoryInfoObject(String result){
		List<String[]> contentArrayList = super.transferListofStringArray(result,"total");
		return transfer(contentArrayList);
	}
	
	private MemoryInfo transfer(List<String[]> resList) {
		List<String> head = Arrays.asList(resList.get(0));
		MemoryInfo memoryInfo = new MemoryInfo();
		Swap swap = new Swap();
		memoryInfo.setTotal(resolveValueByTagName(head, resList.get(1), "total"));
		memoryInfo.setUsed(resolveValueByTagName(head, resList.get(1), "used"));
		memoryInfo.setFree(resolveValueByTagName(head, resList.get(1), "free"));
		memoryInfo.setShared(resolveValueByTagName(head, resList.get(1), "shared"));
		memoryInfo.setBuffers(resolveValueByTagName(head, resList.get(1), "buffers"));
		memoryInfo.setCached(resolveValueByTagName(head, resList.get(1), "cached"));
		swap.setTotal(resolveValueByTagName(head, resList.get(3), "total"));
		swap.setUsed(resolveValueByTagName(head, resList.get(3), "used"));
		swap.setFree(resolveValueByTagName(head, resList.get(3), "free"));
		memoryInfo.setSwap(swap);
		return memoryInfo;
	}
	
}
