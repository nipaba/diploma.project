package com.nesvadba.tomas.celldetection.util;

import com.nesvadba.tomas.celldetection.domain.Architecture;

public class ArchitectureLoader {

    public static Architecture loadArchitecture() {

	Architecture arch = new Architecture();

	arch.setOsArchitecture(System.getProperty("os.arch"));
	arch.setOsName(System.getProperty("os.name"));
	arch.setOsVersion(System.getProperty("os.version"));
	arch.setOsDataModel(System.getProperty("sun.arch.data.model"));

	return arch;
    }

}
