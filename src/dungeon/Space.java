package dungeon;

import java.util.ArrayList;
import dnd.models.Monster;
import dnd.models.Treasure;

public abstract class Space implements java.io.Serializable {

  /**
  * Produce string representation of the space.
  *
  * @return string representation.
  */
  public abstract  String getDescription();

  /**
  * Make door connection to the space.
  *
  * @param theDoor the door to connect.
  */
  public abstract void setDoor(Door theDoor);

  public abstract ArrayList<Door> getDoors();

  public abstract void addDoor();

  public abstract void addMonster(Monster theMonster);

  public abstract void addTreasure(Treasure treasure);

  public abstract ArrayList<String> getMonsters();

  public abstract ArrayList<Monster> getMonstersO();

  public abstract ArrayList<String> getTreasureList();

  public abstract ArrayList<Treasure> getTreasures();

  public abstract void removeMonster(int n);

  public abstract void removeTreasure(int n);

}
