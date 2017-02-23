package com.nesvadba.tomas.celldetection.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Logger;

public class FolderLoader {

    private static final Logger LOGGER = Logger.getLogger(FolderLoader.class);

    @SuppressWarnings("unchecked")
    public static List<File> loadFolder(File folder, boolean proccesSubDirs) {

	List<File> allFiles = new ArrayList<>();
	allFiles.addAll(FileUtils.listFiles(folder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE));

	List<File> supportedFiles = filterSuportedFiles(allFiles);

	LOGGER.debug("Supported files loaded : " + supportedFiles.size());
	return supportedFiles;
    }

    public static List<File> loadImageFiles(File folder, boolean proccesSubDirs) {

	List<File> allFiles = new ArrayList<>();
	allFiles.addAll(FileUtils.listFiles(folder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE));

	return (filterSuportedFiles(allFiles));

    }

    private static List<File> filterSuportedFiles(List<File> files) {

	List<File> suported = new ArrayList<>();
	for (File f : files) {
	    if (!f.isDirectory() && isSupportedExt(f)) {
		suported.add(f);
	    }
	}

	return suported;
    }

    // TODO - Add other files
    private static boolean isSupportedExt(File f) {
	boolean isPng = f.getName().endsWith(".png");
	boolean isTif = f.getName().endsWith(".tif");
	return isPng || isTif;
    }

}
