package biz.advanceitgroup.rdvserver.jobs.dto;



import biz.advanceitgroup.rdvserver.jobs.entities.Bid;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.BidStatus;

import java.time.ZonedDateTime;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 12/02/2020 16:11
 */
public class BidDto {
    private Long bidID;
    private Long jobID;
    private String bidDescription;
    private double bidOfferAmount;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String videoLink;
    private BidStatus bidStatus;
    private boolean isConflictedBid;
    private boolean isReportedAsAbuseBid;
    private ZonedDateTime bidDate;
    private ZonedDateTime acceptedDate;
    private ZonedDateTime attributedDate;
    private ZonedDateTime conflictedDate;
    private ZonedDateTime closedDate;
    private ZonedDateTime cancelledDate;
    private Long abuseReportCount;

    public BidDto() {
    }

    public BidDto(Long bidID, Long jobID) {
        this.bidID = bidID;
        this.jobID = jobID;
    }

    public BidDto(Bid bid) {
        this.bidID = bid.getId();
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
        this.abuseReportCount = bid.getAbuseReportCount();
    }

    public BidDto(Long bidID, Long jobID, String bidDescription, double bidOfferAmount, ZonedDateTime startDate, ZonedDateTime endDate, String videoLink, BidStatus bidStatus, boolean isConflictedBid, boolean isReportedAsAbuseBid, ZonedDateTime bidDate, ZonedDateTime acceptedDate, ZonedDateTime attributedDate, ZonedDateTime conflictedDate, ZonedDateTime closedDate, ZonedDateTime cancelledDate) {
        this.bidID = bidID;
        this.jobID = jobID;
        this.bidDescription = bidDescription;
        this.bidOfferAmount = bidOfferAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.videoLink = videoLink;
        this.bidStatus = bidStatus;
        this.isConflictedBid = isConflictedBid;
        this.isReportedAsAbuseBid = isReportedAsAbuseBid;
        this.bidDate = bidDate;
        this.acceptedDate = acceptedDate;
        this.attributedDate = attributedDate;
        this.conflictedDate = conflictedDate;
        this.closedDate = closedDate;
        this.cancelledDate = cancelledDate;
    }

    public Long getBidID() {
        return bidID;
    }

    public void setBidID(Long bidID) {
        this.bidID = bidID;
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

    public Long getAbuseReportCount() {
        return abuseReportCount;
    }

    public void setAbuseReportCount(Long abuseReportCount) {
        this.abuseReportCount = abuseReportCount;
    }

    @Override
    public String toString() {
        return "BidDto{" +
                "bidID=" + bidID +
                ", jobID=" + jobID +
                ", abuseReportCount=" + abuseReportCount +
                ", bidDescription='" + bidDescription + '\'' +
                ", bidOfferAmount=" + bidOfferAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", videoLink='" + videoLink + '\'' +
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
