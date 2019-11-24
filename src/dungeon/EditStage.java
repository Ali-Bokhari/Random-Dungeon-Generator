package dungeon;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
//import dnd.die.Percentile;
import dnd.models.Monster;
import dnd.models.Treasure;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EditStage extends javafx.stage.Stage {

  private Scene editScene;
  private Button monsterButton;
  private Button treasureButton;
  private Button removeMonster;
  private Button removeTreasure;
  //private Button doorButton;
  private VBox mainBox;
  private VBox edits;
  private HBox monsterHbox;
  private HBox hedits;
  private VBox monsterListVbox;
  private VBox treasureListVbox;
  private ObservableList<String> monsterList;
  private ListView<String> monsters;
  private Spinner<Integer> monsterSpinner;
  private Label monsterLabel;
  private HBox treasureHbox;
  private ObservableList<String> treasureList;
  private ListView<String> treasures;
  private Spinner<Integer> treasureSpinner;
  private Label treasureLabel;
  private GridPane pane;
  private ListView<Space> spacesListView;
  public EditStage(ListView<Space> SListView) {
    super();
    this.spacesListView = SListView;
    mainBox = new VBox(20);
    edits = new VBox(10);
    edits.setPadding(new Insets(10));
    monsterHbox = new HBox(10);
    treasureHbox = new HBox(10);
    hedits = new HBox(20);
    monsterListVbox = new VBox(5);
    monsterListVbox.setAlignment(Pos.CENTER);
    treasureListVbox = new VBox(5);
    treasureListVbox.setAlignment(Pos.CENTER);
    pane = buildGraphic();
    monsterLabel = new Label("Pick monster with 1-100 roll");
    treasureLabel = new Label("Pick treasure with 1-100 roll");
    monsterButton = new Button("Add Monster");
    treasureButton = new Button("Add Treasure");
    removeMonster = new Button("Remove");
    removeTreasure = new Button("Remove");
    //doorButton = new Button("Add Door");
    monsterList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getMonsters());
    monsters = new ListView<String>(monsterList);
    monsterSpinner = new Spinner<Integer>(1, 100, 0);
    treasureList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getTreasureList());
    treasures = new ListView<String>(treasureList);
    treasureSpinner = new Spinner<Integer>(1, 100, 0);

    monsterListVbox.getChildren().addAll(monsters, removeMonster);
    treasureListVbox.getChildren().addAll(treasures, removeTreasure);
    monsterHbox.getChildren().addAll(monsterSpinner, monsterButton);
    treasureHbox.getChildren().addAll(treasureSpinner, treasureButton);
    edits.getChildren().addAll(monsterLabel, monsterHbox, treasureLabel, treasureHbox);
    hedits.getChildren().addAll(edits, monsterListVbox, treasureListVbox);
    mainBox.getChildren().addAll(hedits, pane);


    //Percentile d100 = new Percentile();

    monsterButton.setOnAction(e->{
      Monster m = new Monster();
      m.setType(monsterSpinner.getValue());
      spacesListView.getSelectionModel().getSelectedItem().addMonster(m);
      monsterList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getMonsters());
      monsters.setItems(monsterList);
    });

    treasureButton.setOnAction(e->{
      Treasure t = new Treasure();
      t.chooseTreasure(treasureSpinner.getValue());
      spacesListView.getSelectionModel().getSelectedItem().addTreasure(t);
      treasureList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getTreasureList());
      treasures.setItems(treasureList);
    });

    removeMonster.setOnAction(e->{
      spacesListView.getSelectionModel().getSelectedItem().removeMonster(monsters.getSelectionModel().getSelectedIndices().get(0));
      monsterList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getMonsters());
      monsters.setItems(monsterList);
    });

    removeTreasure.setOnAction(e->{
      spacesListView.getSelectionModel().getSelectedItem().removeTreasure(treasures.getSelectionModel().getSelectedIndices().get(0));
      treasureList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getTreasureList());
      treasures.setItems(treasureList);
    });

    //doorButton.setOnAction(e->{
        //spacesListView.getSelectionModel().getSelectedItem().addDoor();
    //});

    editScene = new Scene(mainBox);
    this.setScene(editScene);
  }

  public GridPane buildGraphic() {
    GridPane view = new GridPane();
    Image image = new Image("res/door.png");

    for (int i=0; i<5; i++) {
      ImageView pic = new ImageView(image);
      pic.setFitWidth(50);
      pic.setFitHeight(50);
      view.add(pic,i,0);
    }
    return view;
  }


}
