package com.example.bookmyshow.controller;

import com.example.bookmyshow.dtos.BookMovieRequestDto;
import com.example.bookmyshow.dtos.BookMovieResponseDto;
import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.exceptions.InvalidShowException;
import com.example.bookmyshow.exceptions.InvalidUserException;
import com.example.bookmyshow.exceptions.SeatsUnavailableException;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    public BookMovieResponseDto bookMovie(BookMovieRequestDto request){
        try {
            Booking booking = bookingService.bookMovie(request);
            return new BookMovieResponseDto(booking.getId(), booking.getAmount(), ResponseStatus.SUCCESS, "Successfully booked the ticket");
        } catch (InvalidUserException e) {
            return new BookMovieResponseDto(null,0, ResponseStatus.FAILURE,
                    "User is not present");
        } catch (InvalidShowException e) {
            return new BookMovieResponseDto(null,0, ResponseStatus.FAILURE,
                    "Show is not present");
        } catch (SeatsUnavailableException e) {
            return new BookMovieResponseDto(null,0, ResponseStatus.FAILURE,
                    "Seats are unavailable");
        }

    }
}
