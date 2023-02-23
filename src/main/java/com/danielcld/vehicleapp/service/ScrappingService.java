package com.danielcld.vehicleapp.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.danielcld.vehicleapp.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class ScrappingService {

    private String productionYear;
    private String brand;
    private String model;
    private String currentMileage;
    private String insuranceStatus;
    private String insuranceDate;
    private String inspectionStatus;
    private String inspectionDate;
    private String engineCapacity;
    private String enginePower;
    private String fuelType;
    private String vehicleWeight;


    public ScrappingService() {
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(String currentMileage) {
        this.currentMileage = currentMileage;
    }

    public String getInsuranceStatus() {
        return insuranceStatus;
    }

    public void setInsuranceStatus(String insuranceStatus) {
        this.insuranceStatus = insuranceStatus;
    }

    public String getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(String insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(String vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public Vehicle downloadVehicleDetails(Vehicle vehicle) throws Exception {

        submittingForm(vehicle.getRegistrationNumber(), vehicle.getVinNumber(), vehicle.getFirstRegistration());

        vehicle.setBrand(this.getBrand());
        vehicle.setModel(this.getModel());
        vehicle.setProductionYear(this.getProductionYear());
        vehicle.setMileage(this.getCurrentMileage());
        vehicle.setInsuranceDate(this.getInsuranceDate());
        vehicle.setInsuranceStatus(this.getInsuranceStatus());
        vehicle.setInspectionStatus(this.getInspectionStatus());
        vehicle.setEngineCapacity(this.getEngineCapacity());
        vehicle.setEnginePower(this.getEnginePower());
        vehicle.setFuelType(this.getFuelType());

        return vehicle;


    }

    public void submittingForm(String plates, String vin, String firstReg) throws Exception {
        try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_68)) {
            webClient.getOptions().setUseInsecureSSL(true);


            final HtmlPage page1 = webClient.getPage("https://historiapojazdu.gov.pl/");

            final HtmlForm form = page1.getFormByName("_historiapojazduportlet_WAR_historiapojazduportlet_:formularz");

            final HtmlSubmitInput button = form.getInputByName("_historiapojazduportlet_WAR_historiapojazduportlet_:btnSprawdz");
            final HtmlTextInput textField1 = form.getInputByName("_historiapojazduportlet_WAR_historiapojazduportlet_:rej");
            final HtmlTextInput textField2 = form.getInputByName("_historiapojazduportlet_WAR_historiapojazduportlet_:vin");
            final HtmlTextInput textField3 = form.getInputByName("_historiapojazduportlet_WAR_historiapojazduportlet_:data");

            textField1.setValueAttribute(plates);
            textField2.setValueAttribute(vin);
            textField3.setValueAttribute(firstReg);

            final HtmlPage page2 = button.click();

            DomElement productionYear = page2.getFirstByXPath("//*[@id='_historiapojazduportlet_WAR_historiapojazduportlet_:j_idt10']/div[2]/p[1]/span");
            this.productionYear = productionYear.getTextContent();

            DomElement brand = page2.getFirstByXPath("//*[@id='_historiapojazduportlet_WAR_historiapojazduportlet_:j_idt10:marka']");
            this.brand = brand.getTextContent();

            DomElement model = page2.getFirstByXPath("//*[@id='_historiapojazduportlet_WAR_historiapojazduportlet_:j_idt10:model']");
            this.model = model.getTextContent();

            DomElement mileage = page2.getFirstByXPath("//*[@id='timeline-summary-box']/div[2]/p[7]/span");
            this.currentMileage = mileage.getTextContent();

            DomElement insuranceStatus = page2.getFirstByXPath("//*[@id='timeline-summary-box']/div[2]/p[4]/span");
            this.insuranceStatus = insuranceStatus.getTextContent();

            DomElement insuranceDate = page2.getFirstByXPath("//*[@id='timeline-summary-box']/div[2]/p[5]/span");
            this.insuranceDate = insuranceDate.getTextContent();

            DomElement inspectionStatus = page2.getFirstByXPath("//*[@id='timeline-summary-box']/div[2]/p[6]/span");
            this.inspectionStatus = inspectionStatus.getTextContent();

            DomElement engineCapacity = page2.getFirstByXPath("//*[@id='_historiapojazduportlet_WAR_historiapojazduportlet_']/div[3]/section[2]/div[1]/div[1]/p[1]/strong/span");
            this.engineCapacity = engineCapacity.getTextContent();

            DomElement enginePower = page2.getFirstByXPath("//*[@id='_historiapojazduportlet_WAR_historiapojazduportlet_']/div[3]/section[2]/div[1]/div[1]/p[2]/strong/span");
            this.enginePower = enginePower.getTextContent();

            DomElement fuelType = page2.getFirstByXPath("//*[@id='_historiapojazduportlet_WAR_historiapojazduportlet_']/div[3]/section[2]/div[1]/div[1]/p[3]/strong/span");
            this.fuelType = fuelType.getTextContent();

            DomElement vehicleWeight = page2.getFirstByXPath("//*[@id='_historiapojazduportlet_WAR_historiapojazduportlet_']/div[3]/section[2]/div[1]/div[3]/p[1]/strong/span");
            this.vehicleWeight = vehicleWeight.getTextContent();

        }
    }
}
