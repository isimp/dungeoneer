package de.dungeoneer.entitites;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "weapons")
public class Weapon {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String damageType;

    @DatabaseField
    private int attack;

    @DatabaseField
    private int initiative;

    @DatabaseField(foreign = true)
    private DiceDamage diceDamage;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Character> characters;

    public Weapon() {
    }

    public Weapon(String damageType, int attack, int initiative, DiceDamage diceDamage, ForeignCollection<Character> characters) {
        this.damageType = damageType;
        this.attack = attack;
        this.initiative = initiative;
        this.diceDamage = diceDamage;
        this.characters = characters;
    }

    public String getDamageType() {
        return damageType;
    }

    public int getAttack() {
        return attack;
    }

    public int getInitiative() {
        return initiative;
    }

    public DiceDamage getDiceDamage() {
        return diceDamage;
    }

    public ForeignCollection<Character> getCharacters() {
        return characters;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setDiceDamage(DiceDamage diceDamage) {
        this.diceDamage = diceDamage;
    }

    public void setCharacters(ForeignCollection<Character> characters) {
        this.characters = characters;
    }
}
