package com.data.datawarehouseapp.scrapper.impl;

import com.data.datawarehouseapp.model.Apartment;
import com.data.datawarehouseapp.repository.ApartmentRepository;
import lombok.Data;
import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.data.datawarehouseapp.converters.StringConverter.parseToDoubleFormat;
import static com.data.datawarehouseapp.converters.StringConverter.parseToIntegerFormat;

@Service
@Data
@Getter
public class ApartmentScrapper extends AbstractLivingSpace {

    final static String APARTMENT_URL = "https://www.otodom.pl/sprzedaz/mieszkanie/?page=";
    final static String APARTMENT_RENT_URL = "https://www.otodom.pl/wynajem/mieszkanie/?page=";
    final static String PRICE_PER_M2 = ".offer-item-price-per-m";
    final static String NUMBER_OF_ROOMS = ".offer-item-rooms";


    @Autowired
    ApartmentRepository apartmentRepository;

    public void createAndParseSinglePage(int page) throws IOException {

        Document singlePage = getSinglePage(page, APARTMENT_URL);

        Elements pricesPerMeterSquared = parsePricePerMeterSquared(singlePage, PRICE_PER_M2);
        Elements prices = parsePrice(singlePage);
        Elements area = parseMeterSquared(singlePage);
        Elements numberOfRooms = parseNumberOfRooms(singlePage);
        Elements titles = parseTitle(singlePage);
        Elements locations = parseLocation(singlePage);

        for (int item = 0; item < titles.size(); item++) {
            double singleItemPricePerMeterSquared = parseToDoubleFormat(pricesPerMeterSquared.get(item));
            double singleItemPrice = parseToDoubleFormat(prices.get(item));
            double singleItemArea = parseToDoubleFormat(area.get(item));
            int singleItemNumberOfRooms = parseToIntegerFormat(numberOfRooms.get(item));
            String title = titles.get(item).text();
            String location = parseLocationToCorrectFormat(locations.get(item));

            Apartment apartment = Apartment.builder()
                    .isToRent(false)
                    .title(title)
                    .location(location)
                    .area(singleItemArea)
                    .numberOfRooms(singleItemNumberOfRooms)
                    .price(singleItemPrice)
                    .pricePerMeterSquared(singleItemPricePerMeterSquared)
                    .build();

            try {
                apartmentRepository.save(apartment);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void createAndParseSingleRentPage(int page) throws IOException {

        Document singlePage = getSinglePage(page, APARTMENT_RENT_URL);
        Elements prices = parsePrice(singlePage);
        Elements area = parseMeterSquared(singlePage);
        Elements numberOfRooms = parseNumberOfRooms(singlePage);
        Elements titles = parseTitle(singlePage);
        Elements locations = parseLocation(singlePage);

        for (int item = 0; item < titles.size(); item++) {
            double singleItemPrice = parseToDoubleFormat(prices.get(item));
            double singleItemArea = parseToDoubleFormat(area.get(item));
            int singleItemNumberOfRooms = parseToIntegerFormat(numberOfRooms.get(item));
            String title = titles.get(item).text();
            String location = parseRentLocationToCorrectFormat(locations.get(item));

            Apartment apartment = Apartment.builder()
                    .isToRent(true)
                    .title(title)
                    .location(location)
                    .area(singleItemArea)
                    .numberOfRooms(singleItemNumberOfRooms)
                    .price(singleItemPrice)
                    .build();

            try {
                apartmentRepository.save(apartment);
            } catch (Exception e) {

            }
        }
    }

    static Elements parseNumberOfRooms(Document singlePage) {
        return singlePage.select(NUMBER_OF_ROOMS);
    }

    public String parseRentLocationToCorrectFormat(Element e) {
        return e.text().substring(23);
    }
    public String parseLocationToCorrectFormat(Element e) {
        return e.text().substring(24);
    }
}
