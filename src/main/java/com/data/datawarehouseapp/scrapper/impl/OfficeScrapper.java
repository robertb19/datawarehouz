package com.data.datawarehouseapp.scrapper.impl;

import com.data.datawarehouseapp.model.Office;
import com.data.datawarehouseapp.repository.OfficeRepository;
import lombok.Data;
import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.data.datawarehouseapp.converters.StringConverter.parseToDoubleFormat;

@Service
@Data
@Getter
public class OfficeScrapper extends AbstractLivingSpace {

    final static String OFFICE_URL = "https://www.otodom.pl/sprzedaz/lokal/?page=";
    final static String OFFICE_RENT_URL = "https://www.otodom.pl/wynajem/lokal/?page=";
    final static String PRICE_PER_M2 = ".offer-item-price-per-m";

    @Autowired
    OfficeRepository officeRepository;

    @Override
    public void createAndParseSinglePage(int page) throws IOException {

        Document singlePage = getSinglePage(page, OFFICE_URL);

        Elements pricesPerMeterSquared = parsePricePerMeterSquared(singlePage, PRICE_PER_M2);
        Elements prices = parsePrice(singlePage);
        Elements area = parseMeterSquared(singlePage);
        Elements titles = parseTitle(singlePage);
        Elements locations = parseLocation(singlePage);

        for (int item = 0; item < titles.size(); item++) {
            double singleItemPrice = parseToDoubleFormat(prices.get(item));
            double singleItemArea = parseToDoubleFormat(area.get(item));
            double singleItemPricePerMeterSquared = parseToDoubleFormat(pricesPerMeterSquared.get(item));
            String title = titles.get(item).text();
            String location = parseLocationToCorrectFormat(locations.get(item));

            Office office = Office.builder()
                    .isToRent(false)
                    .title(title)
                    .location(location)
                    .area(singleItemArea)
                    .price(singleItemPrice)
                    .pricePerMeterSquared(singleItemPricePerMeterSquared)
                    .build();

            try {
                officeRepository.save(office);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void createAndParseSingleRentPage(int page) throws IOException {

        Document singlePage = getSinglePage(page, OFFICE_RENT_URL);

        Elements prices = parsePrice(singlePage);
        Elements area = parseMeterSquared(singlePage);
        Elements titles = parseTitle(singlePage);
        Elements locations = parseLocation(singlePage);

        for (int item = 0; item < titles.size(); item++) {
            double singleItemPrice = parseToDoubleFormat(prices.get(item));
            double singleItemArea = parseToDoubleFormat(area.get(item));
            String title = titles.get(item).text();
            String location = parseRentLocationToCorrectFormat(locations.get(item));

            Office office = Office.builder()
                    .isToRent(true)
                    .title(title)
                    .location(location)
                    .area(singleItemArea)
                    .price(singleItemPrice)
                    .build();

            try {
                officeRepository.save(office);
            } catch (Exception e) {

            }
        }
    }

    public String parseLocationToCorrectFormat(Element e) {
        return e.text().substring(28);
    }
    public String parseRentLocationToCorrectFormat(Element e) {
        return e.text().substring(27);
    }
}
