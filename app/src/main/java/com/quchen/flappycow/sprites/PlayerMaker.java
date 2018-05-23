package com.quchen.flappycow.sprites;

public class PlayerMaker {
    private PlayerBuilder playerBuilder;
    public PlayerMaker(PlayerBuilder playerBuilder) {
        this.playerBuilder = playerBuilder;
    }

    public Player getPlayer() {
        return this.playerBuilder.getPlayer();
    }

    public void makePlayer() {
        this.playerBuilder.buildCharacteristics();
    }
}
