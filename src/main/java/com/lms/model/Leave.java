package com.lms.model;

import java.io.InputStream;
import java.sql.Date;

public class Leave {
    private int id;
    private int studentId;
    private int facultyId;
    private String leaveType;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private String status;
    private String facultyComments;
    private Date appliedDate;
    private Date reviewedDate;
    private String studentName;
    private String facultyName;
    private String rollNumber;
    private InputStream medicalDoc;
    public InputStream getMedicalDoc() { return medicalDoc; }
    public void setMedicalDoc(InputStream medicalDoc) { this.medicalDoc = medicalDoc; }
    private boolean hasMedicalDoc;

    public boolean isHasMedicalDoc() {
        return hasMedicalDoc;
    }

    public void setHasMedicalDoc(boolean hasMedicalDoc) {
        this.hasMedicalDoc = hasMedicalDoc;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public int getFacultyId() {
        return facultyId;
    }
    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }
    public String getLeaveType() {
        return leaveType;
    }
    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
    public Date getFromDate() {
        return fromDate;
    }
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
    public Date getToDate() {
        return toDate;
    }
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getFacultyComments() {
        return facultyComments;
    }
    public void setFacultyComments(String facultyComments) {
        this.facultyComments = facultyComments;
    }
    public Date getAppliedDate() {
        return appliedDate;
    }
    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }
    public Date getReviewedDate() {
        return reviewedDate;
    }
    public void setReviewedDate(Date reviewedDate) {
        this.reviewedDate = reviewedDate;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getFacultyName() {
        return facultyName;
    }
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
    public String getRollNumber() {
        return rollNumber;
    }
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
}