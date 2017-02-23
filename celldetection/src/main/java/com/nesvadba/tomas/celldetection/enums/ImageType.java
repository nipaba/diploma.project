package com.nesvadba.tomas.celldetection.enums;

// TODO -  REMOVE
public enum ImageType {

    SEGMENTED("Segmented"), LABELS("labels"), GRAYSCALE("grayscale"), INITIAL("init"), BINARY("binary"), ERODE(
	    "erode"), DILATE("dilate"), OPEN("open"), CLOSE("close"), DENOISE("denoise");

    private String imageType;

    private ImageType(final String imageType) {
	this.imageType = imageType;
    }

    @Override
    public String toString() {
	return imageType;
    }
}
