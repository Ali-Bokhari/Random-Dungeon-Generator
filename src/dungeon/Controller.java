package dungeon;

import java.util.ArrayList;

final class Controller {

  private Controller() {
    Level newLevel = new Level();
    ArrayList<Space> spaces = newLevel.generate();
  }

  public static void main(String[] args) {
    Controller newController = new Controller();
  }
}
