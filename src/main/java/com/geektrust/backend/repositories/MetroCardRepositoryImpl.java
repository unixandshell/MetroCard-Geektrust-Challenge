package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.MetroCard;

public class MetroCardRepositoryImpl implements MetroCardRepository {
    private Map<String, MetroCard> metroCardMap;
    private int autoIncrement = 0;

    public MetroCardRepositoryImpl(Map<String, MetroCard> metroCardMap) {
        this.metroCardMap = metroCardMap;
        this.autoIncrement = metroCardMap.size();
    }

    public MetroCardRepositoryImpl() {
        this.metroCardMap = new HashMap<>();
    }

    @Override
    public MetroCard save(MetroCard entity) {
        if(entity.getId() == null)
        {
            autoIncrement++;
            MetroCard metroCard = new MetroCard(Integer.toString(autoIncrement), entity.getCardNumber(), entity.getBalance());
            metroCardMap.put(metroCard.getId(), metroCard);
            return metroCard;
        }
        metroCardMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<MetroCard> findByCardNumber(String cardNumber) {
        List<MetroCard> metroCardList = metroCardMap.values().stream().collect(Collectors.toList());

        for(MetroCard metroCard : metroCardList)
        {
            if(metroCard.getCardNumber().equals(cardNumber))
                return Optional.ofNullable(metroCard);
        }
        return Optional.ofNullable(null);
    }

}
