package com.nesvadba.tomas.celldetection.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Point3;

public class ConnectedComponentsTree {

    int level;
    List<Point3> points = new ArrayList<>();
    int label;
    Map<Integer, ConnectedComponentsTree> nodes = new HashMap();

    public void insertPointToNode(Point3 point, int inLevel, int inLabel) {
	ConnectedComponentsTree cct = findNode(inLevel, inLabel);
	if (cct == null) {
	    cct = createNode(inLevel, inLabel);
	}
	cct.insertPoint(point);
    }

    private void insertPoint(Point3 point) {
	points.add(point);
    }

    public ConnectedComponentsTree() {
	level = 0;
    }

    public ConnectedComponentsTree createNode(int inLevel, int inLabel) {

	ConnectedComponentsTree cct = new ConnectedComponentsTree(inLevel, inLabel);
	nodes.put(inLabel, cct);

	return cct;

    }

    public ConnectedComponentsTree(int inLevel, int inLabel) {
	level = inLevel;
	label = inLabel;
    }

    public ConnectedComponentsTree findNode(int inLevel, int inLabel) {
	ConnectedComponentsTree cct = null;

	if (level == inLabel && label == inLabel) {
	    cct = this;
	} else if (level < inLevel) {
	    cct = null;
	} else {

	    for (ConnectedComponentsTree cctNode : nodes.values()) {
		cct = cctNode.findNode(inLevel, inLabel);

		if (cct != null) {
		    break;
		}
	    }
	}

	return cct;
    }

}
