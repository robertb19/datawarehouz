package com.data.datawarehouseapp.controllers;

import com.data.datawarehouseapp.Wrapper;
import org.springframework.ui.Model;

import javax.naming.LimitExceededException;
import java.io.IOException;

public interface IHouseController {

    /*
    Adds houses to the thymeleaf model.
    */
    String houses(Model model);

    /*
    Reads the query and persists the houses to the database.
     */
    String persistHouses(Wrapper wrapper) throws IOException, LimitExceededException;

}
