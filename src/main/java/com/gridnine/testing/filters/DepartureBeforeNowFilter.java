package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.List;

public class DepartureBeforeNowFilter implements FlightFilter {
    @Override
    public boolean filter(Flight flight) {
        List<Segment> segments = flight.getSegments();
        if (segments.isEmpty()) return false;
        LocalDateTime firstDeparture = segments.get(0).getDepartureDate();
        return firstDeparture.isBefore(LocalDateTime.now());
    }
}