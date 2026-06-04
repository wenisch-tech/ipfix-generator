package tech.wenisch.ipfix.generator.datastructures.ipfix;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import tech.wenisch.ipfix.generator.datastructures.networking.MacAddress;
import tech.wenisch.ipfix.generator.exceptions.HeaderBytesException;
import tech.wenisch.ipfix.generator.exceptions.HeaderParseException;
import tech.wenisch.ipfix.generator.managers.UtilityManager;

public class L2IPDataRecord extends DataRecord implements FlowDataRecord {
	public static final int TEMPLATE_ID = 306;
	protected static final int LENGTH = 111;
	
	private MacAddress sourceMacAddress;
	private MacAddress destinationMacAddress;
	private long ingressPhysicalInterface;
	private long egressPhysicalInterface;
	private int dot1qVlanId;
	private int dot1qCustomerVlanId;
	private int postDot1qVlanId;
	private int postDot1qCustomerVlanId;
	private Inet4Address sourceIPv4Address;
	private Inet4Address destinationIPv4Address;
	private Inet6Address sourceIPv6Address;
	private Inet6Address destinationIPv6Address;
	private long packetDeltaCount;
	private long octetDeltaCount;
	private BigInteger flowStartMilliseconds;
	private BigInteger flowEndMilliseconds;
	private int sourceTransportPort;
	private int destinationTransportPort;
	private int tcpControlBits;
	private short protocolIdentifier;
	private long ipv6ExtensionHeaders;
	private short nextHeaderIPv6;
	private long flowLabelIPv6;
	private short ipClassOfService;
	private short ipVersion;
	private int icmpTypeCodeIPv4;
	
	public L2IPDataRecord() throws UnknownHostException {
		sourceMacAddress = new MacAddress();
		destinationMacAddress = new MacAddress();
		
		byte[] addrIPv4 = new byte[4];
		sourceIPv4Address = (Inet4Address) InetAddress.getByAddress(addrIPv4);
		destinationIPv4Address = (Inet4Address) InetAddress.getByAddress(addrIPv4);
		
		byte[] addrIPv6 = new byte[16];
		sourceIPv6Address = (Inet6Address) InetAddress.getByAddress(addrIPv6);
		destinationIPv6Address = (Inet6Address) InetAddress.getByAddress(addrIPv6);
	}
		
	public MacAddress getSourceMacAddress() {
		return sourceMacAddress;
	}

	public void setSourceMacAddress(MacAddress sourceMacAddress) {
		this.sourceMacAddress = sourceMacAddress;
	}

	public MacAddress getDestinationMacAddress() {
		return destinationMacAddress;
	}

	public void setDestinationMacAddress(MacAddress destinationMacAddress) {
		this.destinationMacAddress = destinationMacAddress;
	}

	public long getIngressPhysicalInterface() {
		return ingressPhysicalInterface;
	}

	public void setIngressPhysicalInterface(long ingressPhysicalInterface) {
		this.ingressPhysicalInterface = ingressPhysicalInterface;
	}

	public long getEgressPhysicalInterface() {
		return egressPhysicalInterface;
	}

	public void setEgressPhysicalInterface(long egressPhysicalInterface) {
		this.egressPhysicalInterface = egressPhysicalInterface;
	}

	public int getDot1qVlanId() {
		return dot1qVlanId;
	}

	public void setDot1qVlanId(int dot1qVlanId) {
		this.dot1qVlanId = dot1qVlanId;
	}

	public int getDot1qCustomerVlanId() {
		return dot1qCustomerVlanId;
	}

	public void setDot1qCustomerVlanId(int dot1qCustomerVlanId) {
		this.dot1qCustomerVlanId = dot1qCustomerVlanId;
	}

	public int getPostDot1qVlanId() {
		return postDot1qVlanId;
	}

	public void setPostDot1qVlanId(int postDot1qVlanId) {
		this.postDot1qVlanId = postDot1qVlanId;
	}

	public int getPostDot1qCustomerVlanId() {
		return postDot1qCustomerVlanId;
	}

	public void setPostDot1qCustomerVlanId(int postDot1qCustomerVlanId) {
		this.postDot1qCustomerVlanId = postDot1qCustomerVlanId;
	}

	public Inet4Address getSourceIPv4Address() {
		return sourceIPv4Address;
	}

	public void setSourceIPv4Address(Inet4Address sourceIPv4Address) {
		this.sourceIPv4Address = sourceIPv4Address;
	}

	public Inet4Address getDestinationIPv4Address() {
		return destinationIPv4Address;
	}

