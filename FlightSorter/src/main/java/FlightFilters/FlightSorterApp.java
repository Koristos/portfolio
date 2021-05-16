package FlightFilters;

import Flights.Flight;

import java.util.List;

public class FlightSorterApp {

    public static List<Flight> sortFlights(List<Flight> flights, FilterType filter, FilterType... filters) {
        if (flights==null || flights.size()==0){
            throw new RuntimeException("Flight list can not be empty!");
        }
        flights=filter.getFilter().applyFilter(flights);
        if (filters.length != 0) {
            for (FilterType f : filters) {
                flights = f.getFilter().applyFilter(flights);
            }
        }
        return flights;
    }
}
