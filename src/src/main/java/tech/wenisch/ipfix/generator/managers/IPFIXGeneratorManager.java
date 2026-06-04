package tech.wenisch.ipfix.generator.managers;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;

import tech.wenisch.ipfix.generator.datastructures.IPFIXTemplateType;
import tech.wenisch.ipfix.generator.datastructures.ipfix.InformationElement;
import tech.wenisch.ipfix.generator.datastructures.ipfix.IPv4FiveTupleDataRecord;
import tech.wenisch.ipfix.generator.datastructures.ipfix.L2IPDataRecord;
import tech.wenisch.ipfix.generator.datastructures.ipfix.MessageHeader;
import tech.wenisch.ipfix.generator.datastructures.ipfix.OptionTemplateRecord;
import tech.wenisch.ipfix.generator.datastructures.ipfix.SamplingDataRecord;
import tech.wenisch.ipfix.generator.datastructures.ipfix.SetHeader;
import tech.wenisch.ipfix.generator.datastructures.ipfix.TemplateRecord;
import tech.wenisch.ipfix.generator.datastructures.networking.MacAddress;
import tech.wenisch.ipfix.generator.exceptions.UtilityException;

public class IPFIXGeneratorManager {
	public static final long OBSERVATION_DOMAIN_ID = 1337;

	static boolean debug;

	static MessageHeader createMessageHeader(long observationDomainID) {
		MessageHeader mh = new MessageHeader();
		mh.setExportTime(new Date());
		mh.setVersionNumber(10);
		mh.setObservationDomainID(observationDomainID);
		return mh;
	}

