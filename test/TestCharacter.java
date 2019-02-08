import de.dungeoneer.entitites.Character;
import de.dungeoneer.entitites.DiceDamage;
import de.dungeoneer.entitites.Weapon;
import de.dungeoneer.helper.DiceHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCharacter {
    @Test
    public void testGetCombinedInitiative() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        int weaponInitiative = 3;
        int characterInitiative = 2;
        int result = weaponInitiative + characterInitiative;

        weapon.setInitiative(weaponInitiative);
        character.setWeapon(weapon);
        character.setInitiative(characterInitiative);

        assertEquals(result, character.getCombinedInitiative());
    }

    @Test
    public void testGetCombinedAttack() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        int weaponAttack = 3;
        int characterAttack = 2;
        int result = weaponAttack + characterAttack;

        weapon.setAttack(weaponAttack);
        character.setWeapon(weapon);
        character.setAttack(characterAttack);
        character.setName("Charakter");

        assertEquals(result, character.getCombinedAttack());
    }

    @Test
    public void testGetCombinedDefense() {
        Character character = new Character();
        int characterArmor = 3;
        int characterDefense = 2;
        int result = characterArmor + characterDefense;

        character.setArmor(characterArmor);
        character.setDefense(characterDefense);
        character.setName("Charakter");

        assertEquals(result, character.getCombinedDefense());
    }

    @Test
    public void testSetSlipAttack() {
        Character character = new Character();
        int life = 10;
        int diceDamage = 3;
        int result = life - (diceDamage - 1);

        character.setName("Charakter");
        character.setLife(life);

        DiceHelper.setIsRandom(false);
        DiceHelper.setValue(diceDamage);

        character.setSlipAttack(true);
        assertEquals(result, character.getLife());
    }

    @Test
    public void testSetRolledInitiative() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        int weaponInitiative = 3;
        int characterInitiative = 2;
        int roll = 10;
        int result = weaponInitiative + characterInitiative + roll;

        weapon.setInitiative(weaponInitiative);
        character.setWeapon(weapon);
        character.setInitiative(characterInitiative);
        character.setRolledInitiative(roll);

        assertEquals(roll, character.getRolledInitiative());
        assertEquals(result, character.getRolledEntireInitiative());
    }

    @Test
    public void testSetRolledAttack() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        int weaponAttack = 3;
        int characterAttack = 2;
        int roll = 10;
        int result = weaponAttack + characterAttack + roll;

        weapon.setAttack(weaponAttack);
        character.setWeapon(weapon);
        character.setAttack(characterAttack);
        character.setName("Charakter");
        character.setRolledAttack(roll);

        assertEquals(roll, character.getRolledAttack());
        assertEquals(result, character.getRolledEntireAttack());
        assertFalse(character.isCriticAttack());
        assertFalse(character.isSlipAttack());

        character.setRolledAttack(1);
        assertFalse(character.isCriticAttack());
        assertTrue(character.isSlipAttack());

        character.setRolledAttack(20);
        assertTrue(character.isCriticAttack());
        assertFalse(character.isSlipAttack());
    }

    @Test
    public void testSetRolledDefense() {
        Character character = new Character();
        int characterArmor = 3;
        int characterDefense = 2;
        int roll = 10;
        int result = characterArmor + characterDefense + roll;

        character.setArmor(characterArmor);
        character.setDefense(characterDefense);
        character.setName("Charakter");
        character.setRolledDefense(roll);

        assertEquals(roll, character.getRolledDefense());
        assertEquals(result, character.getRolledEntireDefense());
        assertFalse(character.isCriticDefense());
        assertFalse(character.isSlipDefense());

        character.setRolledDefense(1);
        assertFalse(character.isCriticDefense());
        assertTrue(character.isSlipDefense());

        character.setRolledDefense(20);
        assertTrue(character.isCriticDefense());
        assertFalse(character.isSlipDefense());
    }

    @Test
    public void testRollInitiative() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        int weaponInitiative = 3;
        int characterInitiative = 2;
        int roll = 10;
        int result = weaponInitiative + characterInitiative + roll;

        weapon.setInitiative(weaponInitiative);
        character.setWeapon(weapon);
        character.setInitiative(characterInitiative);

        DiceHelper.setIsRandom(false);
        DiceHelper.setValue(roll);

        assertEquals(result, character.rollInitiative());

        DiceHelper.setIsRandom(true);
    }

    @Test
    public void testRollAttack() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        int weaponAttack = 3;
        int characterAttack = 2;
        int roll = 10;
        int result = weaponAttack + characterAttack + roll;

        weapon.setAttack(weaponAttack);
        character.setWeapon(weapon);
        character.setAttack(characterAttack);
        character.setName("Charakter");

        DiceHelper.setIsRandom(false);
        DiceHelper.setValue(roll);

        assertEquals(result, character.rollAttack());

        DiceHelper.setIsRandom(true);
    }

    @Test
    public void testRollDefense() {
        Character character = new Character();
        int characterArmor = 3;
        int characterDefense = 2;
        int roll = 10;
        int result = characterArmor + characterDefense + roll;

        character.setArmor(characterArmor);
        character.setDefense(characterDefense);
        character.setName("Charakter");

        DiceHelper.setIsRandom(false);
        DiceHelper.setValue(roll);

        assertEquals(result, character.rollDefense());

        DiceHelper.setIsRandom(true);
    }

    @Test
    public void testIsAlive() {
        Character character = new Character();
        character.setLife(1);

        assertTrue(character.isAlive());

        character.setLife(0);

        assertFalse(character.isAlive());

        character.setLife(-1);

        assertFalse(character.isAlive());
    }

    @Test
    public void testDoDamage() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        DiceDamage diceDamage = new DiceDamage();
        int baseDamage = 1;
        int d6 = 1;
        int result = baseDamage + d6;
        int resultCritical = baseDamage + (d6 * 2);

        character.setName("Charakter");
        character.setWeapon(weapon);
        assertEquals(0, character.doDamage(false));
        assertEquals(0, character.doDamage(true));

        diceDamage.setBase(baseDamage);
        diceDamage.setD6(d6);
        weapon.setDiceDamage(diceDamage);

        DiceHelper.setIsRandom(false);
        DiceHelper.setValue(1);

        assertEquals(result, character.doDamage(false));
        assertEquals(result, character.getOverallDamage());

        assertEquals(resultCritical, character.doDamage(true));
        assertEquals(result + resultCritical, character.getOverallDamage());

        DiceHelper.setIsRandom(true);
    }

    @Test
    public void testTakeDamage() {
        Character character = new Character();
        int life = 10;
        int damage = 3;
        int result = life - damage;

        character.setName("Charakter");
        character.setLife(life);

        assertEquals(result, character.takeDamage(damage));
    }

    @Test
    public void testDefend() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        int attack = 20;

        character.setName("Charakter");
        weapon.setDamageType("");

        character.setRolledEntireDefense(0);
        assertTrue(character.defend(attack, weapon));

        weapon.setDamageType("melee");

        character.setRolledEntireDefense(attack);
        assertTrue(character.defend(attack, weapon));
        character.setRolledEntireDefense(attack + 1);
        assertTrue(character.defend(attack, weapon));
        character.setRolledEntireDefense(attack - 1);
        assertFalse(character.defend(attack, weapon));

        weapon.setDamageType("range");
        character.setBlock(0);

        character.setArmor(attack);
        assertTrue(character.defend(attack, weapon));
        character.setArmor(attack + 1);
        assertTrue(character.defend(attack, weapon));
        character.setArmor(attack - 1);
        assertFalse(character.defend(attack, weapon));
        character.setBlock(100);
        assertTrue(character.defend(attack, weapon));
    }

    @Test
    public void testCompareTo() {
        Character characterOne = new Character();
        Character characterTwo = new Character();

        characterOne.setRolledEntireInitiative(1);
        characterTwo.setRolledEntireInitiative(0);

        assertEquals(1, characterOne.compareTo(characterTwo));
        assertEquals(-1, characterTwo.compareTo(characterOne));

        characterOne.setRolledEntireInitiative(0);
        characterOne.setInitiative(1);
        characterTwo.setInitiative(0);

        assertEquals(1, characterOne.compareTo(characterTwo));
        assertEquals(-1, characterTwo.compareTo(characterOne));

        characterOne.setInitiative(0);
        characterOne.setRolledInitiative(1);
        characterTwo.setRolledInitiative(0);

        assertEquals(1, characterOne.compareTo(characterTwo));
        assertEquals(-1, characterTwo.compareTo(characterOne));

        characterOne.setRolledInitiative(0);

        assertEquals(0, characterOne.compareTo(characterTwo));
        assertEquals(0, characterTwo.compareTo(characterOne));
    }
}
