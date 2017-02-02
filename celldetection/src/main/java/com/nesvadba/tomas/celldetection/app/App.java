package com.nesvadba.tomas.celldetection.app;

import java.io.File;

import org.apache.log4j.Logger;
import org.opencv.core.Core;

import com.nesvadba.tomas.celldetection.domain.Architecture;
import com.nesvadba.tomas.celldetection.gui.MainWindow;
import com.nesvadba.tomas.celldetection.util.ArchitectureLoader;

/**
 * 
 * @author nipaba
 *
 */
public class App {

    private final static Logger LOGGER = Logger.getLogger(App.class);

    private static void loadOpenCvLibrary(Architecture arch) {

	File opencvLib;
	if (arch.getOsName().contains("Windows")) {
	    LOGGER.debug("Detected windows architecture");
	    if ("64".equals(arch.getOsDataModel())) {
		LOGGER.debug("Detected 64bit architecture");
		opencvLib = new File("lib\\opencv\\x64"); // TODO -
							  // CONFIGURATION

	    } else {
		LOGGER.debug("Detected 32bit architecture");
		opencvLib = new File("lib\\opencv\\x86");// TODO - CONFIGURATION
	    }

	    System.load(opencvLib.getAbsolutePath() + "\\" + Core.NATIVE_LIBRARY_NAME + ".dll");
	}

    }

    public static void main(String[] args) {

	Architecture arch = ArchitectureLoader.loadArchitecture();
	LOGGER.debug(arch);
	loadOpenCvLibrary(arch);

	MainWindow mw = new MainWindow();
	mw.show(true);

    }

}
