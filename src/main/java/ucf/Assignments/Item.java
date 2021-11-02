/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 tiffany thani
 */


package ucf.Assignments;

import java.util.Date;

public class Item {
    String itemDesc;
    Date due_date;
    boolean done;

    public Item(String desc, Date due, boolean doneOrNot){
        itemDesc = desc;
        due_date = due;
        done = doneOrNot;
    }

    public boolean isDone(){
        return done;
    }

    public String getDesc(){
        return itemDesc;
    }

    public Date getDate(){
        return due_date;
    }
}
