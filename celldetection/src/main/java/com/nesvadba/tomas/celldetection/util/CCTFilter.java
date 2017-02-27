package com.nesvadba.tomas.celldetection.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.nesvadba.tomas.celldetection.enums.ComponentProperty;
import com.nesvadba.tomas.celldetection.enums.FilterProps;

public class CCTFilter {

    private static final Logger LOGGER = Logger.getLogger(CCTFilter.class);

    public Set<Point> filterByProperties(ConnectedComponentsTree cct, Map<FilterProps, Integer> filterProperties) {

	Set<ConnectedComponentsTree> filtered = filterBySize(filterProperties, cct.getAllNodes());

	Set<Point> filteredPoints = new HashSet<>();
	for (ConnectedComponentsTree node : filtered) {
	    filteredPoints.addAll(node.getPoints());
	}
	return filteredPoints;
    }

    private Set<ConnectedComponentsTree> filterBySize(Map<FilterProps, Integer> filterProperties,
	    Set<ConnectedComponentsTree> nodes) {

	int min = filterProperties.get(FilterProps.SIZE_MIN);
	int max = filterProperties.get(FilterProps.SIZE_MAX);
	LOGGER.debug("FILTER:  min=" + min + ", max=" + max);

	Set<ConnectedComponentsTree> filteredSet = new HashSet<>();
	for (ConnectedComponentsTree node : nodes) {
	    int size = node.getProperties().get(ComponentProperty.SIZE);

	    if (size >= min && size <= max) {
		filteredSet.add(node);
	    }
	}

	return filteredSet;
    }
}
