package view;

import controller.Controller;
import interfaces.Song;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.rmi.RemoteException;


public  class View extends BorderPane implements Serializable {

    private Controller contr;
    private ListView<Song> listview = new ListView<>();
    private ListView<Song> libraryview = new ListView<>();


    //Metadata - Right
    private Label title = new Label("Title:");
    private TextField titleInput = new TextField();
    private Label interpret = new Label("Interpret:");
    private TextField interpretInput = new TextField();
    private Label album = new Label("Album:");
    private TextField albumInput = new TextField();

    //Metadata controls (to-do: use icons from assets instead
    private ToggleGroup controlGroup = new ToggleGroup();
    private ToggleButton playButton = new ToggleButton();
    private ToggleButton pauseButton = new ToggleButton();
    private Button nextButton = new Button();
    private Button commitButton = new Button("Commit");
    private Button deleteButton = new Button("Delete");
    private Button addToPlaylistButton = new Button("Add to Playlist");

    //Top
    private ComboBox dropdown = new ComboBox();
    private Button loadButton = new Button("Load");
    private Button saveButton = new Button("Save");

    // Playtime
    private Text playTime = new Text("00:00 / 00:00");

    //Bottom
    private Button addAllButton = new Button("Add all");
    private Button directoryButton = new Button("Add Songs from Folder");

    //Selected song variable
    private interfaces.Song selectedSong = null;

    private ImageView setIcon(Image img){
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(10);
        imgView.setFitWidth(10);
        return imgView;

    }

    public View(){

        //Right
        //Define Icons for Buttons
        Image nextImg  = null;
        Image playImg  = null;
        Image pauseImg  = null;
        try {
            nextImg = new Image( new FileInputStream("src/main/resources/assets/next.png"));
            pauseImg = new Image( new FileInputStream("src/main/resources/assets/pause.png"));
            playImg = new Image( new FileInputStream("src/main/resources/assets/play.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //PLay/Pause Buttons as Togglegroup
        nextButton.setGraphic(this.setIcon(nextImg));
        playButton.setGraphic(this.setIcon(playImg));
        pauseButton.setGraphic(this.setIcon(pauseImg));
        playButton.setToggleGroup(controlGroup);
        pauseButton.setToggleGroup(controlGroup);
        playButton.setSelected(false);
        pauseButton.setSelected(false);


        HBox controls = new HBox(playButton, pauseButton , nextButton, commitButton);
        controls.setSpacing(10);
        Insets paddingMeta = new Insets(4, 2, 4,2);
        controls.setPadding(paddingMeta);

        addToPlaylistButton.setMinWidth(100);
        HBox addToPlaylistControl = new HBox(addToPlaylistButton, deleteButton);
        addToPlaylistControl.setSpacing(10);
        addToPlaylistControl.setPadding(new Insets(8,0,8,2));
        addToPlaylistControl.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 0.1;" + "-fx-border-color: black;");
        VBox metadata = new VBox(title, titleInput, interpret, interpretInput, album, albumInput, controls, addToPlaylistControl);


        //Top
        dropdown.setMinWidth(300);
        HBox borderTop = new HBox(dropdown, loadButton, saveButton, playTime);
        borderTop.setPadding(paddingMeta);
        borderTop.setSpacing(10);

        //Bottom
        addAllButton.setMinWidth(60);
        HBox bottomPane = new HBox(addAllButton,directoryButton);
        bottomPane.setPadding(paddingMeta);

        //BorderPane layout
        setTop(borderTop);
        setCenter(listview);
        setBottom(bottomPane);
        setLeft(libraryview);
        setRight(metadata);

        //Actions
        libraryview.setOnMouseClicked(e -> {
            if(!libraryview.getProperties().isEmpty() ) {
                titleInput.setText(libraryview.getSelectionModel().getSelectedItem().getTitle());
                albumInput.setText(libraryview.getSelectionModel().getSelectedItem().getAlbum());
                interpretInput.setText(libraryview.getSelectionModel().getSelectedItem().getInterpret());
                selectedSong = libraryview.getSelectionModel().getSelectedItem();
            }
        });

        listview.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2){
                try {
                    contr.play();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
            if(!listview.getProperties().isEmpty()){
                titleInput.setText(listview.getSelectionModel().getSelectedItem().getTitle());
                albumInput.setText(listview.getSelectionModel().getSelectedItem().getAlbum());
                interpretInput.setText(listview.getSelectionModel().getSelectedItem().getInterpret());
                selectedSong = listview.getSelectionModel().getSelectedItem();
            }

        });

        commitButton.setOnAction(e ->{
            try {
                contr.changeSongProperties(selectedSong, titleInput.getText(), albumInput.getText(), interpretInput.getText());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            listview.refresh();
            libraryview.refresh();
        });

        addAllButton.setOnAction(e-> {
            try {
                contr.addAllToPlaylist();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        directoryButton.setOnAction(e ->{
            DirectoryChooser d = new DirectoryChooser();
            d.setInitialDirectory(new File("src/main/resources/songs"));
            try {
                File f = d.showDialog(this.getScene().getWindow());
                if(f != null){
                    contr.addSongsFromFolder(f.getCanonicalPath());
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        addToPlaylistButton.setOnAction(e ->{
            try {
                contr.addToPlaylist();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            listview.refresh();
        } );

        deleteButton.setOnAction(e ->{
            try {
                contr.deleteSongFromPlaylist();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            listview.refresh();
        });

        playButton.setOnAction(e -> {
            try {
                contr.play();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        pauseButton.setOnAction(e -> {
            try {
                contr.pause();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        nextButton.setOnAction( e -> {
            try {
                contr.next();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        //Cell Factory
        libraryview.setCellFactory(c -> {

			ListCell<Song> cell = new ListCell<>() {
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

            ListCell<Song> cell = new ListCell<>() {
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

        dropdown.setOnAction(e -> {
            try {
                contr.selectStrategy();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        loadButton.setOnAction(e -> {
            try {
                contr.load();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        saveButton.setOnAction(e -> {
            try {
                contr.save();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
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

    public ComboBox getDropdown() {
        return dropdown;
    }

    public Text getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Text playTime) {
        this.playTime = playTime;
    }

    public ToggleButton getPlayButton() {
        return playButton;
    }

    public ToggleButton getPauseButton() {
        return pauseButton;
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

}
