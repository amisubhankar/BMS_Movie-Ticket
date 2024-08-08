package com.example.bookmyshow.services;

import com.example.bookmyshow.dtos.BookMovieRequestDto;
import com.example.bookmyshow.exceptions.InvalidShowException;
import com.example.bookmyshow.exceptions.InvalidUserException;
import com.example.bookmyshow.exceptions.SeatsUnavailableException;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.ShowSeatRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public Booking bookMovie(BookMovieRequestDto request) throws InvalidUserException, InvalidShowException, SeatsUnavailableException {

        Long userId = request.getUserId();
        List<Long> showSeatIds = request.getShowSeatIds();
        Long showId = request.getShowId();

        //1. Get user details
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new InvalidUserException();
        }

        //2. Get Show Details
        Optional<Show> show = showRepository.findById(showId);

        if(show.isEmpty()){
            throw new InvalidShowException();
        }

        //3.
        List<ShowSeat> showSeats = reserveShowSeats(showSeatIds);

        //create the booking object
        Booking booking = getBooking(showSeats, user, show);

        return booking;
    }

    private Booking getBooking(List<ShowSeat> showSeats, Optional<User> user, Optional<Show> show) {
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.WAITING);
        booking.setShowSeatList(showSeats);
        booking.setPayments(new ArrayList<>());
        booking.setAmount(calculatePrice(showSeats));
        booking.setUser(user.get());
        booking.setShow(show.get());

        bookingRepository.save(booking);
        return booking;
    }

    private int calculatePrice(List<ShowSeat> showSeats) {
        return 0;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ShowSeat> reserveShowSeats(List<Long> showSeatIds)
            throws SeatsUnavailableException {

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        for(ShowSeat showSeat : showSeats){
            if(! (showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE))
               || ( showSeat.getShowSeatStatus().equals(ShowSeatStatus.LOCKED)
                    && Duration.between(new Date().toInstant(), showSeat.getLockedAt().toInstant()).toMinutes() < 10)){

                //throw an error
                throw new SeatsUnavailableException();
            }
        }

        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
        }

        return showSeatRepository.saveAll(showSeats);

    }
}
