package biz.advanceitgroup.rdvserver.jobs.repositories;

import biz.advanceitgroup.rdvserver.jobs.entities.Bid;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.BidStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 12/02/2020 16:02
 */
@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Bid findBidByJobIDAndBidStatus(Long jobId, BidStatus bidStatus);
}
