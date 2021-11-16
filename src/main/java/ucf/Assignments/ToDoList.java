/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 tiffany thani
 */


package ucf.Assignments;

import java.util.ArrayList;

public class ToDoList {
    public ArrayList<Item> listItems;
    private String title;

    public ToDoList(String listTitle){
        listItems = new ArrayList<Item>();
        title = listTitle;
    }

    public String getTitle(){
        return title;
    }

    public void editTitle(String newTitle){
        title = newTitle;
    }
}
