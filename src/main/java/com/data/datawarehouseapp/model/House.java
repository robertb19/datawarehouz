package com.data.datawarehouseapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Data
@ToString
@Entity
@Table
public class House extends LivingSpace {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(unique = true)
    String title;

    @Column
    String location;

    @Column(name="number_of_rooms")
    int numberOfRooms;

    @Column
    double area;

    @Column(name="area_of_field")
    double areaOfField;

    @Column
    double price;

    @Column(name="rent")
    boolean isToRent;
}
