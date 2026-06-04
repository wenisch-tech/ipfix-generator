package tech.wenisch.ipfix.generator.datastructures.ipfix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;

import org.junit.jupiter.api.Test;

class IPv4FiveTupleDataRecordTest {

	@Test
	void recordRoundTripsThroughBytes() throws Exception {
		IPv4FiveTupleDataRecord record = new IPv4FiveTupleDataRecord();
		record.setSourceIPv4Address((Inet4Address) InetAddress.getByName("10.10.10.1"));
		record.setDestinationIPv4Address((Inet4Address) InetAddress.getByName("10.10.10.2"));
		record.setSourceTransportPort(12345);
		record.setDestinationTransportPort(443);
		record.setProtocolIdentifier((short) 6);
		record.setPacketDeltaCount(11);
		record.setOctetDeltaCount(4096);
		record.setFlowStartMilliseconds(BigInteger.valueOf(1000L));
		record.setFlowEndMilliseconds(BigInteger.valueOf(2000L));
		record.setTcpControlBits(18);

		IPv4FiveTupleDataRecord parsed = IPv4FiveTupleDataRecord.parse(record.getBytes());

		assertEquals(IPv4FiveTupleDataRecord.LENGTH, record.getLength());
		assertEquals("10.10.10.1", parsed.getSourceIPv4Address().getHostAddress());
		assertEquals("10.10.10.2", parsed.getDestinationIPv4Address().getHostAddress());
		assertEquals(12345, parsed.getSourceTransportPort());
		assertEquals(443, parsed.getDestinationTransportPort());
		assertEquals(6, parsed.getProtocolIdentifier());
		assertEquals(11, parsed.getPacketDeltaCount());
		assertEquals(4096, parsed.getOctetDeltaCount());
		assertEquals(BigInteger.valueOf(1000L), parsed.getFlowStartMilliseconds());
		assertEquals(BigInteger.valueOf(2000L), parsed.getFlowEndMilliseconds());
		assertEquals(18, parsed.getTcpControlBits());
	}
}
