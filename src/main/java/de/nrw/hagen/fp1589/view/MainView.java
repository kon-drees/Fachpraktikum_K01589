package de.nrw.hagen.fp1589.view;

import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainView extends Application {


    private TreeViewShow frmTree;
    private ListView<String> list;

    @Override
    public void start(Stage stage) throws Exception {
        Button button = new Button("Baumdatei auswählen");
        button.setLayoutX(30);
        button.setLayoutY(20);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    if (list.getSelectionModel().getSelectedItem() != null) {
                        Scene sc = frmTree.start(list.getSelectionModel().getSelectedItem());
                        stage.setScene(sc);
                        stage.setTitle("Baumansicht");
                    }
                    else {
                        button.setText("Bitte in der Liste eine Datei auswählen");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        button.setOnAction(event);





        ObservableList<String> items = FXCollections.observableArrayList();


        final File f = new File("src/main/resources/");
        File[] fileArray = f. listFiles();
        for (File fname : fileArray) {
            items.add(fname.getName());
        }

        list = new ListView();

        list.setLayoutX(300);
        list.setLayoutY(20);

        list.setItems(items);

        list.setMaxHeight(225);



        List<Node> controls = new ArrayList<>();

        controls.add(button);
        controls.add(list);
        Group root = new Group(controls);

        Scene scene = new Scene(root, 900, 500);

        //Setting title to the Stage
        stage.setTitle("Hauptmenü");

        //Adding scene to the stage
        stage.setScene(scene);

        this.frmTree = new TreeViewShow(stage);
        this.frmTree.setMainScene(scene);
        //Displaying the contents of the stage
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
