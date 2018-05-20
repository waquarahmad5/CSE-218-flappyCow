package com.quchen.flappycow;
import java.util.HashSet;

public class CharacterFactory {
    private HashSet<String> availableCharacters;
    public CharacterFactory(){
        availableCharacters = new HashSet<String>();
    }

    public void addCharacter(String newCharacter){
        availableCharacters.add(newCharacter);
    }

    public boolean hasCharacter(String thisCharacter){
        return availableCharacters.contains(thisCharacter);
    }
}
