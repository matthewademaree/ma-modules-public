package com.serotonin.m2m2.mbus;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.serotonin.m2m2.web.mvc.rest.v1.csv.CSVColumnGetter;
import com.serotonin.m2m2.web.mvc.rest.v1.csv.CSVColumnSetter;
import com.serotonin.m2m2.web.mvc.rest.v1.csv.CSVEntity;
import com.serotonin.m2m2.web.mvc.rest.v1.model.dataPoint.PointLocatorModel;

@CSVEntity(typeName=MBusPointLocatorModelDefinition.TYPE_NAME)
public class MBusPointLocatorModel extends PointLocatorModel<MBusPointLocatorVO>{

	private MBusPointLocatorVO data;
	public MBusPointLocatorModel(MBusPointLocatorVO data) {
		super(data);
		this.data = data;
	}
	
	public MBusPointLocatorModel() {
		super(new MBusPointLocatorVO());
	}

	@Override
	public String getTypeName() {
		return MBusPointLocatorModelDefinition.TYPE_NAME;
	}
	
	@JsonGetter("address")
	@CSVColumnGetter(order=10, header="address")
	public byte getAddress() {
	    return this.data.getAddress();
	}

	@JsonSetter("address")
	@CSVColumnSetter(order=10, header="address")
	public void setAddress(byte address) {
	    this.data.setAddress(address);
	}

	@JsonGetter("difCode")
	@CSVColumnGetter(order=11, header="difCode")
	public String getDifCode() {
	    return this.data.getDifCode();
	}

	@JsonSetter("difCode")
	@CSVColumnSetter(order=11, header="difCode")
	public void setDifCode(String difCode) {
	    this.data.setDifCode(difCode);
	}

	@JsonGetter("functionField")
	@CSVColumnGetter(order=12, header="functionField")
	public String getFunctionField() {
	    return this.data.getFunctionField();
	}

	@JsonSetter("functionField")
	@CSVColumnSetter(order=12, header="functionField")
	public void setFunctionField(String functionField) {
	    this.data.setFunctionField(functionField);
	}

	@JsonGetter("deviceUnit")
	@CSVColumnGetter(order=13, header="deviceUnit")
	public int getDeviceUnit() {
	    return this.data.getDeviceUnit();
	}

	@JsonSetter("deviceUnit")
	@CSVColumnSetter(order=13, header="deviceUnit")
	public void setDeviceUnit(int deviceUnit) {
	    this.data.setDeviceUnit(deviceUnit);
	}

	@JsonGetter("tariff")
	@CSVColumnGetter(order=14, header="tariff")
	public int getTariff() {
	    return this.data.getTariff();
	}

	@JsonSetter("tariff")
	@CSVColumnSetter(order=14, header="tariff")
	public void setTariff(int tariff) {
	    this.data.setTariff(tariff);
	}

	@JsonGetter("storageNumber")
	@CSVColumnGetter(order=15, header="storageNumber")
	public long getStorageNumber() {
	    return this.data.getStorageNumber();
	}

	@JsonSetter("storageNumber")
	@CSVColumnSetter(order=15, header="storageNumber")
	public void setStorageNumber(long storageNumber) {
	    this.data.setStorageNumber(storageNumber);
	}

	@JsonGetter("vifType")
	@CSVColumnGetter(order=16, header="vifType")
	public String getVifType() {
	    return this.data.getVifType();
	}

	@JsonSetter("vifType")
	@CSVColumnSetter(order=16, header="vifType")
	public void setVifType(String vifType) {
	    this.data.setVifType(vifType);
	}

	@JsonGetter("vifLabel")
	@CSVColumnGetter(order=17, header="vifLabel")
	public String getVifLabel() {
	    return this.data.getVifLabel();
	}

	@JsonSetter("vifLabel")
	@CSVColumnSetter(order=17, header="vifLabel")
	public void setVifLabel(String vifLabel) {
	    this.data.setVifLabel(vifLabel);
	}

	@JsonGetter("unitOfMeasurement")
	@CSVColumnGetter(order=18, header="unitOfMeasurement")
	public String getUnitOfMeasurement() {
	    return this.data.getUnitOfMeasurement();
	}

