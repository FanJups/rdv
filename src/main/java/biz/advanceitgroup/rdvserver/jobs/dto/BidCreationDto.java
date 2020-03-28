package biz.advanceitgroup.rdvserver.jobs.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 12/02/2020 16:48
 */
public class BidCreationDto {

    private Long JobId;
    private String bidDescription;
    private double bidOfferAmount;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String videoLink;
    private String codeIsoLang;

    public BidCreationDto() {
    }

    public BidCreationDto(Long jobId) {
        JobId = jobId;
    }

    public BidCreationDto(Long jobId, String bidDescription, double bidOfferAmount, ZonedDateTime startDate, ZonedDateTime endDate, String videoLink, String codeIsoLang) {
        JobId = jobId;
        this.bidDescription = bidDescription;
        this.bidOfferAmount = bidOfferAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.videoLink = videoLink;
        this.codeIsoLang = codeIsoLang;
    }

    public Long getJobId() {
        return JobId;
    }

    public void setJobId(Long jobId) {
        JobId = jobId;
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

    public String getCodeIsoLang() {
        return codeIsoLang;
    }

    public void setCodeIsoLang(String codeIsoLang) {
        this.codeIsoLang = codeIsoLang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidCreationDto that = (BidCreationDto) o;
        return Double.compare(that.bidOfferAmount, bidOfferAmount) == 0 &&
                Objects.equals(JobId, that.JobId) &&
                Objects.equals(bidDescription, that.bidDescription) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(videoLink, that.videoLink) &&
                Objects.equals(codeIsoLang, that.codeIsoLang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(JobId, bidDescription, bidOfferAmount, startDate, endDate, videoLink, codeIsoLang);
    }

    @Override
    public String toString() {
        return "BidCreationDto{" +
                "JobId=" + JobId +
                ", bidDescription='" + bidDescription + '\'' +
                ", bidOfferAmount=" + bidOfferAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", videoLink='" + videoLink + '\'' +
                ", codeIsoLang='" + codeIsoLang + '\'' +
                '}';
    }
}
