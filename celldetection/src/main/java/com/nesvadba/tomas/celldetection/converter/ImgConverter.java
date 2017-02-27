package com.nesvadba.tomas.celldetection.converter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import com.nesvadba.tomas.celldetection.domain.ImageFile;
import com.nesvadba.tomas.celldetection.enums.PointStatus;
import com.nesvadba.tomas.celldetection.util.Point;

public class ImgConverter {

    private static final Logger LOGGER = Logger.getLogger(ImgConverter.class);

    public static BufferedImage mat2BufferedImage(Mat m) {
	LOGGER.debug("-- Converting Mat2BufferedImage --");

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
	LOGGER.debug("-- Converting file2ImageFile --");
	ImageFile imageFile = new ImageFile();
	imageFile.setFile(file);

	return imageFile;
    }

    public static List<ImageFile> file2ImageFile(List<File> files) {
	LOGGER.debug("-- Converting file2ImageFile --");
	List<ImageFile> imageFiles = new ArrayList<>();

	for (File file : files) {
	    ImageFile imageFile = new ImageFile();
	    imageFile.setFile(file);
	    imageFiles.add(imageFile);
	}

	return imageFiles;
    }

    public static Point[][] mat2Array(Mat initMat) {
	LOGGER.debug("-- Converting mat2Array --");
	Point[][] points = new Point[initMat.rows()][initMat.cols()];
	for (int row = 0; row < initMat.rows(); row++) {
	    for (int col = 0; col < initMat.cols(); col++) {
		Point p = new Point();
		p.x = row;
		p.y = col;
		p.status = PointStatus.U;
		p.value = Double.valueOf(initMat.get(row, col)[0]).intValue();
		points[row][col] = p;
	    }
	}

	return points;

    }

    public static Mat setToMat(Size matSize, Set<Point> points) {
	LOGGER.debug("-- Converting setToMat --");
	Mat targetMat = new Mat(matSize, CvType.CV_8U);
	for (Point p : points) {
	    targetMat.put(p.x, p.y, p.value);
	}

	return targetMat;

    }

}
