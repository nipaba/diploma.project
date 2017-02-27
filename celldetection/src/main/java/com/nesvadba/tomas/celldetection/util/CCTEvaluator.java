package com.nesvadba.tomas.celldetection.util;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.nesvadba.tomas.celldetection.enums.ComponentProperty;

public class CCTEvaluator {

    ConnectedComponentsTree cct;

    private static final Logger LOGGER = Logger.getLogger(CCTEvaluator.class);

    public static void evaluatePropertiest(Set<Point> points, Map<ComponentProperty, Integer> properties) {
	calculateBB(points, properties);
	calculateSize(points, properties);

    }

    private static void calculateBB(Set<Point> points, Map<ComponentProperty, Integer> properties) {

	int left = Integer.MAX_VALUE;
	int right = Integer.MIN_VALUE;
	int up = Integer.MAX_VALUE;
	int down = Integer.MIN_VALUE;
	int centerX = 0;
	int centerY = 0;

	for (Point p : points) {
	    if (p.x < left) {
		left = p.x;
	    }
	    if (p.x > right) {
		right = p.x;
	    }
	    if (p.y < up) {
		up = p.y;
	    }
	    if (p.y > down) {
		down = p.y;
	    }

	    centerX += p.x;
	    centerY += p.y;
	}

	properties.put(ComponentProperty.LEFT, left);
	properties.put(ComponentProperty.RIGHT, right);
	properties.put(ComponentProperty.UP, up);
	properties.put(ComponentProperty.DOWN, down);
	properties.put(ComponentProperty.CENTERX, centerX / points.size());
	properties.put(ComponentProperty.CENTERY, centerY / points.size());
    }

    private static void calculateSize(Set<Point> points, Map<ComponentProperty, Integer> properties) {

	properties.put(ComponentProperty.SIZE, points.size());

    }
}
