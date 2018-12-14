package com.data.datawarehouseapp.controllers;

import com.data.datawarehouseapp.Wrapper;
import org.springframework.ui.Model;

import javax.naming.LimitExceededException;
import java.io.IOException;

public interface IOfficeController {

    /*
   Adds offices to the thymeleaf model.
   */
    String offices(Model model);

    /*
    Reads the query and persists the offices to the database.
     */
    String persistOffices(Wrapper wrapper) throws IOException, LimitExceededException;


}
