package biz.advanceitgroup.rdvserver.jobs.webs;

import biz.advanceitgroup.rdvserver.jobs.dto.BidDto;
import biz.advanceitgroup.rdvserver.jobs.entities.Bid;
import biz.advanceitgroup.rdvserver.jobs.entities.Job;
import biz.advanceitgroup.rdvserver.authentication.webs.util.HeaderUtil;
import biz.advanceitgroup.rdvserver.jobs.dto.BidAcceptDto;
import biz.advanceitgroup.rdvserver.jobs.dto.BidConfirmDto;
import biz.advanceitgroup.rdvserver.jobs.dto.BidCreationDto;
import biz.advanceitgroup.rdvserver.jobs.services.interfaces.BidService;
import biz.advanceitgroup.rdvserver.jobs.services.interfaces.JobService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 12/02/2020 15:57
 */
@RestController
@RequestMapping("/api/bid")
public class BidController {

    private final Logger log = LoggerFactory.getLogger(BidController.class);

    @Autowired
    private BidService bidService;

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/reportAnAbuseOnBid/{bidId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reportAnAbuseOnBid(@PathVariable Long bidId, @RequestParam("message") String message, @RequestParam("codeIsoLang") String codeIsoLang) {
        log.debug("REST request to informed abuse and increment number of its about bid");

        BidDto bidDto = null;
        Bid bid = bidService.getBidById(bidId);
        if (bid == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bid = bidService.reportAnAbuseOnBid(bidId, message, codeIsoLang);
            bidDto = new BidDto(bid);
        }

        // URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Request to update bid for abuse", bidDto.toString())).body(bidDto);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBid(@RequestBody BidCreationDto bidCreationDto) {
        log.debug("REST request to create a Bid on a Job");

        Job job = jobService.getJobById(bidCreationDto.getJobId());
        if (job == null) {
            // --- return new ResponseEntity<>(HttpStatus.NOT_FOUND).;
            return ResponseEntity.notFound().headers(
                    HeaderUtil.createFailureAlert("Bid", HttpStatus.NOT_FOUND.toString(), "The matching job not found")
            ).build();
        }

        Bid bid = bidService.createBid(bidCreationDto);
        BidDto bidDto = new BidDto(bid);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
        return ResponseEntity.created(location).headers(HeaderUtil.createEntityCreationAlert("Bid", bidDto.getBidID().toString())).body(bidDto);
    }

    @RequestMapping(value = "/{bidID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findBidById(@PathVariable Long bidID) {
        log.debug("REST request to get a specific bid by id");

        Bid bid = bidService.getBidById(bidID);
        BidDto bidDto = new BidDto(bid);

        return Optional.ofNullable(bidDto)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK)
                ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/accept/{bidID}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptBid(@PathVariable Long bidID, @RequestBody BidAcceptDto bidAcceptDto) {
        log.debug("REST request to accept a specific bid by id");

        BidDto bidDto = null;
        Bid bid = bidService.getBidById(bidID);
        if (bid == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bid = bidService.acceptBid(bid, bidAcceptDto);
        bidDto = new BidDto(bid);

        return Optional.ofNullable(bidDto)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK)
                ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/confirm/{bidID}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> confirmBid(@PathVariable Long bidID, @RequestBody BidConfirmDto bidConfirmDto) {
        log.debug("REST request to confirm a bid");

        BidDto bidDto = null;
        Bid bid = bidService.getBidById(bidID);
        if (bid == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(HeaderUtil.createFailureAlert("Bid", HttpStatus.NOT_FOUND.toString(), "The matching bid does not exists"))
                    .body(bidID.toString());
        }
        bid = bidService.confirmBid(bid, bidConfirmDto);
        bidDto = new BidDto(bid);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Bid", HttpStatus.OK.toString() + " - Bid confirmed")).body(bidDto);
    }
}
