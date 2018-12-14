package com.data.datawarehouseapp.controllers.impl;

import com.data.datawarehouseapp.Wrapper;
import com.data.datawarehouseapp.controllers.IOfficeController;
import com.data.datawarehouseapp.scrapper.impl.OfficeScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import java.io.IOException;

@Controller
public class OfficeController implements IOfficeController {

    @Autowired
    OfficeScrapper officeScrapper;

    @GetMapping("/offices/persist")
    @ResponseBody
    public String persistOffices(@ModelAttribute Wrapper wrapper) throws LimitExceededException, IOException {
        int page = Integer.parseInt(wrapper.form.pages);
        if (page > 500) return "You have selected too many pages";
        officeScrapper.scrapAndPersistAll(page, wrapper.rentOrBuy);
        return "Parsowanie do bazy danych powiodło się.";
    }

    @GetMapping("/offices")
    public String offices(Model model) {
        model.addAttribute("wrapper", new Wrapper());
        return "offices";
    }

}
