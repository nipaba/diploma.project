package com.nesvadba.tomas.celldetection.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;

public class CCTGenerator {

    private static final Logger LOGGER = Logger.getLogger(CCTGenerator.class);

    private Map<Integer, Queue<Point>> quegeMap = new HashMap<>();
    private Point[][] points;
    private Map<Integer, Integer> number_nodes = new HashMap<>();
    private Map<String, ConnectedComponentsTree> createdNodes = new HashMap<>();

    private ConnectedComponentsTree cct = null;

    public ConnectedComponentsTree temp(Mat initMat) {

	initPoints(initMat);
	initNumberNodes(256);

	long startTime = System.currentTimeMillis();
	proccess(points[0][0], 0);
	LOGGER.debug("Proccessing time: " + (System.currentTimeMillis() - startTime));
	LOGGER.debug("Image Size : " + initMat.rows() * initMat.cols());

	return cct;
    }

    private void proccess(Point p, int level) {

	int m = p.value;
	addToQueue(p);
	while (m >= level) {
	    m = flood(m);
	}
    }

    private int flood(int level) {
	// LOGGER.debug("**********************************************************************************");
	// LOGGER.debug("LEVEL " + level);
	// print();
	Queue<Point> queue = quegeMap.get(level);

	while (!queue.isEmpty()) {
	    Point p = queue.peek();
	    getNode(level, "point" + p).getPoints().add(p);
	    // get All Neigborous
	    for (Point n : getNeigb(p)) {

		if (n.value > p.value && n.status == PointStatus.U) {
		    // LOGGER.debug("Found upperLevel" + p);
		    proccess(n, level);

		} else if (n.status == PointStatus.U) {
		    addToQueue(n);
		}
	    }

	    // p.status = PointStatus.A;
	    // print();
	    // p.status = PointStatus.P;

	    queue.remove(p);

	}
	int m = level - 1;
	// LOGGER.debug("m : " + m);
	while (m >= 0 && (quegeMap.get(m) == null || quegeMap.get(m).isEmpty())) {
	    m--;
	}
	// LOGGER.debug("LEVEL " + level);
	if (m >= 0) {
	    // LOGGER.debug("Parent " + level + "," + number_nodes.get(level) +
	    // "<- C " + m + "," + number_nodes.get(m));
	    ConnectedComponentsTree parent = getNode(m, "parent");
	    ConnectedComponentsTree child = getNode(level, "child");

	    if (!child.getPoints().isEmpty()) {
		parent.getNodes().put(number_nodes.get(level), child);

	    }
	    // parent.print("");
	    cct = parent;

	} else {
	    LOGGER.debug("Parent " + level + "," + number_nodes.get(level) + "<- end");
	}

	increaseNumberNodes(level);
	return m;
    }

    public ConnectedComponentsTree getNode(int level, String msg) {
	String key = level + "#" + number_nodes.get(level);
	ConnectedComponentsTree node = createdNodes.get(key);
	if (node == null) {
	    // LOGGER.debug("created " + key + " - " + msg);
	    node = new ConnectedComponentsTree(level, number_nodes.get(level));
	    createdNodes.put(key, node);
	}
	return node;
    }

    // ------------------------- HELP FUNCTIONS
    // --------------------------------------
    private List<Point> getNeigb(Point p) {

	List<Point> neigb = new ArrayList<>();
	if (p.x - 1 >= 0) {
	    neigb.add(points[p.x - 1][p.y]);
	}
	if (p.y - 1 >= 0) {
	    neigb.add(points[p.x][p.y - 1]);
	}
	if (p.x + 1 < points.length) {
	    neigb.add(points[p.x + 1][p.y]);
	}
	if (p.y + 1 < points[0].length) {
	    neigb.add(points[p.x][p.y + 1]);
	}
	return neigb;
    }

    private void increaseNumberNodes(int level) {
	if (number_nodes.get(level) == null) {
	    number_nodes.put(level, 1);
	} else {
	    number_nodes.put(level, number_nodes.get(level) + 1);
	}

    }

    private void addToQueue(Point n) {
	Queue<Point> queqe = quegeMap.get(n.value);
	if (queqe == null) {
	    queqe = new LinkedList<>();
	    quegeMap.put(n.value, queqe);
	}
	queqe.add(n);
	n.status = PointStatus.I;

    }

    // ------------------------------- INIT
    // ---------------------------------------------
    private void initPoints(Mat initMat) {
	points = new Point[initMat.rows()][initMat.cols()];
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

    }

    private void initNumberNodes(int layerCount) {
	for (int i = 0; i < layerCount; i++) {
	    number_nodes.put(i, 0);
	}

    }

}
