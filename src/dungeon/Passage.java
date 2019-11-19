package dungeon;

import dnd.models.Monster;

import java.util.ArrayList;
/*
A passage begins at a door and ends at a door.  It may have many other doors along
the way

You will need to keep track of which door is the "beginning" of the passage
so that you know how to
*/

public class Passage extends Space {
  //these instance variables are suggestions only
  //you can change them if you wish.

  /**
  * Holds the list of passages.
  */
  private ArrayList<PassageSection> thePassage;
  //private HashMap<Door,PassageSection> doorMap;

  /**
  * Creates a passage object.
  */
  public Passage() {
  this.thePassage = new ArrayList<>();
  }

/**
* Returns the PassageSection list.
*
* @return PassageSection list
*/
public ArrayList<PassageSection> getPassage() {
  return this.thePassage;
}

/**
* Gets list of doors in the passage.
*
* @return ArrayList of doors.
*/
public ArrayList getDoors() {
//gets all of the doors in the entire passage
ArrayList<Door> doors = new ArrayList<>();
for (PassageSection p: thePassage) {
  if (p.getDoor() != null) {
  doors.add(p.getDoor());
  }
}
return doors;
}

/**
* Get door at specfic index.
*
* @param i index.
*
* @return Door at the index.
*/
public Door getDoor(int i) {
  //returns the door in section 'i'. If there is no door, returns null

  return this.thePassage.get(i).getDoor();
}

/**
* Add monster to PassageSection at index i.
*
* @param theMonster monster to add.
* @param i index.
*/
public void addMonster(Monster theMonster, int i) {
  // adds a monster to section 'i' of the passage
  this.thePassage.get(i).setMonster(theMonster);
}

/**
* Get monster at index.
*
* @param i index.
*
* @return Monster from the index.
*/
public Monster getMonster(int i) {
  //returns Monster door in section 'i'. If there is no Monster, returns null
return this.thePassage.get(i).getMonster();
}

/**
* Add a new PassageSection to the passage.
*
* @param toAdd PassageSection to add.
*/
public void addPassageSection(PassageSection toAdd) {
  //adds the passage section to the passageway
  this.thePassage.add(toAdd);
}

/**
* Make a door connection to this passage.
*
* @param newDoor Door to connect.
*/
@Override
public void setDoor(Door newDoor) {
  //should add a door connection to the current Passage Section
  if (this.thePassage.size() == 0) {
  PassageSection p = new PassageSection();
  addPassageSection(p);
  }
  (thePassage.get(thePassage.size() - 1)).setDoor(newDoor);
  if ((thePassage.get(thePassage.size() - 1)).getRollDescription() == null) {
  (thePassage.get(thePassage.size() - 1)).setDescription("Entrance door");
  }
  newDoor.getSpaces().add(this);
}

/**
* Produce a string representation of the passage.
*
* @return String representation.
*/
@Override
public String getDescription() {
  String toReturn = "";
  for (PassageSection p: thePassage) {
  toReturn += p.getDescription() + "\n";
  }
return toReturn;
}
}