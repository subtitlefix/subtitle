package com.hu.training.stocktest.inventory;

/**
 *
 * @author author
 */
public class Asset {

    private String account;
    private String type;
    private String name;
    private int quantity;

    /**
     *
     */
    public Asset() {
    }

    /**
     *
     * @param account
     * @param type
     * @param name
     * @param quantity
     */
    public Asset(String account, String type, String name, int quantity) {
        this.account = account;
        this.type = type;
        this.name = name;
        this.quantity = quantity;
    }

    /**
     *
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     *
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @param str
     * @return new Asset with the parsed arguments
     */
    public static Asset parseFromString(String str) {
        String[] arr = str.split(",");
        if (arr.length == 4) {
            int quantity = Integer.parseInt(arr[3]);
            return new Asset(arr[0], arr[1], arr[2], quantity);
        }
        return null;
    }

}
