package com.nesvadba.tomas.celldetection.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.nesvadba.tomas.celldetection.enums.ComponentProperty;

public class ConnectedComponentsTree {

    private static final Logger LOGGER = Logger.getLogger(ConnectedComponentsTree.class);

    private int level;
    private int label;

    private Set<Point> points = new HashSet<>();
    private Map<Integer, ConnectedComponentsTree> nodes = new HashMap<>();

    private Map<ComponentProperty, Integer> properties = new HashMap<>();

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

    public Set<Point> getPoints() {
	return points;
    }

    public void setPoints(Set<Point> points) {
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

    public void printPoints() {
	LOGGER.debug("POINTS : " + getCode() + points);
	for (ConnectedComponentsTree node : nodes.values()) {
	    node.printPoints();
	}

    }

    public String getCode() {
	return level + "#" + label;
    }

    public void recalculateProperties() {
	// size
	// roundness
	// ...
	List<Point> allPoints = getAllPointsOfLevel();
	calculateSize(allPoints);
    }

    private void calculateSize(List<Point> allPoints) {

	properties.put(ComponentProperty.SIZE, allPoints.size());
	LOGGER.debug("SIZE" + getCode() + "   " + properties.get(ComponentProperty.SIZE));
    }

    // Ziskame vsechny componenty s body
    public List<Point> getAllPointsOfLevel() {
	List<Point> allPoints = new ArrayList<>();
	allPoints.addAll(points);
	if (!nodes.isEmpty()) {
	    for (ConnectedComponentsTree node : nodes.values()) {
		allPoints.addAll(node.getAllPointsOfLevel());
	    }
	}
	return allPoints;
    }

}