	public void setDestinationIPv4Address(Inet4Address destinationIPv4Address) {
		this.destinationIPv4Address = destinationIPv4Address;
	}

	public Inet6Address getSourceIPv6Address() {
		return sourceIPv6Address;
	}

	public void setSourceIPv6Address(Inet6Address sourceIPv6Address) {
		this.sourceIPv6Address = sourceIPv6Address;
	}

	public Inet6Address getDestinationIPv6Address() {
		return destinationIPv6Address;
	}

	public void setDestinationIPv6Address(Inet6Address destinationIPv6Address) {
		this.destinationIPv6Address = destinationIPv6Address;
	}

	public long getPacketDeltaCount() {
		return packetDeltaCount;
	}

	public void setPacketDeltaCount(long packetDeltaCount) {
		this.packetDeltaCount = packetDeltaCount;
	}

	public long getOctetDeltaCount() {
		return octetDeltaCount;
	}

	public void setOctetDeltaCount(long octetDeltaCount) {
		this.octetDeltaCount = octetDeltaCount;
	}

	public BigInteger getFlowStartMilliseconds() {
		return flowStartMilliseconds;
	}

	public void setFlowStartMilliseconds(BigInteger flowStartMilliseconds) {
		this.flowStartMilliseconds = flowStartMilliseconds;
	}

	public BigInteger getFlowEndMilliseconds() {
		return flowEndMilliseconds;
	}

	public void setFlowEndMilliseconds(BigInteger flowEndMilliseconds) {
		this.flowEndMilliseconds = flowEndMilliseconds;
	}

	public int getSourceTransportPort() {
		return sourceTransportPort;
	}

	public void setSourceTransportPort(int sourceTransportPort) {
		this.sourceTransportPort = sourceTransportPort;
	}

	public int getDestinationTransportPort() {
		return destinationTransportPort;
	}

	public void setDestinationTransportPort(int destinationTransportPort) {
		this.destinationTransportPort = destinationTransportPort;
	}

	public int getTcpControlBits() {
		return tcpControlBits;
	}

	public void setTcpControlBits(int tcpControlBits) {
		this.tcpControlBits = tcpControlBits;
	}

	public short getProtocolIdentifier() {
		return protocolIdentifier;
	}

	public void setProtocolIdentifier(short protocolIdentifier) {
		this.protocolIdentifier = protocolIdentifier;
	}

	public long getIpv6ExtensionHeaders() {
		return ipv6ExtensionHeaders;
	}

	public void setIpv6ExtensionHeaders(long ipv6ExtensionHeaders) {
		this.ipv6ExtensionHeaders = ipv6ExtensionHeaders;
	}

	public short getNextHeaderIPv6() {
		return nextHeaderIPv6;
	}

	public void setNextHeaderIPv6(short nextHeaderIPv6) {
		this.nextHeaderIPv6 = nextHeaderIPv6;
	}

	public long getFlowLabelIPv6() {
		return flowLabelIPv6;
	}

	public void setFlowLabelIPv6(long flowLabelIPv6) {
		this.flowLabelIPv6 = flowLabelIPv6;
	}

	public short getIpClassOfService() {
		return ipClassOfService;
	}

	public void setIpClassOfService(short ipClassOfService) {
		this.ipClassOfService = ipClassOfService;
	}

	public short getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(short ipVersion) {
		this.ipVersion = ipVersion;
	}

	public int getIcmpTypeCodeIPv4() {
		return icmpTypeCodeIPv4;
	}

	public void setIcmpTypeCodeIPv4(int icmpTypeCodeIPv4) {
		this.icmpTypeCodeIPv4 = icmpTypeCodeIPv4;
	}
	
	@Override
	public int getLength() {
		return LENGTH;
	}

	@Override
	public String getSourceAddressText() {
		return ipVersion == 6 ? sourceIPv6Address.getHostAddress() : sourceIPv4Address.getHostAddress();
	}

	@Override
	public String getDestinationAddressText() {
		return ipVersion == 6 ? destinationIPv6Address.getHostAddress() : destinationIPv4Address.getHostAddress();
	}

