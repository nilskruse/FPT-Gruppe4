package view;

import controller.Controller;
import interfaces.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class View extends BorderPane {
    private ObservableList<Song> list = FXCollections.observableArrayList();
    private Controller contr;
    private ListView<Song> listview = new ListView<Song>();

    private Button buttonAdd = new Button();
    private Button buttonDelete = new Button("Delete");
    private TextField input = new TextField();

    public View(){
        HBox box = new HBox(input, buttonAdd,buttonDelete);
        setCenter(listview);
        setBottom(box);
        buttonAdd.setOnAction(e->{contr.add(new model.Song());});
    }


    public ObservableList<Song> getList() {
        return list;
    }

    public void setList(ObservableList<Song> list) {
        this.list = list;
    }
    public void addController(Controller contr){
        this.contr = contr;
    }
}
