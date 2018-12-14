package com.data.datawarehouseapp.converters;

import org.jsoup.nodes.Element;

public class StringConverter {

    public static int parseToIntegerFormat(Element e) {
        String singlePricePerMeterSquared = e.text().replaceAll("[^0-9.]", "");
        if(singlePricePerMeterSquared == null || singlePricePerMeterSquared.equals("")) return 0;
        return Integer.parseInt(singlePricePerMeterSquared);
    }

    public static double parseToDoubleFormat(Element e) {
        String singlePricePerMeterSquaredWithAComma = e.text().replaceAll("[^0-9.,]", "");
        String singlePricePerMeterSquared = singlePricePerMeterSquaredWithAComma.replaceAll(",", ".");
        if(singlePricePerMeterSquared == null || singlePricePerMeterSquared.equals("")) return Double.valueOf(0);
        return Double.valueOf(singlePricePerMeterSquared);
    }
}
