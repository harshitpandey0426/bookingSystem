package com.lld.system.design.bookingmyshow;

import com.lld.system.design.bookingmyshow.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class BookmyshowApplication {


	public static void main(String[] args) {
//		SpringApplication.run(BookmyshowApplication.class,args);
				Setup setup = new Setup();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter user 1: ");
//		String user1 = scanner.nextLine();
		String user1 = "User1";
		String user2 = "User2";

		setup.setupControllers(10, 0);

		final String movie = setup.movieController.createMovie("Movie 1");
		final String screen = setup.setupScreen();
		final List<String> screen1SeatIds = setup.createSeats(setup.theatreController, screen, 2, 10);

		final String show = setup.showController.createShow(movie, screen, new Date(), 2 * 60 * 60);

		List<String> u1AvailableSeats = setup.showController.getAvailableSeats(show);

		// Validate that seats u1 received has all screen seats
//	validateSeatsList(u1AvailableSeats, screen1SeatIds, Collections.emptyList());
		System.out.println("here");
		List<String> u1SelectedSeats = new ArrayList<>();
		u1SelectedSeats.add(screen1SeatIds.get(0));
		u1SelectedSeats.add(screen1SeatIds.get(2));
		u1SelectedSeats.add(screen1SeatIds.get(5));
		u1SelectedSeats.add(screen1SeatIds.get(16));

		final String bookingId1 = setup.bookingController.createBooking(user1, show, u1SelectedSeats);
		setup.paymentsController.paymentSuccess(bookingId1, user1);

		final List<String> u2AvailableSeats = setup.showController.getAvailableSeats(show);

		List<String> u2SelectedSeats = new ArrayList<>();
		//to check if you can book locked seats
//		u2SelectedSeats.add(screen1SeatIds.get(0));
		final String bookingId2 = setup.bookingController.createBooking(user2, show, u2SelectedSeats);
		setup.paymentsController.paymentSuccess(bookingId2, user2);

		//Check if locks applied correctly
		ifLocksAppliedCorrectly(u2AvailableSeats,u1SelectedSeats);


	}
	private static boolean ifLocksAppliedCorrectly(List<String>excludedSeats,List<String>availableSeats){
		for (String includedSeat: excludedSeats) {
			if(availableSeats.contains(includedSeat)){
				System.out.println("Locks are applied incorrectly");
				return false;
			}
		}
		System.out.println("Locks are applied correctly");
		return true;
	}



}
