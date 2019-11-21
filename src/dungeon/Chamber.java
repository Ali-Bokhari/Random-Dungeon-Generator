package dungeon;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.die.D20;
import dnd.die.Percentile;
import java.util.ArrayList;

public class Chamber extends Space {

  /**
  * Holds ChamberContents object.
  */
  private ChamberContents myContents;

  /**
  * Holds ChamberShape object.
  */
  private ChamberShape mySize;

  /**
  * Holds the list of door.
  */
  private ArrayList<Door> doors;

  /**
  * Holds the list of monsters.
  */
  private ArrayList<Monster> monsters;

  /**
  * Holds the list of treasures.
  */
  private ArrayList<Treasure> treasures;

/**
* Creates Chamber object.
*/
public Chamber() {
  D20 roller = new D20();
  this.setShape(ChamberShape.selectChamberShape(roller.roll()));
  this.myContents = new ChamberContents();
  this.doors = new ArrayList<>();
  this.treasures = new ArrayList<>();
  this.monsters = new ArrayList<>();
  //this.mySize.setShape();
  this.mySize.setNumExits();
  fillContent();
}

/**
* Creates chamber object based on provided shape and contents.
*
* @param theShape ChamberShape object.
* @param theContents ChamberContents object.
*/
public Chamber(ChamberShape theShape, ChamberContents theContents) {
  this.setShape(theShape);
  this.myContents = theContents;
  this.doors = new ArrayList<>();
  this.treasures = new ArrayList<>();
  this.monsters = new ArrayList<>();
  fillContent();
}

/**
* Sets the shape.
*
* @param theShape ChamberShape object to set.
*/
public void setShape(ChamberShape theShape) {
  this.mySize = theShape;
  createDoors();
}

/**
* Returns chamber shape object.
*
* @return ChamberShape object
*/
public ChamberShape getShape() {
  return this.mySize;
}

/**
* Retuns chamber contents object.
*
* @return ChamberContents object
*/
public ChamberContents getContents() {
  return this.myContents;
}

/**
* Get list of doors.
*
* @return Arraylist of doors.
*/
public ArrayList<Door> getDoors() {
  return this.doors;
}

public void addDoor() {
  this.doors.add(new Door());
}

/**
* Add a monster.
*
* @param theMonster Monster to add.
*/
public void addMonster(Monster theMonster) {
  this.monsters.add(theMonster);
}

/**
* Get list of monsters.
*
* @return Arraylist of monsters.
*/
public ArrayList<Monster> getMonsters() {
  return this.monsters;
}

/**
* Add treasure to the chamber.
*
* @param theTreasure treasure to add.
*/
public void addTreasure(Treasure theTreasure) {
  this.treasures.add(theTreasure);
}

/**
* Get list of treasures.
*
* @return ArrayList of treasures.
*/
public ArrayList<Treasure> getTreasureList() {
  return this.treasures;
}


/**
* Produces a string representation of the chamber.
*
* @return String representation.
*/
@Override
public String getDescription() {
  String toReturn = "Chamber shape:\n" + this.mySize.getShape() + "\n";
  for (int i = 0; i < this.mySize.getNumExits(); i++) {
    Door dooor = new Door();
    toReturn += dooor.getDescription() + "\n";
  }
  toReturn += "\nChamber contents:\n" + this.myContents.getDescription() + " ";
  for (Monster m: this.monsters) {
  toReturn += m.getDescription() + "\n";
  }
  for (Treasure t: this.treasures) {
  toReturn += t.getDescription() + "\n";
  }
  return toReturn;
}

/**
* Make a door connection to this room.
*
* @param newDoor Door to add.
*/
@Override
public void setDoor(Door newDoor) {
  //should add a door connection to this room
  this.doors.add(newDoor);
  newDoor.getSpaces().add(this);
}

/**
* Create monsters or treasure based on the ChamberContents description.
*/
  private void fillContent() {
  D20 roller = new D20();
  while (!contentsResolver(this.myContents.getDescription())) {
  this.myContents.chooseContents(roller.roll());
  }
  createDoors();
  }

  /**
  * Create doors based on the chambers exits.
  */
  private void createDoors() {
    this.doors = new ArrayList<>();
    for (int i = 0; i < this.mySize.getNumExits(); i++) {
      Door dooor = new Door();
      dooor.getSpaces().add(this);
      this.doors.add(dooor);
    }
  }

  /**
  * Checks what content was returned from the content generator functions and calls the corresponding specific content functions.
  *
  * @param content Response from the content generator functions.
  *
  * @return Response from the specfic contents based on the input.
  */
  private Boolean contentsResolver(String content) {
  Percentile d100 = new Percentile();
  if (content.equals("treasure")) {
  Treasure t = new Treasure();
  t.chooseTreasure(d100.roll());
  this.addTreasure(t);
  return true;
  } else if (content.equals("monster and treasure")) {
  Treasure t = new Treasure();
  Monster m = new Monster();
  t.chooseTreasure(d100.roll());
  m.setType(d100.roll());
  this.addTreasure(t);
  this.addMonster(m);
  return true;
  } else if (content.equals("monster only")) {
  Monster m = new Monster();
  m.setType(d100.roll());
  this.addMonster(m);
  return true;
  }
  return false;
  }
}
