package com.app.drugcorner32.dc_template.Data;

import android.net.Uri;

import com.app.drugcorner32.dc_template.Helpers.helperIDGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 28-02-2015.
 *
 */
public class PrescriptionDetails implements Serializable {

    //Translated prescription means that it contains the medicine list.
    //Untranslated prescription means that it is just the image of prescription

    public enum TypesOfPrescription{
        TRANSLATED_PRESCRIPTION,UNTRANSLATED_PRESCRIPTION
    }

    private TypesOfPrescription prescriptionType;

    private boolean isSelected = false;

    //The path to the image of the subscription
    private String imageUri;

    private String thumbnailUri;

    private int prescriptionID;

    //The prescription will be translated to medicine list by the pharmacist. This is that list
    //Empty at the beginning
    private List<MedicineDetails> medicineList = new ArrayList<>();

    //TODO this constructor needs to be removed on finalization of the app
    public PrescriptionDetails(){
        imageUri = null;
        prescriptionID = helperIDGenerator.getID();
        prescriptionType = TypesOfPrescription.TRANSLATED_PRESCRIPTION;
    }

    public PrescriptionDetails(PrescriptionDetails details){
        prescriptionID = details.getPrescriptionID();
        imageUri = details.getImageUri().toString();
        if(prescriptionType == TypesOfPrescription.TRANSLATED_PRESCRIPTION)
            for(int i = 0;i<details.getMedicineList().size();i++){
                if(details.getMedicineList().get(i).getSelection()) {
                    medicineList.add(new MedicineDetails(details.getMedicineList().get(i)));
                }
            }
    }

    public PrescriptionDetails(Uri imageUri,Uri thumbnailUri) {
        prescriptionID = helperIDGenerator.getID();
        this.imageUri = imageUri.toString();
        this.thumbnailUri = thumbnailUri.toString();
        prescriptionType = TypesOfPrescription.UNTRANSLATED_PRESCRIPTION;
    }

    public PrescriptionDetails(Uri imageUri,List<MedicineDetails> medicineList) {
        prescriptionID = helperIDGenerator.getID();
        this.imageUri = imageUri.toString();
        this.medicineList = medicineList;
        prescriptionType = TypesOfPrescription.TRANSLATED_PRESCRIPTION;
    }

    public void addMedicine(MedicineDetails details){
        medicineList.add(new MedicineDetails(details));
    }


    //Getters
    public Uri getImageUri(){
        return Uri.parse(imageUri);
    }

    public TypesOfPrescription getPrescriptionType(){
        return prescriptionType;
    }

    public Uri getThumbnailUri() {
        return Uri.parse(thumbnailUri);
    }

    public float getCost(){
        float sum = 0;
        if(prescriptionType == TypesOfPrescription.TRANSLATED_PRESCRIPTION)
            for(MedicineDetails details : medicineList)
                sum += (details.getCost() * details.getQuantity());

        return sum;
    }

    public int getPrescriptionID(){
        return prescriptionID;
    }


    public List<MedicineDetails> getMedicineList(){
        return medicineList;
    }

    //Setters
    public void setMedicineList(List<MedicineDetails> medicineList){
        this.medicineList = medicineList;
    }

    public void setImageUri(Uri imageUri){
        this.imageUri = imageUri.toString();
    }

    public void setThumbnailUri(Uri thumbnailUri){
        this.thumbnailUri = thumbnailUri.toString();
    }

    public void setPrescriptionType(TypesOfPrescription prescriptionType){
        this.prescriptionType = prescriptionType;
    }

    public void setPrescriptionID(int id){
        prescriptionID = id;
    }

    @Override
    public boolean equals(Object o) {
        return getPrescriptionID() == ((PrescriptionDetails)o).getPrescriptionID();
    }

}
