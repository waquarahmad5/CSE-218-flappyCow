package com.quchen.flappycow.sprites;
import java.util.HashSet;

public abstract class PowerUpFactory {
    private HashSet<String> availablePowerUps;
    public PowerUpFactory(){
        availablePowerUps = new HashSet<String>();
    }

    public void addPowerUp(String newPowerUp){
        availablePowerUps.add(newPowerUp);
    }

    public boolean hasPowerUp(String thisPowerUp){
        return availablePowerUps.contains(thisPowerUp);
    }
}