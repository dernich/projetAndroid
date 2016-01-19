package com.henallux.testmenu.DataAccess;

import com.henallux.testmenu.Model.Nurse;
import com.henallux.testmenu.Model.Patient;
import java.util.ArrayList;

public class AllData {

    private ArrayList<Patient> patients;
    private ArrayList<Nurse> nurses;

    public AllData() {
        patients = new ArrayList<Patient>();
        /*patients.add(new Patient(1, "Dernivoix", "Antoine", "09/11/1993", "0494089554", "Place de la gare", 7, "6840", "Longlier"));
        patients.add(new Patient(2, "Doumont", "Kévin", "02/01/1995", "0497403226", "Longtry", 119, "5070", "Leroux"));
        patients.add(new Patient(3, "Hayward", "Juliette", "10/01/1992", "0494465836", "Avenue Roi baudouin", 15, "6600", "Bastogne"));
        patients.add(new Patient(4, "Leonard", "Sébastien", "15/06/1995", "0475761904", "route d'onhaye", 17, "5524", "Dinant"));
        patients.add(new Patient(5, "Degraux", "Maxence", "16/06/1995", "0498139358", "rue du monument", 108, "5620", "Rosée"));
        patients.add(new Patient(6, "Lambert", "Christine", "28/08/1968", "0494561231", "Place de la gare", 7, "6840", "Longlier"));
        patients.add(new Patient(7, "Lejeune", "Julien", "15/03/1993", "0484351565", "rue de la maladrie", 19, "6840", "Longlier"));
        patients.add(new Patient(8, "Zabata", "Zakaria", "16/06/1996", "0498115113", "rue tout vent", 15, "6041", "Gosselies"));*/

/*        nurses = new ArrayList<Nurse>();
        nurses.add(new Nurse(1, "Lambert", "Christine", "0494423445", "blablabla@hotmail.com", true));
        nurses.add(new Nurse(2, "Preser", "Aline", "0494423445", "blablabla@hotmail.com", true));
        nurses.add(new Nurse(3, "Hayward", "Sarah", "0494423445", "blablabla@hotmail.com", true));
        nurses.add(new Nurse(4, "Vartan", "Jeanne", "0494423445", "blablabla@hotmail.com", true));
        nurses.add(new Nurse(5, "Arnould", "Tom", "0494423445", "blablabla@hotmail.com", true));*/
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public void deletePatient(Patient patient) {
        for(int i = 0; i < patients.size(); i++) {
            if(patients.get(i).equals(patient)) {
                patients.remove(i);
            }
        }
    }
}
