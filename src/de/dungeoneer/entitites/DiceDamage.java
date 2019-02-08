package de.dungeoneer.entitites;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import de.dungeoneer.helper.DiceHelper;

@DatabaseTable(tableName = "dice_damages")
public class DiceDamage {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int d4;

    @DatabaseField
    private int d6;

    @DatabaseField
    private int d8;

    @DatabaseField
    private int d10;

    @DatabaseField
    private int d12;

    @DatabaseField
    private int d20;

    @DatabaseField
    private int base;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Weapon> weapons;

    public DiceDamage() {
    }

    public DiceDamage(int d4, int d6, int d8, int d10, int d12, int d20, int base, ForeignCollection<Weapon> weapons) {
        this.d4 = d4;
        this.d6 = d6;
        this.d8 = d8;
        this.d10 = d10;
        this.d12 = d12;
        this.d20 = d20;
        this.base = base;
        this.weapons = weapons;
    }

    public int getD4() {
        return d4;
    }

    public int getD6() {
        return d6;
    }

    public int getD8() {
        return d8;
    }

    public int getD10() {
        return d10;
    }

    public int getD12() {
        return d12;
    }

    public int getD20() {
        return d20;
    }

    public int getBase() {
        return base;
    }

    public ForeignCollection<Weapon> getWeapons() { return weapons; }

    public void setD4(int d4) {
        this.d4 = d4;
    }

    public void setD6(int d6) {
        this.d6 = d6;
    }

    public void setD8(int d8) {
        this.d8 = d8;
    }

    public void setD10(int d10) {
        this.d10 = d10;
    }

    public void setD12(int d12) {
        this.d12 = d12;
    }

    public void setD20(int d20) {
        this.d20 = d20;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public void setWeapons(ForeignCollection<Weapon> weapons) { this.weapons = weapons; }

    /**
     * Generates a random number, resulting from multiple dice rolls (defined by variables d4 through d20), adding base.
     * @param critical doubling dice rolls (not base)
     * @return random number
     */
    public int rollDamage(boolean critical) {
        int damage = 0;
        int mod = (critical ? 2 : 1);

        for(int i = 0; i < (d4 * mod); i++) {
            damage += DiceHelper.roll(4);
        }

        for(int i = 0; i < (d6 * mod); i++) {
            damage += DiceHelper.roll(6);
        }

        for(int i = 0; i < (d8 * mod); i++) {
            damage += DiceHelper.roll(8);
        }

        for(int i = 0; i < (d10 * mod); i++) {
            damage += DiceHelper.roll(10);
        }

        for(int i = 0; i < (d12 * mod); i++) {
            damage += DiceHelper.roll(12);
        }

        for(int i = 0; i < (d20 * mod); i++) {
            damage += DiceHelper.roll(20);
        }

        damage += base;

        return damage;
    }
}
