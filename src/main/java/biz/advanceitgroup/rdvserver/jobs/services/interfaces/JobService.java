package biz.advanceitgroup.rdvserver.jobs.services.interfaces;

import biz.advanceitgroup.rdvserver.jobs.dto.JobDto;
import biz.advanceitgroup.rdvserver.jobs.entities.Job;
import biz.advanceitgroup.rdvserver.jobs.dto.FindJobDto;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.JobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 01/02/2020 06:00
 */
public interface JobService {

    Job getJobById(Long jobId);

    Job postJob(JobDto jobDto);

    Page<JobDto> findPageByFieldAndWorker(Pageable pageable, Long workerID);

    Page<JobDto> findJobs(FindJobDto findJobDto);

    Job requestCloseByWorker(Job job);

    Job setStatus(Job job, JobStatus status);

    Job incrementViewCount(Long jobId, String codeIsoLang);

    Job reportAnAbuseOnJob(Long jobId, String message, String codeIsoLang);

    Job reportConflict(Long jobId, String comment, String codeIsoLang);

    Job closeJob(Job job, String codeIsoLang);
}
