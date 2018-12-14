package com.data.datawarehouseapp.controllers.impl;

import com.data.datawarehouseapp.Wrapper;
import com.data.datawarehouseapp.controllers.IHouseController;
import com.data.datawarehouseapp.scrapper.impl.HouseScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import java.io.IOException;

@Controller
public class HouseController implements IHouseController {

    @Autowired
    HouseScrapper houseScrapper;

    @GetMapping("/houses/persist")
    @ResponseBody
    public String persistHouses(@ModelAttribute Wrapper wrapper) throws LimitExceededException, IOException {
        int page = Integer.parseInt(wrapper.form.pages);
        if (page > 500) return "You have selected too many pages";
        houseScrapper.scrapAndPersistAll(page, wrapper.rentOrBuy);
        return "Parsowanie do bazy danych powiodło się.";
    }

    @GetMapping("/houses")
    public String houses(Model model) {
        model.addAttribute("wrapper", new Wrapper());
        return "houses";
    }


}
