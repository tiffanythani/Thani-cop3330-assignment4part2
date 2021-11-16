/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 tiffany thani
 */


package ucf.Assignments;

import java.util.Date;
import java.util.GregorianCalendar;

public class Item {
    private String itemDesc;
    private GregorianCalendar due_date;
    private boolean done;

    public Item(String desc, GregorianCalendar due, boolean doneOrNot){
        int endRange = desc.length();
        if (endRange > 256){
            endRange = 256;
        }
        itemDesc = desc.substring(0, endRange);
        due_date = due;
        done = doneOrNot;
    }

    public boolean isDone(){
        return done;
    }

    public String getDesc(){
        return itemDesc;
    }

    public GregorianCalendar getDate(){
        return due_date;
    }

    public void editDesc(String newDesc){
        itemDesc = newDesc;
    }

    public void editDate(GregorianCalendar newDate){
        due_date = newDate;
    }

    public void editDone(boolean newDone){
        done = newDone ;
    }
}
