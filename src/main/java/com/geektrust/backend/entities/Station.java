package com.geektrust.backend.entities;

import java.util.ArrayList;
import java.util.List;
import com.geektrust.backend.exceptions.InvalidAmountException;
import com.geektrust.backend.exceptions.InvalidPassengerException;

public class Station implements Comparable<Station> {
    private String id;
    private final String name;
    private final List<Passenger> boardedPassengers;
    private int travelChargeCollection;
    private int serviceFeeCollection;
    private int discountCollection;
    private static final int MIN_AMOUNT = 0;

    public Station(String id, String name) {
        this(name);
        this.id = id;
    }

    public Station(String name) {
        this.name = name;
        this.boardedPassengers = new ArrayList<>();
        this.travelChargeCollection = 0;
        this.serviceFeeCollection = 0;
        this.discountCollection = 0;
    }

    public void addTravelCharge(int travelCharge) {
        validateAmount(travelCharge);
        this.travelChargeCollection += travelCharge;
    }

    public void addServiceFee(int serviceFee) {
        validateAmount(serviceFee);
        this.serviceFeeCollection += serviceFee;
    }

    public void addDiscount(int discount) {
        validateAmount(discount);
        this.discountCollection += discount;
    }

    public void addPassenger(Passenger passenger) {
        validatePassenger(passenger);
        this.boardedPassengers.add(passenger);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Passenger> getBoardedPassengers() {
        return this.boardedPassengers;
    }

    public int getTravelChargeCollection() {
        return this.travelChargeCollection;
    }

    public int getServiceFeeCollection() {
        return this.serviceFeeCollection;
    }

    public int getDiscountCollection() {
        return this.discountCollection;
    }

    public int getTotalCollection() {
        return this.travelChargeCollection + this.serviceFeeCollection;
    }
    
    private void validateAmount(int amount) {
        if (amount <= MIN_AMOUNT)
            throw new InvalidAmountException();
    }

    private void validatePassenger(Passenger passenger) {
        if (passenger == null)
            throw new InvalidPassengerException();
    }

    @Override
    public int compareTo(Station other) {
        return other.name.compareTo(this.name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Station other = (Station) obj;
        if(this.name.equals(other.name))
            return true;
            
        return false;
    }
}
