package com.nesvadba.tomas.celldetection.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ConnectedComponentsTree {

    private static final Logger LOGGER = Logger.getLogger(ConnectedComponentsTree.class);

    private int level;
    private int label;

    private List<Point> points = new ArrayList<>();
    private Map<Integer, ConnectedComponentsTree> nodes = new HashMap<>();

    public ConnectedComponentsTree(int nodeLevel, Integer nodeLabel) {
	label = nodeLabel;
	level = nodeLevel;
    }

    public ConnectedComponentsTree insertLeafToNode(int leafLevel, Integer leafLabel, int nodeLevel,
	    Integer nodeLabel) {

	ConnectedComponentsTree parent = findNode(nodeLevel, nodeLabel);
	if (parent == null) {
	    parent = new ConnectedComponentsTree(nodeLevel, nodeLabel);
	}

	ConnectedComponentsTree child = findNode(leafLevel, leafLabel);
	if (child == null) {
	    child = new ConnectedComponentsTree(leafLevel, leafLabel);
	}
	parent.getNodes().put(leafLabel, child);

	return parent;
    }

    private ConnectedComponentsTree findNode(int nodeLevel, int nodeLabel) {

	if (level == nodeLevel && label == nodeLabel) {
	    return this;
	} else {
	    ConnectedComponentsTree cctSearched = null;
	    for (ConnectedComponentsTree node : nodes.values()) {
		cctSearched = node.findNode(nodeLevel, nodeLabel);
		if (cctSearched != null)
		    return cctSearched;
	    }
	}

	return null;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }

    public int getLabel() {
	return label;
    }

    public void setLabel(int label) {
	this.label = label;
    }

    public List<Point> getPoints() {
	return points;
    }

    public void setPoints(List<Point> points) {
	this.points = points;
    }

    public Map<Integer, ConnectedComponentsTree> getNodes() {
	return nodes;
    }

    public void setNodes(Map<Integer, ConnectedComponentsTree> nodes) {
	this.nodes = nodes;
    }

    public void print(String str) {
	String thisLevel = "[" + level + "|" + label + "/" + nodes.size() + "]";
	if (nodes.size() == 0) {
	    LOGGER.debug(str + thisLevel + points);
	} else {
	    for (ConnectedComponentsTree node : nodes.values()) {
		node.print(str + thisLevel);
	    }
	}
    }

    public String getCode() {
	return level + "#" + label;
    }

}
