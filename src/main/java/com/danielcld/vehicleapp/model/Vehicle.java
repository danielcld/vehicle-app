package com.danielcld.vehicleapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vehicle_id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "podaj nazwe pojazdu")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "production_year")
    private String productionYear;

    @Column(name = "vin_number")
    private String vinNumber;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "firstRegistration")
    private String firstRegistration;

    @Column(name = "mileage")
    private String mileage;

    @Column(name = "insurance_date")
    private String insuranceDate;

    @Column(name = "inspection_date")
    private String inspectionDate;

    @Column(name = "inspection_status")
    private String inspectionStatus;

    @Column(name = "insurance_status")
    private String insuranceStatus;

    @Column(name = "engine_capacity")
    private String engineCapacity;

    @Column(name = "engine_power")
    private String enginePower;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "vehicle_weight")
    private String vehicleWeight;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Repair> repairs;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Reminder reminder;


}
