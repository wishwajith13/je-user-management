package com.jeewaeducation.user_management.dto.application;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationUpdateDTO {
    private int applicationId;
    @NotNull(message = "Application date is required")
    private Date applicationDate;
    @NotEmpty(message = "Title is required")
    private String title;
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;
    @Size(min = 2, message = "Place of Birth must contain at least 2 characters")
    private String placeOfBirth;
    @Min(value = 0, message = "Age must be a positive number")
    private int age;
    @Size(min = 2, message = "Family Name must contain at least 2 characters")
    private String familyName;
    @Size(min = 2, message = "Given Name must contain at least 2 characters")
    private String givenName;
    @NotEmpty(message = "Gender is required")
    private String gender;
    @Size(min = 2, message = "Passport or Nic must contain at least 2 characters")
    private String passportOrNic;
    @NotEmpty(message = "Relationship status is required")
    private String relationshipStatus;
    private Date dateOfMarriage;
    @Min(value = 0, message = "Number of children must be a positive number")
    private int numberOfChildren;
    @NotEmpty(message = "Mobile contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile contact number must be 10 digits")
    private String mobileContactNumber;
    @Pattern(regexp = "^[0-9]{10}$", message = "Home contact number must be 10 digits")
    private String homeContactNumber;
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 2, message = "Postal Address must contain at least 2 characters")
    private String postalAddress;
    @Size(min = 2, message = "School Attended must contain at least 2 characters")
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
    @NotEmpty(message = "Sponsor Relationship is required")
    private List<String> sponsorRelationship;
    @NotEmpty(message = "Preferred Study Country is required")
    private List<String> preferredStudyCountry;
    private String preferredStudyCountryOther;
    private List<String> preferredCity;
    @NotEmpty(message = "Methode Of Knowing is required")
    private List<String> methodeOfKnowing;
    private boolean isVerified;
    @Min(value = 0, message = "Reception must be a positive number")
    private int reception;
}

