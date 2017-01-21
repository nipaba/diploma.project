package com.nesvadba.tomas.celldetection.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;

import com.nesvadba.tomas.celldetection.enums.ImageType;

/**
 *
 * @author nipaba
 *
 */
public class ImageFile {

    private File file;
    private String cutRegion;

    private List<Yeast> yeasts = new ArrayList<>();
    private Map<ImageType, Mat> data = new HashMap<>();

    public File getFile() {
	return file;
    }

    public void setFile(File file) {
	this.file = file;
    }

    public List<Yeast> getYeasts() {
	return yeasts;
    }

    public void setYeasts(List<Yeast> yeasts) {
	this.yeasts = yeasts;
    }

    public String getCutRegion() {
	return cutRegion;
    }

    public void setCutRegion(String cutRegion) {
	this.cutRegion = cutRegion;
    }

    @Override
    public String toString() {
	return "ImageFile{" + "file=" + file + ", yeasts=" + yeasts + ", cutRegion=" + cutRegion + '}';
    }

    public Map<ImageType, Mat> getData() {
	return data;
    }

    public void setData(Map<ImageType, Mat> data) {
	this.data = data;
    }

}
