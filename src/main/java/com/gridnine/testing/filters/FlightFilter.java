package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

@FunctionalInterface
public interface FlightFilter {
    boolean filter(Flight flight);
}