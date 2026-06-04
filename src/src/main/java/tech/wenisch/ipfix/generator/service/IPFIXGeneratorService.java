package tech.wenisch.ipfix.generator.service;


import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import tech.wenisch.ipfix.generator.datastructures.IPFIXGeneratorJobRequest;
import tech.wenisch.ipfix.generator.threads.IPFIXGeneratorJob;

@Service
public class IPFIXGeneratorService {
	ExecutorService executorService = Executors.newFixedThreadPool(10);
	static  Map<Integer, IPFIXGeneratorJob> jobs = new ConcurrentHashMap<Integer, IPFIXGeneratorJob> ();

	public IPFIXGeneratorJob startRequest(IPFIXGeneratorJobRequest request) {
    	IPFIXGeneratorJob job = new IPFIXGeneratorJob(request);
    	executorService.submit(job);
    	jobs.put(job.getId(), job);
    	
    	return job;
	}


	public IPFIXGeneratorJob getJobById(String jobId) {
		return jobs.get(Integer.valueOf(jobId));
	}
	public Collection<IPFIXGeneratorJob> getJobsList() {
		return jobs.values();
	}

	public IPFIXGeneratorJob stopJob(String jobId) {
		IPFIXGeneratorJob job = getJobById(jobId);
		if (job != null) {
			job.stop();
		}
		return job;
	}
}
