package dungeon;

import java.util.ArrayList;

final class Level {

  /**
  * Random generates a level consisting of passages connecting 5 chambers.
  *
  * @return All dungeon spaces in order
  */
  public ArrayList<Space> generate() {
    int numChamber = 0;
    NewTable d20 = new NewTable();
    Passage newPassage;
    Chamber newChamber = new Chamber();
    String rolled;
    ArrayList<Space> spaces = new ArrayList<Space>();
    while (numChamber < 5) {
      newPassage = new Passage();
      if (numChamber != 0) {
        //System.out.println("|\nv");
        newChamber.getDoors().get(0).setSpaces(newChamber, newPassage);
      }
      PassageSection p = new PassageSection();
      while (!p.getChamber()) {
        //String rolled = d20.roll();
        //System.out.println(rolled);
        rolled = d20.roll();
        while (rolled.equals("Dead End")) {
          rolled = d20.roll();
        }
        p = new PassageSection(rolled);
        newPassage.addPassageSection(p);
        //System.out.println(p.getDescription());
      }
      newChamber = new Chamber();
      p.getDoor().setSpaces(newPassage, newChamber);
      numChamber++;
      spaces.add(newPassage);
      spaces.add(newChamber);
      //System.out.println("************************\n" + newPassage.getDescription() + "************************\n|\nv\n************************\n" + newChamber.getDescription() + "************************");
    }
    for (Space s: spaces) {
      System.out.println(s.getDescription());
    }
    return spaces;
  }
}
