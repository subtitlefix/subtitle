package com.hu.training.stocktest.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Account {
	
	Map<String, Long> stockMap = new HashMap<>();
	Map<String, Long> bondMap = new HashMap<>();
	
	private String id;
	
	public Account(String id) {
		this.id = id;
	}
	
	private void addToMap(Map<String, Long> map, String name, Long quantity) {
		Long value = map.get(name);
		if(value == null) {
			map.put(name, quantity);
		}
		else {
			value += quantity;
			map.put(name, value);
		}
	}

	public void addBond(String name, Long value) {	
		addToMap(bondMap, name, value);
	}
	
	public void addStock(String name, Long value) {
		addToMap(stockMap, name, value);
	}

	public int bondCount(String bondname) {
		return bondMap.get(bondname) == null ? 0 : bondMap.get(bondname).intValue();
	}
	
	public int stockCount(String stockname) {
		return stockMap.get(stockname) == null ? 0 : stockMap.get(stockname).intValue();
	}

}