	static TemplateRecord createDefaultTemplateRecord() {

		TemplateRecord tr = new TemplateRecord();

		tr.setTemplateID(L2IPDataRecord.TEMPLATE_ID);
		tr.setFieldCount(26);

		InformationElement iESrcMAC = new InformationElement();
		iESrcMAC.setFieldLength(6);
		iESrcMAC.setInformationElementID(56);
		tr.getInformationElements().add(iESrcMAC);

		InformationElement iEDestMAC = new InformationElement();
		iEDestMAC.setFieldLength(6);
		iEDestMAC.setInformationElementID(80);
		tr.getInformationElements().add(iEDestMAC);

		InformationElement iEingressPhysicalID = new InformationElement();
		iEingressPhysicalID.setFieldLength(4);
		iEingressPhysicalID.setInformationElementID(252);
		tr.getInformationElements().add(iEingressPhysicalID);

		InformationElement iEegressPhysicalID = new InformationElement();
		iEegressPhysicalID.setFieldLength(4);
		iEegressPhysicalID.setInformationElementID(253);
		tr.getInformationElements().add(iEegressPhysicalID);

		InformationElement iEdot1qVlandId = new InformationElement();
		iEdot1qVlandId.setFieldLength(2);
		iEdot1qVlandId.setInformationElementID(243);
		tr.getInformationElements().add(iEdot1qVlandId);

		InformationElement iEdot1qCVlandId = new InformationElement();
		iEdot1qCVlandId.setFieldLength(2);
		iEdot1qCVlandId.setInformationElementID(245);
		tr.getInformationElements().add(iEdot1qCVlandId);

		InformationElement iEPostDot1qVlandId = new InformationElement();
		iEPostDot1qVlandId.setFieldLength(2);
		iEPostDot1qVlandId.setInformationElementID(255);
		tr.getInformationElements().add(iEPostDot1qVlandId);

		InformationElement iEPostDot1qCVlandId = new InformationElement();
		iEPostDot1qCVlandId.setFieldLength(2);
		iEPostDot1qCVlandId.setInformationElementID(254);
		tr.getInformationElements().add(iEPostDot1qCVlandId);

		InformationElement iEIPv4Src = new InformationElement();
		iEIPv4Src.setFieldLength(4);
		iEIPv4Src.setInformationElementID(8);
		tr.getInformationElements().add(iEIPv4Src);

		InformationElement iEIPv4Dest = new InformationElement();
		iEIPv4Dest.setFieldLength(4);
		iEIPv4Dest.setInformationElementID(12);
		tr.getInformationElements().add(iEIPv4Dest);

		InformationElement iEIPv6Src = new InformationElement();
		iEIPv6Src.setFieldLength(16);
		iEIPv6Src.setInformationElementID(27);
		tr.getInformationElements().add(iEIPv6Src);

		InformationElement iEIPv6Dest = new InformationElement();
		iEIPv6Dest.setFieldLength(16);
		iEIPv6Dest.setInformationElementID(28);
		tr.getInformationElements().add(iEIPv6Dest);

		InformationElement iEPkts = new InformationElement();
		iEPkts.setFieldLength(4);
		iEPkts.setInformationElementID(2);
		tr.getInformationElements().add(iEPkts);

		InformationElement iEBytes = new InformationElement();
		iEBytes.setFieldLength(4);
		iEBytes.setInformationElementID(1);
		tr.getInformationElements().add(iEBytes);

		InformationElement iEFlowStart = new InformationElement();
		iEFlowStart.setFieldLength(8);
		iEFlowStart.setInformationElementID(152);
		tr.getInformationElements().add(iEFlowStart);

		InformationElement iEFlowEnd = new InformationElement();
		iEFlowEnd.setFieldLength(8);
		iEFlowEnd.setInformationElementID(153);
		tr.getInformationElements().add(iEFlowEnd);

		InformationElement iEPortSrc = new InformationElement();
		iEPortSrc.setFieldLength(2);
		iEPortSrc.setInformationElementID(7);
		tr.getInformationElements().add(iEPortSrc);

		InformationElement iEPortDest = new InformationElement();
		iEPortDest.setFieldLength(2);
		iEPortDest.setInformationElementID(7);
		tr.getInformationElements().add(iEPortDest);

		InformationElement iETCPFlags = new InformationElement();
		iETCPFlags.setFieldLength(1);
		iETCPFlags.setInformationElementID(6);
		tr.getInformationElements().add(iETCPFlags);

		InformationElement iEProtocol = new InformationElement();
		iEProtocol.setFieldLength(1);
		iEProtocol.setInformationElementID(4);
		tr.getInformationElements().add(iEProtocol);

		InformationElement iEIPv6OptionHeaders = new InformationElement();
		iEIPv6OptionHeaders.setFieldLength(4);
		iEIPv6OptionHeaders.setInformationElementID(64);
		tr.getInformationElements().add(iEIPv6OptionHeaders);

		InformationElement iENextHeaderIPv6 = new InformationElement();
		iENextHeaderIPv6.setFieldLength(1);
		iENextHeaderIPv6.setInformationElementID(193);
		tr.getInformationElements().add(iENextHeaderIPv6);

		InformationElement iEFlowLabel = new InformationElement();
		iEFlowLabel.setFieldLength(4);
		iEFlowLabel.setInformationElementID(31);
		tr.getInformationElements().add(iEFlowLabel);

		InformationElement iEIPTOS = new InformationElement();
		iEIPTOS.setFieldLength(1);
		iEIPTOS.setInformationElementID(5);
		tr.getInformationElements().add(iEIPTOS);

		InformationElement iEIPVersion = new InformationElement();
		iEIPVersion.setFieldLength(1);
		iEIPVersion.setInformationElementID(60);
		tr.getInformationElements().add(iEIPVersion);

		InformationElement iEICMPType = new InformationElement();
		iEICMPType.setFieldLength(2);
		iEICMPType.setInformationElementID(32);
		tr.getInformationElements().add(iEICMPType);
		return tr;
	}

