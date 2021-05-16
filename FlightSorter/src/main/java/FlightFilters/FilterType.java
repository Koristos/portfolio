package FlightFilters;

public enum FilterType {
    ARR_BEFORE_DEP(new ArrivalBeforeDepartureFlightFilter()),
    GROUND_TIME(new GroundTimeFlightFilter()),
    EXPIRED_FLIGHT(new ExpiredFlightFilter());


    private final FlightFilter filter;

    FilterType(FlightFilter filter) {
        this.filter = filter;
    }

    FlightFilter getFilter() {
        return this.filter;
    }

}