	@JsonSetter("unitOfMeasurement")
	@CSVColumnSetter(order=18, header="unitOfMeasurement")
	public void setUnitOfMeasurement(String unitOfMeasurement) {
	    this.data.setUnitOfMeasurement(unitOfMeasurement);
	}

	@JsonGetter("siPrefix")
	@CSVColumnGetter(order=19, header="siPrefix")
	public String getSiPrefix() {
	    return this.data.getSiPrefix();
	}

	@JsonSetter("siPrefix")
	@CSVColumnSetter(order=19, header="siPrefix")
	public void setSiPrefix(String siPrefix) {
	    this.data.setSiPrefix(siPrefix);
	}

	@JsonGetter("exponent")
	@CSVColumnGetter(order=20, header="exponent")
	public Integer getExponent() {
	    return this.data.getExponent();
	}

	@JsonSetter("exponent")
	@CSVColumnSetter(order=20, header="exponent")
	public void setExponent(Integer exponent) {
	    this.data.setExponent(exponent);
	}

	@JsonGetter("vifeLabels")
	@CSVColumnGetter(order=21, header="vifeLabels")
	public String[] getVifeLabels() {
	    return this.data.getVifeLabels();
	}

	@JsonSetter("vifeLabels")
	@CSVColumnSetter(order=21, header="vifeLabels")
	public void setVifeLabels(String[] vifeLabels) {
	    this.data.setVifeLabels(vifeLabels);
	}

	@JsonGetter("vifeTypes")
	@CSVColumnGetter(order=22, header="vifeTypes")
	public String[] getVifeTypes() {
	    return this.data.getVifeTypes();
	}

	@JsonSetter("vifeTypes")
	@CSVColumnSetter(order=22, header="vifeTypes")
	public void setVifeTypes(String[] vifeTypes) {
	    this.data.setVifeTypes(vifeTypes);
	}

	@JsonGetter("medium")
	@CSVColumnGetter(order=23, header="medium")
	public String getMedium() {
	    return this.data.getMedium();
	}

	@JsonSetter("medium")
	@CSVColumnSetter(order=23, header="medium")
	public void setMedium(String medium) {
	    this.data.setMedium(medium);
	}

	@JsonGetter("responseFrame")
	@CSVColumnGetter(order=24, header="responseFrame")
	public String getResponseFrame() {
	    return this.data.getResponseFrame();
	}

	@JsonSetter("responseFrame")
	@CSVColumnSetter(order=24, header="responseFrame")
	public void setResponseFrame(String responseFrame) {
	    this.data.setResponseFrame(responseFrame);
	}

	@JsonGetter("version")
	@CSVColumnGetter(order=25, header="version")
	public byte getVersion() {
	    return this.data.getVersion();
	}

	@JsonSetter("version")
	@CSVColumnSetter(order=25, header="version")
	public void setVersion(byte version) {
	    this.data.setVersion(version);
	}

	@JsonGetter("identNumber")
	@CSVColumnGetter(order=26, header="identNumber")
	public int getIdentNumber() {
	    return this.data.getIdentNumber();
	}

	@JsonSetter("identNumber")
	@CSVColumnSetter(order=26, header="identNumber")
	public void setIdentNumber(int identNumber) {
	    this.data.setIdentNumber(identNumber);
	}

	@JsonGetter("manufacturer")
	@CSVColumnGetter(order=27, header="manufacturer")
	public String getManufacturer() {
	    return this.data.getManufacturer();
	}

	@JsonSetter("manufacturer")
	@CSVColumnSetter(order=27, header="manufacturer")
	public void setManufacturer(String manufacturer) {
	    this.data.setManufacturer(manufacturer);
	}

	@JsonGetter("addressing")
	@CSVColumnGetter(order=28, header="addressing")
	public String getAddressing() {
	    return this.data.getAddressing();
	}

	@JsonSetter("addressing")
	@CSVColumnSetter(order=28, header="addressing")
	public void setAddressing(String addressing) {
	    this.data.setAddressing(addressing);
	}



}
