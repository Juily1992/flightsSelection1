package com.gridnine.testing;

import com.gridnine.testing.filters.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filters.DepartureBeforeNowFilter;
import com.gridnine.testing.filters.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FlightFilterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightFilterTest {

    private LocalDateTime now;
    private Flight flightNormal;
    private Flight flightDepartureInPast;
    private Flight flightArrivalBeforeDeparture;
    private Flight flightGroundTimeExceeds;
    private Flight flightGroundTimeOk;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        flightNormal = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(3))
        ));

        flightDepartureInPast = new Flight(List.of(
                new Segment(now.minusHours(1), now.plusHours(1))
        ));

        flightArrivalBeforeDeparture = new Flight(List.of(
                new Segment(now.plusHours(1), now.minusHours(1))
        ));

        flightGroundTimeExceeds = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(2)),
                new Segment(now.plusHours(5), now.plusHours(6)) // 3 часа на земле
        ));

        flightGroundTimeOk = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(2)),
                new Segment(now.plusHours(3), now.plusHours(4)) // 1 час на земле
        ));
    }

    @Test
    void testDepartureBeforeNowFilter() {
        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();
        assertTrue(filter.filter(flightDepartureInPast));
        assertFalse(filter.filter(flightNormal));
    }

    @Test
    void testArrivalBeforeDepartureFilter() {
        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();
        assertTrue(filter.filter(flightArrivalBeforeDeparture));
        assertFalse(filter.filter(flightNormal));
    }

    @Test
    void testGroundTimeExceedsTwoHoursFilter() {
        GroundTimeExceedsTwoHoursFilter filter = new GroundTimeExceedsTwoHoursFilter();
        assertTrue(filter.filter(flightGroundTimeExceeds));
        assertFalse(filter.filter(flightGroundTimeOk));
        assertFalse(filter.filter(flightNormal)); // один сегмент — ground time = 0
    }

    @Test
    void testFlightFilterService() {
        List<Flight> flights = List.of(flightDepartureInPast, flightNormal);
        List<Flight> result = FlightFilterService.filterFlights(flights, new DepartureBeforeNowFilter());
        assertEquals(1, result.size());
        assertEquals(flightNormal, result.get(0));
    }
}