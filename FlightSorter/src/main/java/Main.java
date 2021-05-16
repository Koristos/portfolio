import FlightFilters.*;
import Flights.Flight;
import Flights.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Убран вылет до текущего момента времени:");
        printFlights(FlightSorterApp.sortFlights(flights, FilterType.EXPIRED_FLIGHT));
        System.out.println("Убраны рейсы, имеющие сегменты с датой прилёта раньше даты вылета:");
        printFlights(FlightSorterApp.sortFlights(flights, FilterType.ARR_BEFORE_DEP));
        System.out.println("Убраны рейсы, где общее время, проведённое на земле превышает два часа:");
        printFlights(FlightSorterApp.sortFlights(flights, FilterType.GROUND_TIME));
        System.out.println("Все фильтры сразу:");
        printFlights(FlightSorterApp.sortFlights(flights, FilterType.EXPIRED_FLIGHT,
                FilterType.GROUND_TIME,
                FilterType.ARR_BEFORE_DEP));
    }

    public static void printFlights(List<Flight> flightList) {
        flightList.forEach(flight -> System.out.println(flight.toString()));
    }
}


