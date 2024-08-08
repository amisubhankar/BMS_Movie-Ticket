package com.example.bookmyshow.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookMovieResponseDto {

    private Long bookingId;
    private int amount;
    private ResponseStatus responseStatus;
    private String responseMessage;
}
