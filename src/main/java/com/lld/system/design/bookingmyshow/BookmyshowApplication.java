package com.lld.system.design.bookingmyshow;

import com.lld.system.design.bookingmyshow.model.City;
import com.lld.system.design.bookingmyshow.model.Movie;
import com.lld.system.design.bookingmyshow.model.Show;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

		//------ SETUP START-------
		setup.setupControllers(10, 0);

		//-----mcreate all movies-------
		final String movie1 = setup.movieController.createMovie(City.Bangalore,"Movie 1");
		final String movie2 = setup.movieController.createMovie(City.Bangalore,"Movie 2");
		final String movie3= setup.movieController.createMovie(City.Delhi,"Movie 1");
		final String movie4= setup.movieController.createMovie(City.Delhi,"Movie 2");

		//------- create screen and seat-------
		final String screen = setup.setupScreen();
		final List<String> screen1SeatIds = setup.createSeats(setup.theatreController, screen, 2, 10);

		//---- create shows--------
		final String show1 = setup.showController.createShow(movie1, screen, new Date(), 2 * 60 * 60);
		final String show2 = setup.showController.createShow(movie1, screen, new Date(), 2 * 40 * 60);

		//------- helper methods for end user------
		List<Movie>moviesInCity = setup.movieController.getMoviesByCity(City.Bangalore);
		List<Show>allShows= setup.showController.getAllShow(City.Bangalore,setup.movieController.getMovie(movie1));
		List<String> u1AvailableSeats = setup.showController.getAvailableSeats(show1);

		//------SETUP ENDS-------

	//Case: Seat booked by one user should be locked for other user
		List<String>users = new ArrayList<>();
		for(int i=0;i<usersCount;i++){
			System.out.println("What is the user name?");
			String userName = scanner.nextLine();
			users.add(userName);
			System.out.println("Seat count for this user");
			int seatCount = scanner.nextInt();
			scanner.nextLine();
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
				Future<String> bookingIdFuture = executor.submit(() -> setup.bookingController.createBooking(user, show1, userSelectedSeats));
				String bookingId1 = null;
				try {
					bookingId1 = bookingIdFuture.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
//				final String bookingId1 = setup.bookingController.createBooking(user, show1, userSelectedSeats);
				setup.paymentsController.paymentSuccess(bookingId1, user);
			}
		}
		//------seat booking closed---------
		executor.shutdown();
		System.out.println("Seats booked Successfully");
	}
}
