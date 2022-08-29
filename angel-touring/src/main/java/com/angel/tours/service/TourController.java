package com.angel.tours.service;

import com.angel.tours.dto.AngelTourRequest;
import com.angel.tours.dto.AngelTourResponse;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Logger;

/*
* A service to provide various tour services
* */

@RestController
@RequestMapping(value = "/")
public class TourController {
    private static Logger log = Logger.getLogger(TourController.class.getName());

    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    /*
     * A service up and running indicator
     * */
    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

    /*
     * Api that plans tour based on input parameters
     * @param AngelTourRequest object - contains various fields required for proper tour plan
     * return AngelTourResponse
     * */
    @RequestMapping(value = "plan", method = RequestMethod.GET)
    public AngelTourResponse planTourForAngel(@RequestBody final AngelTourRequest angelTourRequest) throws IOException, JSONException {
        validateTourRequest(angelTourRequest);
        return tourService.planTourForAngel(angelTourRequest);
    }

    private void validateTourRequest(@RequestBody AngelTourRequest angelTourRequest) {
        if (angelTourRequest.getStartingCountry()== null) {
            log.severe("Starting country should not be null");
            throw new IllegalArgumentException("Starting country should not be null");
        }

        if (angelTourRequest.getTotalBudget() < 0) {
            log.severe("Total budget should be greater than 0");
            throw new IllegalArgumentException("Total budget should be greater than 0");
        }

        if (angelTourRequest.getInputCurrency()== null) {
            log.severe("Currency type in hand should not be null");
            throw new IllegalArgumentException("Currency type in hand should not be null");
        }
    }
}
