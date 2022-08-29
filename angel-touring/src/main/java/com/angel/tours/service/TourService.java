package com.angel.tours.service;

import com.angel.tours.client.CurrencyClient;
import com.angel.tours.dto.AngelTourResponse;
import com.angel.tours.dto.BudgetPerCountry;
import com.angel.tours.dto.CountryInfoResponse;
import com.angel.tours.client.CountryInfoClient;
import com.angel.tours.dto.AngelTourRequest;
import com.angel.tours.dto.GeoName;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TourService {

    private static Logger log = Logger.getLogger(TourController.class.getName());

    @Resource(name="mapCodeBean")
    private Map<String, GeoName> countryCodeAndInfoMap;
    @Resource(name="mapCountryBean")
    private Map<String, GeoName> countryNameAndInfoMap;
    private CountryInfoClient countryInfoClient;
    private CurrencyClient currencyClient;

    @Autowired
    public TourService(CountryInfoClient countryInfoClient, CurrencyClient currencyClient) {
        this.countryInfoClient = countryInfoClient;
        this.currencyClient = currencyClient;
    }

    public AngelTourResponse planTourForAngel(final AngelTourRequest angelTourRequest) throws IOException, JSONException {

        double totalBudgetForWholeTrip = angelTourRequest.getTotalBudget();
        String startingCountryCode = getStartCountryCode(angelTourRequest);

        if (startingCountryCode == null) {
            log.severe("Unsupported country for touring right now: " + angelTourRequest.getStartingCountry());
            return new AngelTourResponse(0, totalBudgetForWholeTrip, null,
                    "Unsupported country for touring right now");
        }

        return caculatePossibleTrips(angelTourRequest, totalBudgetForWholeTrip, startingCountryCode);
    }

    private String getStartCountryCode(final AngelTourRequest angelTourRequest) {
        GeoName geoName;
        if (angelTourRequest.getStartingCountry().length() == 2) {
            geoName = countryCodeAndInfoMap.get(angelTourRequest.getStartingCountry().toLowerCase());
            if (geoName != null) {
                return geoName.getCountryCode();
            }
        } else {
            geoName = countryNameAndInfoMap.get(angelTourRequest.getStartingCountry().toLowerCase());
            if (geoName != null) {
                return geoName.getCountryCode();
            }
        }
        return null;
    }

    private String getCountryCurrencyCode(final String countryCode) {
        return countryCodeAndInfoMap.get(countryCode.toLowerCase()).getCurrencyCode();
    }

    private AngelTourResponse caculatePossibleTrips(AngelTourRequest angelTourRequest, double totalBudget, String startingCountryCode) throws IOException, JSONException {

        double balanceAfterTrip = 0;
        int roundTripsPossible = 0;
        CountryInfoResponse neighbourInfo = getNeighbourInfo(angelTourRequest, startingCountryCode);

        if (neighbourInfo == null || neighbourInfo.getTotalResultsCount() == 0) {
            log.severe("No neighbours found for " + angelTourRequest.getStartingCountry() + ":");
            return new AngelTourResponse(0, balanceAfterTrip, null,
                    "No neighbours found for starting country, kindly chose another");
        }

        double budgetNeededToTourAllNeighboursOnce = neighbourInfo.getTotalResultsCount() * angelTourRequest.getBudgetPerCountry();

        if (budgetNeededToTourAllNeighboursOnce > totalBudget) {
            balanceAfterTrip = totalBudget;
            return new AngelTourResponse(0, balanceAfterTrip, null,
                    "Trip is very costly, kindly reduce per country budget or increase total budget");
        } else {
            roundTripsPossible = (int) (totalBudget / budgetNeededToTourAllNeighboursOnce);
            balanceAfterTrip = totalBudget - (budgetNeededToTourAllNeighboursOnce * roundTripsPossible);
        }

        List<BudgetPerCountry> budgetPerCountry = calcuateBudgetPerCountry(roundTripsPossible, neighbourInfo, angelTourRequest);
        return new AngelTourResponse(roundTripsPossible, balanceAfterTrip, budgetPerCountry, "Have a great trip !!");

    }

    private List<BudgetPerCountry> calcuateBudgetPerCountry(int roundTripsPossible, CountryInfoResponse neighbourInfo,
                                                            AngelTourRequest angelTourRequest) {
        List<GeoName> neighbours = neighbourInfo.getGeonames();
        String sourceCurrencyCode = angelTourRequest.getInputCurrency();
        String destinationCurrencyCode;
        double amountPerTrip;
        List<BudgetPerCountry> budgetList = new ArrayList<>();
        for (GeoName neighbour : neighbours) {
            neighbour.setCurrencyCode(getCountryCurrencyCode(neighbour.getCountryCode()));
            try {
                if (neighbour.getCurrencyCode() == null) {
                    destinationCurrencyCode = sourceCurrencyCode;
                } else {
                    destinationCurrencyCode = neighbour.getCurrencyCode();
                }
                if (sourceCurrencyCode.equals(destinationCurrencyCode)) {
                    amountPerTrip = angelTourRequest.getBudgetPerCountry();
                } else {
                    amountPerTrip = currencyClient.convertCurrency(sourceCurrencyCode, destinationCurrencyCode, angelTourRequest.getBudgetPerCountry());
                }
                budgetList.add(new BudgetPerCountry(amountPerTrip * roundTripsPossible, destinationCurrencyCode, neighbour.getCountryName()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return budgetList;
    }

    private CountryInfoResponse getNeighbourInfo(final AngelTourRequest angelTourRequest, String startingCountryCode) throws IOException, JSONException {
        return countryInfoClient.getCountryNeighbourInfo(startingCountryCode);
    }
}
