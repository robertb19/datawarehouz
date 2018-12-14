package com.data.datawarehouseapp.scrapper.impl;

import com.data.datawarehouseapp.RentOrBuy;
import com.data.datawarehouseapp.model.LivingSpace;
import com.data.datawarehouseapp.scrapper.IAbstractLivingSpace;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.naming.LimitExceededException;
import java.io.IOException;
import java.util.List;

public abstract class AbstractLivingSpace implements IAbstractLivingSpace {

    final static String TITLE =".offer-item-title";
    final static String PRICE = ".offer-item-price";
    final static String AREA = ".offer-item-area";
    final static String LOCATION = ".text-nowrap.hidden-xs";

    public void scrapAndPersistAll(int pages, RentOrBuy isToRent) throws IOException, LimitExceededException {
        try {
            for (int page = 1; page <= pages; page++) {
             if(isToRent.isToRent.equals("s")) {
                 createAndParseSinglePage(page);
             } else {
                 createAndParseSingleRentPage(page);
             }
            }
        } catch (Exception e){
            throw new LimitExceededException();
        }
    }

    public Elements parsePricePerMeterSquared(Document singlePage, String pricePerMeterSquared) throws IOException {
        return singlePage.select(pricePerMeterSquared);
    }

    public Elements parsePrice(Document singlePage) {
        return singlePage.select(PRICE);
    }

    public Elements parseMeterSquared(Document singlePage) {
        return singlePage.select(AREA + ":not(li:contains(działka))");
    }

    public Elements parseTitle(Document singlePage) {
        return singlePage.select(TITLE);
    }

    public Elements parseLocation(Document singlePage) {
        return singlePage.select(LOCATION);
    }

    public Document getSinglePage(int page, String url) throws IOException {
            String link = url.concat(String.valueOf(page));
            System.out.println(link + " link");
            return Jsoup.connect(link).get();
    }

    public abstract void createAndParseSingleRentPage(int page) throws IOException;

    public abstract void createAndParseSinglePage(int page) throws IOException;

    public abstract String parseLocationToCorrectFormat(Element e);

    public abstract String parseRentLocationToCorrectFormat(Element e);

}
