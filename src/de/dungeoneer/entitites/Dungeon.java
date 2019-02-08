package de.dungeoneer.entitites;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "dungeons")
public class Dungeon {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private int iterations;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Room> rooms;

    public Dungeon() {
    }

    public Dungeon(String name, int iterations, ForeignCollection<Room> rooms) {
        this.name = name;
        this.iterations = iterations;
        this.rooms = rooms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIterations(int iterations) { this.iterations = iterations; }

    public void setRooms(ForeignCollection<Room> rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public int getIterations() { return iterations; }

    public ForeignCollection<Room> getRooms() {
        return rooms;
    }
}
