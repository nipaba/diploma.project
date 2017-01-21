/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nesvadba.tomas.celldetection.gui;

import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.nesvadba.tomas.celldetection.converter.ImageConverter;
import com.nesvadba.tomas.celldetection.domain.ImageFile;
import com.nesvadba.tomas.celldetection.enums.ImageType;
import com.nesvadba.tomas.celldetection.util.FolderLoader;
import com.nesvadba.tomas.celldetection.util.ImageOps;

/**
 *
 * @author nipaba
 */
public class MainWindowOperationsImpl {

    private static final Logger LOGGER = Logger.getLogger(MainWindowOperationsImpl.class);

    MainWindowOperationsImpl() {

    }

    public List<ImageFile> loadFolder(MainWindow mw) {

	// JFileChooser chooser = new JFileChooser();
	// chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	//
	// int returnVal = chooser.showOpenDialog(mw);
	// if (returnVal == JFileChooser.APPROVE_OPTION) {
	// System.out.println("You chose to open this file: " +
	// chooser.getSelectedFile().getName());
	// }
	//
	// return
	// ImageConverter.file2ImageFile(FolderLoader.loadImageFiles(chooser.getSelectedFile(),
	// false));
	return ImageConverter.file2ImageFile(
		FolderLoader.loadImageFiles(new File("C:\\Users\\nipaba\\Documents\\Diplomka\\img\\NAK"), false));

    }

    public ImageIcon getInitImage(ImageFile imageFile) {
	ImageIcon imageIcon = null;

	Imgcodecs.imread(imageFile.getFile().getAbsolutePath());

	if (imageFile.getData().isEmpty()) {
	    Mat image = Imgcodecs.imread(imageFile.getFile().getAbsolutePath());
	    imageFile.getData().put(ImageType.INITIAL, image);
	    imageIcon = new ImageIcon(ImageConverter.Mat2BufferedImage(image));
	} else {
	    imageIcon = new ImageIcon(ImageConverter.Mat2BufferedImage(imageFile.getData().get(0)));
	}
	LOGGER.debug("Data for image loaded [" + imageFile.getFile().getAbsolutePath() + "]");

	return imageIcon;

    }

    public ImageIcon getGrayScaleImage(ImageFile file) {
	Mat mat = ImageOps.getGrayScale(file.getData().get(ImageType.DENOISE));
	file.getData().put(ImageType.GRAYSCALE, mat);
	return new ImageIcon(ImageConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getBinaryImage(ImageFile file) {
	Mat mat = ImageOps.getTriangleTreshold(file.getData().get(ImageType.GRAYSCALE));
	file.getData().put(ImageType.BINARY, mat);
	return new ImageIcon(ImageConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getCloseImage(ImageFile file) {
	Mat mat = ImageOps.closing(file.getData().get(ImageType.BINARY));
	file.getData().put(ImageType.CLOSE, mat);
	return new ImageIcon(ImageConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getOpenImage(ImageFile file) {
	Mat mat = ImageOps.openening(file.getData().get(ImageType.BINARY));
	file.getData().put(ImageType.CLOSE, mat);
	return new ImageIcon(ImageConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getDilateImage(ImageFile file) {
	Mat mat = ImageOps.dilation(file.getData().get(ImageType.BINARY));
	file.getData().put(ImageType.CLOSE, mat);
	return new ImageIcon(ImageConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getErodeImage(ImageFile file) {
	Mat mat = ImageOps.erosion(file.getData().get(ImageType.BINARY));
	file.getData().put(ImageType.CLOSE, mat);
	return new ImageIcon(ImageConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getDenoisedImage(ImageFile file) {
	Mat mat = ImageOps.denoise(file.getData().get(ImageType.INITIAL));
	file.getData().put(ImageType.DENOISE, mat);
	return new ImageIcon(ImageConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getNakSegmentation(ImageFile file) {

	Mat init = file.getData().get(ImageType.INITIAL);

	Mat gray = ImageOps.getGrayScale(init);
	Mat denoise = ImageOps.denoise(gray);
	Mat bin = ImageOps.getTriangleTreshold(denoise);
	Mat close = ImageOps.closing(bin);
	Mat close2 = ImageOps.closing(close);
	Mat close3 = ImageOps.closing(close2);
	Mat invert = ImageOps.invert(close3);
	Mat dst = ImageOps.distanceTransform(invert);
	dst.convertTo(dst, CvType.CV_8U);
	Mat erode = ImageOps.erosion(dst);

	Mat bin2 = ImageOps.getTreshold(erode);
	Mat wathershed = ImageOps.whaterShaded(bin2);

	return new ImageIcon(ImageConverter.Mat2BufferedImage(bin2));

    }

}
