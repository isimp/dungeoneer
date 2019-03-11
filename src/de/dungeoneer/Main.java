package de.dungeoneer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.dungeoneer.entitites.*;
import de.dungeoneer.entitites.Character;
import de.dungeoneer.helper.DiceHelper;
import de.dungeoneer.helper.GroupHelper;
import de.dungeoneer.helper.LogHelper;

import java.io.IOException;
import java.security.acl.Group;
import java.sql.SQLException;
import java.util.*;

public class Main {

    private Dao<Dungeon, Integer> dungeonDao;
    private Dao<Room, Integer> roomDao;
    private Dao<Character, Integer> characterDao;
    private Dao<CharacterInRoom, Integer> characterInRoomDao;
    private Dao<Weapon, Integer> weaponDao;
    private Dao<DiceDamage, Integer> diceDamageDao;

    public static void main(String[] args) {
        //ORMLite Log-Output
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");

        new Main().doMain(args);
    }

    private void setupDatabase(ConnectionSource connectionSource) throws Exception {
        diceDamageDao = DaoManager.createDao(connectionSource, DiceDamage.class);
        weaponDao = DaoManager.createDao(connectionSource, Weapon.class);
        characterDao = DaoManager.createDao(connectionSource, Character.class);
        characterInRoomDao = DaoManager.createDao(connectionSource, CharacterInRoom.class);
        roomDao = DaoManager.createDao(connectionSource, Room.class);
        dungeonDao = DaoManager.createDao(connectionSource, Dungeon.class);

        TableUtils.createTableIfNotExists(connectionSource, DiceDamage.class);
        TableUtils.createTableIfNotExists(connectionSource, Weapon.class);
        TableUtils.createTableIfNotExists(connectionSource, Character.class);
        TableUtils.createTableIfNotExists(connectionSource, CharacterInRoom.class);
        TableUtils.createTableIfNotExists(connectionSource, Room.class);
        TableUtils.createTableIfNotExists(connectionSource, Dungeon.class);
    }