	static TemplateRecord createIPv4FiveTupleTemplateRecord() {
		TemplateRecord tr = new TemplateRecord();

		tr.setTemplateID(IPv4FiveTupleDataRecord.TEMPLATE_ID);
		tr.setFieldCount(10);

		InformationElement sourceIPv4 = new InformationElement();
		sourceIPv4.setFieldLength(4);
		sourceIPv4.setInformationElementID(8);
		tr.getInformationElements().add(sourceIPv4);

		InformationElement destinationIPv4 = new InformationElement();
		destinationIPv4.setFieldLength(4);
		destinationIPv4.setInformationElementID(12);
		tr.getInformationElements().add(destinationIPv4);

		InformationElement sourcePort = new InformationElement();
		sourcePort.setFieldLength(2);
		sourcePort.setInformationElementID(7);
		tr.getInformationElements().add(sourcePort);

		InformationElement destinationPort = new InformationElement();
		destinationPort.setFieldLength(2);
		destinationPort.setInformationElementID(11);
		tr.getInformationElements().add(destinationPort);

		InformationElement protocolIdentifier = new InformationElement();
		protocolIdentifier.setFieldLength(1);
		protocolIdentifier.setInformationElementID(4);
		tr.getInformationElements().add(protocolIdentifier);

		InformationElement packetDeltaCount = new InformationElement();
		packetDeltaCount.setFieldLength(4);
		packetDeltaCount.setInformationElementID(2);
		tr.getInformationElements().add(packetDeltaCount);

		InformationElement octetDeltaCount = new InformationElement();
		octetDeltaCount.setFieldLength(4);
		octetDeltaCount.setInformationElementID(1);
		tr.getInformationElements().add(octetDeltaCount);

		InformationElement flowStartMilliseconds = new InformationElement();
		flowStartMilliseconds.setFieldLength(8);
		flowStartMilliseconds.setInformationElementID(152);
		tr.getInformationElements().add(flowStartMilliseconds);

		InformationElement flowEndMilliseconds = new InformationElement();
		flowEndMilliseconds.setFieldLength(8);
		flowEndMilliseconds.setInformationElementID(153);
		tr.getInformationElements().add(flowEndMilliseconds);

		InformationElement tcpControlBits = new InformationElement();
		tcpControlBits.setFieldLength(1);
		tcpControlBits.setInformationElementID(6);
		tr.getInformationElements().add(tcpControlBits);

		return tr;
	}

	static OptionTemplateRecord createDefaultOptionTemplate() {
		OptionTemplateRecord otr = new OptionTemplateRecord();

		otr.setTemplateID(256);
		otr.setScopeFieldCount(1);
		otr.setFieldCount(4);

		InformationElement iEObservationDomainID = new InformationElement();
		iEObservationDomainID.setFieldLength(4);
		iEObservationDomainID.setInformationElementID(149);
		otr.getInformationElements().add(iEObservationDomainID);

		InformationElement iESelectorAlgorithm = new InformationElement();
		iESelectorAlgorithm.setFieldLength(2);
		iESelectorAlgorithm.setInformationElementID(304);
		otr.getInformationElements().add(iESelectorAlgorithm);

		InformationElement iESamplingPacketInterval = new InformationElement();
		iESamplingPacketInterval.setFieldLength(4);
		iESamplingPacketInterval.setInformationElementID(305);
		otr.getInformationElements().add(iESamplingPacketInterval);

		InformationElement iESamplingPacketSpace = new InformationElement();
		iESamplingPacketSpace.setFieldLength(4);
		iESamplingPacketSpace.setInformationElementID(306);
		otr.getInformationElements().add(iESamplingPacketSpace);
		return otr;
	}

