package dungeon;
import dnd.models.Exit;
import dnd.models.Trap;
import dnd.die.D20;
import java.util.ArrayList;
import java.util.Random;

public class Door implements java.io.Serializable {

  /**
  * Holds ifOpen bool.
  */
  private boolean open;

  /**
  * Holds ifTrapped bool.
  */
  private boolean trapped;

  /**
  * Holds ifArchway bool.
  */
  private boolean archway;

  /**
  * Holds the Trap.
  */
  private Trap trap;

  /**
  * Holds door location.
  */
  private String location;

  /**
  * Holds door direction.
  */
  private String direction;

  /**
  * Holds list of 2 spaces.
  */
  private ArrayList<Space> spaces;

  /**
  * Create a door object and randomize generation of door attributes.
  */
  public Door() {
  //needs to set defaults
    this.spaces = new ArrayList<Space>();
    D20 rand = new D20();
    int random20 = rand.roll();
    if (random20 < 9) {
      setTrapped(true);
    }
    if (random20 < 4) {
      setOpen(false);
    } else if (random20 < 7) {
      setArchway(true);
    }
  }

  /**
  * Create door based on the Exit.
  *
  * @param theExit exit from which to build door.
  */
  private Door(Exit theExit) {
  //sets up the door based on the Exit from the tables
  this();
  this.location = theExit.getLocation();
  this.direction = theExit.getDirection();
  }

  /**
  * Set the door as trapped and generate a trap.
  *
  * @param flag boolean value to set to trapped.
  * @param roll optional variable to specify the roll.
  */
  public void setTrapped(boolean flag, int... roll) {
  // true == trapped.  Trap must be rolled if no integer is given
  D20 roller = new D20();
  this.trapped = flag;
  this.trap = new Trap();
  this.trap.chooseTrap(roller.roll());
  }

  /**
  * Set the door as open or closed.
  *
  * @param flag Bool value to assign to door.
  */
  public void setOpen(boolean flag) {
  //true == open
    if (!isArchway()) {
    this.open = flag;
    }
  }

  /**
  * Set if door is archway.
  *
  * @param flag bool to assign to archway.
  */
  public void setArchway(boolean flag) {
  //true == is archway
  this.archway = flag;
  if (flag) {
  this.open = true;
  }
  }
  /**
  * Check if door is trapped.
  *
  * @return true if trapped.
  */
  public boolean isTrapped() {
  return this.trapped;
  }

  /**
  * Check if door is open.
  *
  * @return true is open.
  */
  public boolean isOpen() {
  return this.open;
  }

  /**
  * Check if archway.
  *
  * @return true if archway.
  */
  public boolean isArchway() {
  return this.archway;
  }

  /**
  * Gets description of the trap.
  *
  * @return Trap description.
  */
  public String getTrapDescription() {
  return this.trap.getDescription();
  }

  /**
  * Get the two door connections.
  *
  * @return Arraylist containing the two connections.
  */
  public ArrayList<Space> getSpaces() {
  //returns the two spaces that are connected by the door
  return this.spaces;
  }

  /**
  * Set the door connections.
  *
  * @param spaceOne First connection.
  * @param spaceTwo Second connection.
  */
  public void setSpaces(Space spaceOne, Space spaceTwo) {
  //identifies the two spaces with the door
  // this method should also call the addDoor method from Space
  spaceOne.setDoor(this);
  spaceTwo.setDoor(this);
  }

  /**
  * String representation of Door.
  *
  * @return String representation.
  */
  public String getDescription() {
  String toReturn = "Door: ";
  if (this.location != null) {
  toReturn += "Chamber door " + this.location + " " + this.direction + "\n";
  }
  //if (this.spaces.size() > 0){
  //toReturn += "Connecting " + this.spaces.get(0) + " and " + this.spaces.get(1);
  //}
  toReturn += "Open:" + this.open + " Trapped:" + this.trapped + " Archway:" + this.archway;
  if (this.trapped) {
  toReturn += "\ntrap:" + getTrapDescription();
  }
  if(this.spaces.size() > 1) {
    toReturn += "\nLeads to " + spaces.get(1);
  }
  //toReturn += "\nLeads to " + spaces.get(1);
  return toReturn;
  }
}
