package view;

import controller.Controller;
import interfaces.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Playlist;

import java.net.URL;


public class View extends BorderPane {

    private Controller contr;
    private ListView<Song> listview = new ListView<Song>();
    private ListView<Song> libraryview = new ListView<Song>();

    //private Button buttonAdd = new Button("Add all");
    //private Button buttonDelete = new Button("Delete");
    //private TextField input = new TextField();

    //Metadata - Right
    private Label title = new Label("Title:");
    private TextField titleInput = new TextField();
    private Label interpret = new Label("Interpret:");
    private TextField interpretInput = new TextField();
    private Label album = new Label("Album:");
    private TextField albumInput = new TextField();

    //Metadata controls (to-do: use icons from assets instead)
    private Button playButton = new Button("Play");
    private Button pauseButton = new Button("Pause");
    private Button nextButton = new Button("Next");
    private Button commitButton = new Button("Commit");
    private Button deleteButton = new Button("Delete");
    private Button addToPlaylistButton = new Button("Add to Playlist");

    //Top
    private ComboBox dropdown = new ComboBox();
    private Button loadButton = new Button("Load");
    private Button saveButton = new Button("Save");
    private Text playTime = new Text("0:14");

    //Bottom
    private Button addAllButton = new Button("Add all");

    public View(){

        //Right
        //To-do: use icons from assets for controls instead
        //Image pauseImg = new Image("assets/pause.png");
        HBox controls = new HBox(playButton, pauseButton, nextButton, commitButton);
        controls.setSpacing(10);
        Insets paddingMeta = new Insets(4, 2, 4,2);
        controls.setPadding(paddingMeta);

        addToPlaylistButton.setMinWidth(100);
        HBox addToPlaylistControl = new HBox(addToPlaylistButton);
        addToPlaylistControl.setPadding(new Insets(8,0,8,2));
        addToPlaylistControl.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 0.1;" + "-fx-border-color: black;");
        VBox metadata = new VBox(title, titleInput, interpret, interpretInput, album, albumInput, controls, addToPlaylistControl, deleteButton);


        //Top
        dropdown.setMinWidth(300);
        HBox borderTop = new HBox(dropdown, loadButton, saveButton, playTime);
        borderTop.setPadding(paddingMeta);
        borderTop.setSpacing(10);

        //Bottom
        addAllButton.setMinWidth(60);
        HBox bottomPane = new HBox(addAllButton);
        bottomPane.setPadding(paddingMeta);

        //BorderPane layout
        setTop(borderTop);
        setCenter(listview);
        setBottom(bottomPane);
        setLeft(libraryview);
        setRight(metadata);

        //Actions
        libraryview.setOnMouseClicked(e -> {
            if(!libraryview.getProperties().isEmpty()) {
                titleInput.setText(libraryview.getSelectionModel().getSelectedItem().getTitle());
                albumInput.setText(libraryview.getSelectionModel().getSelectedItem().getAlbum());
                interpretInput.setText(libraryview.getSelectionModel().getSelectedItem().getInterpret());
            }
        });

        listview.setOnMouseClicked(e -> {
            if(!listview.getProperties().isEmpty()){
                titleInput.setText(listview.getSelectionModel().getSelectedItem().getTitle());
                albumInput.setText(listview.getSelectionModel().getSelectedItem().getAlbum());
                interpretInput.setText(listview.getSelectionModel().getSelectedItem().getInterpret());
            }

        });
        commitButton.setOnAction(e ->{
            contr.changeSongProperties(libraryview.getSelectionModel().getSelectedItem(),titleInput.getText(),albumInput.getText(),interpretInput.getText());
        });
        addAllButton.setOnAction(e->{
            contr.addAllToPlaylist();
        });

        addToPlaylistButton.setOnAction(e ->{
            contr.addToPlaylist(libraryview.getSelectionModel().getSelectedItem());
            System.out.println(libraryview.getSelectionModel().getSelectedItem());
        } );

        deleteButton.setOnAction(e ->{contr.deleteSongFromPlaylist(listview.getSelectionModel().getSelectedItem());});

        //Cell Factory
        libraryview.setCellFactory(c -> {

			ListCell<Song> cell = new ListCell<Song>() {
                @Override
                protected void updateItem(Song myObject, boolean b) {
                    super.updateItem(myObject, myObject == null || b);
                    if (myObject != null) {
                        setText(myObject.toString());
                    } else {
                        setText("");
                    }
                }

            };
			return cell;

          });

        listview.setCellFactory(c -> {

            ListCell<Song> cell = new ListCell<Song>() {
                @Override
                protected void updateItem(Song myObject, boolean b) {
                    super.updateItem(myObject, myObject == null || b);
                    if (myObject != null) {
                        setText(myObject.toString());
                    } else {
                        setText("");
                    }
                }

            };
            return cell;

        });
    }


    public ListView<Song> getList() {

        return libraryview;
    }
    public ListView<Song> getPlaylist(){
        return listview;
    }
    public void setList(ListView<Song> list) {

        this.libraryview = list;
    }
    public void addController(Controller contr){

        this.contr = contr;
    }
}
