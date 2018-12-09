package com.hu.training.stocktest.inventory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.hu.training.stocktest.jaxb.po.Basket;
import com.hu.training.stocktest.jaxb.po.BasketList;
import com.hu.training.stocktest.jaxb.po.Content;

/**
 *
 * @author author
 */
public class AssetCounter {

    /**
     * bond name
     */
    public static final String BOND = "BOND";

    /**
     * stock name
     */
    public static final String STOCK = "STOCK";

    /**
     * basket name
     */
    public static final String BASKET = "BASKET";

    /**
     * asset resource file name
     */
    public static String assetSource = "accounts.csv";

    /**
     * basket resource file name
     */
    public static String basketSource = "stocks.xml";

    private BasketList basketList;
    private ArrayList<Asset> assetList;

    /**
     *
     */
    public AssetCounter() {
        loadDefaultBasketList();
        loadDefaultAssetList();
    }

    /**
     *
     * @param basketList
     * @param assetList
     */
    public AssetCounter(BasketList basketList, ArrayList<Asset> assetList) {
        this.basketList = basketList;
        this.assetList = assetList;
    }

    /**
     *
     * @return basketList
     */
    public BasketList getBasketList() {
        return basketList;
    }

    /**
     *
     * @param basketList
     */
    public void setBasketList(BasketList basketList) {
        this.basketList = basketList;
    }

    /**
     *
     * @return assetList
     */
    public ArrayList<Asset> getAssetList() {
        return assetList;
    }

    /**
     *
     * @param assetList
     */
    public void setAssetList(ArrayList<Asset> assetList) {
        this.assetList = assetList;
    }

    /**
     * counts the given bond product of the specific account
     *
     * @param account
     * @param bondname
     * @return
     */
    public int bondCount(String account, String bondname) {
        int sum = 0;
        for (Asset asset : assetList) {
            if (asset.getAccount().equals(account)) {
                if (asset.getType().equals(BOND)) {
                    if (asset.getName().equals(bondname)) {
                        sum += asset.getQuantity();
                    }
                } else if (asset.getType().equals(BASKET)) {
                    sum += asset.getQuantity() * basketBondCount(asset.getName(), bondname);
                }
            }
        }
        return sum;
    }

    /**
     * counts the given stock product of the specific account
     *
     * @param account
     * @param stockname
     * @return
     */
    public int stockCount(String account, String stockname) {
        int sum = 0;
        for (Asset asset : assetList) {
            if (asset.getAccount().equals(account)) {
                if (asset.getType().equals(STOCK)) {
                    if (asset.getName().equals(stockname)) {
                        sum += asset.getQuantity();
                    }
                } else if (asset.getType().equals(BASKET)) {
                    sum += asset.getQuantity() * basketStockCount(asset.getName(), stockname);
                }
            }
        }
        return sum;
    }

    /**
     * counts the given bond product on all accounts
     *
     * @param bondname
     * @return count of bond
     */
    public int bondCount(String bondname) {
        int sum = 0;
        for (Asset asset : assetList) {
            if (asset.getType().equals(BOND)) {
                if (asset.getName().equals(bondname)) {
                    sum += asset.getQuantity();
                }
            } else if (asset.getType().equals(BASKET)) {
                sum += asset.getQuantity() * basketBondCount(asset.getName(), bondname);
            }
        }
        return sum;
    }

    /**
     * counts the given stock product on all accounts
     *
     * @param stockname
     * @return count of stock
     */
    public int stockCount(String stockname) {
        int sum = 0;
        for (Asset asset : assetList) {
            if (asset.getType().equals(STOCK)) {
                if (asset.getName().equals(stockname)) {
                    sum += asset.getQuantity();
                }
            } else if (asset.getType().equals(BASKET)) {
                sum += asset.getQuantity() * basketStockCount(asset.getName(), stockname);
            }
        }
        return sum;
    }

    /**
     * counts the given bond product in the given basket
     *
     * @param basketName
     * @param bondname
     * @return count of bond in the given basket
     */
    public int basketBondCount(String basketName, String bondname) {
        Basket basket = findBasket(basketName);
        if (basket == null) {
            return 0;
        }
        int count = 0;
        for (Content content : basket.getContentList().getContent()) {
            if (content.getType().equals(BOND)) {
                if (content.getSymbol().equals(bondname)) {
                    count += content.getQuantity().intValue();
                }
            } else if (content.getType().equals(BASKET)) {
                count += content.getQuantity().intValue() * basketBondCount(content.getSymbol(), bondname);
            }
        }
        return count;
    }

    /**
     * counts the given stock product in the given basket
     *
     * @param basketName
     * @param stockname
     * @return count of stock in the given basket
     */
    public int basketStockCount(String basketName, String stockname) {
        Basket basket = findBasket(basketName);
        if (basket == null) {
            return 0;
        }
        int count = 0;
        for (Content content : basket.getContentList().getContent()) {
            if (content.getType().equals(STOCK)) {
                if (content.getSymbol().equals(stockname)) {
                    count += content.getQuantity().intValue();
                }
            } else if (content.getType().equals(BASKET)) {
                count += content.getQuantity().intValue() * basketStockCount(content.getSymbol(), stockname);
            }
        }
        return count;
    }

    /**
     * finds the the given basket by name
     *
     * @param name
     * @return basket
     */
    public Basket findBasket(String name) {
        for (Basket basket : basketList.getBasket()) {
            if (basket.getName().equals(name)) {
                return basket;
            }
        }
        return null;
    }

    /**
     *
     * @return assetList loaded from resource "assetSource"
     */
    private void loadDefaultAssetList() {
        assetList = new ArrayList<>();
        URL url = Thread.currentThread().getContextClassLoader().getResource(assetSource);
        File file = new File(url.getPath());
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 4) {
                    Asset asset = Asset.parseFromString(line);
                    if (asset != null) {
                        assetList.add(asset);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AssetCounter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AssetCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return basketList loaded from resource "basketSource"
     */
    private void loadDefaultBasketList() {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(BasketList.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream in = AssetCounter.class.getClassLoader().getResourceAsStream(basketSource);
            basketList = (BasketList) unmarshaller.unmarshal(in);
        } catch (JAXBException ex) {
            Logger.getLogger(AssetCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
