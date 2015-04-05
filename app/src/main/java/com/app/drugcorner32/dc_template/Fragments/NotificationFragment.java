package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.NotificationDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;

//TODO remove the notification list if of no use in the future
public class NotificationFragment extends android.support.v4.app.Fragment {
    private OrderDetails orderDetails;
    private ArrayList<NotificationDetails> notificationDetailses = new ArrayList<>();
    private Callback callback;
    private LinearLayout layout;
    public static String TAG = "Notification";

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

        notificationDetailses.add(new NotificationDetails
                ("Some of the prescription images you sent were incomprehensible. " +
                        "We request you to send them again.",
                NotificationDetails.NOTIFICATION_TYPE.CLICK_AGAIN));

        notificationDetailses.add(new NotificationDetails("You may now edit your medicines now.\nYou have to do so in :",
                        NotificationDetails.NOTIFICATION_TYPE.EDIT));

        for(NotificationDetails details : notificationDetailses){
            createNotificcationView(layout,inflater,details);
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    //The view of the notification cards is being created here
    public void createNotificcationView(final LinearLayout layout,final LayoutInflater inflater, final NotificationDetails notificationDetails){
        final View view;
        if(notificationDetails.getType() == NotificationDetails.NOTIFICATION_TYPE.JUST_TEXT){
            view = (View)inflater.inflate(R.layout.cards_simple_notification,null);
            TextView messageView = (TextView)view.findViewById(R.id.simpleNotificationCardTextView1);
            messageView.setText(notificationDetails.getMessage());
        }
        else{
            view = (View)inflater.inflate(R.layout.cards_button_notification,null);
            final TextView messageView = (TextView)view.findViewById(R.id.buttonNotificationCardTextView1);
            Button button = (Button)view.findViewById(R.id.buttonNotificationCardButton1);

            messageView.setText(notificationDetails.getMessage());
            if(notificationDetails.getType() == NotificationDetails.NOTIFICATION_TYPE.VIEW)
                button.setText("View");
            else if (notificationDetails.getType() == NotificationDetails.NOTIFICATION_TYPE.EDIT) {
                button.setText("Edit");
                new CountDownTimer(10*1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        int minutes = (int)(millisUntilFinished / (60 * 1000));
                        int seconds = (int)((millisUntilFinished / 1000) % 60);
                        String str = String.format("%d:%02d", minutes, seconds);

                        messageView.setText(notificationDetails.getMessage() + " " + str);
                    }

                    public void onFinish() {
                        layout.removeView(view);
                        notificationDetailses.remove(notificationDetails);
                        NotificationDetails details1 = new NotificationDetails("Your estimated delivery time is 10minutes",
                                NotificationDetails.NOTIFICATION_TYPE.JUST_TEXT);

                        NotificationDetails details2 = new NotificationDetails("Your order has been finalized.",
                                NotificationDetails.NOTIFICATION_TYPE.JUST_TEXT);

                        notificationDetailses.add(details2);
                        notificationDetailses.add(details1);

                        createNotificcationView(layout, inflater, details2);
                        createNotificcationView(layout,inflater,details1);
                    }
                }.start();
            }
            else
                button.setText("Click Again");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.replaceFragment(R.id.buttonNotificationCardButton1,notificationDetails);
                }
            });
        }
        layout.addView(view);
    }

    public void setOrderDetails(OrderDetails details){
        orderDetails = details;
    }

    public static interface Callback{
        public void replaceFragment(int id,Object o);
    }
}
