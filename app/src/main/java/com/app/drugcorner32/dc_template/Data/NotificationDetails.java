package com.app.drugcorner32.dc_template.Data;

/**
 * Created by Tarun on 02-04-2015.
 * This class represents the various notifications that are to be displayed
 * on the notification screen (duh) as cards.
 */
public class NotificationDetails {

    /*Notification types might appear confusing here's me dumbing them down for u
        JUST_TEXT   : this is used to inform the user of some info and that is it. It comprises of simply text

        The rest are cards with buttons
        VIEW        : Clicking on it makes the medicines list visible (used when some medicines are not found)
        EDIT        : Clicking on it makes the medicines list visible plus they are editable
        CLICK_AGAIN : Clicking on it lets u recapture images that might have been bad or incomprehensible
         */
    public static enum NOTIFICATION_TYPE{
        JUST_TEXT,VIEW,EDIT,CLICK_AGAIN
    }

    //The message to be displayed with the notification card
    private String message;
    private NOTIFICATION_TYPE type;

    public NotificationDetails(String message,NOTIFICATION_TYPE type){
        this.message = message;
        this.type = type;
    }

    public NOTIFICATION_TYPE getType(){
        return type;
    }

    public String getMessage(){
        return message;
    }
}
