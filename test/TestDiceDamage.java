import de.dungeoneer.entitites.DiceDamage;
import de.dungeoneer.helper.DiceHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDiceDamage {
    @Test
    public void testRollDamage() {
        DiceDamage damage = new DiceDamage();
        damage.setBase(1);

        assertEquals(1, damage.rollDamage(false));
        assertEquals(1, damage.rollDamage(true));

        damage.setD6(2);
        DiceHelper.setIsRandom(false);
        DiceHelper.setValue(1);

        assertEquals(3, damage.rollDamage(false));
        assertEquals(5, damage.rollDamage(true));

        DiceHelper.setIsRandom(true);

        assertTrue(damage.rollDamage(true) <= 25 );
        assertTrue(damage.rollDamage(true) >= 5);
        assertTrue(damage.rollDamage(false) <= 13 );
        assertTrue(damage.rollDamage(false) >= 3);
    }
}
