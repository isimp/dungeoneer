package de.dungeoneer.entitites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import de.dungeoneer.helper.DiceHelper;
import de.dungeoneer.helper.LogHelper;

@DatabaseTable(tableName = "characters")
public class Character implements Comparable<Character> {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private boolean isPlayer;

    @DatabaseField
    private int actions;

    @DatabaseField
    private int life;

    @DatabaseField
    private int initiative;

    @DatabaseField
    private int attack;

    @DatabaseField(foreign = true)
    private Weapon weapon;

    @DatabaseField
    private int armor;

    @DatabaseField
    private int defense;

    @DatabaseField
    private int block;

    private int rolledInitiative;
    private int rolledEntireInitiative;
    private int rolledAttack;
    private int rolledEntireAttack;
    private boolean isCriticAttack;
    private boolean isSlipAttack;
    private int rolledDefense;
    private int rolledEntireDefense;
    private boolean isCriticDefense;
    private boolean isSlipDefense;

    private int overallDamage;


    public Character() {
    }

    public Character(String name, boolean isPlayer, int actions, int life, int initiative, int attack, Weapon weapon, int armor, int defense, int block) {
        this.name = name;
        this.isPlayer = isPlayer;
        this.actions = actions;
        this.life = life;
        this.initiative = initiative;
        this.attack = attack;
        this.weapon = weapon;
        this.armor = armor;
        this.defense = defense;
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public int getActions() { return actions; }

    public int getLife() {
        return life;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAttack() {
        return attack;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getArmor() {
        return armor;
    }

    public int getDefense() {
        return defense;
    }

    public int getBlock() {
        return block;
    }

    public int getRolledInitiative() {
        return rolledInitiative;
    }

    public int getRolledEntireInitiative() { return rolledEntireInitiative; }

    public int getRolledAttack() {
        return rolledAttack;
    }

    public int getRolledEntireAttack() {
        return rolledEntireAttack;
    }

    public boolean isCriticAttack() { return isCriticAttack; }

    public boolean isSlipAttack() { return isSlipAttack; }

    public int getRolledDefense() {
        return rolledDefense;
    }

    public int getRolledEntireDefense() {
        return rolledEntireDefense;
    }

    public boolean isCriticDefense() { return isCriticDefense; }

    public boolean isSlipDefense() { return isSlipDefense; }

    public int getOverallDamage() {
        return overallDamage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public void setActions(int actions) { this.actions = actions; }

    public void setLife(int life) {
        this.life = life;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    /**
     * Sets variables for rolledInitiative and rolledEntireInitiative.
     * @param rolledInitiative result a of dice roll
     */
    public void setRolledInitiative(int rolledInitiative) {
        setRolledEntireInitiative(rolledInitiative + getCombinedInitiative());
        this.rolledInitiative = rolledInitiative;
    }

    public void setRolledEntireInitiative(int rolledEntireInitiative) { this.rolledEntireInitiative = rolledEntireInitiative; }

    /**
     * Sets variables for rolledAttack and rolledEntireAttack. Also changes status of isCriticAttack and isSlipAttack accordingly.
     * @param rolledAttack result of a dice roll
     */
    public void setRolledAttack(int rolledAttack) {
        setRolledEntireAttack(rolledAttack + getCombinedAttack());
        setCriticAttack((rolledAttack == 20 ? true : false));
        setSlipAttack((rolledAttack == 1 ? true : false));

        this.rolledAttack = rolledAttack;
    }


    public void setRolledEntireAttack(int rolledEntireAttack) { this.rolledEntireAttack = rolledEntireAttack; }

    public void setCriticAttack(boolean criticAttack) {
        if(criticAttack) LogHelper.logDetailledInfo(getName() + " hat eine kritische Attacke gewürfelt.");
        isCriticAttack = criticAttack;
    }

    /**
     * Sets isSlipAttack. If true, deals 0-2 damage to character (dice roll 3 - 1)
     * @param slipAttack character slipped attack
     */
    public void setSlipAttack(boolean slipAttack) {
        if(slipAttack) {
            LogHelper.logDetailledInfo(getName() + " hat eine gepatzte Attacke gewürfelt.");
            takeDamage(DiceHelper.roll(3) - 1);
        }

        isSlipAttack = slipAttack;
    }

    /**
     * Sets variables for rolledDefense and rolledEntireDefense. Also changes status of isCriticDefense and isSlipDefense accordingly.
     * @param rolledDefense result of a dice roll
     */
    public void setRolledDefense(int rolledDefense) {
        setRolledEntireDefense(rolledDefense + getCombinedDefense());
        setCriticDefense((rolledDefense == 20 ? true : false));
        setSlipDefense((rolledDefense == 1 ? true : false));

        this.rolledDefense = rolledDefense;
    }

    public void setRolledEntireDefense(int rolledEntireDefense) {
        this.rolledEntireDefense = rolledEntireDefense;
    }

    public void setCriticDefense(boolean criticDefense) {
        if(criticDefense) LogHelper.logDetailledInfo(getName() + " hat eine kritische Verteidigung gewürfelt.");
        isCriticDefense = criticDefense;
    }

    public void setSlipDefense(boolean slipDefense) {
        if(slipDefense) LogHelper.logDetailledInfo(getName() + " hat eine gepatzte Verteidigung gewürfelt.");
        isSlipDefense = slipDefense;
    }

    public void setOverallDamage(int overallDamage) { this.overallDamage = overallDamage; }


    /**
     * Gives combined result of character and weapon initiative.
     * @return combined initiative
     */
    public int getCombinedInitiative() {
        return getInitiative() + (getWeapon() == null ? 0 : getWeapon().getInitiative());
    }

    /**
     * Gives combined result of character attack and weapon attack.
     * @return combined attack
     */
    public int getCombinedAttack() {
        return getAttack() + (getWeapon() == null ? 0 : getWeapon().getAttack());
    }

    /**
     * Gives combined result of character defense and character armor.
     * @return combined defense
     */
    public int getCombinedDefense() {
        return getArmor()+ getDefense();
    }

    /**
     * Rolls a dice and hands result to setRolledInitiative (wrapper method)
     * @return getRolledEntireInitiative
     */
    public int rollInitiative() {
        int roll = DiceHelper.roll(10);

        setRolledInitiative(roll);

        return getRolledEntireInitiative();
    }

    /**
     * Rolls a dice and hands result to setRolledAttack (wrapper method)
     * @return getRolledEntireAttack
     */
    public int rollAttack() {
        int roll = DiceHelper.roll(20);

        setRolledAttack(roll);

        LogHelper.logDetailledInfo(getName() + " würfelt Attacke: " + roll);

        return getRolledEntireAttack();
    }

    /**
     * Rolls a dice and hands result to setRolledDefense (wrapper method)
     * @return getRolledEntireDefense
     */
    public int rollDefense() {
        int roll = DiceHelper.roll(20);

        setRolledDefense(roll);

        LogHelper.logDetailledInfo(getName() + " würfelt Verteidigung: " + roll);

        return getRolledEntireDefense();
    }

    /**
     * Checks if character is alive.
     * @return characters alive status
     */
    public boolean isAlive() {
        if(this.getLife() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Gives damage based on characters weapon and adds caused damage to overallDamage.
     * @param critical hit is critical
     * @return caused damage
     */
    public int doDamage(boolean critical) {
        if(getWeapon() == null || getWeapon().getDiceDamage() == null) {
            LogHelper.logDetailledInfo(getName() + " führt keine Waffe und verursacht deshalb keinen Schaden.");
            return 0;
        }

        int damage = getWeapon().getDiceDamage().rollDamage(critical);
        LogHelper.logDetailledInfo(getName() + " verursacht " + damage + " Schaden.");
        setOverallDamage(getOverallDamage() + damage);
        return damage;
    }

    /**
     * Modifies life of character by value.
     * @param damage value to modify life by
     * @return life of character after modification
     */
    public int takeDamage(int damage) {
        this.setLife(getLife() - damage);
        if(damage == 0) LogHelper.logDetailledInfo(getName() + " hat Glück und erleidet keinen Schaden.");
        else LogHelper.logDetailledInfo(getName() + " erleidet " + damage  + " Schaden (noch " + getLife() + " Leben übrig).");
        return getLife();
    }

    /**
     * Makes character try to defend against a weapon.
     * @param attack entire attack value
     * @param weapon attacking weapon
     * @return wether character could defend or not
     */
    public boolean defend(int attack, Weapon weapon) {
        boolean canDefend = true;

        switch(weapon.getDamageType()) {
            case "melee":
                if(attack > getRolledEntireDefense()) canDefend = false;
                break;

            case "range":
                if(attack > getArmor() && getBlock() < DiceHelper.roll(100)) canDefend = false;
                break;

            default:
                break;
        }

        if(canDefend) LogHelper.logDetailledInfo(getName() + " hat den Angriff verteidigt.");
        else LogHelper.logDetailledInfo(getName() + " wird von dem Angriff getroffen.");

        return canDefend;
    }

    /**
     * Compares character against another character based on initiative.
     * @param character character to compare to
     * @return -1 on lower initiative, 0 on equal initiative, 1 on higher initiative
     */
    public int compareTo(Character character) {
        if(this.getRolledEntireInitiative() < character.getRolledEntireInitiative()) {
            return -1;
        } else if(this.getRolledEntireInitiative() == character.getRolledEntireInitiative()) {
            if(this.getInitiative() < character.getInitiative()) {
                return -1;
            } else if(this.getInitiative() == character.getInitiative()) {
                if(this.getRolledInitiative() < character.getRolledInitiative()) {
                    return -1;
                } else if(this.getRolledInitiative() > character.getRolledInitiative()) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}