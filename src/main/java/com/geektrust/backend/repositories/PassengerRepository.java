package com.geektrust.backend.repositories;

import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.Passenger;

public interface PassengerRepository {
    public Passenger save(Passenger entity);
    public Optional<Passenger> findByMetroCard(MetroCard metroCard);
}
