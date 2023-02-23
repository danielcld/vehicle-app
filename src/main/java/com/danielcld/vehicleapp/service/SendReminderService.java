package com.danielcld.vehicleapp.service;


import com.danielcld.vehicleapp.model.User;
import com.danielcld.vehicleapp.model.Vehicle;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
@Service
public class SendReminderService {

    @Autowired
    private VehicleService vehicleService;

    private EmailService emailService;

    public void sendEmail(String userEmail, String userName, String reason) throws UnsupportedEncodingException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress("powiadomienie@pojazdy.com", "Powiadomienie"))
                .to(newArrayList(new InternetAddress(userEmail, userName)))
                .subject("Wymagane dzialanie")
                .body(reason)
                .encoding("UTF-8").build();
        emailService.send(email);
    }

    public void update() throws Exception {

        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        for (int i = 0; i < vehicles.size(); i++) {
            ScrappingService scrappingService = new ScrappingService();

            if (vehicles.get(i).getRegistrationNumber() != null && vehicles.get(i).getFirstRegistration() != null && vehicles.get(i).getVinNumber() != null) {
                scrappingService.submittingForm(vehicles.get(i).getRegistrationNumber(), vehicles.get(i).getVinNumber(), vehicles.get(i).getFirstRegistration());
                vehicles.get(i).setInsuranceStatus(scrappingService.getInsuranceStatus());
                vehicles.get(i).setInspectionStatus(scrappingService.getInspectionStatus());
                vehicles.get(i).setInsuranceDate(scrappingService.getInsuranceDate());
                vehicles.get(i).setMileage(scrappingService.getCurrentMileage());
            }
        }
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void checkStatus() throws Exception {
        update();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            User user = vehicles.get(i).getUser();
            if (vehicles.get(i).getReminder().getActive() == true) {
                if (vehicles.get(i).getInspectionStatus() != "aktualna" && vehicles.get(i).getReminder().getInspection() == true) {
                    sendEmail(user.getEmail(), user.getUserName(), "Koniec waznosci polisy w pojezdzie " + vehicles.get(i).getName());
                }
                if (vehicles.get(i).getInsuranceStatus() != "aktualne" && vehicles.get(i).getReminder().getInsurance() == true) {
                    sendEmail(user.getEmail(), user.getUserName(), "Koniec waznosci przegladu w pojezdzie" + vehicles.get(i).getName());
                }
            }
        }
    }
}
