import FlightFilters.FilterType;
import FlightFilters.FlightSorterApp;
import Flights.Flight;
import Flights.FlightBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlightSorterAppTest {

    private static List<Flight> flights;
    private static List<List<Flight>> flightParams;


    @BeforeAll
    void init(){
        flights=FlightBuilder.createFlights();
        flightParams = new ArrayList<>();
        for (int i = 0; i < flights.size(); i++) {
            flightParams.add(List.of(flights.get(i)));
        }
    }

    @Test
    void nullFlightListMustThrowRuntimeException (){
        assertThrows(RuntimeException.class, ()-> FlightSorterApp.sortFlights(null, FilterType.EXPIRED_FLIGHT));
    }
    @Test
    void emptyFlightListMustThrowRuntimeException (){
        assertThrows(RuntimeException.class, ()-> FlightSorterApp.sortFlights(new ArrayList<Flight>(), FilterType.EXPIRED_FLIGHT));
    }

    @Test
    void nullFilterMustThrowRuntimeException (){
        assertThrows(NullPointerException.class, ()-> FlightSorterApp.sortFlights(flights, null));
    }

    static Stream<Arguments> argumentsForExpiredFilter (){
        return Stream.of(
                Arguments.of(flightParams.get(0), 1),
                Arguments.of(flightParams.get(1), 1),
                Arguments.of(flightParams.get(2), 0),
                Arguments.of(flightParams.get(3), 1),
                Arguments.of(flightParams.get(4), 1),
                Arguments.of(flightParams.get(5), 1)
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForExpiredFilter")
    void expiredFilterTest (List<Flight> input, int expected){
        assertEquals(expected, FlightSorterApp.sortFlights(input,FilterType.EXPIRED_FLIGHT).size());
    }

    static Stream<Arguments> argumentsForArrBeforeDepFilter (){
        return Stream.of(
                Arguments.of(flightParams.get(0), 1),
                Arguments.of(flightParams.get(1), 1),
                Arguments.of(flightParams.get(2), 1),
                Arguments.of(flightParams.get(3), 0),
                Arguments.of(flightParams.get(4), 1),
                Arguments.of(flightParams.get(5), 1)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsForArrBeforeDepFilter")
    void arrBeforeDepFilterTest (List<Flight> input, int expected){
        assertEquals(expected, FlightSorterApp.sortFlights(input,FilterType.ARR_BEFORE_DEP).size());
    }

    static Stream<Arguments> argumentsForGroundTimeFilter (){
        return Stream.of(
                Arguments.of(flightParams.get(0), 1),
                Arguments.of(flightParams.get(1), 1),
                Arguments.of(flightParams.get(2), 1),
                Arguments.of(flightParams.get(3), 1),
                Arguments.of(flightParams.get(4), 0),
                Arguments.of(flightParams.get(5), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsForGroundTimeFilter")
    void groundTimeFilterTest (List<Flight> input, int expected){
        assertEquals(expected, FlightSorterApp.sortFlights(input,FilterType.GROUND_TIME).size());
    }


}
