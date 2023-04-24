package com.geektrust.backend.repositories;

import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;

public interface MetroCardRepository {
    public MetroCard save(MetroCard entity);
    public Optional<MetroCard> findByCardNumber(String cardNumber);
}
