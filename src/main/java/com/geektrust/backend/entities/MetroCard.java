package com.geektrust.backend.entities;

import com.geektrust.backend.exceptions.InvalidAmountException;

public class MetroCard {
    private String id;
    private final String cardNumber;
    private int balance;
    private static final int MIN_AMOUNT = 0;

    public MetroCard(String id, String cardNumber, int balance) {
        this(cardNumber, balance);
        this.id = id;
    }

    public MetroCard(String cardNumber, int balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public String getId() {
        return this.id;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public int getBalance() {
        return this.balance;
    }

    public void addAmount(int amount) {
        validateAmountForCredit(amount);
        this.balance += amount;
    }

    public void deductAmount(int amount) {
        validateAmountForDebit(amount);
        this.balance -= amount;
    }

    public boolean hasSufficientBalance(int amount) {
        if (amount <= this.balance)
            return true;
        else
            return false;
    }

    private void validateAmountForCredit(int amount) {
        if (amount <= MIN_AMOUNT)
            throw new InvalidAmountException();
    }

    private void validateAmountForDebit(int amount) {
        if ((amount <= MIN_AMOUNT) || (amount > this.balance))
            throw new InvalidAmountException();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + balance;
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        
        MetroCard other = (MetroCard) obj;
        if((this.cardNumber.equals(other.cardNumber)) && (this.balance == other.balance))
            return true;
        
        return false;
    }
}
