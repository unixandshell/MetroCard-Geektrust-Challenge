package com.geektrust.backend.entities;

import java.util.ArrayList;
import java.util.List;

public class Station implements Comparable<Station> {
    private String id;
    private String name;
    private List<Passenger> boardedPassengers; //contains list of passengers boarded from the station
    private int travelChargeCollection;
    private int serviceFeeCollection;
    private int discountCollection;

    public Station(String id, String name) {
        this(name);
        this.id = id;
    }

    public Station(String name) {
        this.name = name;
        this.boardedPassengers = new ArrayList<>();
    }

    public void addTravelCharge(int travelCharge) {
        this.travelChargeCollection += travelCharge;
    }

    public void addServiceFee(int serviceFee) {
        this.serviceFeeCollection += serviceFee;
    }

    public void addDiscount(int discount) {
        this.discountCollection += discount;
    }

    public void addPassenger(Passenger passenger) {
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
        if(this.id == null)
        {
            if(other.id != null)
                return false;
            else if(this.name.equals(other.name))
                return true;
        }
        else if((this.id.equals(other.id)) && (this.name.equals(other.name)))
            return true;
            
        return false;
    }

    @Override
    public String toString() {
        return "Station {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", boardedPassengers=" + boardedPassengers +
                ", travelChargeCollection=" + travelChargeCollection +
                ", serviceFeeCollection=" + serviceFeeCollection +
                ", discountCollection=" + discountCollection +
                '}';
    }
}
