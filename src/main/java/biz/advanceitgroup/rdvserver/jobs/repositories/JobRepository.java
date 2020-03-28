package biz.advanceitgroup.rdvserver.jobs.repositories;

import biz.advanceitgroup.rdvserver.jobs.entities.Job;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.JobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 01/02/2020 05:50
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Page<Job> findAllByAttributedBidIDAndJobStatusValidatedAndPublicJob(Long workerId, Pageable pageable);
    Page<Job> findAllByAttributedBidIDAndJobStatusIsAndPublicJobTrue(Long workerId, JobStatus status, Pageable pageable);

    Page<Job> findAllByCategoryId(Long categoryID, Pageable pageable);

    Page<Job> findAllByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate, Pageable pageable);

    List<Job> findAllByJobStatus(JobStatus jobStatus);

    Page<Job> findAllByCreateUserName(String username, Pageable pageable);

    Page<Job> findAllByReportedAsAbuseJobIsAndConflictedJobIsAndEquireInsuranceIs(boolean reportAbuse, boolean conflicted, boolean enquireInsurance, Pageable pageable);
}
