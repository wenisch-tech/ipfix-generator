package tech.wenisch.ipfix.generator.datastructures.ipfix;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import tech.wenisch.ipfix.generator.exceptions.HeaderBytesException;
import tech.wenisch.ipfix.generator.exceptions.HeaderParseException;
import tech.wenisch.ipfix.generator.managers.UtilityManager;

public class IPv4FiveTupleDataRecord extends DataRecord implements FlowDataRecord {
	public static final int TEMPLATE_ID = 400;
	protected static final int LENGTH = 38;

	private Inet4Address sourceIPv4Address;
	private Inet4Address destinationIPv4Address;
	private int sourceTransportPort;
	private int destinationTransportPort;
	private short protocolIdentifier;
	private long packetDeltaCount;
	private long octetDeltaCount;
	private BigInteger flowStartMilliseconds;
	private BigInteger flowEndMilliseconds;
	private int tcpControlBits;

	public IPv4FiveTupleDataRecord() throws UnknownHostException {
		byte[] addrIPv4 = new byte[4];
		sourceIPv4Address = (Inet4Address) InetAddress.getByAddress(addrIPv4);
		destinationIPv4Address = (Inet4Address) InetAddress.getByAddress(addrIPv4);
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

	@Override
	public int getSourceTransportPort() {
		return sourceTransportPort;
	}

	public void setSourceTransportPort(int sourceTransportPort) {
		this.sourceTransportPort = sourceTransportPort;
	}

	@Override
	public int getDestinationTransportPort() {
		return destinationTransportPort;
	}

	public void setDestinationTransportPort(int destinationTransportPort) {
		this.destinationTransportPort = destinationTransportPort;
	}

	public short getProtocolIdentifier() {
		return protocolIdentifier;
	}

	public void setProtocolIdentifier(short protocolIdentifier) {
		this.protocolIdentifier = protocolIdentifier;
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

	@Override
	public void setFlowStartMilliseconds(BigInteger flowStartMilliseconds) {
		this.flowStartMilliseconds = flowStartMilliseconds;
	}

	public BigInteger getFlowEndMilliseconds() {
		return flowEndMilliseconds;
	}

	@Override
	public void setFlowEndMilliseconds(BigInteger flowEndMilliseconds) {
		this.flowEndMilliseconds = flowEndMilliseconds;
	}

	public int getTcpControlBits() {
		return tcpControlBits;
	}

	public void setTcpControlBits(int tcpControlBits) {
		this.tcpControlBits = tcpControlBits;
	}

	@Override
	public int getLength() {
		return LENGTH;
	}

	@Override
	public String getSourceAddressText() {
		return sourceIPv4Address.getHostAddress();
	}

	@Override
	public String getDestinationAddressText() {
		return destinationIPv4Address.getHostAddress();
	}

	public static IPv4FiveTupleDataRecord parse(byte[] data) throws HeaderParseException {
		try {
			if (data.length < LENGTH) {
				throw new HeaderParseException("Data array too short.");
			}

			IPv4FiveTupleDataRecord record = new IPv4FiveTupleDataRecord();

			byte[] sourceIPv4Address = new byte[4];
			System.arraycopy(data, 0, sourceIPv4Address, 0, 4);
			record.setSourceIPv4Address((Inet4Address) Inet4Address.getByAddress(sourceIPv4Address));

			byte[] destinationIPv4Address = new byte[4];
			System.arraycopy(data, 4, destinationIPv4Address, 0, 4);
			record.setDestinationIPv4Address((Inet4Address) Inet4Address.getByAddress(destinationIPv4Address));

			byte[] sourceTransportPort = new byte[2];
			System.arraycopy(data, 8, sourceTransportPort, 0, 2);
			record.setSourceTransportPort(UtilityManager.twoBytesToInteger(sourceTransportPort));

			byte[] destinationTransportPort = new byte[2];
			System.arraycopy(data, 10, destinationTransportPort, 0, 2);
			record.setDestinationTransportPort(UtilityManager.twoBytesToInteger(destinationTransportPort));

			record.setProtocolIdentifier(UtilityManager.oneByteToShort(data[12]));

			byte[] packetDeltaCount = new byte[4];
			System.arraycopy(data, 13, packetDeltaCount, 0, 4);
			record.setPacketDeltaCount(UtilityManager.fourBytesToLong(packetDeltaCount));

			byte[] octetDeltaCount = new byte[4];
			System.arraycopy(data, 17, octetDeltaCount, 0, 4);
			record.setOctetDeltaCount(UtilityManager.fourBytesToLong(octetDeltaCount));

			byte[] flowStartMilliseconds = new byte[8];
			System.arraycopy(data, 21, flowStartMilliseconds, 0, 8);
			record.setFlowStartMilliseconds(UtilityManager.eightBytesToBigInteger(flowStartMilliseconds));

			byte[] flowEndMilliseconds = new byte[8];
			System.arraycopy(data, 29, flowEndMilliseconds, 0, 8);
			record.setFlowEndMilliseconds(UtilityManager.eightBytesToBigInteger(flowEndMilliseconds));

			record.setTcpControlBits(UtilityManager.oneByteToInteger(data[37]));
			return record;
		} catch (Exception e) {
			throw new HeaderParseException("Parse error: " + e.getMessage());
		}
	}

	@Override
	public byte[] getBytes() throws HeaderBytesException {
		try {
			byte[] data = new byte[LENGTH];

			System.arraycopy(sourceIPv4Address.getAddress(), 0, data, 0, 4);
			System.arraycopy(destinationIPv4Address.getAddress(), 0, data, 4, 4);
			System.arraycopy(UtilityManager.integerToTwoBytes(sourceTransportPort), 0, data, 8, 2);
			System.arraycopy(UtilityManager.integerToTwoBytes(destinationTransportPort), 0, data, 10, 2);
			data[12] = UtilityManager.shortToOneByte(protocolIdentifier);
			System.arraycopy(UtilityManager.longToFourBytes(packetDeltaCount), 0, data, 13, 4);
			System.arraycopy(UtilityManager.longToFourBytes(octetDeltaCount), 0, data, 17, 4);
			System.arraycopy(UtilityManager.BigIntegerToEightBytes(flowStartMilliseconds), 0, data, 21, 8);
			System.arraycopy(UtilityManager.BigIntegerToEightBytes(flowEndMilliseconds), 0, data, 29, 8);
			data[37] = UtilityManager.integerToOneByte(tcpControlBits);

			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[IPv4FiveTupleDataRecord]: ");
		sb.append("SourceIPv4Address: ");
		sb.append(sourceIPv4Address.getHostAddress());
		sb.append(", DestinationIPv4Address: ");
		sb.append(destinationIPv4Address.getHostAddress());
		sb.append(", SourceTransportPort: ");
		sb.append(sourceTransportPort);
		sb.append(", DestinationTransportPort: ");
		sb.append(destinationTransportPort);
		sb.append(", ProtocolIdentifier: ");
		sb.append(protocolIdentifier);
		sb.append(", PacketDeltaCount: ");
		sb.append(packetDeltaCount);
		sb.append(", OctetDeltaCount: ");
		sb.append(octetDeltaCount);
		sb.append(", FlowStartMilliseconds: ");
		sb.append(flowStartMilliseconds);
		sb.append(", FlowEndMilliseconds: ");
		sb.append(flowEndMilliseconds);
		sb.append(", TcpControlBits: ");
		sb.append(tcpControlBits);
		return sb.toString();
	}
}
