package dungeon;

import java.util.ArrayList;
import dnd.models.Monster;
import dnd.models.Treasure;

public abstract class Space {

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

}
