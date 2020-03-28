package biz.advanceitgroup.rdvserver.jobs.entities;


import biz.advanceitgroup.rdvserver.jobs.dto.BidCreationDto;
import biz.advanceitgroup.rdvserver.jobs.dto.BidDto;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.BidStatus;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.commons.entities.AbstractEntityModel;
import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 12/02/2020 09:58
 */
@Entity

@AttributeOverride(name="id",column=@Column(name="bidID"))
public class Bid extends AbstractEntityModel {

	@Column
	private Long jobID;


	@Column
	@Size(max = 500)
	private String bidDescription;

	@Column
	private double bidOfferAmount;

	@Column
	private ZonedDateTime startDate;

	@Column
	private ZonedDateTime endDate;

	@Column
	@Size(max = 500)
	private String videoLink;

	@Column
	private BidStatus bidStatus = BidStatus.WAITING;

	@Column
	private boolean isConflictedBid = false;

	@Column
	private boolean isReportedAsAbuseBid = false;

	@Column
	private ZonedDateTime bidDate;

	@Column
	private ZonedDateTime acceptedDate;

	@Column
	private ZonedDateTime attributedDate;

	@Column
	private ZonedDateTime conflictedDate;

	@Column
	private ZonedDateTime closedDate;

	@Column
	private ZonedDateTime cancelledDate;

	@Column
	private Long abuseReportCount;
	
	@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID",referencedColumnName="id")
	private User userID;

	public Bid() {
		super();
	}

	public Bid(BidDto bid) {
		super();
		this.setId(bid.getBidID());
		this.jobID = bid.getJobID();
		this.bidDescription = bid.getBidDescription();
		this.bidOfferAmount = bid.getBidOfferAmount();
		this.startDate = bid.getStartDate();
		this.endDate = bid.getEndDate();
		this.videoLink = bid.getVideoLink();
		this.bidStatus = bid.getBidStatus();
		this.isConflictedBid = bid.isConflictedBid();
		this.isReportedAsAbuseBid = bid.isReportedAsAbuseBid();
		this.bidDate = bid.getBidDate();
		this.acceptedDate = bid.getAcceptedDate();
		this.attributedDate = bid.getAttributedDate();
		this.conflictedDate = bid.getConflictedDate();
		this.closedDate = bid.getClosedDate();
		this.cancelledDate = bid.getCancelledDate();
		abuseReportCount = bid.getAbuseReportCount();
	}

	public Bid(Long bidId) {
		super();
		this.setId(bidId);
	}

	public Bid(BidCreationDto bidCreationDto) {
		super();
		this.jobID = bidCreationDto.getJobId();
		this.bidDescription = bidCreationDto.getBidDescription();
		this.bidOfferAmount = bidCreationDto.getBidOfferAmount();
		this.startDate = bidCreationDto.getStartDate();
		this.endDate = bidCreationDto.getEndDate();
		this.videoLink = bidCreationDto.getVideoLink();
		this.bidStatus = BidStatus.WAITING;
		this.bidDate = ZonedDateTime.now(ZoneId.systemDefault());
	}

	public Long getJobID() {
		return jobID;
	}

	public void setJobID(Long jobID) {
		this.jobID = jobID;
	}

	public String getBidDescription() {
		return bidDescription;
	}

	public void setBidDescription(String bidDescription) {
		this.bidDescription = bidDescription;
	}

	public double getBidOfferAmount() {
		return bidOfferAmount;
	}

	public void setBidOfferAmount(double bidOfferAmount) {
		this.bidOfferAmount = bidOfferAmount;
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

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public BidStatus getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(BidStatus bidStatus) {
		this.bidStatus = bidStatus;
	}

	public boolean isConflictedBid() {
		return isConflictedBid;
	}

	public void setConflictedBid(boolean conflictedBid) {
		isConflictedBid = conflictedBid;
	}

	public boolean isReportedAsAbuseBid() {
		return isReportedAsAbuseBid;
	}

	public void setReportedAsAbuseBid(boolean reportedAsAbuseBid) {
		isReportedAsAbuseBid = reportedAsAbuseBid;
	}

	public ZonedDateTime getBidDate() {
		return bidDate;
	}

	public void setBidDate(ZonedDateTime bidDate) {
		this.bidDate = bidDate;
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

	public Long getAbuseReportCount() { return abuseReportCount; }

	public void setAbuseReportCount(Long abuseReportCount) { this.abuseReportCount = abuseReportCount; }

	
	/**
	 * @return the userID
	 */
	public User getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(User userID) {
		this.userID = userID;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Bid bid = (Bid) o;
		return Objects.equals(getId(), bid.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return "Bid{" +
				"bidID=" + getId() +
				", jobID='" + jobID + '\'' +
				", bidDescription='" + bidDescription + '\'' +
				", bidOfferAmount=" + bidOfferAmount +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", videoLink='" + videoLink + '\'' +
				", abuseReportCount=" + abuseReportCount +
				", bidStatus=" + bidStatus +
				", isConflictedBid=" + isConflictedBid +
				", isReportedAsAbuseBid=" + isReportedAsAbuseBid +
				", bidDate=" + bidDate +
				", acceptedDate=" + acceptedDate +
				", attributedDate=" + attributedDate +
				", conflictedDate=" + conflictedDate +
				", closedDate=" + closedDate +
				", cancelledDate=" + cancelledDate +
				'}';
	}
}
