package com.hu.training.stocktest.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AContainer {

    private Map<String, Long> bondMap = new HashMap<>();
    private Map<String, Long> stockMap = new HashMap<>();
    private Map<ABasket, Long> basketMap = new HashMap<>();

    private String name;

    public AContainer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void addToMap(Map<String, Long> map, String name, Long quantity) {
        Long value = map.get(name);
        if (value == null) {
            map.put(name, quantity);
        } else {
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

    public int stockCount(String stockname) {
        int ct = stockMap.get(stockname) == null ? 0 : stockMap.get(stockname).intValue();
        for (Entry<ABasket, Long> entry : basketMap.entrySet()) {
            ABasket ab = entry.getKey();
            ct += ab.stockCount(stockname) * entry.getValue();
        }
        return ct;
    }

    public int bondCount(String bondname) {
        int ct = bondMap.get(bondname) == null ? 0 : bondMap.get(bondname).intValue();
        for (Entry<ABasket, Long> entry : basketMap.entrySet()) {
            ABasket ab = entry.getKey();
            ct += ab.bondCount(bondname) * entry.getValue();
        }
        return ct;
    }
    
    public void addBasket( ABasket b, Long quantity) {
        Long value = basketMap.get(b);
        if (value == null) {
            basketMap.put(b, quantity);
        } else {
            value += quantity;
            basketMap.put(b, value);
        }   
    }
}
