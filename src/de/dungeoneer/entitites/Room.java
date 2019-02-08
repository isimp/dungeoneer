package de.dungeoneer.entitites;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "rooms")
public class Room {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField(canBeNull = false, foreign = true)
    private Dungeon dungeon;

    public Room() {
    }

    public Room(String name, Dungeon dungeon, ForeignCollection<CharacterInRoom> characters) {
        this.name = name;
        this.dungeon = dungeon;
    }

    public String getName() {
        return name;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }
}
