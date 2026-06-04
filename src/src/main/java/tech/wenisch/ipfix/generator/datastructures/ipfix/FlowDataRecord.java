package tech.wenisch.ipfix.generator.datastructures.ipfix;

import java.math.BigInteger;

public interface FlowDataRecord {
	String getSourceAddressText();
	String getDestinationAddressText();
	int getSourceTransportPort();
	int getDestinationTransportPort();
	void setFlowStartMilliseconds(BigInteger flowStartMilliseconds);
	void setFlowEndMilliseconds(BigInteger flowEndMilliseconds);
}
