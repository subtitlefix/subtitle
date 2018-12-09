package com.hu.training.stocktest.parser;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.hu.training.stocktest.inventory.AssetCounter;
import com.hu.training.stocktest.jaxb.po.BasketList;

public class BasketXMLParser {
	
	public BasketList loadDefaultBasketList(String basketSource) {
        try {
        	JAXBContext jc = JAXBContext.newInstance(BasketList.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream in = BasketXMLParser.class.getClassLoader().getResourceAsStream(basketSource);
            return (BasketList) unmarshaller.unmarshal(in);
        } catch (JAXBException ex) {
            Logger.getLogger(AssetCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
		return null;
    }
}
