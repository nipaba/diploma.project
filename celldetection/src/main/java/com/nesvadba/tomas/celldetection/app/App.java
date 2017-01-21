package com.nesvadba.tomas.celldetection.app;

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

	// TODO - ZPRACOVAT RUZNE podle arhc
	// System.load("C:\\Program Files\\opencv\\build\\java\\x64\\" +
	// Core.NATIVE_LIBRARY_NAME + ".dll");

	System.load("D:\\GIT\\DIPLOMA\\celldetection\\opencv\\x64\\" + Core.NATIVE_LIBRARY_NAME + ".dll");
    }

    public static void main(String[] args) {

	Architecture arch = ArchitectureLoader.loadArchitecture();
	LOGGER.debug(arch);
	loadOpenCvLibrary(arch);

	MainWindow mw = new MainWindow();
	mw.show(true);

    }

}
