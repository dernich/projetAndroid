package com.henallux.testmenu.Model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kevin on 14-12-15.
 */
public class Care {
    private String careDate;
    private int idPatient;
    private int idNurse;

    public Care(String careDate, int idPatient, int idNurse) {
        this.careDate = careDate;
        this.idPatient = idPatient;
        this.idNurse = idNurse;
    }

    public Care() {}

    public String getCareDate() {
        return careDate;
    }

    public void setCareDate(String careDate) {
        this.careDate = careDate;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIdNurse() {
        return idNurse;
    }

    public void setIdNurse(int idNurse) {
        this.idNurse = idNurse;
    }
}
