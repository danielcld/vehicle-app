package com.danielcld.vehicleapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reminders")
public class Reminder {

    public Reminder(Boolean active, Boolean inspection, Boolean insurance, Vehicle vehicle) {
        this.id = id;
        this.active = active;
        this.inspection = inspection;
        this.insurance = insurance;
        this.vehicle = vehicle;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reminder_id")
    private Integer id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "inspection")
    private Boolean inspection;

    @Column(name = "insurance")
    private Boolean insurance;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vehicle_id", updatable = false)
    private Vehicle vehicle;
}
