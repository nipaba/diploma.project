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
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.nesvadba.tomas.celldetection.converter.ImgConverter;
import com.nesvadba.tomas.celldetection.domain.ImageFile;
import com.nesvadba.tomas.celldetection.domain.ImageStats;
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
	return ImgConverter.file2ImageFile(

		// FolderLoader.loadImageFiles(new
		// File("C:\\Users\\nipaba\\Documents\\Diplomka\\img\\NAK"),
		// false));
		// FolderLoader.loadImageFiles(new
		// File("D:\\GIT\\DIPLOMA\\celldetection\\img\\Fluo2"), false));
		FolderLoader.loadImageFiles(
			new File("C:\\Users\\nipaba\\Documents\\GIT\\project\\celldetection\\img\\NAK"), false));

    }

    public ImageIcon getInitImage(ImageFile imageFile) {
	LOGGER.debug(imageFile);
	ImageIcon imageIcon = null;

	Imgcodecs.imread(imageFile.getFile().getAbsolutePath());

	if (imageFile.getData().isEmpty()) {
	    Mat image = Imgcodecs.imread(imageFile.getFile().getAbsolutePath());
	    imageFile.getData().put(ImageType.INITIAL, image);
	    imageIcon = new ImageIcon(ImgConverter.Mat2BufferedImage(image));
	} else {
	    imageIcon = new ImageIcon(ImgConverter.Mat2BufferedImage(imageFile.getData().get(ImageType.INITIAL)));
	}
	LOGGER.debug("Data for image loaded [" + imageFile.getFile().getAbsolutePath() + "]");

	return imageIcon;

    }

    public ImageIcon getGrayScaleImage(ImageFile file) {
	Mat mat = ImageOps.getGrayScale(file.getData().get(ImageType.DENOISE));
	file.getData().put(ImageType.GRAYSCALE, mat);
	return new ImageIcon(ImgConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getBinaryImage(ImageFile file) {
	Mat mat = ImageOps.getTriangleTreshold(file.getData().get(ImageType.GRAYSCALE));
	file.getData().put(ImageType.BINARY, mat);
	return new ImageIcon(ImgConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getCloseImage(ImageFile file) {
	Mat mat = ImageOps.closing(file.getData().get(ImageType.BINARY));
	file.getData().put(ImageType.CLOSE, mat);
	return new ImageIcon(ImgConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getOpenImage(ImageFile file) {
	Mat mat = ImageOps.openening(file.getData().get(ImageType.BINARY));
	file.getData().put(ImageType.CLOSE, mat);
	return new ImageIcon(ImgConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getDilateImage(ImageFile file) {
	Mat mat = ImageOps.dilation(file.getData().get(ImageType.BINARY));
	file.getData().put(ImageType.CLOSE, mat);
	return new ImageIcon(ImgConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getErodeImage(ImageFile file) {
	Mat mat = ImageOps.erosion(file.getData().get(ImageType.BINARY));
	file.getData().put(ImageType.CLOSE, mat);
	return new ImageIcon(ImgConverter.Mat2BufferedImage(mat));
    }

    public ImageIcon getDenoisedImage(ImageFile file) {
	Mat mat = ImageOps.denoise(file.getData().get(ImageType.INITIAL));
	file.getData().put(ImageType.DENOISE, mat);
	return new ImageIcon(ImgConverter.Mat2BufferedImage(mat));
    }

    public void proccessBasic(ImageFile file) {

	Mat init = file.getData().get(ImageType.INITIAL);

	ImageStats stats = ImageOps.getBasicSegmentation(init);

	Mat segmented = stats.getSegmentedImg();
	Mat labels = stats.getLabels();

	file.getData().put(ImageType.SEGMENTED, segmented);
	file.getData().put(ImageType.LABELS, labels);

	file.setYeasts(stats.getYeasts());

    }

}
