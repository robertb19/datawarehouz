package com.data.datawarehouseapp.controllers;

import com.data.datawarehouseapp.Wrapper;
import org.springframework.ui.Model;

import javax.naming.LimitExceededException;
import java.io.IOException;

public interface IApartmentController {

    /*
    Adds apartments to the thymeleaf model.
     */
    String apartments(Model model);

    /*
    Reads the query and persists the apartments to the database.
     */
    String persistApartments(Wrapper wrapper) throws IOException, LimitExceededException;

}
