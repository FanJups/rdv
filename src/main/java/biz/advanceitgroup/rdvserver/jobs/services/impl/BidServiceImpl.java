package biz.advanceitgroup.rdvserver.jobs.services.impl;

import biz.advanceitgroup.rdvserver.jobs.entities.Bid;
import biz.advanceitgroup.rdvserver.jobs.entities.Job;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.repositories.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.security.SecurityUtils;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import biz.advanceitgroup.rdvserver.jobs.dto.BidAcceptDto;
import biz.advanceitgroup.rdvserver.jobs.dto.BidConfirmDto;
import biz.advanceitgroup.rdvserver.jobs.dto.BidCreationDto;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.BidStatus;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.JobStatus;
import biz.advanceitgroup.rdvserver.jobs.repositories.BidRepository;
import biz.advanceitgroup.rdvserver.jobs.repositories.JobRepository;
import biz.advanceitgroup.rdvserver.jobs.services.interfaces.BidService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 12/02/2020 16:01
 */
@Service
public class BidServiceImpl implements BidService {

    private final Logger log = LoggerFactory.getLogger(BidServiceImpl.class);

    private final static String PAYMENT_MODE = "STRIPE";

    @Autowired
    BidRepository bidRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public Bid getBidById(Long bidId) {
        log.info("Get bid by Id from datasource");

        Bid bid = null;
        Optional<Bid> optBid = bidRepository.findById(bidId);
        if (optBid.isPresent()) {
            bid = optBid.get();
        }
        return bid;
    }

    @Override
    public Bid reportAnAbuseOnBid(Long bidId, String message, String codeIsoLang) {
        Bid bid = null;
        Optional<Bid> optBid = bidRepository.findById(bidId);
        if (optBid.isPresent()) {
            bid = optBid.get();
            bid.setReportedAsAbuseBid(true);
            // --- bid.setConflictedDate(ZonedDateTime.now(ZoneId.systemDefault()));
            bid.setAbuseReportCount(bid.getAbuseReportCount()+1);
            bid.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
            bid.setUpdateUserName(SecurityUtils.getCurrentUserLogin());
            bid = bidRepository.save(bid);
            // Sent email to the RDV administrator with the message
            List<User> admins = userService.findAllAdmin();
            if (admins != null && admins.size() != 0) {
                admins.forEach(admin -> { /* mailService.sendInvitationMail(admin, rdvNowProperties.getHosting().getUrl(), message, "en"); */ });
            }
        }
        return bid;
    }

    @Override
    public Bid createBid(BidCreationDto bidCreationDto) {
        log.info("Save bid and get the inserted item");

        Bid bid = new Bid(bidCreationDto);

        if(SecurityUtils.getCurrentUserLogin() != null) {
            bid.setCreateUserName(SecurityUtils.getCurrentUserLogin());
            bid.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        }
        bid = bidRepository.save(bid);
        return bid;
    }

    @Override
    public Bid acceptBid(Bid bid, BidAcceptDto bidAcceptDto) {
        log.info("Accept bid on a job");

        bid.setBidStatus(BidStatus.ACCEPTED);
        bid.setStartDate(bidAcceptDto.getStartDate());
        bid.setEndDate(bidAcceptDto.getEndDate());
        bid.setAcceptedDate(ZonedDateTime.now(ZoneId.systemDefault()));
        bid.setBidOfferAmount(bidAcceptDto.getPaymentAmount());
        bidAcceptDto.setConfirmationTimeout(bidAcceptDto.getConfirmationTimeout());
        bid.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        bid.setUpdateUserName(SecurityUtils.getCurrentUserLogin());

        Optional<Job> opJob = jobRepository.findById(bid.getJobID());
        Job job = null;
        if (opJob.isPresent()) {

            bid = bidRepository.save(bid);

            job = opJob.get();
            job.setJobStatus(JobStatus.BID_ACCEPTED);
            job.setAcceptedBidID(bid.getId());
            job.setAcceptedDate(ZonedDateTime.now(ZoneId.systemDefault()));
            job.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
            job.setUpdateUserName(SecurityUtils.getCurrentUserLogin());

            // --- Enter here the payment properties.
            // job.setPaymentMode()

            jobRepository.save(job);

            User worker = userService.findByNickName(bid.getCreateUserName());
            /* mailService.sendInvitationMail(worker, rdvNowProperties.getHosting().getUrl(), message, "en"); */
        }
        return bid;
    }

    @Override
    public Bid confirmBid(Bid bid, BidConfirmDto bidConfirmDto) {
        log.info("Confirm bid on a job");

        bid.setBidStatus(BidStatus.ATTRIBUTED);
        bid.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        bid.setUpdateUserName(SecurityUtils.getCurrentUserLogin());

        Optional<Job> opJob = jobRepository.findById(bid.getJobID());
        Job job = null;
        if (opJob.isPresent()) {

            job = opJob.get();
            bid = bidRepository.save(bid);

            User worker = userService.findByNickName(bid.getCreateUserName());
            User employer = userService.findByNickName(job.getCreateUserName());

            job.setJobStatus(JobStatus.ATTRIBUTED);
            job.setAttributedBidID(bid.getId());
            job.setLstWorker(Collections.singletonList(worker));
            job.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
            job.setUpdateUserName(SecurityUtils.getCurrentUserLogin());

            jobRepository.save(job);
            employer.setPaymentMode(bidConfirmDto.getPaymentMode());
            if (bidConfirmDto.getPaymentMode().equalsIgnoreCase(PAYMENT_MODE)) {
                employer.setStripeAccount(String.valueOf(bidConfirmDto.getPaymentAccount()));
            } else {
                employer.setPaypalAccount(String.valueOf(bidConfirmDto.getPaymentAccount()));
            }
            employer = userRepository.save(employer);

            /* mailService.sendInvitationMail(employer, rdvNowProperties.getHosting().getUrl(), message, "en"); */
        }
        return bid;
    }
}
