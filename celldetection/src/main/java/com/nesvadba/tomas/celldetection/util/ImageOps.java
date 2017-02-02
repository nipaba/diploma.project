package com.nesvadba.tomas.celldetection.util;

import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.nesvadba.tomas.celldetection.domain.ImageStats;
import com.nesvadba.tomas.celldetection.domain.Yeast;

public class ImageOps {

    private static final Logger LOGGER = Logger.getLogger(ImageOps.class);

    private static Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(new Point(13.0, 13.0)));

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
	Imgproc.erode(src, dst, kernel);
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

    public static ImageStats getBasicSegmentation(Mat src) {
	Long startTime = System.currentTimeMillis();

	Mat gray = new Mat(src.size(), CvType.CV_8UC3);

	Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);

	Mat denoise = new Mat();
	Imgproc.GaussianBlur(gray, denoise, new Size(new Point(15.0, 15.0)), 5);

	Mat bin = new Mat();
	Imgproc.threshold(denoise, bin, 0, 255, Imgproc.THRESH_TRIANGLE + Imgproc.THRESH_BINARY);

	Mat dst = distanceTransform(invert(bin));
	dst.convertTo(dst, CvType.CV_8UC1);

	Mat topHap = new Mat();
	Imgproc.morphologyEx(bin, topHap, Imgproc.MORPH_ERODE, Mat.ones(new Size(3, 3), CvType.CV_8U));

	Core.absdiff(bin, topHap, topHap);

	Mat segmentedOrig = new Mat();
	Core.add(topHap, gray, segmentedOrig, new Mat(), CvType.CV_8UC3);
	double maxSum = Core.norm(segmentedOrig, Core.NORM_INF);

	ImageStats stats = getStats(bin);
	stats.setSegmentedImg(dst);

	LOGGER.debug("getFlou2Stats time :" + (System.currentTimeMillis() - startTime));
	return stats;//
    }

    /**
     * Fuknce urcena k zistakni statistik o celém obrazu ve chvili kdy je
     * segmentace hotová - lze použít connected components.
     * 
     * @param src
     * @return
     * 
     */
    public static ImageStats getStats(Mat src) {

	Mat labels = new Mat();
	Mat stats = new Mat();
	Mat centroids = new Mat();
	Imgproc.connectedComponentsWithStats(src, labels, stats, centroids);
	labels.convertTo(labels, CvType.CV_8UC1);

	ImageStats imageStats = new ImageStats();

	for (int count = 0; count < stats.size().height; count++) {

	    Yeast yeast = new Yeast();
	    yeast.setId(count);
	    yeast.getParametrs().put("CC_STAT_LEFT", stats.get(count, 0)[0]);
	    yeast.getParametrs().put("CC_STAT_TOP", stats.get(count, 1)[0]);
	    yeast.getParametrs().put("CC_STAT_WIDTH", stats.get(count, 2)[0]);
	    yeast.getParametrs().put("CC_STAT_HEIGHT", stats.get(count, 3)[0]);
	    yeast.getParametrs().put("CC_STAT_AREA", stats.get(count, 4)[0]);

	    yeast.getParametrs().put("Centr X", centroids.get(count, 0)[0]);
	    yeast.getParametrs().put("Centr Y", centroids.get(count, 1)[0]);

	    imageStats.getYeasts().add(yeast);

	}
	imageStats.setLabels(labels);

	return imageStats;
    }

    // Mat gray = new Mat(src.size(), CvType.CV_8UC3);
    //
    // Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);
    // LOGGER.debug("1. Grayscale " + CvType.typeToString(gray.type()));
    // //
    // =========================================================================================
    // Mat denoise = new Mat();
    // Imgproc.GaussianBlur(gray, denoise, new Size(new Point(15.0, 15.0)),
    // 5);
    // LOGGER.debug("2. Denoise " + CvType.typeToString(denoise.type()));
    // //
    // =========================================================================================
    // Mat bin = new Mat();
    // Imgproc.threshold(denoise, bin, 0, 255, Imgproc.THRESH_TRIANGLE);
    // LOGGER.debug("3. Threshold " + CvType.typeToString(bin.type()));
    // //
    // =========================================================================================
    // Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new
    // Size(3, 3)); // 19,19
    // Mat ret = new Mat(src.size(), CvType.CV_8U);
    // Imgproc.morphologyEx(bin, ret, Imgproc.MORPH_OPEN, kernel);
    //
    // // Sure background area
    // Mat sure_bg = new Mat(src.size(), CvType.CV_8U);
    // Imgproc.dilate(ret, sure_bg, new Mat(), new Point(-1, -1), 3);
    // Imgproc.threshold(sure_bg, sure_bg, 1, 128,
    // Imgproc.THRESH_BINARY_INV);
    //
    // Mat sure_fg = new Mat(src.size(), CvType.CV_8U);
    // Imgproc.distanceTransform(bin, sure_fg, Imgproc.CV_DIST_L2, 5);
    // sure_fg.convertTo(sure_fg, CvType.CV_8UC1);
    // Imgproc.threshold(sure_fg, sure_fg, 10, 255,
    // Imgproc.THRESH_TRIANGLE);
    //
    // Mat markers = new Mat(src.size(), CvType.CV_8U, new Scalar(0));
    // Core.add(sure_fg, sure_bg, markers);
    //
    // markers.convertTo(markers, CvType.CV_32SC1);
    //
    // Imgproc.watershed(src, markers);
    // Core.convertScaleAbs(markers, markers);
    // LOGGER.debug("9. convertScaleAbs " +
    // CvType.typeToString(markers.type()));
    //
    // Set<Double> set = new HashSet<>();
    // for (int row = 0; row < markers.rows(); row++) {
    // for (int col = 0; col < markers.cols(); col++) {
    // short aaa[] = new short[5];
    // double[] a = markers.get(row, col);
    // set.add(a[0]);
    // }
    // }
    // LOGGER.debug(set);
    // return markers;

}
