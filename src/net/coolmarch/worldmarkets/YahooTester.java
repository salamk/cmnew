/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.worldmarkets;

import java.io.IOException;
import java.math.BigDecimal;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 *
 * @author sania
 */
public class YahooTester {

    public static void main(String[] args) {
        try{
        Stock stock = YahooFinance.get("JK");

        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal peg = stock.getStats().getPeg();
        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

        System.out.println("Price: "+price.doubleValue());
        System.out.println("Change: "+change);
        stock.print();
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

}
