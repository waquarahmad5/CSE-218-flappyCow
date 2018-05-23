package com.quchen.flappycow.sprites;

public class CowBuilder implements PlayerBuilder {

    private Player player;
    public CowBuilder () {
        this.player = new Player();
    }
    public void buildCharacteristics() {
        player.setCharacteristics("Cow");
    }
    public Player getPlayer() {
        return this.player;
    }
}
