package com.data.datawarehouseapp.scrapper.impl;

import com.data.datawarehouseapp.model.House;
import com.data.datawarehouseapp.repository.HouseRepository;
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
public class HouseScrapper extends AbstractLivingSpace {

    final static String HOUSE_URL = "https://www.otodom.pl/sprzedaz/dom/?page=";
    final static String HOUSE_RENT_URL = "https://www.otodom.pl/wynajem/dom/?page=";
    final static String NUMBER_OF_ROOMS = ".offer-item-rooms";

    @Autowired
    HouseRepository houseRepository;

    @Override
    public void createAndParseSinglePage(int page) throws IOException {

        Document singlePage = getSinglePage(page, HOUSE_URL);

        Elements prices = parsePrice(singlePage);
        Elements area = parseMeterSquared(singlePage);
        Elements numberOfRooms = parseNumberOfRooms(singlePage);
        Elements areaOfField = parseAreaOfField(singlePage);
        Elements titles = parseTitle(singlePage);
        Elements locations = parseLocation(singlePage);

        for (int item = 0; item < titles.size(); item++) {
            double singleItemPrice = parseToDoubleFormat(prices.get(item));
            double singleItemArea = parseToDoubleFormat(area.get(item));
            double singleItemAreaOfField = parseToDoubleFormat(areaOfField.get(item));
            int singleItemNumberOfRooms = parseToIntegerFormat(numberOfRooms.get(item));
            String title = titles.get(item).text();
            String location = parseLocationToCorrectFormat(locations.get(item));

            House house = House.builder()
                    .isToRent(false)
                    .title(title)
                    .location(location)
                    .area(singleItemArea)
                    .numberOfRooms(singleItemNumberOfRooms)
                    .price(singleItemPrice)
                    .areaOfField(singleItemAreaOfField)
                    .build();

            try {
                houseRepository.save(house);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void createAndParseSingleRentPage(int page) throws IOException {

        Document singlePage = getSinglePage(page, HOUSE_RENT_URL);

        Elements prices = parsePrice(singlePage);
        Elements area = parseMeterSquared(singlePage);
        Elements numberOfRooms = parseNumberOfRooms(singlePage);
        Elements areaOfField = parseAreaOfField(singlePage);
        Elements titles = parseTitle(singlePage);
        Elements locations = parseLocation(singlePage);

        for (int item = 0; item < titles.size(); item++) {
            if(item < areaOfField.size()) {
                double singleItemPrice = parseToDoubleFormat(prices.get(item));
                double singleItemArea = parseToDoubleFormat(area.get(item));
                double singleItemAreaOfField = parseToDoubleFormat(areaOfField.get(item));
                int singleItemNumberOfRooms = parseToIntegerFormat(numberOfRooms.get(item));
                String title = titles.get(item).text();
                String location = parseRentLocationToCorrectFormat(locations.get(item));

                House house = House.builder()
                        .isToRent(true)
                        .title(title)
                        .location(location)
                        .area(singleItemArea)
                        .numberOfRooms(singleItemNumberOfRooms)
                        .price(singleItemPrice)
                        .areaOfField(singleItemAreaOfField)
                        .build();

                try {
                    houseRepository.save(house);
                } catch (Exception e) {
                }
            }
        }
    }

    private static Elements parseNumberOfRooms(Document singlePage) {
        return singlePage.select(NUMBER_OF_ROOMS + ":not(li:contains(działka))");
    }

    private static Elements parseAreaOfField(Document singlePage) {
        return singlePage.select(AREA).select("li:contains(działka)");
    }

    public String parseLocationToCorrectFormat(Element e) {
        return e.text().substring(17);
    }
    public String parseRentLocationToCorrectFormat(Element e) {
        return e.text().substring(16);
    }
}
