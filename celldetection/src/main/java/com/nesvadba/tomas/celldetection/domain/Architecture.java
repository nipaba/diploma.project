package com.nesvadba.tomas.celldetection.domain;

public class Architecture {

    private String osArchitecture;
    private String osName;
    private String osVersion;
    private String osDataModel;

    public String getOsArchitecture() {
	return osArchitecture;
    }

    public void setOsArchitecture(String osArchitecture) {
	this.osArchitecture = osArchitecture;
    }

    public String getOsName() {
	return osName;
    }

    public void setOsName(String osName) {
	this.osName = osName;
    }

    public String getOsVersion() {
	return osVersion;
    }

    public void setOsVersion(String osVersion) {
	this.osVersion = osVersion;
    }

    public String getOsDataModel() {
	return osDataModel;
    }

    public void setOsDataModel(String osDataModel) {
	this.osDataModel = osDataModel;
    }

    @Override
    public String toString() {
	return "Architecture [osArchitecture=" + osArchitecture + ", osName=" + osName + ", osVersion=" + osVersion
		+ ", osDataModel=" + osDataModel + "]";
    }

}
