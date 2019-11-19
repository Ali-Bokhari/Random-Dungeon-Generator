package dungeon;

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

}
