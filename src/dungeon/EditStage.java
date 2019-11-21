package dungeon;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import dnd.die.Percentile;
import dnd.models.Monster;
import dnd.models.Treasure;

public class EditStage extends javafx.stage.Stage {

  private Scene editScene;
  private Button monsterButton;
  private Button treasureButton;
  private Button doorButton;
  private VBox edits;
  public EditStage(ListView<Space> spacesListView) {
    super();
    edits = new VBox(10);
    monsterButton = new Button("Add Monster");
    treasureButton = new Button("Add Treasure");
    doorButton = new Button("Add Door");

    edits.getChildren().addAll(monsterButton, treasureButton, doorButton);

    Percentile d100 = new Percentile();

    monsterButton.setOnAction(e->{
      Monster m = new Monster();
      m.setType(d100.roll());
      spacesListView.getSelectionModel().getSelectedItem().addMonster(m);
    });

    treasureButton.setOnAction(e->{
      Treasure t = new Treasure();
      t.chooseTreasure(d100.roll());
      spacesListView.getSelectionModel().getSelectedItem().addTreasure(t);
    });

    doorButton.setOnAction(e->{
        spacesListView.getSelectionModel().getSelectedItem().addDoor();
    });

    editScene = new Scene(edits);
    this.setScene(editScene);
  }
}
