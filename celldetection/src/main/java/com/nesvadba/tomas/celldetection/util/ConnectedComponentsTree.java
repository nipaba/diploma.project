package com.nesvadba.tomas.celldetection.util;

import java.util.HashMap;
import java.util.HashSet;
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

    public Map<ComponentProperty, Integer> getProperties() {
	return properties;
    }

    public void setProperties(Map<ComponentProperty, Integer> properties) {
	this.properties = properties;
    }

    // ========================================================================================
    // CUSTOM
    // ========================================================================================
    public String getCode() {
	return level + "#" + label;
    }

    public Set<Point> getAllPoints() {
	Set<Point> allPoints = new HashSet<>();
	allPoints.addAll(points);
	if (!nodes.isEmpty()) {
	    for (ConnectedComponentsTree node : nodes.values()) {
		allPoints.addAll(node.getAllPoints());
	    }
	}
	return allPoints;
    }

    public Set<ConnectedComponentsTree> getAllNodes() {

	Set<ConnectedComponentsTree> allNodes = new HashSet<>();
	allNodes.add(this);
	if (!nodes.isEmpty()) {
	    for (ConnectedComponentsTree node : nodes.values()) {
		allNodes.addAll(node.getAllNodes());
	    }
	}
	return allNodes;

    }

    public int getNodeCount() {
	int count = 1;

	if (!nodes.isEmpty()) {
	    for (ConnectedComponentsTree node : nodes.values()) {
		count += node.getNodeCount();
	    }
	}
	return count;

    }

    // ========================================================================================
    // CALCULATION
    // ========================================================================================
    public Set<Point> recalculateProperties() {
	Set<Point> allPoints = new HashSet<>();
	allPoints.addAll(points);
	if (!nodes.isEmpty()) {
	    for (ConnectedComponentsTree node : nodes.values()) {
		allPoints.addAll(node.recalculateProperties());
	    }
	}

	CCTEvaluator.evaluatePropertiest(allPoints, properties);

	// LOGGER.debug(properties);
	return allPoints;

    }

    // ========================================================================================
    // Printing
    // ========================================================================================
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
}
