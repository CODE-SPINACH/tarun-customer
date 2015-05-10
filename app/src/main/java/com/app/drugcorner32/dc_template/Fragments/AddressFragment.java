package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drugcorner32.dc_template.Data.AddressDetails;
import com.app.drugcorner32.dc_template.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        final TextView phoneNoView = (TextView) view.findViewById(R.id.addressTextView3);
        final TextView houseNameView = (TextView) view.findViewById(R.id.addressTextView4);
        final TextView streetNameView = (TextView) view.findViewById(R.id.addressTextView5);
        final TextView landamrkNameView = (TextView) view.findViewById(R.id.addressTextView6);
        final TextView pincodeNoView = (TextView) view.findViewById(R.id.addressTextView7);

        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/gothic.ttf");

        firstNameView.setTypeface(typeface);
        lastNameView.setTypeface(typeface);
        phoneNoView.setTypeface(typeface);
        houseNameView.setTypeface(typeface);
        streetNameView.setTypeface(typeface);
        landamrkNameView.setTypeface(typeface);
        pincodeNoView.setTypeface(typeface);
        continueButton.setTypeface(typeface);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressDetails details = new AddressDetails();
                details.setFirstName(firstNameView.getText().toString());
                details.setLastName(lastNameView.getText().toString());
                details.setHouseInfo(houseNameView.getText().toString());
                details.setStreetInfo(streetNameView.getText().toString());
                details.setLandmark(streetNameView.getText().toString());

                Pattern patternFirstName = Pattern.compile("^[a-zA-Z\\s]+$",Pattern.CASE_INSENSITIVE);
                Pattern patternLastName = Pattern.compile("^[a-zA-Z\\s]+$",Pattern.CASE_INSENSITIVE);
                Pattern patternHouseName = Pattern.compile("\\w+",Pattern.CASE_INSENSITIVE);
                Pattern patternStreetName = Pattern.compile("\\w+",Pattern.CASE_INSENSITIVE);
                Pattern patternLandmark = Pattern.compile("^[a-zA-Z\\s]+$",Pattern.CASE_INSENSITIVE);
                Pattern patternPhoneNumber = Pattern.compile("^[789]\\d{9}$");
                Pattern patternPincode = Pattern.compile("^([0-9]{6})$");

                Matcher matcherFirstName = patternFirstName.matcher(firstNameView.getText().toString());
                Matcher matcherLastName = patternLastName.matcher(lastNameView.getText().toString());
                Matcher matcherHouseName = patternHouseName.matcher(houseNameView.getText().toString());
                Matcher matcherStreetName = patternStreetName.matcher(streetNameView.getText().toString());
                Matcher matcherLandmark = patternLandmark.matcher(landamrkNameView.getText().toString());
                Matcher matcherPhoneNumber = patternPhoneNumber.matcher(phoneNoView.getText().toString());
                Matcher matcherPincode = patternPincode.matcher(pincodeNoView.getText().toString());

                if(matcherFirstName.matches() && matcherLastName.matches() && matcherHouseName.matches() && matcherStreetName.matches() && matcherLandmark.matches() && matcherPhoneNumber.matches() && matcherPincode.matches())
                {
                    callback.startNotificationActivity(details);
                }
                else if(firstNameView.getText().toString().matches("") || lastNameView.getText().toString().matches("") || houseNameView.getText().toString().matches("") || streetNameView.getText().toString().matches("") || phoneNoView.getText().toString().matches("") || pincodeNoView.getText().toString().matches("") || landamrkNameView.getText().toString().matches(""))
                {
                    Toast.makeText(getActivity(), "One or more fields is empty", Toast.LENGTH_SHORT).show();
                }
                else if(!matcherFirstName.matches())
                {
                    Toast.makeText(getActivity(), "Enter Correct First Name", Toast.LENGTH_SHORT).show();
                }
                else if(!matcherLastName.matches())
                {
                    Toast.makeText(getActivity(), "Enter Correct Last Name", Toast.LENGTH_SHORT).show();
                }
                else if(!matcherPhoneNumber.matches())
                {
                    Toast.makeText(getActivity(), "Enter Correct Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if(!matcherHouseName.matches())
                {
                    Toast.makeText(getActivity(), "Enter Correct Address", Toast.LENGTH_SHORT).show();
                }
                else if(!matcherStreetName.matches())
                {
                    Toast.makeText(getActivity(), "Enter Correct Address", Toast.LENGTH_SHORT).show();
                }
                else if(!matcherLandmark.matches())
                {
                    Toast.makeText(getActivity(), "Enter Correct Landmark", Toast.LENGTH_SHORT).show();
                }
                else if(!matcherPincode.matches())
                {
                    Toast.makeText(getActivity(), "Enter Correct Pincode", Toast.LENGTH_SHORT).show();
                }


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
