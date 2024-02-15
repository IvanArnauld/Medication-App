package com.example.ikepseuprojectone.models;

import androidx.annotation.NonNull;

public class Medication {

    public int id_;
    public String sPatName_;
    public String sMedName_;
    public String sMedType_;
    public String sDate_;
    public String sTime_;

    public Medication(int id_, String sPatName_, String sMedName_, String sMedType_, String sDate_, String sTime_) {
        this.id_ = id_;
        this.sPatName_ = sPatName_;
        this.sMedName_ = sMedName_;
        this.sMedType_ = sMedType_;
        this.sDate_ = sDate_;
        this.sTime_ = sTime_;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }

    public String getsPatName_() {
        return sPatName_;
    }

    public void setsPatName_(String sPatName_) {
        this.sPatName_ = sPatName_;
    }

    public String getsMedName_() {
        return sMedName_;
    }

    public void setsMedName_(String sMedName_) {
        this.sMedName_ = sMedName_;
    }

    public String getsMedType_() {
        return sMedType_;
    }

    public void setsMedType_(String sMedType_) {
        this.sMedType_ = sMedType_;
    }

    public String getsDate_() {
        return sDate_;
    }

    public void setsDate_(String sDate_) {
        this.sDate_ = sDate_;
    }

    public String getsTime_() {
        return sTime_;
    }

    public void setsTime_(String sTime_) {
        this.sTime_ = sTime_;
    }

    @NonNull
    @Override
    public String toString() {
        return " Patient Name: " + sPatName_ +
                ", Medicine: " + sMedName_ +
                ", Medicine Type: " + sMedType_ +
                ", Date: " + sDate_ +
                ", Time: " + sTime_;
    }
}
