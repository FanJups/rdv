package biz.advanceitgroup.rdvserver.jobs.webs;

import biz.advanceitgroup.rdvserver.jobs.dto.JobDto;
import biz.advanceitgroup.rdvserver.jobs.entities.Job;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.security.AuthoritiesConstants;
import biz.advanceitgroup.rdvserver.authentication.security.SecurityUtils;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import biz.advanceitgroup.rdvserver.authentication.webs.util.HeaderUtil;
import biz.advanceitgroup.rdvserver.authentication.webs.util.PageableSize;
import biz.advanceitgroup.rdvserver.authentication.webs.util.RandomUtil;
import biz.advanceitgroup.rdvserver.jobs.dto.FindJobDto;
import biz.advanceitgroup.rdvserver.jobs.dto.JobFilterDto;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.JobStatus;
import biz.advanceitgroup.rdvserver.jobs.services.interfaces.JobService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 31/01/2020 15:50
 */
@RestController
@RequestMapping("/api/job")
public class JobController {

    private final Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{jobId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJobById(@PathVariable Long jobId) {
        log.debug("REST request to get a specific job by id");

        Job job = jobService.getJobById(jobId);

        return Optional.ofNullable(job)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createJob(@Validated @RequestBody JobDto jobDto) {
        log.debug("REST request to create a Job");

        Job job = new Job(jobDto);
        try {
            if(SecurityUtils.getCurrentUserLogin() != null) {
                job.setCreateUserName(SecurityUtils.getCurrentUserLogin());
                job.setWorkerValidationKeyPart(RandomUtil.jobValidationKey());
                job.setProviderValidationKeyPart(RandomUtil.jobValidationKey());
            }
            job = jobService.postJob(jobDto);
            jobDto.setId(job.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
        return ResponseEntity.created(location).headers(HeaderUtil.createEntityCreationAlert("request to create job", jobDto.toString())).body(jobDto);
    }

    @RequestMapping(value = "/last/{workerID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<JobDto> findLastJobs(@RequestParam(defaultValue = "0") int page, @PageableDefault(value = PageableSize.SIZE,
            sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long workerID, @Valid JobFilterDto jobFilterDto) {
        log.debug("REST request to get jobs by page and filtered criteria");

        pageable = PageRequest.of(page, Integer.parseInt(jobFilterDto.getLastJobCount()), Sort.Direction.DESC, jobFilterDto.getSortBy());

        return jobService.findPageByFieldAndWorker(pageable, workerID);

    }

    @RequestMapping(value = "/requestCloseByWorker/{jobId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestCloseByWorker(@PathVariable Long jobId) {
        log.debug("REST request to get request close a job");

        JobDto jobDto = null;
        Job job = jobService.getJobById(jobId);
        if (job != null) {
            job = jobService.requestCloseByWorker(job);
            jobDto = new JobDto(job);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.GONE).headers(HeaderUtil.createAlert("Request sent for job iden ", String.valueOf(jobId)))
                .body(jobDto);

    }

    @RequestMapping(value = "/setStatus",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setStatus(@RequestParam("jobId") Long jobId, @RequestParam("status") JobStatus status) {
        log.debug("REST request to set status on a jobId");

        JobDto jobDto = null;
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            job = jobService.setStatus(job, status);
            jobDto = new JobDto(job);
        }

        return ResponseEntity.status(HttpStatus.GONE).headers(HeaderUtil.createAlert("Request sent for job iden ", String.valueOf(jobId)))
                .body(jobDto);

    }

    @RequestMapping(value = "/incrementViewCount/{jobId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incrementViewCount(@PathVariable Long jobId, @RequestParam("codeIsoLang") String codeIsoLang) {
        log.debug("REST request to increment number of view job");

        JobDto jobDto = null;
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            job = jobService.incrementViewCount(jobId, codeIsoLang);
            jobDto = new JobDto(job);
        }

        return ResponseEntity.status(HttpStatus.GONE).headers(HeaderUtil.createAlert("Request sent for job iden ", String.valueOf(jobId)))
                .body(jobDto);

    }

    @RequestMapping(value = "/reportAnAbuseOnJob/{jobId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reportAnAbuseOnJob(@PathVariable Long jobId, @RequestParam("message") String message, @RequestParam("codeIsoLang") String codeIsoLang) {
        log.debug("REST request to informed abuse and increment number of its about job");

        JobDto jobDto = null;
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            job = jobService.reportAnAbuseOnJob(jobId, message, codeIsoLang);
            jobDto = new JobDto(job);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Request to update job for abuse", job.getId().toString())).body(jobDto);
    }

    @RequestMapping(value = "/initiateConflict/{jobId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> initiateConflict(@PathVariable Long jobId, @RequestParam("comment") String comment, @RequestParam("codeIsoLang") String codeIsoLang) {
        log.debug("REST request to initiate a conflict on a job");

        JobDto jobDto = null;
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (job.getJobStatus().equals(JobStatus.DONE)) {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        } else {
            job = jobService.reportConflict(jobId, comment, codeIsoLang);
            jobDto = new JobDto(job);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Request to update job for conflict", job.getId().toString())).body(jobDto);
    }

    @RequestMapping(value = "/closeByProvider/{jobId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> closeByProvider(@PathVariable Long jobId, @RequestParam("codeIsoLang") String codeIsoLang) {
        log.debug("REST request to close a job by provider");

        User user = userService.findByNickName(SecurityUtils.getCurrentUserLogin());
        if (!user.getRoles().contains(AuthoritiesConstants.ROLE_EMPLOYER)) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("Job", HttpStatus.BAD_REQUEST.toString(), "User does not have a role provider"))
                    .body(user);
        }
        JobDto jobDto = null;
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(HeaderUtil.createFailureAlert("Job", HttpStatus.NOT_FOUND.toString(), "The matching job does not exists"))
                    .body(jobId.toString());
        } else {
            job = jobService.closeJob(job, codeIsoLang);
            jobDto = new JobDto(job);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Request to close job by provider", job.getId().toString())).body(jobDto);
    }

    @RequestMapping(value = "/closeByAdmin/{jobId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> closeByAdmin(@PathVariable Long jobId, @RequestParam("codeIsoLang") String codeIsoLang) {
        log.debug("REST request to close a job by admin");

        User user = userService.findByNickName(SecurityUtils.getCurrentUserLogin());
        if (!user.getRoles().contains(AuthoritiesConstants.ROLE_ADMIN)) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("Job", HttpStatus.BAD_REQUEST.toString(), "User does not have a role admin"))
                    .body(user);
        }
        JobDto jobDto = null;
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(HeaderUtil.createFailureAlert("Job", HttpStatus.NOT_FOUND.toString(), "The matching job does not exists"))
                    .body(jobId.toString());
        } else {
            job = jobService.closeJob(job, codeIsoLang);
            jobDto = new JobDto(job);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Request to close job by admin", job.getId().toString())).body(jobDto);
    }

    @RequestMapping(value = "/closeByWorker/{jobId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> closeByWorker(@PathVariable Long jobId, @RequestParam("codeIsoLang") String codeIsoLang) {
        log.debug("REST request to close a job by admin");

        User user = userService.findByNickName(SecurityUtils.getCurrentUserLogin());
        if (!user.getRoles().contains(AuthoritiesConstants.ROLE_WORKER)) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("Job", HttpStatus.BAD_REQUEST.toString(), "User does not have a role admin"))
                    .body(user);
        }
        JobDto jobDto = null;
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(HeaderUtil.createFailureAlert("Job", HttpStatus.NOT_FOUND.toString(), "The matching job does not exists"))
                    .body(jobId.toString());
        } else {
            job = jobService.closeJob(job, codeIsoLang);
            jobDto = new JobDto(job);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Request to close job by admin", job.getId().toString())).body(jobDto);
    }

    @RequestMapping(value = "/findJobs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<JobDto> findJobs( FindJobDto findJobDto) {
        log.debug("REST request to find jobs by param");

        return jobService.findJobs(findJobDto);
    }
}
