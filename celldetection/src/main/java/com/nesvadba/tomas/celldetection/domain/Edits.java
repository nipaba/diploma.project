package com.nesvadba.tomas.celldetection.domain;

import java.util.ArrayList;
import java.util.List;

public class Edits {

    public List<EditType> getNAK_edit() {

	List<EditType> steps = new ArrayList<>();

	steps.add(EditType.GAUSFILTER);
	steps.add(EditType.GRAYSCALE);
	steps.add(EditType.BINARY);
	steps.add(EditType.CLOSE);

	return steps;
    }
}
