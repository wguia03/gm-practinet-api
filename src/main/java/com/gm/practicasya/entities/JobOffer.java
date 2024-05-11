package com.gm.practicasya.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Integer companyId;


    private String title;
    private String subtitle;
    private int areaId;
    private String description;
    private int workingModalityId;
    private String jobAddress;
//    private String postalCode;
    private int districtId;
//    private int countryId;
    private String workingHours;
    private int contractTypeId;
    private float salary;
//    private Date hireDate;
    private Date publicationDate;
    private int vacancyQuantity;
//    private int experienceYears;
//    private int minimumAge;
//    private int maximumAge;
    private String requiredEducation;
    private int languageId;
    private int languageLevelId;
    private String skills;
    private boolean isActive;
}

/*
- ¿Cuál será el formato de las peticiones y respuestas?
- ¿Los errores se manejarán en el front con los códigos HTTP?
*/