    private void doMain(String[] args) {
        String databaseUrl = "jdbc:sqlite:dungeoneer.db";
        ConnectionSource connectionSource = null;

        /*DiceHelper.setIsRandom(false);
        DiceHelper.setValue(1);*/

        try {
            connectionSource = new JdbcConnectionSource(databaseUrl);
            setupDatabase(connectionSource);
            LogHelper.logDebug("Datenbank geöffnet und erstellt. Viel Freude!");

            List<Character> players = lookupPlayerCharacters();

            if (players.isEmpty()) {
                LogHelper.logDetailledInfo("Keine Mitspieler.");
                return;
            }

            for (Character character : players) {
                LogHelper.logDetailledInfo(character.getName() + " spielt mit.");
                weaponDao.refresh(character.getWeapon());
                diceDamageDao.refresh(character.getWeapon().getDiceDamage());
            }

            Dungeon dungeon = dungeonDao.queryForId(1);

            if (dungeon == null || dungeon.getRooms() == null) {
                LogHelper.logDetailledInfo("Dungeon nicht existent oder keine Räume in Dungeon.");
                return;
            }

            LogHelper.logDetailledInfo("Betrete " + dungeon.getName());

            int countPlayersAlive = 0;

            for(int iterations = 0; iterations < dungeon.getIterations(); iterations++) {
                for (Room room : dungeon.getRooms()) {
                    List<Character> enemies;

                    LogHelper.logDetailledInfo("Betrete Raum: " + room.getName());

                    enemies = lookupCharactersInRoom(room);
                    if (enemies.isEmpty()) {
                        LogHelper.logDetailledInfo("Raum ist leer. Fortfahren.");
                        continue;
                    }

                    LogHelper.logDetailledInfo("Gegner gefunden. Beginne Kampf.");

                    for(Character character : enemies) {
                        weaponDao.refresh(character.getWeapon());
                        diceDamageDao.refresh(character.getWeapon().getDiceDamage());
                    }

                    ArrayList<Character> fighters = new ArrayList<>();
                    fighters.addAll(enemies);
                    fighters.addAll(players);

                    for (Character character : fighters) {
                        character.rollInitiative();
                    }

                    Collections.sort(fighters);

                    while (GroupHelper.isGroupAlive(players) && GroupHelper.isGroupAlive(enemies)) {
                        for (Character character : fighters) {
                            LogHelper.logDetailledInfo(character.getName() + " ist dran (Initiative: " + character.getRolledEntireInitiative() + ")");

                            if (!character.isAlive()) {
                                LogHelper.logDetailledInfo(character.getName() + " ist tot. Überspringe seine Kampfrunde.");
                                continue;
                            }

                            for (int i = 0; i < character.getActions(); i++) {
                                Character target = GroupHelper.getRandomCharacter(GroupHelper.getAliveCharacters((character.isPlayer() ? enemies : players)));
                                if (target == null) {
                                    LogHelper.logDetailledInfo("Alle Gegner tot. Überspringe Aktion.");
                                    break;
                                }

                                target.rollDefense();
                                character.rollAttack();
                                if (target.isCriticDefense()) continue;

                                if (character.isCriticAttack()) {
                                    target.rollDefense();
                                    character.rollAttack();

                                    if (target.defend(character.getRolledEntireAttack(), character.getWeapon())) {
                                        target.takeDamage(character.doDamage(false));
                                    } else {
                                        target.takeDamage(character.doDamage(true));
                                    }
                                } else if (!character.isSlipAttack()) {
                                    if (target.isSlipDefense()) {
                                        target.takeDamage(character.doDamage(false));
                                    } else {
                                        if (!target.defend(character.getRolledEntireAttack(), character.getWeapon())) {
                                            target.takeDamage(character.doDamage(false));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (!GroupHelper.isGroupAlive(players)) {
                        LogHelper.logDetailledInfo("Die Gruppe ist tot. Beende Dungeonsimulation.");
                        break;
                    }

                    LogHelper.logDetailledInfo("Der Kampf ist gewonnen. Suche nächsten Raum.");
                }

                if (GroupHelper.isGroupAlive(players)) {
                    LogHelper.logDetailledInfo("Keine weiteren Räume gefunden. Spieler haben den Dungeon erfolgreich bezwungen.");
                    if(GroupHelper.isEntireGroupAlive(players)) {
                        LogHelper.logDetailledInfo("Komplette Gruppe am Leben. Iteration wird als bestanden gewertet.");
                        countPlayersAlive++;
                    }
                }

                for (Character character : players) {
                    LogHelper.logDetailledInfo(character.getName() + " hat noch " + character.getLife() + " Leben");
                }

                for(Character character : players) {
                    characterDao.refresh(character);
                }
                LogHelper.logInfo("Iteration " + (iterations + 1) + " abgeschlossen.");
            }

            LogHelper.logInfo("Spieler haben den Dungeon " + countPlayersAlive + " von " + dungeon.getIterations() + " Mal abgeschlossen.");
        } catch(SQLException e) {
            LogHelper.logCritical("Datenbankprobleme: " + e.toString());
        } catch(NullPointerException e) {
            LogHelper.logCritical("NullPointer: " + e.toString());
        } catch (Exception e) {
            LogHelper.logCritical("Unknown: " + e.toString());
        } finally {
            if(connectionSource != null) {
                try {
                    connectionSource.close();
                    LogHelper.logDebug("Datenbank geschlossen.");
                } catch(IOException e) {
                    LogHelper.logCritical("Datenbank konnte nicht geschlossen werden: " + e.toString());
                }
            }
        }

        LogHelper.logDetailledInfo("Simulation vorbei. Danke für deinen Besuch!");
    }

    private PreparedQuery<Character> charactersInRoomQuery = null;

    /**
     * Gives all characters in room from database.
     * @param room room in which to search for characters
     * @return list of characters in room
     * @throws SQLException
     */
    private List<Character> lookupCharactersInRoom(Room room) throws SQLException {
        if (charactersInRoomQuery == null) {
            charactersInRoomQuery = makeCharactersInRoomQuery();
        }
        charactersInRoomQuery.setArgumentHolderValue(0, room);
        return characterDao.query(charactersInRoomQuery);
    }

    /**
     * Builds basic query for character in room search.
     * @return prepared query, argument 0 is room id
     * @throws SQLException
     */
    private PreparedQuery<Character> makeCharactersInRoomQuery() throws SQLException {
        QueryBuilder<CharacterInRoom, Integer> charactersRoomQb = characterInRoomDao.queryBuilder();
        charactersRoomQb.selectColumns("character_id");
        SelectArg charactersSelectArg = new SelectArg();
        charactersRoomQb.where().eq("room_id", charactersSelectArg);

        QueryBuilder<Character, Integer> charactersQb = characterDao.queryBuilder();
        charactersQb.where().in("id", charactersRoomQb);
        return charactersQb.prepare();
    }

    /**
     * Gives all player characters from database.
     * @return list of player characters.
     * @throws SQLException
     */
    private List<Character> lookupPlayerCharacters() throws SQLException {
        QueryBuilder<Character, Integer> charactersQb = characterDao.queryBuilder();
        charactersQb.where().in("isPlayer", true);
        return charactersQb.query();
    }
}
