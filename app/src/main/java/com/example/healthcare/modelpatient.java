package com.example.healthcare;

public class modelpatient
{
    private String Patients;

    public modelpatient() {
    }

    public modelpatient(String patients) {
        Patients = patients;
    }

    public String getPatients() {
        return Patients;
    }

    public void setPatients(String patients) {
        Patients = patients;
    }
}
