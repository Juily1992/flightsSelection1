package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.filters.FlightFilter;

import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterService {
    public static List<Flight> filterFlights(List<Flight> flights, FlightFilter filter) {
        return flights.stream()
                .filter(flight -> !filter.filter(flight)) // оставляем только НЕ отфильтрованные (т.е. хорошие)
                .collect(Collectors.toList());
    }
}