	static L2IPDataRecord createDataRecord(long ingressPhysicalInterfaceIDValue, long egressPhysicalInterfaceIDValue, int outerVLANValue, int innerVLANValue, MacAddress srcMACValue, MacAddress destMACValue, short ipVersionValue, short transportProtocolValue, Inet4Address srcIPv4Value, Inet4Address destIPv4Value, Inet6Address srcIPv6Value, Inet6Address destIPv6Value, int srcPortValue, int destPortValue, int icmpTypeValue, long packetsValue, long octetsValue) throws UnknownHostException {
		L2IPDataRecord l2ip = new L2IPDataRecord();


		l2ip.setIngressPhysicalInterface(ingressPhysicalInterfaceIDValue);
		l2ip.setEgressPhysicalInterface(egressPhysicalInterfaceIDValue);
		l2ip.setDot1qVlanId(outerVLANValue);
		l2ip.setDot1qCustomerVlanId(innerVLANValue);
		l2ip.setSourceMacAddress(srcMACValue);
		l2ip.setDestinationMacAddress(destMACValue);
		l2ip.setIpVersion(ipVersionValue);
		l2ip.setProtocolIdentifier(transportProtocolValue);
		if (ipVersionValue == 4) {
			l2ip.setSourceIPv4Address(srcIPv4Value);
			l2ip.setDestinationIPv4Address(destIPv4Value);
		}
		if (ipVersionValue == 6) {
			l2ip.setSourceIPv6Address(srcIPv6Value);
			l2ip.setDestinationIPv6Address(destIPv6Value);
		}
		l2ip.setSourceTransportPort(srcPortValue);
		l2ip.setDestinationTransportPort(destPortValue);
		l2ip.setIcmpTypeCodeIPv4(icmpTypeValue);
		l2ip.setPacketDeltaCount(packetsValue);
		l2ip.setOctetDeltaCount(octetsValue);

		return l2ip;
	}
	static L2IPDataRecord createRandomDataRecord() throws UnknownHostException, UtilityException {
		L2IPDataRecord l2ip = new L2IPDataRecord();

		Random random = new Random();
		l2ip.setIngressPhysicalInterface( random.nextInt());
		l2ip.setEgressPhysicalInterface(random.nextInt());
		l2ip.setDot1qVlanId( random.nextInt(4096));
		l2ip.setDot1qCustomerVlanId( random.nextInt(4096));
		byte[] mac = new byte[6];
		random.nextBytes(mac);
		l2ip.setSourceMacAddress( new MacAddress(mac));
		random.nextBytes(mac);
		l2ip.setDestinationMacAddress(new MacAddress(mac));
		l2ip.setIpVersion(Short.parseShort("4"));
		l2ip.setProtocolIdentifier((short) (random.nextBoolean() ? 6 : 17));
		byte[] addrIPv4 = new byte[4];
		random.nextBytes(addrIPv4);
		l2ip.setSourceIPv4Address((Inet4Address) Inet4Address.getByAddress(addrIPv4));
		random.nextBytes(addrIPv4);
		l2ip.setDestinationIPv4Address((Inet4Address) Inet4Address.getByAddress(addrIPv4));


		l2ip.setSourceTransportPort(1234);
		l2ip.setDestinationTransportPort(2601);
		l2ip.setIcmpTypeCodeIPv4(1);
		l2ip.setPacketDeltaCount(2);
		l2ip.setOctetDeltaCount(3);
		BigInteger now = BigInteger.valueOf(new Date().getTime());
		l2ip.setFlowStartMilliseconds(now);
		l2ip.setFlowEndMilliseconds(now);

		return l2ip;
	}

