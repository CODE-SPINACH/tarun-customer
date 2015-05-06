package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.NotificationDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;

//TODO remove the notification list if of no use in the future
public class NotificationFragment extends android.support.v4.app.Fragment {
    public static String TAG = "Notification";

    private OnFragmentChange callback1;
    private LinearLayout layout;

    private ArrayList<NotificationDetails> notificationDetailses = new ArrayList<>();

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        layout = (LinearLayout)view.findViewById(R.id.notificationLinearLayout1);

        for(NotificationDetails details : notificationDetailses){
            createNotificationView(details);
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback1 = (OnFragmentChange) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback1 = null;
    }

    //The view of the notification cards is being created here
    public void createNotificationView( final NotificationDetails notificationDetails){
        final View view;
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if(notificationDetails.getType() == NotificationDetails.NOTIFICATION_TYPE.TEXT ||
                notificationDetails.getType() == NotificationDetails.NOTIFICATION_TYPE.DELIVERY_TIME){
            view = inflater.inflate(R.layout.cards_simple_notification,layout,false);
            TextView messageView = (TextView)view.findViewById(R.id.simpleNotificationCardTextView1);

            Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/gothic.ttf");

            messageView.setTypeface(typeface);

            messageView.setText(notificationDetails.getMessage());
        }
        else{
            view = inflater.inflate(R.layout.cards_button_notification,layout,false);
            Button button = (Button)view.findViewById(R.id.buttonNotificationCardButton1);
            TextView messageView = (TextView)view.findViewById(R.id.buttonNotificationCardTextView1);

            Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/gothic.ttf");

            button.setTypeface(typeface);
            messageView.setTypeface(typeface);

            messageView.setText(notificationDetails.getMessage());
            if(notificationDetails.getType() == NotificationDetails.NOTIFICATION_TYPE.VIEW)
                button.setText("View");
            else if (notificationDetails.getType() == NotificationDetails.NOTIFICATION_TYPE.EDIT)
                button.setText("Edit");
            else
                button.setText("Click Again");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback1.replaceFragment(R.id.buttonNotificationCardButton1, notificationDetails);
                }
            });
        }
        layout.addView(view);
    }

    public void addNewNotification(NotificationDetails details){
        notificationDetailses.add(details);
        createNotificationView(details);
    }

    //This is function is used to update the timers present in the various cards like the ones indicating
    // delivery time or time left to edit medicine
    public void updateTimer(String timeString,NotificationDetails.NOTIFICATION_TYPE notificationType){
        int index = -1;
        for(int i = 0; i < notificationDetailses.size(); i++)
            if(notificationDetailses.get(i).getType() == notificationType)
                index = i;
        if(index != -1){
            View view = layout.getChildAt(index);
            if(view != null) {
                TextView messageView = (TextView) view.findViewById(R.id.buttonNotificationCardTextView1);
                messageView.setText(notificationDetailses.get(index).getMessage() + timeString);
            }
        }
    }

    public void deleteNotificationCard(NotificationDetails.NOTIFICATION_TYPE notificationType){
        int index = -1;
        for(int i = 0; i < notificationDetailses.size(); i++)
            if(notificationDetailses.get(i).getType() == notificationType)
                index = i;
        if(index != -1){
            layout.removeViewAt(index);
            notificationDetailses.remove(index);
        }
    }

    public void deleteAllNotifications(){
        notificationDetailses.clear();
        layout.removeAllViews();
    }
}
