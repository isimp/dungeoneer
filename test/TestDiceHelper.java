import de.dungeoneer.helper.DiceHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDiceHelper {
    @Test
    public void testRoll() {
        int rollNumber = 6;
        int roll = DiceHelper.roll(rollNumber);

        assertTrue(roll > 0);
        assertTrue(roll <= rollNumber);

        DiceHelper.setIsRandom(false);
        DiceHelper.setValue(1);

        assertEquals(1, DiceHelper.roll(rollNumber));

        DiceHelper.setIsRandom(true);
    }

    @Test
    public void testRollId() {
        int rollNumber = 6;
        int rollId = DiceHelper.rollId(rollNumber);

        assertTrue(rollId >= 0);
        assertTrue(rollId < rollNumber);
    }
}
