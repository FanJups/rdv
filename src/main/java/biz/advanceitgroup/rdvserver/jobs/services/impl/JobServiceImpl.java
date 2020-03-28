package biz.advanceitgroup.rdvserver.jobs.services.impl;

import biz.advanceitgroup.rdvserver.jobs.dto.JobDto;
import biz.advanceitgroup.rdvserver.jobs.entities.Bid; 
import biz.advanceitgroup.rdvserver.jobs.entities.Job;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.security.SecurityUtils;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import biz.advanceitgroup.rdvserver.jobs.dto.FindJobDto;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.BidStatus;
import biz.advanceitgroup.rdvserver.jobs.entities.enumeration.JobStatus;
import biz.advanceitgroup.rdvserver.jobs.repositories.BidRepository;
import biz.advanceitgroup.rdvserver.jobs.repositories.JobRepository;
import biz.advanceitgroup.rdvserver.jobs.services.interfaces.JobService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 01/02/2020 06:04
 */
@Service
public class JobServiceImpl implements JobService {

    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    JobRepository jobRepository;

    @Autowired
    BidRepository bidRepository;

    /*@Autowired
    private MailService mailService;

    @Autowired
    private RdvNowProperties rdvNowProperties;*/

    @Autowired
    UserService userService;

    /**
     *
     * @param jobId
     * @return
     */
    @Override
    public Job getJobById(Long jobId) {
        log.info("Get job by Id from datasource");

        Job job = null;
        Optional<Job> optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            job = optJob.get();
        }
        return job;
    }

    /**
     *
     * @param jobDto
     * @return
     */
    @Override
    public Job postJob(JobDto jobDto) {
        log.info("Save job and get the inserted item");

        /*
        * + Enregistre ce job dans le système, et place son état en attente de validation si le compte du provider a
        * déjà été activé. Si le compte du provider n'est pas encore activé, alors placer le job dans l'état désactivé.
        * + Envoyer le message de notification à tous les workers listés dans lstWorker.
        * */
        Job job = new Job(jobDto);
        if (SecurityUtils.isAuthenticated()) {
            job.setCreateUserName(SecurityUtils.getCurrentUserLogin());
        }
        User user = userService.findByNickName(SecurityUtils.getCurrentUserLogin());
        if (!user.isValidated()) {
            job.setJobStatus(JobStatus.CANCELLED);
            job = jobRepository.save(job);
        } else {
            job = jobRepository.save(job);
            // sending email to worker selected fo the job
            job.getLstWorker().stream().forEach(worker -> {
                // --- mailService.sendInvitationMail(worker, rdvNowProperties.getHosting().getUrl(), "en");
            });
        }

        return jobRepository.save(job);
    }

    /**
     *
     * @param pageable
     * @param workerID
     * @return
     */
    @Override
    public Page<JobDto> findPageByFieldAndWorker(Pageable pageable, Long workerID) {
        log.debug("Request to get jobs per page sorted and worker");

        Page<JobDto> result = new PageImpl<JobDto>(new ArrayList<>(), pageable, 0);
        List<JobDto> jobs = null;

        Page<Job> pageMessages = jobRepository.findAllByAttributedBidIDAndJobStatusIsAndPublicJobTrue(workerID, JobStatus.VALIDATED, pageable);
        if(pageMessages == null){
            return result;
        }
        jobs = pageMessages.stream().map(JobDto::new).collect(Collectors.toList());

        if (!jobs.isEmpty()) {
            int start = (int) pageable.getOffset();
            int end = (start + pageable.getPageSize()) > jobs.size() ? jobs.size() : (start + pageable.getPageSize());
            if (start < end) {
                result = new PageImpl<>(jobs.subList(start, end), pageable, pageMessages.getTotalElements());
            } else {
                result = new PageImpl<>(jobs, pageable, pageMessages.getTotalElements());
            }
        }

        return result;
    }

    @Override
    public Page<JobDto> findJobs(FindJobDto findJobDto) {
        log.debug("Request to get jobs per params filter");

        Pageable pageable = null;
        Page<JobDto> result = null;

        if (findJobDto.getJobID() != null) {
            Optional<Job> opJob = jobRepository.findById(findJobDto.getJobID());
            if (opJob.isPresent()) {
                JobDto dto = new JobDto(opJob.get());
                pageable = PageRequest.of(0, 1);
                result = new PageImpl<JobDto>(Collections.singletonList(dto), pageable, 1);
            }
        } else {
            try {
                pageable = PageRequest.of(findJobDto.getPageNumber(), Integer.parseInt(String.valueOf(findJobDto.getPageSize())), Sort.Direction.DESC, findJobDto.getSortBy());
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Page<Job> jobs = new PageImpl<>(new ArrayList<>());;
            if (findJobDto.getCategoryID() != null) {
                jobs = jobRepository.findAllByCategoryId(findJobDto.getCategoryID(), pageable);
            }
            if (findJobDto.getStartDate() != null || findJobDto.getEndDate() != null) {
                if (jobs != null) {
                    if (findJobDto.getStartDate() != null) {
                        List<Job> jobList = jobs.stream().filter(job -> job.getStartDate().compareTo(findJobDto.getStartDate()) >= 0).collect(Collectors.toList());
                        jobs = new PageImpl<>(jobList);
                    }
                    if (findJobDto.getEndDate() != null) {
                        List<Job> jobList = jobs.stream().filter(job -> job.getEndDate().compareTo(findJobDto.getEndDate()) <= 0).collect(Collectors.toList());
                        jobs = new PageImpl<>(jobList);
                    }
                } else {
                    // find new research
                    jobs = jobRepository.findAllByStartDateAndEndDate(findJobDto.getStartDate(), findJobDto.getEndDate(), pageable);
                }
            }
            if (findJobDto.getLstStatus() != null) {
                if (jobs != null) {
                    List<Job> jobList = jobs.stream().filter(job -> findJobDto.getLstStatus().contains(job.getJobStatus())).collect(Collectors.toList());
                    jobs = new PageImpl<>(jobList);
                } else {
                    List<Job> jobsLst = new ArrayList<>();
                    findJobDto.getLstStatus().forEach(
                        jobStatus -> {
                            List<Job> jobList = jobRepository.findAllByJobStatus(jobStatus);
                            jobsLst.addAll(jobList);
                        }
                    );
                    if (!jobsLst.isEmpty()) {
                        int start = (int) Objects.requireNonNull(pageable).getOffset();
                        int end = Math.min((start + pageable.getPageSize()), jobsLst.size());
                        if (start < end) {
                            jobs = new PageImpl<>(jobsLst.subList(start, end), pageable, jobsLst.size());
                        } else {
                            jobs = new PageImpl<>(jobsLst, pageable, jobsLst.size());
                        }
                    }
                }
            }
            if (findJobDto.getProviderID() != null) {
                User provider = userService.findById(findJobDto.getProviderID());
                if (jobs != null) {
                    List<Job> jobList = jobs.stream().filter(job -> job.getCreateUserName().equals(provider.getNickName())).collect(Collectors.toList());
                    jobs = new PageImpl<>(jobList);
                } else {
                    jobs = jobRepository.findAllByCreateUserName(provider.getNickName(), pageable);
                }
            }
            if (findJobDto.getBidWorkerID() != null) {
                User worker = userService.findById(findJobDto.getBidWorkerID());
                if (jobs != null) {
                    List<Job> jobList = jobs.stream().filter(job -> job.getCreateUserName().equals(worker.getNickName())).collect(Collectors.toList());
                    jobs = new PageImpl<>(jobList);
                } else {
                    jobs = jobRepository.findAllByCreateUserName(worker.getNickName(), pageable);
                }
            }
            if (findJobDto.getAttributedWorkerID() != null) {
                User worker = userService.findById(findJobDto.getAttributedWorkerID());
                if (jobs != null) {
                    List<Job> jobList = jobs.stream().filter(job -> job.getCreateUserName().equals(worker.getNickName())).collect(Collectors.toList());
                    jobs = new PageImpl<>(jobList);
                } else {
                    jobs = jobRepository.findAllByCreateUserName(worker.getNickName(), pageable);
                }
            }
            if (jobs != null) {
                List<Job> jobList = jobs.stream().filter(
                    job -> (
                        job.isReportedAsAbuseJob() == findJobDto.isReportedAsAbuse() && job.isConflictedJob() == findJobDto.isConflicted() && job.isEquireInsurance() == findJobDto.isRequireInsurance()
                    )
                ).collect(Collectors.toList());
                jobs = new PageImpl<>(jobList);
            } else {
                jobs = jobRepository.findAllByReportedAsAbuseJobIsAndConflictedJobIsAndEquireInsuranceIs(
                        findJobDto.isReportedAsAbuse(), findJobDto.isConflicted(), findJobDto.isRequireInsurance(), pageable
                );
            }
            result = (Page<JobDto>) jobs.stream().map(JobDto::new).collect(Collectors.toList());
        }

        return result;
    }

    /**
     *
     * @param job
     * @return
     */
    @Override
    public Job requestCloseByWorker(Job job) {

        String workerKey = job.getWorkerValidationKeyPart();
        String providerKey = job.getProviderValidationKeyPart();

        User worker = userService.findById(job.getAttributedBidID());
        User provider = userService.findByNickName(job.getCreateUserName());

        // --- mailService.sendJobRequestCloseMail(worker, rdvNowProperties.getHosting().getUrl(), workerKey, "en");
        // --- mailService.sendJobRequestCloseMail(provider, rdvNowProperties.getHosting().getUrl(), providerKey, "en");

        // --- mailService.sendInvitationMail(provider, rdvNowProperties.getHosting().getUrl(), "en");

        job.setJobStatus(JobStatus.REQUEST_CLOSE);

        job = jobRepository.save(job);

        return job;
    }

    /**
     *
     * @param job
     * @param status
     * @return
     */
    @Override
    public Job setStatus(Job job, JobStatus status) {

        if (status.equals(JobStatus.CANCELLED)) {
            User provider = userService.findByNickName(job.getCreateUserName());
            // --- mailService.sendInvitationMail(provider, rdvNowProperties.getHosting().getUrl(), "en");  // mail d'annulation

            if (job.getJobBidCount() > 0 && job.getLstWorker().size() == 0) {
                Bid bid = bidRepository.findBidByJobIDAndBidStatus(job.getId(), BidStatus.ATTRIBUTED);
                if (bid != null) {
                    bid.setBidStatus(BidStatus.CANCELLED);
                    bid.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
                    bid.setUpdateUserName(SecurityUtils.getCurrentUserLogin());

                    // libèration du montant réservé sur le moyen de paiement enregistré par le fournisseur au moment de la validation de cette offre

                    job.setJobStatus(JobStatus.CANCELLED);
                }
            } else if (job.getJobStatus().equals(JobStatus.BID_ACCEPTED)) {
                Bid bid = bidRepository.findBidByJobIDAndBidStatus(job.getId(), BidStatus.ACCEPTED);
                User worker = userService.findByNickName(bid.getCreateUserName());

                job.setJobStatus(JobStatus.CONFLICTED);

                // --- mailService.sendInvitationMail(worker, rdvNowProperties.getHosting().getUrl(), "en");
                // --- mailService.sendInvitationMail(provider, rdvNowProperties.getHosting().getUrl(), "en");
            } else {
                job.setJobStatus(status);
            }
            job.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
            job.setUpdateUserName(SecurityUtils.getCurrentUserLogin());
            job = jobRepository.save(job);
        } else {
            job.setJobStatus(status);
            job.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
            job.setUpdateUserName(SecurityUtils.getCurrentUserLogin());
            job = jobRepository.save(job);
        }

        return job;
    }

    @Override
    public Job incrementViewCount(Long jobId, String codeIsoLang) {

        Job job = null;
        Optional<Job> optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            job = optJob.get();
            job.setJobViewCount(job.getJobViewCount()+1);
            job = jobRepository.save(job);
        }
        return job;
    }

    @Override
    public Job reportAnAbuseOnJob(Long jobId, String message, String codeIsoLang) {
        Job job = null;
        Optional<Job> optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            job = optJob.get();
            job.setReportedAsAbuseJob(true);
            // --- job.setConflictedDate(ZonedDateTime.now(ZoneId.systemDefault()));
            job.setAbuseReportCount(job.getAbuseReportCount()+1);
            job.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
            job.setUpdateUserName(SecurityUtils.getCurrentUserLogin());
            job = jobRepository.save(job);
            // Sent email to the RDV administrator with the message
            List<User> admins = userService.findAllAdmin();
            if (admins != null && admins.size() != 0) {
                admins.forEach(admin -> { /* mailService.sendInvitationMail(admin, rdvNowProperties.getHosting().getUrl(), message, "en"); */ });
            }
        }
        return job;
    }

    @Override
    public Job reportConflict(Long jobId, String comment, String codeIsoLang) {
        Job job = null;
        Optional<Job> optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            job = optJob.get();
            job.setConflictedJob(true);
            job.setConflictedDate(ZonedDateTime.now(ZoneId.systemDefault()));
            job.setJobStatus(JobStatus.CONFLICTED);
            job = jobRepository.save(job);
            // Sent email to the RDV administrator with the message
            List<User> admins = userService.findAllAdmin();
            if (admins != null && admins.size() != 0) {
                admins.forEach(admin -> { /* mailService.sendInvitationMail(admin, rdvNowProperties.getHosting().getUrl(), message, "en"); */ });
            }
        }
        return job;
    }

    @Override
    public Job closeJob(Job job, String codeIsoLang) {

        // Débite le compte du fournisseur du montant de l’offre

        // Crédite les comptes de RDV et du travailleur selon le plan de commissionnement paramétré

        Bid bid = bidRepository.findBidByJobIDAndBidStatus(job.getId(), BidStatus.ACCEPTED);
        User worker = userService.findByNickName(bid.getCreateUserName());
        User provider = userService.findByNickName(job.getCreateUserName());
        /* mailService.sendValidationMail(worker, rdvNowProperties.getHosting().getUrl(), message, "en"); */
        /* mailService.sendValidationMail(provider, rdvNowProperties.getHosting().getUrl(), message, "en"); */

        /* mailService.sendTransactionMail(provider, rdvNowProperties.getHosting().getUrl(), message, "en"); */

        job.setJobStatus(JobStatus.DONE);
        job.setUpdateUserName(SecurityUtils.getCurrentUserLogin());
        job.setUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        job = jobRepository.save(job);

        return job;
    }
}
