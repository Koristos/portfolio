package FlightFilters;

import Flights.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class ExpiredFlightFilter implements FlightFilter {
    @Override
    public List<Flight> applyFilter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getDepartureDate()
                                .isBefore(LocalDateTime.now())))
                .collect(Collectors.toList());
    }
}