	public static L2IPDataRecord parse(byte[] data) throws HeaderParseException {
		try {
			// more parsing
			if (data.length < 111) throw new HeaderParseException("Data array too short.");
			L2IPDataRecord lidr = new L2IPDataRecord();
			// sourceMacAddress
			byte[] sourceMacAddress = new byte[6];
			System.arraycopy(data, 0, sourceMacAddress, 0, 6);
			lidr.setSourceMacAddress(new MacAddress(sourceMacAddress));
			// destinationMacAddress
			byte[] destinationMacAddress = new byte[6];
			System.arraycopy(data, 6, destinationMacAddress, 0, 6);
			lidr.setDestinationMacAddress(new MacAddress(destinationMacAddress));
			// ingressPhysicalInterface
			byte[] ingressPhysicalInterface = new byte[4];
			System.arraycopy(data, 12, ingressPhysicalInterface, 0, 4);
			lidr.setIngressPhysicalInterface(UtilityManager.fourBytesToLong(ingressPhysicalInterface));
			// egressPhysicalInterface
			byte[] egressPhysicalInterface = new byte[4];
			System.arraycopy(data, 16, egressPhysicalInterface, 0, 4);
			lidr.setEgressPhysicalInterface(UtilityManager.fourBytesToLong(egressPhysicalInterface));
			// dot1qVlanId
			byte[] dot1qVlanId = new byte[2];
			System.arraycopy(data, 20, dot1qVlanId, 0, 2);
			lidr.setDot1qVlanId(UtilityManager.twoBytesToInteger(dot1qVlanId));
			// dot1qCustomerVlanId
			byte[] dot1qCustomerVlanId = new byte[2];
			System.arraycopy(data, 22, dot1qCustomerVlanId, 0, 2);
			lidr.setDot1qCustomerVlanId(UtilityManager.twoBytesToInteger(dot1qCustomerVlanId));
			// postDot1qVlanId
			byte[] postDot1qVlanId = new byte[2];
			System.arraycopy(data, 24, postDot1qVlanId, 0, 2);
			lidr.setPostDot1qVlanId(UtilityManager.twoBytesToInteger(postDot1qVlanId));
			// postDot1qCustomerVlanId
			byte[] postDot1qCustomerVlanId = new byte[2];
			System.arraycopy(data, 26, postDot1qCustomerVlanId, 0, 2);
			lidr.setPostDot1qCustomerVlanId(UtilityManager.twoBytesToInteger(postDot1qCustomerVlanId));
			// sourceIPv4Address
			byte[] sourceIPv4Address = new byte[4];
			System.arraycopy(data, 28, sourceIPv4Address, 0, 4);
			lidr.setSourceIPv4Address((Inet4Address) Inet4Address.getByAddress(sourceIPv4Address));
			// destinationIPv4Address
			byte[] destinationIPv4Address = new byte[4];
			System.arraycopy(data, 32, destinationIPv4Address, 0, 4);
			lidr.setDestinationIPv4Address((Inet4Address) Inet4Address.getByAddress(destinationIPv4Address));
			// sourceIPv6Address
			byte[] sourceIPv6Address = new byte[16];
			System.arraycopy(data, 36, sourceIPv6Address, 0, 16);
			lidr.setSourceIPv6Address((Inet6Address) Inet6Address.getByAddress(sourceIPv6Address));
			// destinationIPv6Address
			byte[] destinationIPv6Address = new byte[16];
			System.arraycopy(data, 52, destinationIPv6Address, 0, 16);
			lidr.setDestinationIPv6Address((Inet6Address) Inet6Address.getByAddress(destinationIPv6Address));
			// packetDeltaCount
			byte[] packetDeltaCount = new byte[4];
			System.arraycopy(data, 68, packetDeltaCount, 0, 4);
			lidr.setPacketDeltaCount(UtilityManager.fourBytesToLong(packetDeltaCount));
			// octetDeltaCount
			byte[] octetDeltaCount = new byte[4];
			System.arraycopy(data, 72, octetDeltaCount, 0, 4);
			lidr.setOctetDeltaCount(UtilityManager.fourBytesToLong(octetDeltaCount));
			// flowStartMilliseconds
			byte[] flowStartMilliseconds = new byte[8];
			System.arraycopy(data, 76, flowStartMilliseconds, 0, 8);
			lidr.setFlowStartMilliseconds(UtilityManager.eightBytesToBigInteger(flowStartMilliseconds));
			// flowEndMilliseconds
			byte[] flowEndMilliseconds = new byte[8];
			System.arraycopy(data, 84, flowEndMilliseconds, 0, 8);
			lidr.setFlowEndMilliseconds(UtilityManager.eightBytesToBigInteger(flowEndMilliseconds));
			// sourceTransportPort
			byte[] sourceTransportPort = new byte[2];
			System.arraycopy(data, 92, sourceTransportPort, 0, 2);
			lidr.setSourceTransportPort(UtilityManager.twoBytesToInteger(sourceTransportPort));
			// destinationTransportPort
			byte[] destinationTransportPort = new byte[2];
			System.arraycopy(data, 94, destinationTransportPort, 0, 2);
			lidr.setDestinationTransportPort(UtilityManager.twoBytesToInteger(destinationTransportPort));
			// tcpControlBits
			byte tcpControlBits = data[96];
			lidr.setTcpControlBits(UtilityManager.oneByteToInteger(tcpControlBits));
			// protocolIdentifier
			byte protocolIdentifier = data[97];
			lidr.setProtocolIdentifier(UtilityManager.oneByteToShort(protocolIdentifier));
			// ipv6ExtensionHeaders
			byte[] ipv6ExtensionHeaders = new byte[4];
			System.arraycopy(data, 98, ipv6ExtensionHeaders, 0, 4);
			lidr.setIpv6ExtensionHeaders(UtilityManager.fourBytesToLong(ipv6ExtensionHeaders));
			// nextHeaderIPv6
			byte nextHeaderIPv6 = data[102];
			lidr.setNextHeaderIPv6(UtilityManager.oneByteToShort(nextHeaderIPv6));
			// flowLabelIPv6
			byte[] flowLabelIPv6 = new byte[4];
			System.arraycopy(data, 103, flowLabelIPv6, 0, 4);
			lidr.setFlowLabelIPv6(UtilityManager.fourBytesToLong(flowLabelIPv6));
			// ipClassOfService
			byte ipClassOfService = data[107];
			lidr.setIpClassOfService(UtilityManager.oneByteToShort(ipClassOfService));
			// ipVersion
			byte ipVersion = data[108];
			lidr.setIpVersion(UtilityManager.oneByteToShort(ipVersion));
			// icmpTypeCodeIPv4
			byte[] icmpTypeCodeIPv4 = new byte[2];
			System.arraycopy(data, 109, icmpTypeCodeIPv4, 0, 2);
			lidr.setIcmpTypeCodeIPv4(UtilityManager.twoBytesToInteger(icmpTypeCodeIPv4));
			return lidr;
		} catch (Exception e) {
			throw new HeaderParseException("Parse error: " + e.getMessage());
		}
	}
	
