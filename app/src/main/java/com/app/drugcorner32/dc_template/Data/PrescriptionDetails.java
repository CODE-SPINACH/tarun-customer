package com.app.drugcorner32.dc_template.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.app.drugcorner32.dc_template.Helpers.HelperClass;
import com.app.drugcorner32.dc_template.Helpers.helperIDGenerator;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 28-02-2015.
 *
 */
public class PrescriptionDetails {

    private boolean isSelected = false;

    //The path to the image of the subscription
    private Uri imageUri;

    private int prescriptionID;

    //Thumbnail for faster access
    private Bitmap thumbnail;

    //Notes associated with the prescription
    private String notes;

    //Days of medicine : if explicitly specified
    private int days;

    //if daysTextView of medicine is meant to be as per given in the prescription
    public static final String defaultMessage = "as\nspecified";

    //The prescription will be translated to medicine list by the pharmacist. This is that list
    //Empty at the beginning
    private List<MedicineDetails> medicineList = new ArrayList<>();

    public PrescriptionDetails(){
        imageUri = null;
        this.notes = "";
        days = 0;
        prescriptionID = helperIDGenerator.getID();
    }

    public PrescriptionDetails(PrescriptionDetails details){
        prescriptionID = details.getPrescriptionID();
        imageUri = details.getImageUri();
        notes = details.getNotes();
        days = details.getDays();
        if(details.getMedicineList() != null)
            for(int i = 0;i<details.getMedicineList().size();i++){
                if(details.getMedicineList().get(i).getSelection())
                    medicineList.add(new MedicineDetails(details.getMedicineList().get(i)));
            }
    }

    public PrescriptionDetails(Uri imageUri, String notes,Context context) {
        prescriptionID = helperIDGenerator.getID();

        this.imageUri = imageUri;
        this.notes = notes;
        days = 0;

        thumbnail = HelperClass.getPreview(imageUri,
                (int)context.getResources().getDimension(R.dimen.card_image_size));
    }


    public PrescriptionDetails(Uri imageUri, String notes, int days) {
        prescriptionID = helperIDGenerator.getID();
        this.imageUri = imageUri;
        this.notes = notes;
        this.days = days;
    }

    public Uri getImageUri(){
        return imageUri;
    }

    public String getNotes(){
        return notes;
    }

    public int getDays(){
        return days;
    }

    public List<MedicineDetails> getMedicineList(){
        return medicineList;
    }

    public void setDays(int days){
        this.days = days;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public void setMedicineList(List<MedicineDetails> medicineList){
        this.medicineList = medicineList;
    }

    public float getCost(){
        float sum = 0;
        for(MedicineDetails details : medicineList)
            sum += (details.getCost() * details.getDays() * details.getQuantityPerDay());
        return sum;
    }

    public int getPrescriptionID(){
        return prescriptionID;
    }

    public void setPrescriptionID(int id){
        prescriptionID = id;
    }

    public Bitmap getThumbnail(){
        return thumbnail;
    }
}
