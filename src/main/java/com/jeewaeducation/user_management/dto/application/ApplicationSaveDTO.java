package com.jeewaeducation.user_management.dto.application;

import com.jeewaeducation.user_management.entity.Reception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSaveDTO {
    private Date applicationDate;
    private String applicationTitle;
    private Date dateOfBirth;
    private String placeOfBirth;
    private int age;
    private String familyName;
    private String givenName;
    private String gender;
    private String passportOrNic;
    private String relationshipStatus;
    private Date dateOfMarriage;
    private int numberOfChildren;
    private int mobileContactNumber;
    private int homeContactNumber;
    private String email;
    private String postalAddress;
    private String schoolAttended;
    private Date olExamYear;
    private String olExamDetails;
    private Date alExamYear;
    private String alExamDetails;
    private Date degreeYear;
    private String degreeDetails;
    private Date experienceYear;
    private String experienceDetails;
    private Date ieltsPteYear;
    private String ieltsPteDetails;
    private String preferredAreaOfStudy;
    private boolean appliedForVisaBefore;
    private String countryVisaTypeApplied;
    private boolean visaRefusals;
    private String visaRefusalsDetails;
    private String sponsorRelationship;
    private String preferredStudyCountry;
    private String preferredCity;
    private String methodeOfKnowing;
    private int reception;
}