	public byte[] getBytes() throws HeaderBytesException {
		try {
			byte[] data = new byte[LENGTH];

			// sourceMacAddress
			System.arraycopy(sourceMacAddress.getBytes(), 0, data, 0, 6);
			// destinationMacAddress
			System.arraycopy(destinationMacAddress.getBytes(), 0, data, 6, 6);
			// ingressPhysicalInterface
			System.arraycopy(UtilityManager.longToFourBytes(ingressPhysicalInterface), 0, data, 12, 4);
			// egressPhysicalInterface
			System.arraycopy(UtilityManager.longToFourBytes(egressPhysicalInterface), 0, data, 16, 4);
			// dot1qVlanId
			System.arraycopy(UtilityManager.integerToTwoBytes(dot1qVlanId), 0, data, 20, 2);
			// dot1qCustomerVlanId
			System.arraycopy(UtilityManager.integerToTwoBytes(dot1qCustomerVlanId), 0, data, 22, 2);
			// postDot1qVlanId
			System.arraycopy(UtilityManager.integerToTwoBytes(postDot1qVlanId), 0, data, 24, 2);
			// postDot1qCustomerVlanId
			System.arraycopy(UtilityManager.integerToTwoBytes(postDot1qCustomerVlanId), 0, data, 26, 2);
			// sourceIPv4Address
			System.arraycopy(sourceIPv4Address.getAddress(), 0, data, 28, 4);
			// destinationIPv4Address
			System.arraycopy(destinationIPv4Address.getAddress(), 0, data, 32, 4);
			// sourceIPv6Address
			System.arraycopy(sourceIPv6Address.getAddress(), 0, data, 36, 16);
			// destinationIPv6Address
			System.arraycopy(destinationIPv6Address.getAddress(), 0, data, 52, 16);
			// packetDeltaCount
			System.arraycopy(UtilityManager.longToFourBytes(packetDeltaCount), 0, data, 68, 4);
			// octetDeltaCount
			System.arraycopy(UtilityManager.longToFourBytes(octetDeltaCount), 0, data, 72, 4);
			// flowStartMilliseconds
			System.arraycopy(UtilityManager.BigIntegerToEightBytes(flowStartMilliseconds), 0, data, 76, 8);
			// flowEndMilliseconds
			System.arraycopy(UtilityManager.BigIntegerToEightBytes(flowEndMilliseconds), 0, data, 84, 8);
			// sourceTransportPort
			System.arraycopy(UtilityManager.integerToTwoBytes(sourceTransportPort), 0, data, 92, 2);
			// destinationTransportPort
			System.arraycopy(UtilityManager.integerToTwoBytes(destinationTransportPort), 0, data, 94, 2);
			// tcpControlBits
			data[96] = UtilityManager.integerToOneByte(tcpControlBits);
			// protocolIdentifier
			data[97] = UtilityManager.integerToOneByte(protocolIdentifier);
			// ipv6ExtensionHeaders
			System.arraycopy(UtilityManager.longToFourBytes(ipv6ExtensionHeaders), 0, data, 98, 4);
			// nextHeaderIPv6
			data[102] = UtilityManager.shortToOneByte(nextHeaderIPv6);
			// flowLabelIPv6
			System.arraycopy(UtilityManager.longToFourBytes(flowLabelIPv6), 0, data, 103, 4);
			// ipClassOfService
			data[107] = UtilityManager.shortToOneByte(ipClassOfService);
			// ipVersion
			data[108] = UtilityManager.shortToOneByte(ipVersion);
			// icmpTypeCodeIPv4
			System.arraycopy(UtilityManager.integerToTwoBytes(icmpTypeCodeIPv4), 0, data, 109, 2);

			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[L2IPDataRecord]: ");
		sb.append("SourceMacAddress: ");
		sb.append(sourceMacAddress);
		sb.append(", ");
		sb.append("DestinationMacAddress: ");
		sb.append(destinationMacAddress);
		sb.append(", ");
		sb.append("IngressPhysicalInterface: ");
		sb.append(ingressPhysicalInterface);
		sb.append(", ");
		sb.append("EgressPhysicalInterface: ");
		sb.append(egressPhysicalInterface);
		sb.append(", ");
		sb.append("Dot1qVlanId: ");
		sb.append(dot1qVlanId);
		sb.append(", ");
		sb.append("Dot1qCustomerVlanId: ");
		sb.append(dot1qCustomerVlanId);
		sb.append(", ");
		sb.append("PostDot1qVlanId: ");
		sb.append(postDot1qVlanId);
		sb.append(", ");
		sb.append("PostDot1qCustomerVlanId: ");
		sb.append(postDot1qCustomerVlanId);
		sb.append(", ");
		sb.append("SourceIPv4Address: ");
		sb.append(sourceIPv4Address.getHostAddress());
		sb.append(", ");
		sb.append("DestinationIPv4Address: ");
		sb.append(destinationIPv4Address.getHostAddress());
		sb.append(", ");
		sb.append("SourceIPv6Address: ");
		sb.append(sourceIPv6Address.getHostAddress());
		sb.append(", ");
		sb.append("DestinationIPv6Address: ");
		sb.append(destinationIPv6Address.getHostAddress());
		sb.append(", ");
		sb.append("PacketDeltaCount: ");
		sb.append(packetDeltaCount);
		sb.append(", ");
		sb.append("OctetDeltaCount: ");
		sb.append(octetDeltaCount);
		sb.append(", ");
		sb.append("FlowStartMilliseconds: ");
		sb.append(flowStartMilliseconds);
		sb.append(", ");
		sb.append("FlowEndMilliseconds: ");
		sb.append(flowEndMilliseconds);
		sb.append(", ");
		sb.append("SourceTransportPort: ");
		sb.append(sourceTransportPort);
		sb.append(", ");
		sb.append("DestinationTransportPort: ");
		sb.append(destinationTransportPort);
		sb.append(", ");
		sb.append("TcpControlBits: ");
		sb.append(tcpControlBits);
		sb.append(", ");
		sb.append("ProtocolIdentifier: ");
		sb.append(protocolIdentifier);
		sb.append(", ");
		sb.append("Ipv6ExtensionHeaders: ");
		sb.append(ipv6ExtensionHeaders);
		sb.append(", ");
		sb.append("NextHeaderIPv6: ");
		sb.append(nextHeaderIPv6);
		sb.append(", ");
		sb.append("FlowLabelIPv6: ");
		sb.append(flowLabelIPv6);
		sb.append(", ");
		sb.append("IpClassOfService: ");
		sb.append(ipClassOfService);
		sb.append(", ");
		sb.append("IpVersion: ");
		sb.append(ipVersion);
		sb.append(", ");
		sb.append("IcmpTypeCodeIPv4: ");
		sb.append(icmpTypeCodeIPv4);
		
		return sb.toString();
	}
}
