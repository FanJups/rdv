package biz.advanceitgroup.rdvserver.jobs.dto;

import java.time.ZonedDateTime;
import java.util.List;

import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.JobStatus;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 13/02/2020 10:28
 */
public class FindJobDto {

    private Long jobID;
    private Long categoryID;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    List<JobStatus> lstStatus;
    private Long providerID;
    private Long bidWorkerID;
    private Long attributedWorkerID;
    private boolean isReportedAsAbuse;
    private boolean isConflicted;
    private boolean requireInsurance;
    private double searchScope;
    private String sortBy;
    private int pageSize;
    private int pageNumber;
    private String codeIsoLang;

    public FindJobDto() {
    }

    public FindJobDto(Long jobID, Long categoryID, ZonedDateTime startDate, ZonedDateTime endDate, List<JobStatus> lstStatus, Long providerID, Long bidWorkerID, Long attributedWorkerID,
                      boolean isReportedAsAbuse, boolean isConflicted, boolean requireInsurance, double searchScope, String sortBy, int pageSize, int pageNumber, String codeIsoLang) {

        this.jobID = jobID;
        this.categoryID = categoryID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lstStatus = lstStatus;
        this.providerID = providerID;
        this.bidWorkerID = bidWorkerID;
        this.attributedWorkerID = attributedWorkerID;
        this.isReportedAsAbuse = isReportedAsAbuse;
        this.isConflicted = isConflicted;
        this.requireInsurance = requireInsurance;
        this.searchScope = searchScope;
        this.sortBy = sortBy;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.codeIsoLang = codeIsoLang;
    }

    public Long getJobID() {
        return jobID;
    }

    public void setJobID(Long jobID) {
        this.jobID = jobID;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
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

    public List<JobStatus> getLstStatus() {
        return lstStatus;
    }

    public void setLstStatus(List<JobStatus> lstStatus) {
        this.lstStatus = lstStatus;
    }

    public Long getProviderID() {
        return providerID;
    }

    public void setProviderID(Long providerID) {
        this.providerID = providerID;
    }

    public Long getBidWorkerID() {
        return bidWorkerID;
    }

    public void setBidWorkerID(Long bidWorkerID) {
        this.bidWorkerID = bidWorkerID;
    }

    public Long getAttributedWorkerID() {
        return attributedWorkerID;
    }

    public void setAttributedWorkerID(Long attributedWorkerID) {
        this.attributedWorkerID = attributedWorkerID;
    }

    public boolean isReportedAsAbuse() {
        return isReportedAsAbuse;
    }

    public void setReportedAsAbuse(boolean reportedAsAbuse) {
        isReportedAsAbuse = reportedAsAbuse;
    }

    public boolean isConflicted() {
        return isConflicted;
    }

    public void setConflicted(boolean conflicted) {
        isConflicted = conflicted;
    }

    public boolean isRequireInsurance() {
        return requireInsurance;
    }

    public void setRequireInsurance(boolean requireInsurance) {
        this.requireInsurance = requireInsurance;
    }

    public double getSearchScope() {
        return searchScope;
    }

    public void setSearchScope(double searchScope) {
        this.searchScope = searchScope;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCodeIsoLang() {
        return codeIsoLang;
    }

    public void setCodeIsoLang(String codeIsoLang) {
        this.codeIsoLang = codeIsoLang;
    }
}
