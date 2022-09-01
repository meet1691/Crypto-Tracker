package com.meet.cryptotracker;


// in RV we need 3 item to display so we define that items in this model class
// getter and setter are use full to get value in adapter Class
public class CurrencyModel {
    String currencyName ;
    String symbol;
    double price  ;

    public CurrencyModel(String currencyName, String symbol, double price) {
        this.currencyName = currencyName;
        this.symbol = symbol;
        this.price = price;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
