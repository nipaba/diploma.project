package com.nesvadba.tomas.celldetection.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;

public class CCTops {

    private static final Logger LOGGER = Logger.getLogger(CCTops.class);

    Map<Integer, Queue<Point>> quegeMap = new HashMap<>();
    // Body ke zkoumani
    Point[][] points;

    // AKA labels
    Map<Integer, Integer> number_nodes = new HashMap<>();
    // Mapa vsech uzlu - postupne vytvarime a spojujeme
    Map<String, ConnectedComponentsTree> createdNodes = new HashMap<>();

    ConnectedComponentsTree cct = null;

    public ConnectedComponentsTree temp(Mat initMat) {

	// points = new Point[initMat.rows()][initMat.cols()];
	points = new Point[20][40];
	for (int row = 0; row < 20; row++) {
	    for (int col = 0; col < 40; col++) {
		// for (int row = 0; row < initMat.rows(); row++) {
		// for (int col = 0; col < initMat.cols(); col++) {
		Point p = new Point();
		p.x = row;
		p.y = col;
		p.status = PointStatus.U;
		p.value = Double.valueOf(initMat.get(row, col)[0]).intValue() - 100;
		points[row][col] = p;
	    }
	}

	initNumberNodes(256);

	print();
	proccess(points[0][0], 0);
	cct.print("");
	return null;
    }

    private void initNumberNodes(int layerCount) {
	for (int i = 0; i < layerCount; i++) {
	    number_nodes.put(i, 0);
	}

    }

    private void proccess(Point p, int level) {

	int m = p.value; // hodnota bodu p
	addToQueue(p);
	while (m >= level) {
	    m = flood(m);
	}
    }

    private int flood(int level) {
	LOGGER.debug("LEVEL " + level);
	// print();
	Queue<Point> queue = quegeMap.get(level);

	while (!queue.isEmpty()) {
	    Point p = queue.peek();

	    // get All Neigborous
	    for (Point n : getNeigb(p)) {
		getNode(level, "point" + p).getPoints().add(p);

		if (n.value > p.value && n.status == PointStatus.U) {
		    LOGGER.debug("Found upperLevel" + p);
		    proccess(n, level);

		} else if (n.status == PointStatus.U) {
		    addToQueue(n);
		}
	    }

	    p.status = PointStatus.A;
	    print();
	    p.status = PointStatus.P;

	    queue.remove(p);

	}
	int m = level - 1;
	LOGGER.debug("m : " + m);
	while (m >= 0 && (quegeMap.get(m) == null || quegeMap.get(m).isEmpty())) {
	    m--;
	}
	LOGGER.debug("LEVEL " + level);
	if (m >= 0) {
	    LOGGER.debug("Parent " + level + "," + number_nodes.get(level) + "<- C " + m + "," + number_nodes.get(m));
	    // parent(Clevel,numbernodes(level)) = C m, numbernodes(m) level
	    // M =parent, child=levelu "#" + label;
	    ConnectedComponentsTree parent = getNode(m, "parent");
	    ConnectedComponentsTree child = getNode(level, "child");
	    parent.getNodes().put(number_nodes.get(level), child);
	    parent.print("");
	    cct = parent;

	} else {
	    LOGGER.debug("Parent " + level + "," + number_nodes.get(level) + "<- end");
	}

	increaseNumberNodes(level);
	printLabels();
	return m;
    }

    public ConnectedComponentsTree getNode(int level, String msg) {
	String key = level + "#" + number_nodes.get(level);
	ConnectedComponentsTree node = createdNodes.get(key);
	if (node == null) {
	    LOGGER.debug("created " + key + " - " + msg);
	    node = new ConnectedComponentsTree(level, number_nodes.get(level));
	    createdNodes.put(key, node);
	}
	return node;
    }

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

    private void print() {
	System.out.println();

	for (int col = 0; col < points[0].length; col++) {
	    System.out.printf("%03d|", col);

	}
	System.out
		.println("\n-----------------------------------------------------------------------------------------");
	for (int row = 0; row < points.length; row++) {
	    for (int col = 0; col < points[0].length; col++) {
		System.out.print(points[row][col].value + "|");

	    }
	    System.out.print("  ");
	    for (int col = 0; col < points[0].length; col++) {
		System.out.print(points[row][col].status + "|");

	    }
	    System.out.println();
	}
    }

    private void printLabels() {
	for (int i = 0; i < number_nodes.size(); i++) {
	    if (number_nodes.get(i) > 0) {
		System.out.print(i + "=" + number_nodes.get(i) + ", ");
	    }
	}
	System.out.println();
    }

}
