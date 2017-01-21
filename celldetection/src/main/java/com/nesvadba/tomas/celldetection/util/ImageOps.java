package com.nesvadba.tomas.celldetection.util;

import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ImageOps {

    private static final Logger LOGGER = Logger.getLogger(ImageOps.class);
    private static Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(new Point(12.0, 12.0)));
    private static Mat kernel2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(new Point(13.0, 13.0)));

    public static Mat denoise(Mat src) {
	Mat dst = new Mat();
	Imgproc.GaussianBlur(src, dst, new Size(new Point(5.0, 5.0)), 3);
	return dst;
    }

    public static Mat getTriangleTreshold(Mat src) {
	Mat dst = new Mat();
	Imgproc.threshold(src, dst, 10, 255, Imgproc.THRESH_TRIANGLE);
	return dst;

    }

    public static Mat getTreshold(Mat src) {
	Mat dst = new Mat();

	return dst;

    }

    public static Mat getGrayScale(Mat src) {
	Mat dst = new Mat();
	Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
	return dst;

    }

    public static Mat closing(Mat src) {
	Mat dst = new Mat();
	Imgproc.morphologyEx(src, dst, Imgproc.MORPH_CLOSE, kernel);
	return dst;

    }

    public static Mat openening(Mat src) {
	Mat dst = new Mat();
	Imgproc.morphologyEx(src, dst, Imgproc.MORPH_OPEN, kernel);
	return dst;
    }

    public static Mat erosion(Mat src) {
	Mat dst = new Mat();
	Imgproc.erode(src, dst, kernel2);
	return dst;
    }

    public static Mat dilation(Mat src) {
	Mat dst = new Mat();
	Imgproc.dilate(src, dst, kernel);
	return dst;
    }

    public static Mat invert(Mat src) {
	Mat dst = new Mat();
	Mat ones = Mat.ones(src.size(), CvType.CV_8U);
	ones.setTo(new Scalar(255.0), ones);
	Core.absdiff(ones, src, dst);
	dst.convertTo(dst, CvType.CV_8U);
	return dst;
    }

    public static Mat distanceTransform(Mat src) {
	Mat dst = new Mat();
	Imgproc.distanceTransform(src, dst, Imgproc.CV_DIST_L2, 5);
	return dst;
    }

    public static Mat whaterShaded(Mat src) {

	Mat labels = new Mat();
	Mat dst = new Mat();
	src.convertTo(dst, CvType.CV_8UC3);
	Imgproc.connectedComponents(dst, labels, 8, CvType.CV_32SC1);

	Mat labels2 = new Mat();
	Mat dst2 = new Mat(src.size(), CvType.CV_8UC3);
	labels.convertTo(labels2, CvType.CV_32SC1);

	// Imgproc.watershed(dst2, labels2);

	labels.convertTo(labels, CvType.CV_8UC3);

	return labels;

    }

}
