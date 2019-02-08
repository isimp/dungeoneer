package de.dungeoneer.entitites;

import com.j256.ormlite.field.DatabaseField;

public class CharacterInRoom {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, canBeNull = false)
    private Room room;

    @DatabaseField(foreign = true, canBeNull = false)
    private Character character;

    public CharacterInRoom() {
    }

    public CharacterInRoom(Room room, Character character) {
        this.room = room;
        this.character = character;
    }

    public Room getRoom() {
        return room;
    }

    public Character getCharacter() {
        return character;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
