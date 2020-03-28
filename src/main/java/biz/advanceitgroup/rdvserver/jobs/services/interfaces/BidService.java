package biz.advanceitgroup.rdvserver.jobs.services.interfaces;

import biz.advanceitgroup.rdvserver.jobs.entities.Bid;
import biz.advanceitgroup.rdvserver.jobs.dto.BidAcceptDto;
import biz.advanceitgroup.rdvserver.jobs.dto.BidConfirmDto;
import biz.advanceitgroup.rdvserver.jobs.dto.BidCreationDto;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 12/02/2020 16:00
 */
public interface BidService {

    Bid getBidById(Long bidId);

    Bid reportAnAbuseOnBid(Long bidId, String message, String codeIsoLang);

    Bid createBid(BidCreationDto bidCreationDto);

    Bid acceptBid(Bid bid, BidAcceptDto bidAcceptDto);

    Bid confirmBid(Bid bid, BidConfirmDto bidConfirmDto);
}
