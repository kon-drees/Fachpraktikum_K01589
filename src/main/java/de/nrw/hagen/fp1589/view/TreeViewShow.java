package de.nrw.hagen.fp1589.view;

import de.nrw.hagen.fp1589.controller.ArgController;
import de.nrw.hagen.fp1589.domain.ArgTree;
import de.nrw.hagen.fp1589.domain.InformationNode;
import de.nrw.hagen.fp1589.domain.RuleApplicationNode;
import de.nrw.hagen.fp1589.util.ArgTreeEvaluator;
import de.nrw.hagen.fp1589.util.ArgTreeReaderWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class TreeViewShow  {


    private Scene main;
    private final Stage mainStage;

    private boolean changed = false;


    public TreeViewShow(Stage stage) {
        this.mainStage = stage;
    }


    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public  Scene start(String filename) {
        ArgTree tree = ArgTreeReaderWriter.importTree(filename);
        Hashtable<String, de.nrw.hagen.fp1589.domain.Node> premiseNodes = new Hashtable<>();
        boolean conflict = false;


        List<Node> controls = new ArrayList<>();


        int x = 100;
        int childx;
        int i = 0;
        for (@SuppressWarnings("DataFlowIssue") Iterator<InformationNode> it = tree.getInformationNodes(); it.hasNext(); ) {
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

                final Circle circleCA = new Circle(cx + 161,cy, 20);
                circleCA.setStroke(Color.CHARTREUSE);
                circleCA.setStrokeWidth(2);
                circleCA.setStrokeType(StrokeType.INSIDE);
                circleCA.setFill(Color.AZURE);

                final Text textCA = new Text("CA");
                textCA.setBoundsType(TextBoundsType.VISUAL);
                textCA.setLayoutX(cx+148);
                textCA.setLayoutY(cy+5);

                controls.add(circleCA);
                controls.add(textCA);


                arrow = new Arrow();
                arrow.setStartX(cx + 30);
                arrow.setStartY(cy);
                arrow.setEndX(cx + 140);
                arrow.setEndY(cy);

                controls.add(arrow);

                arrow = new Arrow();
                arrow.setStartX(cx + 170);
                arrow.setStartY(cy);
                arrow.setEndX(cx + 225);
                arrow.setEndY(cy);

                controls.add(arrow);

                if (childNode.getConflictedOf().getConflictingNode() instanceof InformationNode conflictingINode) {
                    text = new TextArea(conflictingINode.getClaimText());
                    text.setEditable(false);
                    text.setLayoutY(110.0);
                    text.setMaxWidth(150);
                    text.setMaxHeight(80);
                    text.setWrapText(true);
                    text.setLayoutX(x + 350);
                    controls.add(text);
                }
                else {
                    conflict = true;
                }
            }


            childx = x + 20 -  (childNode.getPremiseNodes().size() - 1) * 35;

            for (de.nrw.hagen.fp1589.domain.Node premiseNode : childNode.getPremiseNodes()) {
                text = new MyTextArea( ((InformationNode)premiseNode).getClaimText()+ (conflict ? "\n\nArgStrength: " + ((InformationNode) premiseNode).getArgStrength() : "") );
                text.setEditable(false);
                text.setLayoutY(230.0);
                text.setMaxWidth(100);
                text.setMaxHeight(140);
                text.setWrapText(true);
                text.setLayoutX(childx);
                ((MyTextArea)text).setNode((InformationNode) premiseNode);

                controls.add(text);

                premiseNodes.put(premiseNode.getLabel() , premiseNode);

                EventHandler<MouseEvent> eventChangeArgStrength = mouseEvent -> {
                    try {
                        MyTextArea text1 = (MyTextArea) mouseEvent.getSource();
                        InformationNode nodetochange = text1.getNode();
                        String neueArgStrength = JOptionPane.showInputDialog("Neue ArgStrength eingeben", nodetochange.getArgStrength());
                        nodetochange.setArgStrength(Long.parseLong(neueArgStrength));
                        text1.setText(nodetochange.getClaimText() + "\n\nArgStrength: " + nodetochange.getArgStrength());
                        changed = true;
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                };
                if (conflict) {
                    text.setOnMouseClicked(eventChangeArgStrength);
                }

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

        EventHandler<ActionEvent> event = e -> {
            try {
                if (changed) {
                    int reply = JOptionPane.showConfirmDialog(null, "Änderungen speichern?" , "Speichern?", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        ArgTreeReaderWriter.writeTree(tree.getName(), tree);
                    }
                    changed = false;
                }
                Scene sc = getMainScene();
                mainStage.setScene(sc);
                mainStage.setTitle("Hauptmenü");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };

        button.setOnAction(event);
        controls.add(button);


        Button button2 = new Button("Dialog starten");

        button2.setLayoutY(420);
        button2.setLayoutX(400);

        EventHandler<ActionEvent> event2 = e -> {
            try {
                ArgTreeEvaluator argTreeEvaluator = new ArgTreeEvaluator(tree);
                ArgController argController = new ArgController(argTreeEvaluator);
                argController.startVisualConversation();



            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };

        button2.setOnAction(event2);
        controls.add(button2);

        //Creating a Group object
        Group root = new Group(controls);

        return new Scene(root, 900, 500);

    }


    public void setMainScene(Scene scene) {
        this.main = scene;
    }

    public Scene getMainScene() {
        return this.main;
    }

}
