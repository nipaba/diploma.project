package com.nesvadba.tomas.celldetection.domain;

public class EditStep {

    private EditType editType;
    private String editParametr;

    public EditType getEditType() {
	return editType;
    }

    public void setEditType(EditType editType) {
	this.editType = editType;
    }

    public String getEditParametr() {
	return editParametr;
    }

    public void setEditParametr(String editParametr) {
	this.editParametr = editParametr;
    }

    @Override
    public String toString() {
	return "EditStep [editType=" + editType + ", editParametr=" + editParametr + "]";
    }

}
