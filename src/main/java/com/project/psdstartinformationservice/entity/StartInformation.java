package com.project.psdstartinformationservice.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "start_information")
public class StartInformation {

    @Id
    @Column(name = "start_information_id")
    private short startInformationId;
    @Column(name = "name")
    private String name;
    @Column(name = "power")
    private float activePower;
    @Column(name = "amount")
    private short amount;
    @Column(name = "k_i")
    private float ki;
    @Column(name = "cos_f")
    private float cosf;
    @Column(name = "tg_f")
    private float tgf;
    @Column(name = "avg_daily_active_power")
    private float avgDailyActivePower;
    @Column(name = "avg_daily_reactive_power")
    private float avgDailyReactivePower;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartInformation that = (StartInformation) o;
        return startInformationId == that.startInformationId && Float.compare(activePower, that.activePower) == 0 && amount == that.amount && Float.compare(ki, that.ki) == 0 && Float.compare(cosf, that.cosf) == 0 && Float.compare(tgf, that.tgf) == 0 && Float.compare(avgDailyActivePower, that.avgDailyActivePower) == 0 && Float.compare(avgDailyReactivePower, that.avgDailyReactivePower) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startInformationId, name, activePower, amount, ki, cosf, tgf, avgDailyActivePower, avgDailyReactivePower);
    }
}
