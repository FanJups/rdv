package biz.advanceitgroup.rdvserver.jobs.dto;


import biz.advanceitgroup.rdvserver.jobs.entities.Job;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.JobStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.ZonedDateTime;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 31/01/2020 16:22
 */

public class JobDto {

    private Long id;
    private Long categoryID;
    private JobStatus jobStatus = JobStatus.WAITING;
    private boolean isPublicJob;
    private String jobTitle;
    private String jobDescription;
    private String jobAddress;
    private double minOfferAmount;
    private double maxOfferAmount;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private int validityPeriod;
    private String videoLink;
    private boolean equireInsurance;
    @JsonIgnore
    private String workerValidationKeyPart;
    @JsonIgnore
    private String providerValidationKeyPart;

    public JobDto() {
    }

    public JobDto(Long id, Long categoryID, JobStatus jobStatus, boolean isPublicJob, String jobTitle, String jobDescription) {
        this.id = id;
        this.categoryID = categoryID;
        this.jobStatus = jobStatus;
        this.isPublicJob = isPublicJob;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
    }

    public JobDto(Job job) {
        this.id = job.getId();
        this.categoryID = job.getCategory().getId();
        this.jobStatus = job.getJobStatus();
        this.isPublicJob = job.isPublicJob();
        this.jobTitle = job.getJobTitle();
        this.jobDescription = job.getJobDescription();
        this.jobAddress = job.getJobAddress();
        this.minOfferAmount = job.getMinOfferAmount();
        this.maxOfferAmount = job.getMaxOfferAmount();
        this.startDate = job.getStartDate();
        this.endDate = job.getEndDate();
        this.validityPeriod = job.getValidityPeriod();
        this.videoLink = job.getVideoLink();
        this.equireInsurance = job.isEquireInsurance();
        this.setWorkerValidationKeyPart(job.getWorkerValidationKeyPart());
        this.setProviderValidationKeyPart(job.getProviderValidationKeyPart());
    }

    public JobDto(Long id, Long categoryID, JobStatus jobStatus, boolean isPublicJob, String jobTitle, String jobDescription, String jobAddress, double minOfferAmount, double maxOfferAmount, ZonedDateTime startDate, ZonedDateTime endDate, int validityPeriod, String videoLink, boolean equireInsurance) {
        this.id = id;
        this.categoryID = categoryID;
        this.jobStatus = jobStatus;
        this.isPublicJob = isPublicJob;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobAddress = jobAddress;
        this.minOfferAmount = minOfferAmount;
        this.maxOfferAmount = maxOfferAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.validityPeriod = validityPeriod;
        this.videoLink = videoLink;
        this.equireInsurance = equireInsurance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public boolean isPublicJob() {
        return isPublicJob;
    }

    public void setPublicJob(boolean publicJob) {
        isPublicJob = publicJob;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(String jobAddress) {
        this.jobAddress = jobAddress;
    }

    public double getMinOfferAmount() {
        return minOfferAmount;
    }

    public void setMinOfferAmount(double minOfferAmount) {
        this.minOfferAmount = minOfferAmount;
    }

    public double getMaxOfferAmount() {
        return maxOfferAmount;
    }

    public void setMaxOfferAmount(double maxOfferAmount) {
        this.maxOfferAmount = maxOfferAmount;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public boolean isEquireInsurance() {
        return equireInsurance;
    }

    public void setEquireInsurance(boolean equireInsurance) {
        this.equireInsurance = equireInsurance;
    }

    public String getWorkerValidationKeyPart() { return workerValidationKeyPart; }

    public void setWorkerValidationKeyPart(String workerValidationKeyPart) { this.workerValidationKeyPart = workerValidationKeyPart; }

    public String getProviderValidationKeyPart() { return providerValidationKeyPart; }

    public void setProviderValidationKeyPart(String providerValidationKeyPart) { this.providerValidationKeyPart = providerValidationKeyPart; }

    @Override
    public String toString() {
        return "JobDto{" +
                "id=" + id +
                ", categoryID=" + categoryID +
                ", jobStatus=" + jobStatus +
                ", isPublicJob=" + isPublicJob +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobAddress='" + jobAddress + '\'' +
                ", minOfferAmount=" + minOfferAmount +
                ", maxOfferAmount=" + maxOfferAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", validityPeriod=" + validityPeriod +
                ", videoLink='" + videoLink + '\'' +
                ", equireInsurance=" + equireInsurance +
                '}';
    }
}

