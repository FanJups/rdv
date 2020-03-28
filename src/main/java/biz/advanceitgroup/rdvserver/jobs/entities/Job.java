package biz.advanceitgroup.rdvserver.jobs.entities;


import biz.advanceitgroup.rdvserver.jobs.dto.JobDto;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.JobStatus;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.commons.entities.AbstractEntityModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 31/01/2020 16:08
 */

@Entity
@Data
// @Table(name = "job")
@AttributeOverride(name="id",column=@Column(name="jobID"))
public class Job extends AbstractEntityModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryID")
	@JsonIgnore
	private Category category;
	@Column
	private JobStatus jobStatus = JobStatus.WAITING;
	@Column
	private boolean isPublicJob;
	@Column
	private boolean isClosedJob;
	@Column
	private boolean isConflictedJob;
	@Column
	private boolean isReportedAsAbuseJob;
	@Column
	private String jobTitle;
	@Column
	private String jobDescription;
	@Column
	private String jobAddress;
	@Column
	private double minOfferAmount;
	@Column
	private double maxOfferAmount;
	@Column(columnDefinition = "date")
	private ZonedDateTime startDate;
	@Column(columnDefinition = "date")
	private ZonedDateTime endDate;
	@Column
	private int validityPeriod;
	@Column
	private String videoLink;
	@Column
	private boolean equireInsurance;
	@Column(columnDefinition = "date")
	private ZonedDateTime postDate = ZonedDateTime.now();
	@Column(columnDefinition = "date")
	private ZonedDateTime acceptedDate;
	@Column(columnDefinition = "date")
	private ZonedDateTime attributedDate;
	@Column(columnDefinition = "date")
	private ZonedDateTime conflictedDate;
	@Column(columnDefinition = "date")
	private ZonedDateTime closedDate;
	@Column(columnDefinition = "date")
	private ZonedDateTime cancelledDate;
	@Column
	private Long attributedBidID;
	@Column
	private Long acceptedBidID;
	@Column
	@JsonIgnore
	private String workerValidationKeyPart;
	@Column
	@JsonIgnore
	private String providerValidationKeyPart;
	@Column
	private Long jobViewCount;
	@Column
	private Long jobBidCount;
	@Column
	private Long abuseReportCount;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "job", orphanRemoval = true)
	private List<User> lstWorker = new ArrayList<>();

	public Job() {
		super();
	}

	public Job(JobDto job) {
		super();
		setId(job.getId());
		setCategory(new Category(job.getId()));
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
		this.workerValidationKeyPart = job.getWorkerValidationKeyPart();
		this.providerValidationKeyPart = job.getProviderValidationKeyPart();
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public boolean issClosedJob() {
		return isClosedJob;
	}

	public void setsClosedJob(boolean isClosedJob) {
		this.isClosedJob = isClosedJob;
	}

	public boolean isConflictedJob() {
		return isConflictedJob;
	}

	public void setConflictedJob(boolean conflictedJob) {
		isConflictedJob = conflictedJob;
	}

	public boolean isReportedAsAbuseJob() {
		return isReportedAsAbuseJob;
	}

	public void setReportedAsAbuseJob(boolean reportedAsAbuseJob) {
		isReportedAsAbuseJob = reportedAsAbuseJob;
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

	public ZonedDateTime getPostDate() {
		return postDate;
	}

	public void setPostDate(ZonedDateTime postDate) {
		this.postDate = postDate;
	}

	public ZonedDateTime getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(ZonedDateTime acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public ZonedDateTime getAttributedDate() {
		return attributedDate;
	}

	public void setAttributedDate(ZonedDateTime attributedDate) {
		this.attributedDate = attributedDate;
	}

	public ZonedDateTime getConflictedDate() {
		return conflictedDate;
	}

	public void setConflictedDate(ZonedDateTime conflictedDate) {
		this.conflictedDate = conflictedDate;
	}

	public ZonedDateTime getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(ZonedDateTime closedDate) {
		this.closedDate = closedDate;
	}

	public ZonedDateTime getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(ZonedDateTime cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public Long getAttributedBidID() {
		return attributedBidID;
	}

	public void setAttributedBidID(Long attributedBidID) {
		this.attributedBidID = attributedBidID;
	}

	public Long getAcceptedBidID() {
		return acceptedBidID;
	}

	public void setAcceptedBidID(Long acceptedBidID) {
		this.acceptedBidID = acceptedBidID;
	}

	public String getWorkerValidationKeyPart() {
		return workerValidationKeyPart;
	}

	public void setWorkerValidationKeyPart(String workerValidationKeyPart) {
		this.workerValidationKeyPart = workerValidationKeyPart;
	}

	public String getProviderValidationKeyPart() {
		return providerValidationKeyPart;
	}

	public void setProviderValidationKeyPart(String providerValidationKeyPart) {
		this.providerValidationKeyPart = providerValidationKeyPart;
	}

	public Long getJobViewCount() {
		return jobViewCount;
	}

	public void setJobViewCount(Long jobViewCount) {
		this.jobViewCount = jobViewCount;
	}

	public Long getJobBidCount() {
		return jobBidCount;
	}

	public void setJobBidCount(Long jobBidCount) {
		this.jobBidCount = jobBidCount;
	}

	public Long getAbuseReportCount() {
		return abuseReportCount;
	}

	public void setAbuseReportCount(Long abuseReportCount) {
		this.abuseReportCount = abuseReportCount;
	}

	public List<User> getLstWorker() { return lstWorker; }

	public void setLstWorker(List<User> lstWorker) { this.lstWorker = lstWorker; }

	public void addWorker(User worker) { this.lstWorker.add(worker); }

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Category category = (Category) o;
		if(category.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), category.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Job{" +
				"category=" + category +
				", jobStatus=" + jobStatus +
				", isPublicJob=" + isPublicJob +
				", isClosedJob=" + isClosedJob +
				", isConflictedJob=" + isConflictedJob +
				", isReportedAsAbuseJob=" + isReportedAsAbuseJob +
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
				", postDate=" + postDate +
				", acceptedDate=" + acceptedDate +
				", attributedDate=" + attributedDate +
				", conflictedDate=" + conflictedDate +
				", closedDate=" + closedDate +
				", cancelledDate=" + cancelledDate +
				", attributedBidID=" + attributedBidID +
				", acceptedBidID=" + acceptedBidID +
				", workerValidationKeyPart='" + workerValidationKeyPart + '\'' +
				", providerValidationKeyPart='" + providerValidationKeyPart + '\'' +
				", jobViewCount=" + jobViewCount +
				", jobBidCount=" + jobBidCount +
				", abuseReportCount=" + abuseReportCount +
				'}';
	}
}
