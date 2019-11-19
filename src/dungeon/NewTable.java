package dungeon;

import dnd.die.D20;

public class NewTable {

  /**
  * Roll a 20 sided dice to determine where to move.
  *
  * @return Instructions for next step.
  */
  public String roll() {
    D20 dice = new D20();
    int rolled = dice.roll();
    if (rolled > 0 && rolled < 3) {
      return "passage goes straight for 10 ft";
    } else if (rolled < 6) {
      return "passage ends in Door to a Chamber";
    } else if (rolled < 8) {
      return "archway (door) to right (main passage continues straight for 10 ft)";
    } else if (rolled < 10) {
      return "archway (door) to left (main passage continues straight for 10 ft)";
    } else if (rolled < 12) {
      return "passage turns to left and continues for 10 ft";
    } else if (rolled < 14) {
      return "passage turns to right and continues for 10 ft";
    } else if (rolled < 17) {
      return "passage ends in archway (door) to chamber";
    } else if (rolled < 18) {
      return "Stairs, (passage continues straight for 10 ft)";
    } else if (rolled < 20) {
      return "Dead End";
    } else if (rolled < 21) {
      return "Wandering Monster (passage continues straight for 10 ft)";
    }
    return null;
  }
}
