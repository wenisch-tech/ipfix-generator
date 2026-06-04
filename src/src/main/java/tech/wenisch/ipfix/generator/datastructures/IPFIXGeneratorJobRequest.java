package tech.wenisch.ipfix.generator.datastructures;

import com.fasterxml.jackson.annotation.JsonAlias;

import io.swagger.v3.oas.annotations.media.Schema;

public class IPFIXGeneratorJobRequest 
{
	String destHost;
	String destPort;
	@JsonAlias("destPPS")
	String pps;
	@JsonAlias("destTotalPackets")
	String totalPackets;
	@Schema(description = "The IPFIX data template to generate.", allowableValues = {
			IPFIXTemplateType.L2IP, IPFIXTemplateType.IPV4_FIVE_TUPLE }, defaultValue = IPFIXTemplateType.L2IP)
	String template = IPFIXTemplateType.L2IP;

	public IPFIXGeneratorJobRequest() {
	}

	public IPFIXGeneratorJobRequest(String destHost, String destPort, String pps, String totalPackets)
	{
		this(destHost, destPort, pps, totalPackets, IPFIXTemplateType.L2IP);
	}

	public IPFIXGeneratorJobRequest(String destHost, String destPort, String pps, String totalPackets, String template)
	{
		this.destHost=destHost;
		this.destPort=destPort;
		this.pps=pps;
		this.totalPackets=totalPackets;
		this.template=IPFIXTemplateType.normalize(template);
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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = IPFIXTemplateType.normalize(template);
	}
}