	static IPv4FiveTupleDataRecord createRandomIPv4FiveTupleDataRecord() throws UnknownHostException {
		IPv4FiveTupleDataRecord record = new IPv4FiveTupleDataRecord();
		Random random = new Random();

		byte[] addrIPv4 = new byte[4];
		random.nextBytes(addrIPv4);
		record.setSourceIPv4Address((Inet4Address) Inet4Address.getByAddress(addrIPv4));
		random.nextBytes(addrIPv4);
		record.setDestinationIPv4Address((Inet4Address) Inet4Address.getByAddress(addrIPv4));

		record.setSourceTransportPort(1024 + random.nextInt(64512));
		record.setDestinationTransportPort(1024 + random.nextInt(64512));

		short protocol = (short) (random.nextBoolean() ? 6 : 17);
		record.setProtocolIdentifier(protocol);
		record.setPacketDeltaCount(1 + random.nextInt(32));
		record.setOctetDeltaCount(64 + random.nextInt(4096));
		record.setTcpControlBits(protocol == 6 ? (random.nextBoolean() ? 2 : 18) : 0);

		BigInteger now = BigInteger.valueOf(new Date().getTime());
		record.setFlowStartMilliseconds(now);
		record.setFlowEndMilliseconds(now);
		return record;
	}
	public static void main(String args[]) {
		
		String ipfixCollectorHost = "127.0.0.1";
		int ipfixCollectorPort = 7865;
		
		try (DatagramSocket socket = new DatagramSocket())
		{
		MessageHeader mh = createRandomL2IPIPfixMessage();
		L2IPDataRecord l2ip = (L2IPDataRecord) mh.getSetHeaders().get(mh.getSetHeaders().size()-1).getDataRecords().get(0);
		long seqNumber = 0;
		while (true) {
			// Message header updating
			mh.setSequenceNumber(seqNumber);
			mh.setExportTime(new Date());
			seqNumber++;

			// L2IP updating
			BigInteger flowStartEnd = BigInteger.valueOf(new Date().getTime());
			l2ip.setFlowStartMilliseconds(flowStartEnd);
			l2ip.setFlowEndMilliseconds(flowStartEnd);


			DatagramPacket dp = new DatagramPacket(mh.getBytes(), mh.getBytes().length, InetAddress.getByName(ipfixCollectorHost),
					ipfixCollectorPort);
			if (debug)
				System.out.println("Sending: " + mh);
			socket.send(dp);


			Thread.sleep(10000);

		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static MessageHeader createRandomIPFIXMessage(String template) throws UnknownHostException, UtilityException {
		String normalizedTemplate = IPFIXTemplateType.normalize(template);
		if (IPFIXTemplateType.IPV4_FIVE_TUPLE.equals(normalizedTemplate)) {
			return createRandomIPv4FiveTupleIPfixMessage();
		}
		return createRandomL2IPIPfixMessage();
	}

	public static MessageHeader createRandomL2IPIPfixMessage() throws UnknownHostException, UtilityException {
		return createRandomFlowMessage(IPFIXTemplateType.L2IP);
	}

	public static MessageHeader createRandomIPv4FiveTupleIPfixMessage() throws UnknownHostException, UtilityException {
		return createRandomFlowMessage(IPFIXTemplateType.IPV4_FIVE_TUPLE);
	}

	private static MessageHeader createRandomFlowMessage(String template) throws UnknownHostException, UtilityException {
		String normalizedTemplate = IPFIXTemplateType.normalize(template);
		MessageHeader mh = createMessageHeader(OBSERVATION_DOMAIN_ID);

		SetHeader shTemplate = new SetHeader();
		mh.getSetHeaders().add(shTemplate);
		shTemplate.setSetID(2);
		shTemplate.getTemplateRecords().add(createTemplateRecord(normalizedTemplate));

		SetHeader shTemplateOptions = new SetHeader();
		mh.getSetHeaders().add(shTemplateOptions);
		shTemplateOptions.setSetID(3);
		shTemplateOptions.getOptionTemplateRecords().add(createDefaultOptionTemplate());

		SetHeader shSampling = new SetHeader();
		shSampling.setSetID(SamplingDataRecord.SET_ID);
		mh.getSetHeaders().add(shSampling);

		SamplingDataRecord sdr = new SamplingDataRecord();
		shSampling.getDataRecords().add(sdr);
		sdr.setObservationDomainId(67108864);
		sdr.setSelectorAlgorithm(1);
		sdr.setSamplingPacketInterval(1);
		sdr.setSamplingPacketSpace(0);

		SetHeader shDataRecord = new SetHeader();
		shDataRecord.setSetID(getTemplateId(normalizedTemplate));
		mh.getSetHeaders().add(shDataRecord);
		shDataRecord.getDataRecords().add(createDataRecordForTemplate(normalizedTemplate));

		return mh;
	}

	private static TemplateRecord createTemplateRecord(String template) {
		return IPFIXTemplateType.IPV4_FIVE_TUPLE.equals(template)
				? createIPv4FiveTupleTemplateRecord()
				: createDefaultTemplateRecord();
	}

	private static int getTemplateId(String template) {
		return IPFIXTemplateType.IPV4_FIVE_TUPLE.equals(template)
				? IPv4FiveTupleDataRecord.TEMPLATE_ID
				: L2IPDataRecord.TEMPLATE_ID;
	}

	private static tech.wenisch.ipfix.generator.datastructures.ipfix.DataRecord createDataRecordForTemplate(String template)
			throws UnknownHostException, UtilityException {
		if (IPFIXTemplateType.IPV4_FIVE_TUPLE.equals(template)) {
			return createRandomIPv4FiveTupleDataRecord();
		}
		return createRandomDataRecord();
	}

}
