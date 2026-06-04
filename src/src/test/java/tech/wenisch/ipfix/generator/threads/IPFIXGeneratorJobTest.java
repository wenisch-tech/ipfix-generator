package tech.wenisch.ipfix.generator.threads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.junit.jupiter.api.Test;

import tech.wenisch.ipfix.generator.datastructures.IPFIXGeneratorJobRequest;
import tech.wenisch.ipfix.generator.datastructures.IPFIXTemplateType;

class IPFIXGeneratorJobTest {

	@Test
	void zeroTotalPacketsRunsUntilStopped() throws Exception {
		assertContinuousJobCanBeStopped(new IPFIXGeneratorJobRequest("127.0.0.1", "0", "20", "0"));
	}

	@Test
	void zeroTotalPacketsRunsUntilStoppedForIpv4FiveTuple() throws Exception {
		assertContinuousJobCanBeStopped(
				new IPFIXGeneratorJobRequest("127.0.0.1", "0", "20", "0", IPFIXTemplateType.IPV4_FIVE_TUPLE));
	}

	private void assertContinuousJobCanBeStopped(IPFIXGeneratorJobRequest request) throws Exception {
		try (DatagramSocket collectorSocket = new DatagramSocket(0)) {
			collectorSocket.setSoTimeout(2000);
			request.setDestPort(String.valueOf(collectorSocket.getLocalPort()));
			IPFIXGeneratorJob job = new IPFIXGeneratorJob(request);
			Thread worker = new Thread(job);

			worker.start();

			byte[] buffer = new byte[4096];
			collectorSocket.receive(new DatagramPacket(buffer, buffer.length));

			long deadline = System.currentTimeMillis() + 1500;
			while (job.getPacketsSend() == 0 && System.currentTimeMillis() < deadline) {
				Thread.sleep(25);
			}

			job.stop();
			worker.join(2000);

			assertTrue(job.getPacketsSend() > 0, "continuous jobs should send packets before being stopped");
			assertEquals("Stopped", job.getStatus());
			assertTrue(job.getHistory().size() > 0, "continuous jobs should produce history entries");
		}
	}
}
