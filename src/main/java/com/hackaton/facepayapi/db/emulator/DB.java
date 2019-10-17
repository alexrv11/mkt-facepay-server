package com.hackaton.facepayapi.db.emulator;

import java.util.HashMap;

public class DB {
    private static DB ourInstance = new DB();

    public static DB getInstance() {
        return ourInstance;
    }

    private DB() {
    }

    static HashMap<String, String> payers = new HashMap<String, String>() {{
        put("payer_test", "123");
    }};

    static HashMap sellers = new HashMap<String, String>() {{
        put("seller_test", "123");
    }};

    public static HashMap<String, String> getPayers() {
        return payers;
    }

    public HashMap<String, String> getSellers() {
        return sellers;
    }

    public Boolean isValidPayer(String userName, String password) {
        return password.equals(payers.get(userName));
    }

    public Boolean isValidSeller(String userName, String password) {
        return password.equals(sellers.get(userName));
    }

    public Boolean isValidUser(String userName, String password) {
        return isValidPayer(userName, password) || isValidSeller(userName, password);
    }
}
