package com.hu.training.stocktest.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hu.training.stocktest.enums.ContentType;
import com.hu.training.stocktest.jaxb.po.Basket;
import com.hu.training.stocktest.jaxb.po.BasketList;
import com.hu.training.stocktest.jaxb.po.Content;
import com.hu.training.stocktest.model.Account;

public class AccountFactory {
	
	private Map<String, Account> accountMap = new HashMap<>();
	
	private BasketList basketList;
	
	public AccountFactory(BasketList basketList) {
		this.basketList = basketList;
	}

	public void createAccount(String id, ContentType contentType, String name, Long value) {
		Account a = accountMap.get(id);
		if(a == null) {
			a = new Account(id);
			accountMap.put(id, a);
		}
		switch(contentType) {
		case BASKET:
			Basket b = findBasket(name);
			addBasket(a, b, value);
			break;
		case BOND:
			a.addBond( name, value);
			break;
		case STOCK:
			a.addStock( name, value);
			break;
		}
	}

	private Basket findBasket(String name) {
		for(Basket b: basketList.getBasket()) {
			if(b.getName().equals(name)) {
				return b;
			}
		}
		return null;
	}
	
	public void addBasket(Account account, Basket basket, Long value) {
		for(Content c: basket.getContentList().getContent()){
			String name = c.getSymbol();
			Long sum = c.getQuantity().longValue() * value;
			ContentType ct = ContentType.valueOf(c.getType());
			switch(ct) {
			case BASKET:
				addBasket(account, findBasket(name), sum);
				break;
			case STOCK:
				account.addStock(name, sum);
				break;
			case BOND:
				account.addBond(name, sum);
				break;
			}
		}
	}
	
	public int allBondCount(String bondname) {
		int sum = 0;
		for(Entry<String, Account> entry: accountMap.entrySet()) {
			Account a = entry.getValue();
			sum += a.bondCount(bondname);
		}
		return sum;
	}
	
	public int allStockCount(String stockname) {
		int sum = 0;
		for(Entry<String, Account> entry: accountMap.entrySet()) {
			Account a = entry.getValue();
			sum += a.stockCount(stockname);
		}
		return sum;
	}

	public int bondCount(String id, String bondname) {
		Account a = accountMap.get(id);
		return a.bondCount(bondname);
	}

	public int stockCount(String id, String stockname) {
		Account a = accountMap.get(id);
		return a.stockCount(stockname);
	}
}
