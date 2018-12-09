package com.hu.training.stocktest.inventory;

import com.hu.training.stocktest.factory.AccountFactory;
import com.hu.training.stocktest.jaxb.po.BasketList;
import com.hu.training.stocktest.parser.BasketXMLParser;
import com.hu.training.stocktest.parser.CSVParser;

public class InventoryImpl {

    private AccountFactory accountFactory;
    
    private BasketList basketList;
    
	public static final String basketSource = "stocks.xml";
	
	public static String assetSource = "accounts.csv";

    /**
     *
     */
    public InventoryImpl() {
    	basketList = new BasketXMLParser().loadDefaultBasketList(basketSource);
    	accountFactory = new AccountFactory(basketList);
    	new CSVParser(accountFactory).startParse(assetSource);
    }

    /**
     * counts the given bond product of the specific account
     *
     * @param account
     * @param bondname
     * @return
     */
    public int bondCount(String id, String bondname) {
    	return accountFactory.bondCount(id, bondname);
    }

    /**
     * counts the given stock product of the specific account
     *
     * @param account
     * @param stockname
     * @return
     */
    public int stockCount(String id, String stockname) {
    	return accountFactory.stockCount(id, stockname);
    }

    /**
     * counts the given bond product on all accounts
     *
     * @param bondname
     * @return
     */
    public int bondCount(String bondname) {
        return accountFactory.allBondCount(bondname);
    }

    /**
     * counts the given stock product on all accounts
     *
     * @param stockname
     * @return
     */
    public int stockCount(String stockname) {
        return accountFactory.allStockCount(stockname);
    }

}
