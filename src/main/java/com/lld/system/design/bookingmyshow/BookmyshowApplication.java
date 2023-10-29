package com.lld.system.design.bookingmyshow;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class BookmyshowApplication {


	private static void createMoviesScreenSeat(){

	}
	public static void main(String[] args) {
//		SpringApplication.run(BookmyshowApplication.class,args);
		Setup setup = new Setup();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Number of users ");
		int usersCount = scanner.nextInt();
		scanner.nextLine();
		Map<String,Map<Integer,List<Integer>>>userAndSeats = new HashMap<>();

		System.out.println("Number of users "+usersCount);
		List<String>users = new ArrayList<>();

	//Case: seat requirement for users came together, both users should not be able to book seats
		for(int i=0;i<usersCount;i++){
			System.out.println("What is the user name?");
			String userName = scanner.nextLine();
			users.add(userName);
			System.out.println("Seat count for this user");
			int seatCount = scanner.nextInt();
			scanner.nextLine();
			System.out.println(userName+" "+seatCount);
			List<Integer>seatsForUser=new ArrayList<>();
			for(int j=0;j<seatCount;j++){
				System.out.println("Select seat numbers");
				int seatNumber = scanner.nextInt();
				scanner.nextLine();
				seatsForUser.add(seatNumber);
			}
			Map<Integer,List<Integer>>countWithSeats=new HashMap<>();
			countWithSeats.put(seatCount,seatsForUser);
			userAndSeats.put(userName,countWithSeats);
		}
		scanner.close();
		setup.setupControllers(10, 0);

		final String movie = setup.movieController.createMovie("Movie 1");
		final String screen = setup.setupScreen();
		final List<String> screen1SeatIds = setup.createSeats(setup.theatreController, screen, 2, 10);

		final String show = setup.showController.createShow(movie, screen, new Date(), 2 * 60 * 60);

		List<String> u1AvailableSeats = setup.showController.getAvailableSeats(show);

		ExecutorService executor = Executors.newFixedThreadPool(5);
		//seats booked on fifo basis
		for(Map.Entry<String,Map<Integer,List<Integer>>>userSeats:userAndSeats.entrySet()){
			String user = userSeats.getKey();
			Map<Integer,List<Integer>> countWithSeats = userSeats.getValue();
			List<String> userSelectedSeats = new ArrayList<>();

			for(Map.Entry<Integer,List<Integer>>countWithSeatsIt:countWithSeats.entrySet()){
				int seatCount = countWithSeatsIt.getKey();
				List<Integer>seats=countWithSeatsIt.getValue();
				for(int i=0;i<seatCount;i++) {
					userSelectedSeats.add(screen1SeatIds.get(seats.get(i)));
				}
//				final String bookingId1 = executor.submit(() -> setup.bookingController.createBooking(user, show, userSelectedSeats)).get();
				final String bookingId1 = setup.bookingController.createBooking(user, show, userSelectedSeats);
				setup.paymentsController.paymentSuccess(bookingId1, user);
			}
		}
		System.out.println("Seats booked Successfully");
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
