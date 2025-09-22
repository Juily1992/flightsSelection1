package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

public class ArrivalBeforeDepartureFilter implements FlightFilter {
    @Override
    public boolean filter(Flight flight) {
        return flight.getSegments().stream()
                .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }
}