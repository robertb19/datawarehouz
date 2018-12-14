package com.data.datawarehouseapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Data
@ToString
@Entity
@Table
public class Apartment extends LivingSpace {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(unique = true)
    String title;

    @Column
    String location;

    @Column(name = "number_of_rooms")
    int numberOfRooms;

    @Column
    double area;

    @Column(name = "price_per_meter_squared")
    double pricePerMeterSquared;

    @Column
    double price;

    @Column(name="rent")
    boolean isToRent;
}
