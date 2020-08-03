package io.cloudcomputing.jobtracker.exceptions;

public class JobNotFoundExceptionResponse {

    private String JobNotFound;

    public JobNotFoundExceptionResponse(String jobNotFound) {
        JobNotFound = jobNotFound;
    }

    public String getJobNotFound() {
        return JobNotFound;
    }

    public void setJobNotFound(String jobNotFound) {
        JobNotFound = jobNotFound;
    }
}
