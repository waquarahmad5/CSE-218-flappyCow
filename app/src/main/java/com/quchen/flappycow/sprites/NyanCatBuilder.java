package com.quchen.flappycow.sprites;

public class NyanCatBuilder implements PlayerBuilder {
    private Player player;
    public NyanCatBuilder() {
        this.player = new Player();
    }
    public void buildCharacteristics() {
        player.setCharacteristics("NyanCat");
    }
    public Player getPlayer() {
        return this.player;
    }
}
