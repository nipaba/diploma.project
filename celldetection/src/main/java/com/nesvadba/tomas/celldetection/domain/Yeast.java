/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nesvadba.tomas.celldetection.domain;

import java.util.List;
import java.util.Map;
import org.opencv.core.Point;

/**
 *
 * @author nipaba
 */
class Yeast {
    private int id;
    private Map<String, Integer> parametrs;
    private List<Point> bound;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Integer> getParametrs() {
        return parametrs;
    }

    public void setParametrs(Map<String, Integer> parametrs) {
        this.parametrs = parametrs;
    }

    public List<Point> getBound() {
        return bound;
    }

    public void setBound(List<Point> bound) {
        this.bound = bound;
    }

    @Override
    public String toString() {
        return "Yeast{" + "id=" + id + ", parametrs=" + parametrs + ", bound=" + bound + '}';
    }
    
    
}
