package FlightFilters;

import Flights.Flight;

import java.util.List;
import java.util.stream.Collectors;

class ArrivalBeforeDepartureFlightFilter implements FlightFilter {
    @Override
    public List<Flight> applyFilter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getArrivalDate()
                                .isBefore(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }
}
