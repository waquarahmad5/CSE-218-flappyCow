package com.quchen.flappycow;

import com.quchen.flappycow.sprites.PowerUp;

import java.util.List;

public class ClearPowerUps implements ReviveObserver{

    @Override
    public void update(GameView gameView) {
        List<PowerUp> powerUps = gameView.getPowerUps();
        powerUps.clear();
        gameView.setPowerUps(powerUps);
    }
}
