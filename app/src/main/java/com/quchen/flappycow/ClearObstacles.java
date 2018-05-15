package com.quchen.flappycow;

import com.quchen.flappycow.sprites.Obstacle;

import java.util.List;

public class ClearObstacles implements ReviveObserver {

    @Override
    public void update(GameView gameView) {

        List<Obstacle> obstacles = gameView.getObstacles();
        obstacles.clear();
        gameView.setObstacles(obstacles);
    }
}
