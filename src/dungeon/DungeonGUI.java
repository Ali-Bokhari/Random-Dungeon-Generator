package dungeon;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.TextArea;
import java.io.*;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundFill;
import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;

public class DungeonGUI extends Application {
    //Model
    ArrayList<Space> basicSpaces;
    ObservableList<Space> spaces;
    ObservableList<Door> doorList;
    private ListView<Space> spacesListView;
    private ListView<Door> doorListView;
    private Button addButton;
    private Button graphic;
    //private GridPane view;
    private TextArea descriptionText;
    public DungeonGUI(){
        initChamber();
        spacesListView = new ListView<Space>(spaces);
        doorList = FXCollections.observableArrayList();
        doorListView = new ListView<Door>(doorList);
        addButton = new Button("Edit");
        descriptionText = new TextArea();
        descriptionText.setEditable(false);
        graphic = new Button("View");
        //descriptionText.setPrefS(250);
    }
    public void initChamber() {

        basicSpaces = new ArrayList<>();
        Level newLevel = new Level();
        basicSpaces = newLevel.generate();
        spaces = FXCollections.observableArrayList(basicSpaces);
    }
    @Override
    public void start(Stage stage) throws Exception {
        HBox rootNode = new HBox(20);
        VBox leftNode = new VBox(10);
        HBox buttons = new HBox(20);
        Menu fileMenu = new Menu("File");
        MenuBar menubar = new MenuBar();
        menubar.getMenus().add(fileMenu);
        MenuItem m1 = new MenuItem("Save File");
        MenuItem m2 = new MenuItem("Load File");
        fileMenu.getItems().add(m1);
        fileMenu.getItems().add(m2);
        buttons.getChildren().addAll(addButton, graphic);
        buttons.setPadding(new Insets(10));
        leftNode.getChildren().addAll(menubar, spacesListView, buttons);
        rootNode.getChildren().addAll(leftNode,descriptionText,doorListView);
        Scene scene = new Scene(rootNode, 1000, 400);

        spacesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        spacesListView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            doorList.clear();
            doorList.addAll(spacesListView.getSelectionModel().getSelectedItem().getDoors());
            descriptionText.setText(spacesListView.getSelectionModel().getSelectedItem().getDescription());
        });

        doorListView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Stage doorStage = new Stage();
            TextArea doorText = new TextArea();
            HBox doorHbox = new HBox(20);
            doorHbox.getChildren().add(doorText);
            doorStage.setTitle("Door description");
            doorText.setText(doorListView.getSelectionModel().getSelectedItem().getDescription());
            //doorList.addAll(spacesListView.getSelectionModel().getSelectedItem().getDoors());
            //descriptionText.setText(spacesListView.getSelectionModel().getSelectedItem().getDescription());
            if(doorListView.getSelectionModel().getSelectedItem().getSpaces().size() > 1) {
              //ArrayList<Space> temptemp = new ArrayList<>(spacesListView.getItems());
              Button gotonext = new Button("View path");
              doorHbox.getChildren().add(gotonext);
              gotonext.setOnAction(e->{
                  spacesListView.getSelectionModel().selectIndices(spacesListView.getSelectionModel().getSelectedIndices().get(0) + 1);
                  graphic.fire();
              });
            }
            Scene doorScene = new Scene(doorHbox);
            doorStage.setScene(doorScene);
            doorStage.show();
        });

        m1.setOnAction(e->{
            saveState(stage);
        });

        m2.setOnAction(e->{
            loadState(stage);
        });

        addButton.setOnAction(e->{
            EditStage editStage = new EditStage(spacesListView);
            editStage.show();
        });

        graphic.setOnAction(e->{
            Stage viewStage = new Stage();
            viewStage.setTitle("View");
            GridPane pane = new GridPane();
            buildGraphic(pane);
            //pane.setPadding(new Insets(10));
            Scene viewScene = new Scene(pane, 500, 500);
            viewStage.setScene(viewScene);
            viewStage.show();
        });

        stage.setTitle("Dungeon");
        stage.setScene(scene);
        stage.show();
    }

    public void saveState(Stage stage) {
      try {
        FileChooser choose = new FileChooser();
        File file = choose.showSaveDialog(stage);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        this.spaces = this.spacesListView.getItems();
        this.basicSpaces = new ArrayList<Space>(this.spaces);
        out.writeObject(this.basicSpaces);
        out.close();
      }
      catch (IOException e) {

      }
    }

    public void loadState(Stage stage) {
      try
        {
          FileChooser choose = new FileChooser();
          File file = choose.showOpenDialog(stage);
          ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
          this.basicSpaces = (ArrayList<Space>)in.readObject();
          this.spaces = FXCollections.observableArrayList(this.basicSpaces);
          this.spacesListView.setItems(this.spaces);
          in.close();
        }

        catch(IOException ex)
        {
          System.out.println(ex);
        }

        catch(ClassNotFoundException ex)
        {
          System.out.println(ex);
        }
    }

    public void buildGraphic( GridPane view ) {
      if (spacesListView.getSelectionModel().getSelectedItem() instanceof Passage) {
        buildGraphicPassage(view, (Passage)spacesListView.getSelectionModel().getSelectedItem());
        return;
      }
      view.setBackground( new Background(new BackgroundImage(new Image("res/back3.png"), null, null, null, null)));
      Image image = new Image("res/door.png");
      int i = 1;
      for (Door d: spacesListView.getSelectionModel().getSelectedItem().getDoors()) {
        ImageView pic = new ImageView(image);
        pic.setFitWidth(70);
        pic.setFitHeight(70);
        pic.setOnMouseClicked((MouseEvent e) -> {
          Stage doorStage = new Stage();
          TextArea doorText = new TextArea();
          doorStage.setTitle("Door description");
          Scene doorScene = new Scene(doorText);
          doorText.setText(d.getDescription());
          doorStage.setScene(doorScene);
          doorStage.show();
        });
        if(i%2==0) {
          view.add(pic,i+1,0);
        } else {
          view.add(pic,0,i-1);
        }
        i++;
      }
      i = 4;
      image = new Image("res/monster.png");
      for (Monster m: spacesListView.getSelectionModel().getSelectedItem().getMonstersO()) {
        ImageView pic = new ImageView(image);
        pic.setFitWidth(70);
        pic.setFitHeight(70);
        pic.setOnMouseClicked((MouseEvent e) -> {
          Stage doorStage = new Stage();
          TextArea doorText = new TextArea();
          doorStage.setTitle("Monster description");
          Scene doorScene = new Scene(doorText);
          doorText.setText(m.getDescription());
          doorStage.setScene(doorScene);
          doorStage.show();
        });
        view.add(pic,i/2,i);
        i = i + 3;
      }
      i = 2;
      image = new Image("res/treasure.png");
      for (Treasure m: spacesListView.getSelectionModel().getSelectedItem().getTreasures()) {
        ImageView pic = new ImageView(image);
        pic.setFitWidth(70);
        pic.setFitHeight(70);
        pic.setOnMouseClicked((MouseEvent e) -> {
          Stage doorStage = new Stage();
          TextArea doorText = new TextArea();
          doorStage.setTitle("Treasure description");
          Scene doorScene = new Scene(doorText);
          doorText.setText(m.getDescription());
          doorStage.setScene(doorScene);
          doorStage.show();
        });
        view.add(pic,i,i/2);
        i = i + 3;
      }
    }

    public void buildGraphicPassage( GridPane view, Passage p ) {
      view.setBackground( new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
      String temp;
      Boolean inv = false;
      int i=0,j=10,l;

      Image image = new Image("res/back3.png");
      Image monsterimage = new Image("res/monster.png");
      Image treasureimage = new Image("res/treasure.png");
      Image doorimage = new Image("res/door.png");
      for (PassageSection ps: p.getPassage()) {
        temp = ps.getRollDescription();
        if(temp.contains("straight")){
          ImageView pic = new ImageView(image);
          pic.setFitWidth(80);
          pic.setFitHeight(80);
          view.add(pic, i, j);
          if(ps.getMonster() != null) {
            ImageView monsterview = new ImageView(monsterimage);
            monsterview.setFitWidth(80);
            monsterview.setFitHeight(80);
            view.add(monsterview, i, j);
          }
          //if(inv) {
            //j++;
          //} else {
            i++;
          //}
          pic = new ImageView(image);
          pic.setFitWidth(80);
          pic.setFitHeight(80);
          view.add(pic, i, j);
          if(ps.getTreasure() != null) {
            ImageView treasureview = new ImageView(treasureimage);
            treasureview.setFitWidth(80);
            treasureview.setFitHeight(80);
            view.add(treasureview, i, j);
          }
        } else if(temp.contains("turns to left")) {
          ImageView pic = new ImageView(image);
          pic.setFitWidth(80);
          pic.setFitHeight(80);
          j--;
          view.add(pic, i, j);
          if(ps.getMonster() != null) {
            ImageView monsterview = new ImageView(monsterimage);
            monsterview.setFitWidth(80);
            monsterview.setFitHeight(80);
            view.add(monsterview, i, j);
          }
          j--;
          pic = new ImageView(image);
          pic.setFitWidth(80);
          pic.setFitHeight(80);
          view.add(pic, i, j);
          if(ps.getTreasure() != null) {
            ImageView treasureview = new ImageView(treasureimage);
            treasureview.setFitWidth(80);
            treasureview.setFitHeight(80);
            view.add(treasureview, i, j);
          }
        } else if(temp.contains("turns to right")) {
          ImageView pic = new ImageView(image);
          pic.setFitWidth(80);
          pic.setFitHeight(80);
          j++;
          view.add(pic, i, j);
          if(ps.getMonster() != null) {
            ImageView monsterview = new ImageView(monsterimage);
            monsterview.setFitWidth(80);
            monsterview.setFitHeight(80);
            view.add(monsterview, i, j);
          }
          pic = new ImageView(image);
          pic.setFitWidth(80);
          pic.setFitHeight(80);
          j++;
          view.add(pic, i, j);
          if(ps.getTreasure() != null) {
            ImageView treasureview = new ImageView(treasureimage);
            treasureview.setFitWidth(80);
            treasureview.setFitHeight(80);
            view.add(treasureview, i, j);
          }
        } else {
          ImageView pic = new ImageView(image);
          ImageView pic2 = new ImageView(doorimage);
          pic.setFitWidth(80);
          pic.setFitHeight(80);
          pic2.setFitWidth(80);
          pic2.setFitHeight(80);
          i++;
          view.add(pic, i, j);
          if(ps.getMonster() != null) {
            ImageView monsterview = new ImageView(monsterimage);
            monsterview.setFitWidth(80);
            monsterview.setFitHeight(80);
            view.add(monsterview, i, j);
          }
          if(ps.getTreasure() != null) {
            ImageView treasureview = new ImageView(treasureimage);
            treasureview.setFitWidth(80);
            treasureview.setFitHeight(80);
            view.add(treasureview, i, j);
          }
          view.add(pic2, i, j);
        }
      }
    }

    public static void main(String [] args) {
        Application.launch(args);
    }

}
