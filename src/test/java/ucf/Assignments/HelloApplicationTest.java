package ucf.Assignments;//package ucf.Assignments;
//import org.junit.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import ucf.Assignments.HelloApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class HelloApplicationTest {
    @Test
    public void addNewListTest(){
        HelloApplication ha = new HelloApplication();
        ha.toDoLists = new ArrayList<ToDoList>();
        assertTrue(ha.toDoLists.size() == 0);
        ToDoList newList = new ToDoList("nameOfList");
        ha.toDoLists.add(newList);
        assertTrue(ha.toDoLists.size() == 1);

    }

    @Test
    public void getListTitleTest(){
        ToDoList exampleList = new ToDoList("someTitle");
        assertTrue(exampleList.getTitle() == "someTitle");
    }
    @Test
    public void addListItemTest(){
        Item newItem = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        ToDoList exampleList = new ToDoList("someTitle");
        assertTrue(exampleList.listItems.size() == 0);
        exampleList.listItems.add(newItem);
        assertTrue(exampleList.listItems.size() == 1);
    }
    @Test
    public void getItemDescTest(){
        Item newItem = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        assertTrue(newItem.getDesc() == "someDesc");

    }
    @Test
    public void getItemDateTest(){
        Item newItem = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(newItem.getDate());
        String date = fmt.format(newItem.getDate().getTime());
        System.out.println("*" + date + "*");
        String[] date_list = date.split("-");
        System.out.println("*" + date_list[0] + "*");
        assertTrue(date.equals("2021-12-15"));
    }
    @Test
    public void getItemCompletionTest(){
        Item newItem = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        assertTrue(newItem.isDone() == false);

    }
    @Test
    public void removeListTest(){
        HelloApplication ha = new HelloApplication();
        ha.toDoLists = new ArrayList<ToDoList>();
        assertTrue(ha.toDoLists.size() == 0);
        ToDoList newList = new ToDoList("nameOfList");
        ha.toDoLists.add(newList);
        assertTrue(ha.toDoLists.size() == 1);

        //now remove the list
        ha.toDoLists.remove(0);
        assertTrue(ha.toDoLists.size() == 0);
    }

    @Test
    public void editListTitleTest(){
        ToDoList exampleList = new ToDoList("someTitle");
        assertTrue(exampleList.getTitle() == "someTitle");
        exampleList.editTitle("anotherTitle");
        assertTrue(exampleList.getTitle() == "anotherTitle");

    }

    @Test
    public void editItemDescTest(){
        Item newItem = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        assertTrue(newItem.getDesc() == "someDesc");
        newItem.editDesc("newDesc");
        assertTrue(newItem.getDesc() == "newDesc");
    }

    @Test
    public void editItemDateTest(){
        Item newItem = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(newItem.getDate());
        String date = fmt.format(newItem.getDate().getTime());

        assertTrue(date.equals("2021-12-15"));

        newItem.editDate(new GregorianCalendar(2022, 11, 15));
        fmt.setCalendar(newItem.getDate());
        date = fmt.format(newItem.getDate().getTime());
        assertTrue(date.equals("2022-12-15"));

    }

    @Test
    public void editItemCompletionTest(){
        Item newItem = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        assertTrue(newItem.isDone() == false);
        newItem.editDone(true);
        assertTrue(newItem.isDone() );
    }
    @Test
    public void showCompleteItemsTest(){
        Item newItem1 = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        Item newItem2 = new Item("someDesc", new GregorianCalendar(2021, 11, 15), true);
        Item newItem3= new Item("someDesc", new GregorianCalendar(2021, 11, 15), true);

        HelloApplication ha = new HelloApplication();
        ha.toDoLists = new ArrayList<ToDoList>();
        ToDoList newList = new ToDoList("nameOfList");
        ha.toDoLists.add(newList);
        newList.listItems.add(newItem1);
        newList.listItems.add(newItem2);
        newList.listItems.add(newItem3);

        ha.curFilterIndex = 1;
        int itemsToShow = 0;
        for (Item it : newList.listItems){
            if (ha.curFilterIndex == 1 && !it.isDone() ||
                    ha.curFilterIndex == 2 && it.isDone())
                continue;

            itemsToShow++;
        }
        assertTrue(itemsToShow == 2);
    }

    @Test
    public void showIncompleteItemsTest(){
        Item newItem1 = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        Item newItem2 = new Item("someDesc", new GregorianCalendar(2021, 11, 15), true);
        Item newItem3= new Item("someDesc", new GregorianCalendar(2021, 11, 15), true);

        HelloApplication ha = new HelloApplication();
        ha.toDoLists = new ArrayList<ToDoList>();
        ToDoList newList = new ToDoList("nameOfList");
        ha.toDoLists.add(newList);
        newList.listItems.add(newItem1);
        newList.listItems.add(newItem2);
        newList.listItems.add(newItem3);

        ha.curFilterIndex = 2;
        int itemsToShow = 0;
        for (Item it : newList.listItems){
            if (ha.curFilterIndex == 1 && !it.isDone() ||
                    ha.curFilterIndex == 2 && it.isDone())
                continue;

            itemsToShow++;
        }
        assertTrue(itemsToShow == 1);

    }
    @Test
    public void clearListTest(){
        HelloApplication ha = new HelloApplication();
        ha.toDoLists = new ArrayList<ToDoList>();
        ToDoList newList = new ToDoList("nameOfList");
        ha.toDoLists.add(newList);
        ha.toDoLists.add(newList);
        ha.toDoLists.add(newList);
        ha.toDoLists.add(newList);
        ha.toDoLists.add(newList);

        assertTrue(ha.toDoLists.size() == 5);

        ha.toDoLists.clear();
        assertTrue(ha.toDoLists.size() == 0);

    }

   @Test
    public void saveSingleListTest(){
       HelloApplication ha = new HelloApplication();
       ha.toDoLists = new ArrayList<ToDoList>();
       ToDoList newList = new ToDoList("nameOfList");
       Item newItem1 = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
       Item newItem2 = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
       Item newItem3= new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);

       newList.listItems.add(newItem1);
       newList.listItems.add(newItem2);
       newList.listItems.add(newItem3);

       ha.toDoLists.add(newList);

       try {
           ha.save_indi(0);
           try(BufferedReader br = new BufferedReader(new FileReader("nameOfList.txt"))) {
               for(String line; (line = br.readLine()) != null; ) {
                   String[] fields = line.split(";");
                   String itemDesc = fields[0];
                   String done_str = fields[2];
                   String date_str = fields[1];

                   assertTrue(fields[0].equals("someDesc"));
                   assertTrue(fields[1].equals("2021-12-15"));
                   assertTrue(fields[2].equals("Incomplete"));
               }
               // line is not visible here.
           } catch (IOException e) {
               e.printStackTrace();
           }

       }
       catch (IOException e){
           assertTrue(false);
       }

    }

    @Test
    public void loadListsTest(){
        HelloApplication ha = new HelloApplication();
        ha.toDoLists = new ArrayList<ToDoList>();
        ToDoList newList = new ToDoList("nameOfList");
        Item newItem1 = new Item("someDesc", new GregorianCalendar(2021, 11, 15), false);
        Item newItem2 = new Item("someDesc", new GregorianCalendar(2021, 11, 15), true);
        Item newItem3= new Item("someDesc", new GregorianCalendar(2021, 11, 15), true);

        newList.listItems.add(newItem1);
        newList.listItems.add(newItem2);
        newList.listItems.add(newItem3);

        ha.toDoLists.add(newList);


        for (int i = 0; i < ha.toDoLists.size(); i++){
            try {
                ha.save_indi(i);
            }
            catch (IOException e){
                assertTrue(false);
            }
        }

        ha.toDoLists.clear();

        ha.load_files(new String[] {"nameOfList.txt"});

        assertTrue(ha.toDoLists.size() == 1);
        assertTrue(ha.toDoLists.get(0).getTitle().equals("nameOfList"));
    }


}
