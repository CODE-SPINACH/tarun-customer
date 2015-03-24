package com.app.drugcorner32.dc_template.Interfaces;

import com.app.drugcorner32.dc_template.Data.OrderDetails;

/**
 * Created by Tarun on 27-02-2015.
 */
public interface OnFragmentInteractionListener {
    public void replaceFragment(int id, Object object);
    public void takePhoto();
    public void deliverOrderToDialog(OrderDetails details);
    public void startNotificationActivity();
}
