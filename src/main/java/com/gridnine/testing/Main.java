package com.gridnine.testing;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.filters.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filters.DepartureBeforeNowFilter;
import com.gridnine.testing.filters.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		List<Flight> allFlights = FlightBuilder.createFlights();

		System.out.println("Все перелёты: ");
		printFlights(allFlights);
		System.out.println();

		List<Flight> filtered1 = FlightFilterService.filterFlights(allFlights, new DepartureBeforeNowFilter());
		System.out.println(" Исключены перелёты с вылетом до текущего момента ");
		printFlights(filtered1);
		System.out.println();

		List<Flight> filtered2 = FlightFilterService.filterFlights(allFlights, new ArrivalBeforeDepartureFilter());
		System.out.println(" Исключены перелёты с прилётом раньше вылета ");
		printFlights(filtered2);
		System.out.println();

		List<Flight> filtered3 = FlightFilterService.filterFlights(allFlights, new GroundTimeExceedsTwoHoursFilter());
		System.out.println(" Исключены перелёты с временем на земле более 2 часов ");
		printFlights(filtered3);
		System.out.println();
	}

	private static void printFlights(List<Flight> flights) {
		if (flights.isEmpty()) {
			System.out.println("Нет перелётов");
		} else {
			flights.forEach(System.out::println);
		}
	}
}
