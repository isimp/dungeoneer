package de.dungeoneer.helper;
import de.dungeoneer.entitites.Character;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper {
    /**
     * Checks if in a given list of characters, at least one character is alive.
     * @param characters list of characters
     * @return at least one character alive
     */
    public static boolean isGroupAlive(List<Character> characters) {
        boolean isAlive = false;
        for(Character character : characters) {
            if(character.isAlive()) isAlive = true;
        }

        return isAlive;
    }

    /**
     * Gives a list of alive characters out of a list of characters.
     * @param characters list of characters
     * @return list of alive characters
     */
    public static List<Character> getAliveCharacters(List<Character> characters) {
        ArrayList<Character> aliveCharacters = new ArrayList<>();
        for(Character character : characters) {
            if(character.isAlive()) aliveCharacters.add(character);
        }
        return aliveCharacters;
    }

    /**
     * Gives a random character out of a list of characters.
     * @param characters list of characters
     * @return random character
     */
    public static Character getRandomCharacter(List<Character> characters) {
        if(characters.isEmpty()) return null;
        Character character = characters.get(DiceHelper.rollId(characters.size()));
        LogHelper.logDetailledInfo(character.getName() + " wurde ausgewählt.");
        return character;
    }
}
