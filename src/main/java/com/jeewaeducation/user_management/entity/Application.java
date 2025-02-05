package com.jeewaeducation.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="application")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Application {
    @Id
    @Column(name="application_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int applicationId;
    @Column(name="application_date",nullable = false)
    private Date applicationDate;
    @Column(name="application_title")
    private String applicationTitle;
    @Column(name="date_of_birth",nullable = false)
    private Date dateOfBirth;
    @Column(name="place_of_birth")
    private String placeOfBirth;
    @Column(name = "age",nullable = false)
    private int age;
    @Column(name = "family_name",nullable = false)
    private String familyName;
    @Column(name = "given_name",nullable = false)
    private String givenName;
    @Column(name = "gender",nullable = false)
    private String gender;
    @Column(name = "passport_or_nic",nullable = false)
    private String passportOrNic;
    @Column(name = "relationship_status",nullable = false)
    private String relationshipStatus;
    @Column(name = "date_of_marriage")
    private Date dateOfMarriage;
    @Column(name = "number_of_children",nullable = false)
    private int numberOfChildren;
    @Column(name = "mobile_contact_number",nullable = false)
    private int mobileContactNumber;
    @Column(name = "home_contact_number")
    private int homeContactNumber;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "postal_address",nullable = false)
    private String postalAddress;
    @Column(name = "school_attended")
    private String schoolAttended;
    @Column(name = "ol_exam_year")
    private Date olExamYear;
    @Column(name = "ol_exam_details")
    private String olExamDetails;
    @Column(name = "al_exam_year")
    private Date alExamYear;
    @Column(name = "al_exam_details")
    private String alExamDetails;
    @Column(name = "degree_year")
    private Date degreeYear;
    @Column(name = "degree_details")
    private String degreeDetails;
    @Column(name = "experience_year")
    private Date experienceYear;
    @Column(name = "experience_details")
    private String experienceDetails;
    @Column(name = "ielts_pte_year")
    private Date ieltsPteYear;
    @Column(name = "ielts_pte_details")
    private String ieltsPteDetails;
    @Column(name = "preferred_area_of_study")
    private String preferredAreaOfStudy;
    @Column(name = "applied_for_visa_before",nullable = false)
    private boolean appliedForVisaBefore;
    @Column(name = "country_visa_type_appiled")
    private String countryVisaTypeApplied;
    @Column(name = "visa_refusals",nullable = false)
    private boolean visaRefusals;
    @Column(name = "visa_refusals_details")
    private String visaRefusalsDetails;
    @Column(name = "sponsor_relationship",nullable = false)
    private String sponsorRelationship;
    @Column(name = "preferred_study_country",nullable = false)
    private String preferredStudyCountry;
    @Column(name = "preferred_city")
    private String preferredCity;
    @Column(name = "methode_of_Knowing",nullable = false)
    private String methodeOfKnowing;

    @ManyToOne
    @JoinColumn(name = "reception_id")
    private Reception reception;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private Student student;







    //validate name and student id have to add
}
