package tech.wenisch.ipfix.generator.datastructures;

import com.fasterxml.jackson.annotation.JsonAlias;

public class IPFIXGeneratorJobRequest 
{
	String destHost;
	String destPort;
	@JsonAlias("destPPS")
	String pps;
	@JsonAlias("destTotalPackets")
	String totalPackets;

	public IPFIXGeneratorJobRequest() {
	}

	public IPFIXGeneratorJobRequest(String destHost, String destPort, String pps, String totalPackets)
	{
		this.destHost=destHost;
		this.destPort=destPort;
		this.pps=pps;
		this.totalPackets=totalPackets;
	
	}

	public String getDestHost() {
		return destHost;
	}

	public void setDestHost(String destHost) {
		this.destHost = destHost;
	}

	public String getDestPort() {
		return destPort;
	}

	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	public String getPps() {
		return pps;
	}

	public void setPps(String pps) {
		this.pps = pps;
	}

	public String getTotalPackets() {
		return totalPackets;
	}

	public void setTotalPackets(String totalPackets) {
		this.totalPackets = totalPackets;
	}
}
