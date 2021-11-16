/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 tiffany thani
 */


package ucf.Assignments;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class HelloApplication extends Application {
    static ArrayList<ToDoList> toDoLists;
    private   int curListIndex;
    public  int curFilterIndex;
    private Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
        toDoLists = new ArrayList<ToDoList>();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 1200, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void createThings(ActionEvent actionEvent){
        Button creationBut = (Button) actionEvent.getTarget();
        scene = creationBut.getScene();
        String buttonText = creationBut.getText();
        boolean viewingLists = buttonText.contains("List");

        if( viewingLists){
            TextField creationText = (TextField) scene.lookup("#creation_text");
            String nameOfList = creationText.getText();

            boolean present = false;
            for (ToDoList lz : toDoLists){
                if (lz.getTitle().equals(nameOfList)){
                    present = true;
                }
            }

            if (!present) {
                // create a new list and add to the arraylist and to the scene
                ToDoList newList = new ToDoList(nameOfList);
                toDoLists.add(newList);

                visualizeContents(viewingLists);
            }
        }
        else {
            // meaning we are viewing the items
            VBox createContent = (VBox) scene.lookup("#creation_box");

            toDoLists.get(curListIndex).listItems.add( makeItem(createContent));

            visualizeContents(viewingLists);
        }

    }

    public void goBackHome(ActionEvent actionEvent){
        Button homeButton = (Button) actionEvent.getTarget();
        scene = homeButton.getScene();
        VBox createContent = (VBox) scene.lookup("#creation_box");
        VBox deleteContent = (VBox) scene.lookup("#deletion_box");
        createContent.getChildren().remove(2, 4) ;
        deleteContent.getChildren().remove(2, 4) ;

        Button createButton = (Button) scene.lookup("#creation_but");
        Button deleteButton = (Button) scene.lookup("#delete_but");

        createButton.setText("New List");
        deleteButton.setText("Delete List");

        visualizeContents(true);

    }

    public Item makeItem(VBox content){
        DatePicker dp = (DatePicker) content.getChildren().get(2);
        LocalDate ld = dp.getValue();
        GregorianCalendar dateObj = new GregorianCalendar(ld.getYear(),
                                  ld.getMonthValue()-1,ld.getDayOfMonth());

        HBox compBox = (HBox) content.getChildren().get(3);
        CheckBox cb = (CheckBox) compBox.getChildren().get(0);

        TextField tf = (TextField) content.getChildren().get(1);

        return new Item( tf.getText(), dateObj, cb.isSelected()  );
    }

    public void deleteThings(ActionEvent actionEvent){
        Button creationBut = (Button) actionEvent.getTarget();
        scene = creationBut.getScene();
        String buttonText = creationBut.getText();
        boolean viewingLists = buttonText.contains("List");

        TextField creationText = (TextField) scene.lookup("#delete_text");
        String nameToDelete = creationText.getText();
        if( viewingLists){

            // create a new list and add to the arraylist and to the scene
            for (int i = 0; i < toDoLists.size(); i++){
                if (toDoLists.get(i).getTitle().equals(nameToDelete) ){
                    toDoLists.remove(i);
                    visualizeContents(viewingLists);
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < toDoLists.get(curListIndex).listItems.size(); i++){
                if (toDoLists.get(curListIndex).listItems.get(i).getDesc().equals(nameToDelete) ){
                    toDoLists.get(curListIndex).listItems.remove(i);
                    visualizeContents(viewingLists);
                    break;
                }

            }
        }

    }

    public void editListItems(Button editBut_x){
        int item_index  = Integer.parseInt(editBut_x.getId().split(" ")[1]) - 1;
        String butText = editBut_x.getText();
        if (butText.equals("Edit")) {
            editBut_x.setText("Save");
        }
        if (butText.equals("Save")){
            scene = editBut_x.getScene();
            VBox createContent = (VBox) scene.lookup("#creation_box");
            Item newItem = makeItem(createContent);

            toDoLists.get(curListIndex).listItems.set(item_index, newItem);
            VBox scrollContent = (VBox) scene.lookup("#scroll_content");

            HBox itemView = makeItemView( newItem, item_index);
            scrollContent.getChildren().set(item_index, itemView);
            editBut_x.setText("Edit");
        }

    }



    public void showListItems(Button showBut_x){

        scene = showBut_x.getScene();
        VBox createContent = (VBox) scene.lookup("#creation_box");
        VBox deleteContent = (VBox) scene.lookup("#deletion_box");
        createContent.getChildren().add(new DatePicker());
        HBox compBox1 = new HBox(new CheckBox(), new Label("Completed?") );
        HBox compBox2 = new HBox(new CheckBox(), new Label("Completed?") );
        createContent.getChildren().add(compBox1);
        //createContent.getChildren().add(new Label("Completed?"));
        deleteContent.getChildren().add(new DatePicker());
        deleteContent.getChildren().add(compBox2);
        //deleteContent.getChildren().add(new Label("Completed?"));

        Button createButton = (Button) scene.lookup("#creation_but");
        Button deleteButton = (Button) scene.lookup("#delete_but");

        createButton.setText("New Item");
        deleteButton.setText("Delete Item");

        curListIndex = Integer.parseInt(showBut_x.getId().split(" ")[1]) - 1;
        VBox content =  (VBox) scene.lookup("#scroll_content");
        HBox listView = (HBox) content.getChildren().get(curListIndex);
        ComboBox filters = (ComboBox) listView.getChildren().get(2);
        curFilterIndex = filters.getSelectionModel().getSelectedIndex();   ///.getselectedindex();
        visualizeContents(false);

    }

    public void visualizeContents(boolean viewingLists){
        VBox content = (VBox) scene.lookup("#scroll_content");

        // clear contents
        content.getChildren().remove(0, content.getChildren().size());

        // now rebuild
        if (viewingLists){
            // iterate through internal lists of lists
            int index = 0;
            for (ToDoList list : toDoLists){
                index += 1;
                // list name,  show items button, combobox  (all, completed, incomplete)

                Label listName = new Label(list.getTitle());
                Button showStuffBut = new Button("Show Items For List");
                showStuffBut.setId("index " + index);
                showStuffBut.setOnAction(action -> { showListItems(showStuffBut);});
                ObservableList<String> filter =
                        FXCollections.observableArrayList(
                                "All",
                                "Complete",
                                "Incomplete"
                        );
                ComboBox showOptions = new ComboBox(filter);
                showOptions.getSelectionModel().select(0);

                HBox visualList = new HBox( listName, showStuffBut, showOptions);
                content.getChildren().add(visualList);
            }
        }
        else {
            ToDoList viewingList = toDoLists.get(curListIndex);
            int index = 0;
            for (Item item : viewingList.listItems ){
                index += 1;
                if (curFilterIndex == 1 && !item.isDone() ||
                    curFilterIndex == 2 && item.isDone())
                    continue;

                HBox itemView = makeItemView( item, index);

                content.getChildren().add(itemView);

            }
        }
    }

    public HBox makeItemView(Item item, int index){
        // item needs:  desc   edit
        String desc = item.getDesc();
        GregorianCalendar due_date = item.getDate();
        boolean item_done = item.isDone();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(due_date);
        String date = fmt.format(due_date.getTime());

        String done = "Completed";
        if (!item_done)
            done = "Incomplete";

        Label itemLabel = new Label( desc + ", Due By: " + date + " :: Item " + done);

        Button editButton = new Button("Edit");
        editButton.setId("button " + index);
        editButton.setOnAction(action -> { editListItems(editButton);});

        HBox itemView = new HBox(itemLabel , editButton);

        return itemView;
    }

    public void save_indi(int i) throws IOException {
        String listName = toDoLists.get(i).getTitle();
        FileWriter myWriter = new FileWriter(listName + ".txt");


        for (Item item : toDoLists.get(i).listItems ){
            String fields = item.getDesc() + ";";
            GregorianCalendar dueDate = item.getDate();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            fmt.setCalendar(dueDate);
            String date = fmt.format(dueDate.getTime());

            fields = fields + date + ";";
            if (item.isDone()){
                fields = fields + "Completed\n";
            }
            else {
                fields = fields + "Incomplete\n";
            }
            myWriter.write(fields);
        }
        myWriter.close();
    }

    public void save(ActionEvent actionEvent) throws IOException {
        Button creationBut = (Button) actionEvent.getTarget();
        scene = creationBut.getScene();
        Button crate = (Button) scene.lookup("#creation_but");
        String buttonText = crate.getText();
        boolean viewingLists = buttonText.contains("List");

        if (viewingLists){
            for (int i = 0; i < toDoLists.size(); i++ ){
                save_indi(i);
            }
        }
        else {
            save_indi(curListIndex);
        }
    }

    public void load_files(String[] listsToLoad){
        for (String fileName : listsToLoad){
            String listName = fileName.replace(".txt", "");
            ToDoList newList = new ToDoList(listName);
            toDoLists.add(newList);
            try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                for(String line; (line = br.readLine()) != null; ) {
                    String[] fields = line.split(";");
                    String itemDesc = fields[0];
                    String done_str = fields[2];
                    String[] date_arr = fields[1].split("-");
                    int year = Integer.parseInt(date_arr[0]);
                    int month = Integer.parseInt(date_arr[1]) - 1;
                    int day = Integer.parseInt(date_arr[2]);


                    ;                   Item newItem = new Item(itemDesc, new GregorianCalendar(year, month, day), done_str.equals("Completed") );
                    newList.listItems.add(newItem);
                }
                // line is not visible here.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load(ActionEvent actionEvent){
        Button creationBut = (Button) actionEvent.getTarget();
        scene = creationBut.getScene();
        TextField content = (TextField) scene.lookup("#load_desc");
        String[] listsToLoad = content.getText().split(";");

        load_files(listsToLoad);
        visualizeContents(true);
    }

    public void clearList(ActionEvent actionEvent) {
        scene = ((Button) actionEvent.getTarget()).getScene();
        Button createButton = (Button) scene.lookup("#creation_but");

        if (createButton.getText().contains("Item")) {
            toDoLists.get(curListIndex).listItems.clear();
            visualizeContents(false);
        }
    }
}



















