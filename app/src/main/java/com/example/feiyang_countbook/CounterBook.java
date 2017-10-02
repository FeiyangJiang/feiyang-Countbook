package com.example.feiyang_countbook;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Feiyang Jiang on 2017-09-30.
 * The class is for counting book
 * It has name, initial value, current value and comment.
 * Consult Yiding Fan, Zijian He, Qikai Lu
 */

public class CounterBook {
    private String name;
    private Date date;
    private int initial;
    private int current;
    private String comment;

    public CounterBook(String Name, int a, String comment) {
        this.name = Name;
        this.date = Calendar.getInstance().getTime();
        this.comment = comment;
        this.initial = a;
        this.current = a;
    }

    public String getName(){
        return this.name;
    }
    public String getComment(){
        return this.comment;
    }
    public int getInit(){
        return this.initial;
    }
    public int getCurr(){
        return this.current;
    }
    public String toString() {
        return "Name: "+this.name+" \ninitial: "+this.initial+" \ncurrent value: "+this.current+" \nlast modified: "+this.date
                +" \ncomment: "+ this.comment;
    }
    /**
     * Increase/Decrease current value by 1
     */
    public void increase(){
        this.date = Calendar.getInstance().getTime();
        this.current=this.current+1;
    }
    public void decrease(){
        if (this.current>0) {
            this.date = Calendar.getInstance().getTime();
            this.current = this.current - 1;
        }
    }

    /**
     * set the initial value to A
     */
    public void Init(int A){
        this.date = Calendar.getInstance().getTime();
        this.initial=A;
    }

    /**
     * set the name to A
     */
    public void Name(String A){
        this.date = Calendar.getInstance().getTime();
        this.name=A;
    }

    /**
     * set the comment to A
     */
    public void Comment( String A){
        this.date = Calendar.getInstance().getTime();
        this.comment=A;
    }

    /**
     * set the current value to A
     */
    public void C_Value(int A){
        this.date = Calendar.getInstance().getTime();
        this.current=A;
    }
}
