package com.nesvadba.tomas.celldetection.domain;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

public class ImageStats {

    private List<Yeast> yeasts = new ArrayList<>();
    private Mat labels;

    public List<Yeast> getYeasts() {
	return yeasts;
    }

    public void setYeasts(List<Yeast> yeasts) {
	this.yeasts = yeasts;
    }

    public Mat getLabels() {
	return labels;
    }

    public void setLabels(Mat labels) {
	this.labels = labels;
    }

    @Override
    public String toString() {
	return "ImageStats [yeasts=" + yeasts + ", labels=" + labels + "]";
    }

}
