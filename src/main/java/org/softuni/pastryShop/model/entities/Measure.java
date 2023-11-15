package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="measures")
public class Measure extends BaseEntity{
    private String name;
    private String shortName;

}
