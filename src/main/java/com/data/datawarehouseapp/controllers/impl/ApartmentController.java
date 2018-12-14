package com.data.datawarehouseapp.controllers.impl;

import com.data.datawarehouseapp.Wrapper;
import com.data.datawarehouseapp.controllers.IApartmentController;
import com.data.datawarehouseapp.scrapper.impl.ApartmentScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import java.io.IOException;

@Controller
public class ApartmentController implements IApartmentController {

    @Autowired
    ApartmentScrapper apartmentScrapper;

    @GetMapping("/apartments/persist")
    @ResponseBody
    public String persistApartments(@ModelAttribute Wrapper wrapper) throws IOException, LimitExceededException {
        int page = Integer.parseInt(wrapper.form.pages);
        if(page > 500) return "You have selected too many pages";
        apartmentScrapper.scrapAndPersistAll(page, wrapper.rentOrBuy);
        return "Parsowanie do bazy danych powiodło się.";
    }

    @GetMapping("/apartments")
    public String apartments(Model model){
        model.addAttribute("wrapper", new Wrapper());
        return "apartments";
    }

}
