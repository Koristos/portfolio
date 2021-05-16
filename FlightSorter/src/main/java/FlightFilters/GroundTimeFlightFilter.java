package FlightFilters;

import Flights.Flight;
import Flights.Segment;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

class GroundTimeFlightFilter implements FlightFilter {
    @Override
    public List<Flight> applyFilter(List<Flight> flights) {
        ArrayList<Flight> sortedFlights = new ArrayList<>();
        for (Flight f : flights) {
            List<Segment> segments = f.getSegments();
            if (segments.size() > 1) {
                long groundTime = 0L;
                for (int i = 0; i < segments.size() - 1; i++) {
                    groundTime += segments.get(i).getArrivalDate()
                            .until(segments.get(i + 1).getDepartureDate(),
                                    ChronoUnit.HOURS);
                }
                if (groundTime <= 2) {
                    sortedFlights.add(f);
                }
            } else {
                sortedFlights.add(f);
            }
        }
        return sortedFlights;
    }
}
