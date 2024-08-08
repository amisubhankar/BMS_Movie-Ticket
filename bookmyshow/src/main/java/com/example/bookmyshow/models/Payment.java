package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{

    private String refNo;
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;
    private String gateWay;
    @Enumerated(EnumType.ORDINAL)
    private PaymentMode paymentMode;
    private int amount;
}
