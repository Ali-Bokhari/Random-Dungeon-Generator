package dungeon;

import dnd.models.Monster;

/* Represents a 10 ft section of passageway */

public class PassageSection {

  /**
  * Holds descrition from Table A for the door.
  */
  private String description;

  /**
  * Holds door object.
  */
  private Door door;

  /**
  * Holds monster object.
  */
  private Monster monster;

  /**
  * Holds ifChamber bool.
  */
  private boolean chamber;


/**
* Create a passage section.
*/
public PassageSection() {
  //sets up the 10 foot section with default settings
  this.door = null;
  this.monster = null;
  this.chamber = false;
}

/**
* Create a passage section based on a Table 1 roll.
*
* @param d Roll from Table 1.
*/
public PassageSection(String d) {
  //sets up a specific passage based on the values sent in from
  //modified table 1
  this();
  this.description = d;
  if (description.contains("archway") && description.contains("chamber")) {
  this.door = new Door();
  this.chamber = true;
  this.door.setArchway(true);
  } else if (description.contains("Door") && description.contains("Chamber")) {
  this.door = new Door();
  this.chamber = true;
  } else if (description.contains("Monster")) {
  this.monster = new Monster();
  this.monster.setType(42);
  }
}

/**
* Get the door in the section.
*
* @return the door.
*/
public Door getDoor() {
  //returns the door that is in the passage section, if there is one
return this.door;
}

/**
* Setup a door in this section.
*
* @param d door to set.
*/
public void setDoor(Door d) {
  this.door = d;
}

/**
* Get the monster from this section.
*
* @return the monster.
*/
public Monster getMonster() {
  //returns the monster that is in the passage section, if there is one
return this.monster;
}

/**
* Setup a monster in this section.
*
* @param newMonster The Monster.
*/
public void setMonster(Monster newMonster) {
  this.monster = newMonster;
}

/**
* Check if there is a chamber connection.
*
* @return true if chamber connection.
*/
public boolean getChamber() {
  return this.chamber;
}

/**
* Set the description for this section.
*
* @param s description to set.
*/
public void setDescription(String s) {
  this.description = s;
}

/**
* Get the roll description.
*
* @return Roll description.
*/
public String getRollDescription() {
  return this.description;
}

/**
* Get spring representation of this section.
*
* @return String representation.
*/
public String getDescription() {
  String toReturn = this.description;
  if (this.door != null) {
  toReturn += "\n" + this.door.getDescription();
  }
  if (this.monster != null) {
  toReturn += "\n" + this.monster.getDescription();
  }
  return toReturn;
}

}
