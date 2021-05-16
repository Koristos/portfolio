package FlightFilters;

import Flights.Flight;

import java.util.List;

interface FlightFilter {
    List<Flight> applyFilter(List<Flight> flights);
}
