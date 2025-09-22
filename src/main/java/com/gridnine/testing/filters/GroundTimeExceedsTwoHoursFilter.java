package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class GroundTimeExceedsTwoHoursFilter implements FlightFilter {
    @Override
    public boolean filter(Flight flight) {
        List<Segment> segments = flight.getSegments();
        if (segments.size() < 2) {
            return false;
        }

        long totalGroundMinutes = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrival = segments.get(i).getArrivalDate();
            LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();

            if (!nextDeparture.isAfter(arrival)) {
                continue;
            }

            Duration groundTime = Duration.between(arrival, nextDeparture);
            totalGroundMinutes += groundTime.toMinutes();
        }

        return totalGroundMinutes > 120;
    }
}