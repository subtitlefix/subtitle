package com.hu.training.stocktest.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hu.training.stocktest.enums.ContentType;
import com.hu.training.stocktest.jaxb.po.Basket;
import com.hu.training.stocktest.jaxb.po.BasketList;
import com.hu.training.stocktest.jaxb.po.Content;
import com.hu.training.stocktest.model.ABasket;
import com.hu.training.stocktest.model.Account;

public class AccountFactory {

    private Map<String, Account> accountMap = new HashMap<>();

    private Map<String, ABasket> baskets;

    public AccountFactory(BasketList basketList) {
        convert(basketList);
    }

    public void createAccount(String id, ContentType contentType, String name, Long value) {
        Account a = accountMap.get(id);
        if (a == null) {
            a = new Account(id);
            accountMap.put(id, a);
        }
        switch (contentType) {
        case BASKET:
            ABasket b = baskets.get(name);
            a.addBasket(b, value);
            break;
        case BOND:
            a.addBond(name, value);
            break;
        case STOCK:
            a.addStock(name, value);
            break;
        }
    }

    private void convert(BasketList basketList) {
        baskets = new HashMap<>();
        for (Basket b : basketList.getBasket()) {
            ABasket ab = new ABasket(b.getName());
            for (Content c : b.getContentList().getContent()) {
                if (c.getType().equals(ContentType.BOND.name())) {
                    System.out.println("add bond :" + c.getSymbol() + "to " + b.getName());
                    ab.addBond(c.getSymbol(), c.getQuantity().longValue());
                } else if (c.getType().equals(ContentType.STOCK.name())) {
                    ab.addStock(c.getSymbol(), c.getQuantity().longValue());
                } else if (c.getType().equals(ContentType.BASKET.name())) {
                    ab.addBasket(baskets.get(c.getSymbol()), c.getQuantity().longValue());
                }
            }
            baskets.put(b.getName(), ab);
        }
    }

    public int allBondCount(String bondname) {
        int sum = 0;
        for (Entry<String, Account> entry : accountMap.entrySet()) {
            Account a = entry.getValue();
            sum += a.bondCount(bondname);
        }
        return sum;
    }

    public int allStockCount(String stockname) {
        int sum = 0;
        for (Entry<String, Account> entry : accountMap.entrySet()) {
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
