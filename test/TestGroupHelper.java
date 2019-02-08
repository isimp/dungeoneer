import de.dungeoneer.entitites.Character;
import de.dungeoneer.helper.GroupHelper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestGroupHelper {
    @Test
    public void testIsGroupAlive() {
        ArrayList<Character> characters = new ArrayList<>();

        assertFalse(GroupHelper.isGroupAlive(characters));

        Character characterOne = new Character();
        characterOne.setLife(0);
        characters.add(characterOne);

        assertFalse(GroupHelper.isGroupAlive(characters));

        characterOne.setLife(1);

        assertTrue(GroupHelper.isGroupAlive(characters));

        Character characterTwo = new Character();
        characterTwo.setLife(0);
        characters.add(characterTwo);

        assertTrue(GroupHelper.isGroupAlive(characters));
    }

    @Test
    public void testGetAliveCharacters() {
        ArrayList<Character> characters = new ArrayList<>();

        assertEquals(new ArrayList<Character>(), GroupHelper.getAliveCharacters(characters));

        Character characterOne = new Character();
        characterOne.setLife(0);
        characters.add(characterOne);

        assertEquals(new ArrayList<Character>(), GroupHelper.getAliveCharacters(characters));

        characterOne.setLife(1);

        assertEquals(characters, GroupHelper.getAliveCharacters(characters));

        Character characterTwo = new Character();
        characterTwo.setLife(0);
        characters.add(characterTwo);

        ArrayList<Character> assertingList = new ArrayList<>();
        assertingList.add(characterOne);

        assertEquals(assertingList, GroupHelper.getAliveCharacters(characters));
    }

    @Test
    public void testGetRandomCharacter() {
        ArrayList<Character> characters = new ArrayList<>();

        assertEquals(null, GroupHelper.getRandomCharacter(characters));

        Character characterOne = new Character();
        characterOne.setName("Test");
        characters.add(characterOne);

        assertEquals(characterOne, GroupHelper.getRandomCharacter(characters));
    }
}
