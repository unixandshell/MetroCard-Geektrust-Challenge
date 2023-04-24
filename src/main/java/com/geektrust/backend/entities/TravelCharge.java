package com.geektrust.backend.entities;

public enum TravelCharge {
    ADULT(200), SENIOR_CITIZEN(100), KID(50);

    private int charge;

    TravelCharge(int charge) {
        this.charge = charge;
    }

    public int getCharge() {
        return this.charge;
    }
}
