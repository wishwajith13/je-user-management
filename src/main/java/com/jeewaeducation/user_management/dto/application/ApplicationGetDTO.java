package com.jeewaeducation.user_management.dto.application;


import com.jeewaeducation.user_management.dto.reception.ReceptionForApplicationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationGetDTO {
    private int applicationId;
    private Date applicationDate;
    private String title;
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
    private String mobileContactNumber;
    private String homeContactNumber;
    private String email;
    private String postalAddress;
    private String schoolAttended;
    private String olExamType;
    private Date olExamStartYear;
    private Date olExamEndYear;
    private String olExamDetails;
    private String alExamType;
    private Date alExamStartYear;
    private Date alExamEndYear;
    private String alExamDetails;
    private String degreeType;
    private Date degreeStartYear;
    private Date degreeEndYear;
    private String degreeDetails;
    private String otherType;
    private Date otherStartYear;
    private Date otherEndYear;
    private String otherDetails;
    private String experienceType;
    private Date experienceStartYear;
    private Date experienceEndYear;
    private String experienceDetails;
    private String ieltsPteType;
    private Date ieltsPteStartYear;
    private Date ieltsPteEndYear;
    private String ieltsPteDetails;
    private String preferredAreaOfStudy;
    private boolean appliedForVisaBefore;
    private String countryVisaTypeApplied;
    private boolean visaRefusals;
    private String visaRefusalsDetails;
    private List<String> sponsorRelationship;
    private List<String> preferredStudyCountry;
    private String preferredStudyCountryOther;
    private List<String> preferredCity;
    private List<String> methodeOfKnowing;
    private boolean isVerified;
    private ReceptionForApplicationDTO reception;
    private int counselorId;
}
