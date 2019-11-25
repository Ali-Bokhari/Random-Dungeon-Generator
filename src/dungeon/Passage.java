package dungeon;

import dnd.models.Monster;
import dnd.models.Treasure;

import java.util.ArrayList;
/*
A passage begins at a door and ends at a door.  It may have many other doors along
the way

You will need to keep track of which door is the "beginning" of the passage
so that you know how to
*/

public class Passage extends Space{
  //these instance variables are suggestions only
  //you can change them if you wish.
  //private ArrayList<Treasure> treasures;
  private ArrayList<Door> doors;
  //private ArrayList<Monster> monsters;
  private static int amount = 0;
  private String name;
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
  this.doors = new ArrayList<>();
  //this.treasures = new ArrayList<>();
  //this.monsters = new ArrayList<>();
  this.amount++;
  name = "Passage " + this.amount;
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
public ArrayList<Door> getDoors() {
//gets all of the doors in the entire passage
//ArrayList<Door> doors = new ArrayList<>();
//for (PassageSection p: thePassage) {
  //if (p.getDoor() != null) {
  //doors.add(p.getDoor());
  //}
//}
return this.doors;
}

public void addDoor() {
  this.doors.add(new Door());
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

public void addTreasure(Treasure treasure) {
  for(PassageSection p: this.thePassage) {
    if(p.getTreasure() == null) {
      p.setTreasure(treasure);
      return;
    }
  }
}

public ArrayList<String> getTreasureList() {
  ArrayList<String> treasureS = new ArrayList<>();
  for (PassageSection p: this.thePassage) {
    if(p.getTreasure() != null) {
      treasureS.add(p.getTreasure().getDescription());
    }
  }
  return treasureS;
}

public ArrayList<Treasure> getTreasures() {
  ArrayList<Treasure> toReturn = new ArrayList<>();
  for(PassageSection p: this.thePassage) {
    if(p.getTreasure() != null) {
      toReturn.add(p.getTreasure());
    }
  }
  return toReturn;
}

public void removeTreasure(int n) {
  ArrayList<Treasure> toReturn = new ArrayList<>();
  int i = 0;
  for(PassageSection p: this.thePassage) {
    if(p.getTreasure() != null) {
      if(i==n) {
        p.setTreasure(null);
      }
      i++;
    }
  }
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

public void addMonster(Monster theMonster) {
  for(PassageSection p: this.thePassage) {
    if(p.getMonster() == null) {
      p.setMonster(theMonster);
      return;
    }
  }
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

public ArrayList<String> getMonsters() {
  ArrayList<String> monsterss = new ArrayList<>();

  for(PassageSection p: this.thePassage){
    if(p.getMonster() != null) {
      monsterss.add(p.getMonster().getDescription());
    }
  }

  return monsterss;
}

public ArrayList<Monster> getMonstersO() {
  ArrayList<Monster> monsterss = new ArrayList<>();

  for(PassageSection p: this.thePassage){
    if(p.getMonster() != null) {
      monsterss.add(p.getMonster());
    }
  }

  return monsterss;
}

public void removeMonster(int n) {
  ArrayList<Monster> toReturn = new ArrayList<>();
  int i = 0;
  for(PassageSection p: this.thePassage) {
    if(p.getMonster() != null) {
      if(i==n) {
        p.setMonster(null);
      }
      i++;
    }
  }
}

/**
* Add a new PassageSection to the passage.
*
* @param toAdd PassageSection to add.
*/
public void addPassageSection(PassageSection toAdd) {
  //adds the passage section to the passageway
  this.thePassage.add(toAdd);
  if (toAdd.getDoor() != null) {
    this.doors.add(toAdd.getDoor());
  }
  //if(toAdd.getMonster() != null) {
    //this.monsters.add(toAdd.getMonster());
  //}
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
  toReturn += "Content:\n";
  for(Monster m: this.getMonstersO()) {
    toReturn += m.getDescription() + "\n";
  }
  for(Treasure t: this.getTreasures()) {
    toReturn += t.getDescription() + "\n";
  }
return toReturn;
}

@Override
public String toString() {
  return this.name;
}

}
