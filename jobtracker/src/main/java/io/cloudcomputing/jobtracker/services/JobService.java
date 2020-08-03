package io.cloudcomputing.jobtracker.services;

import io.cloudcomputing.jobtracker.exceptions.JobIdException;
import io.cloudcomputing.jobtracker.exceptions.JobNotFoundException;
import io.cloudcomputing.jobtracker.model.Job;
import io.cloudcomputing.jobtracker.model.User;
import io.cloudcomputing.jobtracker.repositories.JobRepository;
import io.cloudcomputing.jobtracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public Job saveOrUpdateJob(Job job, String username){

        if(job.getJobId() != null){
            Job existingJob = jobRepository.findByJobId(job.getJobId());

            if(existingJob != null && (!existingJob.getJobLeader().equals(username))){
                throw new JobNotFoundException("Job NOT found in your account");
            } else if(existingJob == null){
                throw new JobNotFoundException("Job with id '"+job.getJobId()+ "' cannot be updated cuz it doesn't exist.");
            }
        }

        try{
            User user = userRepository.findByUsername(username);
            job.setUser(user);
            job.setJobLeader(user.getUsername());
//            job.setJobId(job.getJobId());
            return jobRepository.save(job);
        }catch(Exception ex){
            throw new JobIdException("Job ID '"+job.getJobId()+"' already exists.");
        }
    }

    public Job findJobById(Long jobId, String username){
        Job job = jobRepository.findByJobId(jobId);

        if(job == null){
            throw new JobIdException("Job ID '"+jobId+"' doesn't exist.");
        }

        if(!job.getJobLeader().equals(username)){
            throw new JobNotFoundException("Job Not found in your account");
        }
        return job;
    }

    public Iterable<Job> findAllJobs(String username){
        return jobRepository.findAllByJobLeader(username);
    }

    public void deleteJobById(Long jobId, String username){
//        Job job = jobRepository.findByJobId(jobId);
//
//        if(job == null){
//            throw new JobIdException("Cannot delete job id '"+jobId+"' because it doesn't exist.");
//        }

        jobRepository.delete(findJobById(jobId, username));
    }
}
