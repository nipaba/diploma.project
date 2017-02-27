package com.nesvadba.tomas.celldetection.util;

import com.nesvadba.tomas.celldetection.enums.PointStatus;

public class Point {

    public int x;
    public int y;
    public PointStatus status;
    public int value;

    @Override
    public String toString() {
	return "Point [x=" + x + ", y=" + y + ", value=" + value + "]";
    }

}
