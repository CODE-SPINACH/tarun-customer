package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.AddressDetails;
import com.app.drugcorner32.dc_template.R;

public class AddressFragment extends android.support.v4.app.Fragment {

    public static String TAG = "Address";
    private Callback callback;

    public static AddressFragment newInstance() {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putCharSequence("TAG",TAG);
        fragment.setArguments(args);
        return fragment;
    }

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        Button continueButton = (Button)view.findViewById(R.id.placeOrderButton);

        final TextView firstNameView = (TextView) view.findViewById(R.id.addressTextView1);
        final TextView lastNameView = (TextView) view.findViewById(R.id.addressTextView2);
        TextView phoneNoView = (TextView) view.findViewById(R.id.addressTextView3);
        final TextView houseNameView = (TextView) view.findViewById(R.id.addressTextView4);
        final TextView streetNameView = (TextView) view.findViewById(R.id.addressTextView5);
        TextView landamrkNameView = (TextView) view.findViewById(R.id.addressTextView6);
        TextView pincodeNoView = (TextView) view.findViewById(R.id.addressTextView7);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressDetails details = new AddressDetails();
                details.setFirstName(firstNameView.getText().toString());
                details.setLastName(lastNameView.getText().toString());
                details.setHouseInfo(houseNameView.getText().toString());
                details.setStreetInfo(streetNameView.getText().toString());
                details.setLandmark(streetNameView.getText().toString());

                callback.startNotificationActivity(details);
            }
        });

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


    public interface Callback{
        public void startNotificationActivity(AddressDetails details);
    }
}
