package com.nesvadba.tomas.celldetection.converter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import com.nesvadba.tomas.celldetection.domain.ImageFile;

public class ImgConverter {

    public static BufferedImage Mat2BufferedImage(Mat m) {

	int type = BufferedImage.TYPE_BYTE_GRAY;
	if (m.channels() > 1) {
	    type = BufferedImage.TYPE_3BYTE_BGR;
	}
	int bufferSize = m.channels() * m.cols() * m.rows();
	byte[] b = new byte[bufferSize];
	m.get(0, 0, b);
	BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
	final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	System.arraycopy(b, 0, targetPixels, 0, b.length);
	return image;

    }

    public static ImageFile file2ImageFile(File file) {
	ImageFile imageFile = new ImageFile();
	imageFile.setFile(file);

	return imageFile;
    }

    public static List<ImageFile> file2ImageFile(List<File> files) {
	List<ImageFile> imageFiles = new ArrayList<>();

	for (File file : files) {
	    ImageFile imageFile = new ImageFile();
	    imageFile.setFile(file);
	    imageFiles.add(imageFile);
	}

	return imageFiles;
    }

}
