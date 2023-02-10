package de.nrw.hagen.fp1589.view;

import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.domain.RuleApplicationNode;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeViewShow  {


    private Scene main;
    private Stage mainStage;



    public TreeViewShow(Stage stage) {
        this.mainStage = stage;
    }


    public  Scene start(String filename) throws Exception {
        ArgTree tree = ArgTreeReaderWriter.importTree(filename);

        List<Node> controls = new ArrayList<>();


        int x = 100;
        int childx = 80;
        int i = 0;
        for (Iterator<InformationNode> it = tree.getInformationNodes(); it.hasNext(); ) {
            InformationNode inode = it.next();
            TextArea text = new TextArea(inode.getClaimText());
            text.setEditable(false);
            text.setLayoutY(10.0);
            text.setMaxWidth(150);
            text.setMaxHeight(80);
            text.setWrapText(true);
            text.setLayoutX(x);
            //text.setAlignment(Pos.BASELINE_CENTER);
            controls.add(text);


            int cx = x + 70;
            int cy = 150;




            final Circle circle = new Circle(cx,cy, 20);
            circle.setStroke(Color.FORESTGREEN);
            circle.setStrokeWidth(2);
            circle.setStrokeType(StrokeType.INSIDE);
            circle.setFill(Color.AZURE);

            //circle.setLayoutX(200);
            //circle.setLayoutX(100);

            final Text textRA = new Text("RA");
            textRA.setBoundsType(TextBoundsType.VISUAL);
            textRA.setLayoutX(cx-7);
            textRA.setLayoutY(cy+5);

            controls.add(circle);
            controls.add(textRA);


            Arrow arrow = new Arrow();
            arrow.setStartX(x + 70);
            arrow.setStartY(90);
            arrow.setEndX(x + 70);
            arrow.setEndY(130);

            controls.add(arrow);

            RuleApplicationNode childNode = inode.getConclusionOfNodes().next();

            if (i==0 && (childNode.getDispreferredOf() != null || childNode.getPreferredOf() != null )) {
                i = 1;
                final Circle circlePA = new Circle(cx + 186,cy, 20);
                circlePA.setStroke(Color.CHARTREUSE);
                circlePA.setStrokeWidth(2);
                circlePA.setStrokeType(StrokeType.INSIDE);
                circlePA.setFill(Color.AZURE);

                final Text textPA = new Text("PA");
                textPA.setBoundsType(TextBoundsType.VISUAL);
                textPA.setLayoutX(cx+178);
                textPA.setLayoutY(cy+5);

                controls.add(circlePA);
                controls.add(textPA);


                arrow = new Arrow();
                arrow.setStartX(cx + 20);
                arrow.setStartY(cy);
                arrow.setEndX(cx + 170);
                arrow.setEndY(cy);

                controls.add(arrow);

                arrow = new Arrow();
                arrow.setStartX(cx + 200);
                arrow.setStartY(cy);
                arrow.setEndX(cx + 280);
                arrow.setEndY(cy);

                controls.add(arrow);
            }

            if (i==0 && (childNode.getConflictedOf() != null || childNode.getConflictingOf() != null )) {
                i = 1;
                final Circle circleCA = new Circle(cx + 186,cy, 20);
                circleCA.setStroke(Color.CHARTREUSE);
                circleCA.setStrokeWidth(2);
                circleCA.setStrokeType(StrokeType.INSIDE);
                circleCA.setFill(Color.AZURE);

                final Text textCA = new Text("CA");
                textCA.setBoundsType(TextBoundsType.VISUAL);
                textCA.setLayoutX(cx+178);
                textCA.setLayoutY(cy+5);

                controls.add(circleCA);
                controls.add(textCA);


                arrow = new Arrow();
                arrow.setStartX(cx + 20);
                arrow.setStartY(cy);
                arrow.setEndX(cx + 170);
                arrow.setEndY(cy);

                controls.add(arrow);

                arrow = new Arrow();
                arrow.setStartX(cx + 200);
                arrow.setStartY(cy);
                arrow.setEndX(cx + 280);
                arrow.setEndY(cy);

                controls.add(arrow);

                if (childNode.getConflictedOf().getConflictingNode() instanceof InformationNode) {
                    InformationNode conflictingINode = (InformationNode) childNode.getConflictedOf().getConflictingNode();
                    System.out.println("zeichne " + conflictingINode.getLabel());
                    text = new TextArea(conflictingINode.getClaimText());
                    text.setEditable(false);
                    text.setLayoutY(110.0);
                    text.setMaxWidth(150);
                    text.setMaxHeight(80);
                    text.setWrapText(true);
                    text.setLayoutX(x + 350);
                    controls.add(text);

                }
            }


            childx = x + 20 -  (childNode.getPremiseNodes().size() - 1) * 35;

            for (de.nrw.hagen.fp1589.domain.Node premiseNode : childNode.getPremiseNodes()) {
                text = new TextArea( ((InformationNode)premiseNode).getClaimText());
                text.setEditable(false);
                text.setLayoutY(230.0);
                text.setMaxWidth(85);
                text.setMaxHeight(140);
                text.setWrapText(true);
                text.setLayoutX(childx);
                controls.add(text);

                arrow = new Arrow();
                arrow.setStartX(x + 70);
                arrow.setStartY(170);
                arrow.setEndX(childx + 30);
                arrow.setEndY(230);
                controls.add(arrow);

                childx+= 100;
            }

            x+= 250 + (childNode.getPremiseNodes().size() - 1) * 50;
        }



        Button button = new Button("Zurück");

        button.setLayoutY(420);
        button.setLayoutX(100);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    Scene sc = getMainScene();
                    mainStage.setScene(sc);
                    mainStage.setTitle("Hauptmenü");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        button.setOnAction(event);
        controls.add(button);
        //Creating a Group object
        Group root = new Group(controls);

        //StackPane stack = new StackPane();
        //stack.getChildren().addAll(root, circle, textt);

        //Creating a scene object
        Scene scene = new Scene(root, 900, 500);

        return scene;

    }


    public void setMainScene(Scene scene) {
        this.main = scene;
    }

    public Scene getMainScene() {
        return this.main;
    }

}
