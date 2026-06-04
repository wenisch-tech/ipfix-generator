package tech.wenisch.ipfix.generator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tech.wenisch.ipfix.generator.datastructures.IPFIXGeneratorJobRequest;
import tech.wenisch.ipfix.generator.service.IPFIXGeneratorService;
import tech.wenisch.ipfix.generator.threads.IPFIXGeneratorJob;

@WebMvcTest(APIController.class)
class APIControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IPFIXGeneratorService ipfixGeneratorService;

	@Test
	void createJobReturnsCreatedJob() throws Exception {
		IPFIXGeneratorJob createdJob = new IPFIXGeneratorJob(new IPFIXGeneratorJobRequest("127.0.0.1", "4739", "1", "10"));

		when(ipfixGeneratorService.startRequest(any(IPFIXGeneratorJobRequest.class))).thenReturn(createdJob);

		mockMvc.perform(post("/api/jobs")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "destHost": "127.0.0.1",
					  "destPort": "4739",
					  "pps": "1",
					  "totalPackets": "10"
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/jobs/" + createdJob.getId()))
			.andExpect(jsonPath("$.destHost").value("127.0.0.1"))
			.andExpect(jsonPath("$.destPort").value(4739))
			.andExpect(jsonPath("$.pps").value(1))
			.andExpect(jsonPath("$.totalPackets").value(10));
	}

	@Test
	void stopJobReturnsStoppedJob() throws Exception {
		IPFIXGeneratorJob stoppedJob = new IPFIXGeneratorJob(new IPFIXGeneratorJobRequest("127.0.0.1", "4739", "1", "0"));
		stoppedJob.setStatus("Stopped");

		when(ipfixGeneratorService.stopJob(String.valueOf(stoppedJob.getId()))).thenReturn(stoppedJob);

		mockMvc.perform(post("/api/jobs/" + stoppedJob.getId() + "/stop"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(stoppedJob.getId()))
			.andExpect(jsonPath("$.status").value("Stopped"))
			.andExpect(jsonPath("$.totalPackets").value(0));
	}

	@Test
	void stopJobReturnsNotFoundForUnknownJob() throws Exception {
		when(ipfixGeneratorService.stopJob("999")).thenReturn(null);

		mockMvc.perform(post("/api/jobs/999/stop"))
			.andExpect(status().isNotFound());
	}
}
