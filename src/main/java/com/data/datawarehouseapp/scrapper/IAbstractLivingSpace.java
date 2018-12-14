package com.data.datawarehouseapp.scrapper;

import com.data.datawarehouseapp.RentOrBuy;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.naming.LimitExceededException;
import java.io.IOException;

public interface IAbstractLivingSpace {

    /*
Use all of the data from the functions under, in order to:
1. Read a single webpage.
2. Parse it onto specific types (long,integer etc.)
3. Persist it to the database, and check if it is to Rent or not.

Do the same in a loop for another webpage in accordance to the pages variable where the loop ends.
 */
    void scrapAndPersistAll(int pages, RentOrBuy isToRent) throws IOException, LimitExceededException;

    /*
    Parse all of the elements contained in <li> html parts with class that reads price per meter squared
    within one page.
     */
    Elements parsePricePerMeterSquared(Document singlePage, String pricePerMeterSquared) throws IOException;

    /*
    Parse all of the <li> html parts on a single page with class describing price.
     */
    Elements parsePrice(Document singlePage);

    /*
    Parse all of the <li> html parts on a single page with class describing meters squared.
     */
    Elements parseMeterSquared(Document singlePage);

    /*
    Parse all of the <li> html parts on a single page with class containing the title.
     */
    Elements parseTitle(Document singlePage);

    /*
    Parse all of the <li> html parts on a single page with class containing the location
     */
    Elements parseLocation(Document singlePage);

    /*
    Take a single page from the website url with all of the elements necessary for later parsing.
     */
    Document getSinglePage(int page, String url) throws IOException;

    /*
    Parse all of the elements on a SINGLE webpage to the database with rent flag being true.
     */
    void createAndParseSingleRentPage(int page) throws IOException;

    /*
    Parse all of the elements on a SINGLE webpage to the database with rent flag being false;
     */
    void createAndParseSinglePage(int page) throws IOException;

       /*
    Parses the location into string format from a html element.
     */
    String parseLocationToCorrectFormat(Element e);

    /*
   Parses the entity to rent location into string format from a html element.
    */
    String parseRentLocationToCorrectFormat(Element e);

}
