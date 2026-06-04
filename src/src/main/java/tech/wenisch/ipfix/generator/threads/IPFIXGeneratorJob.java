package tech.wenisch.ipfix.generator.threads;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tech.wenisch.ipfix.generator.datastructures.IPFIXGeneratorJobHistoryEntry;
import tech.wenisch.ipfix.generator.datastructures.IPFIXGeneratorJobRequest;
import tech.wenisch.ipfix.generator.datastructures.ipfix.L2IPDataRecord;
import tech.wenisch.ipfix.generator.datastructures.ipfix.MessageHeader;
import tech.wenisch.ipfix.generator.managers.IPFIXGeneratorManager;

public class IPFIXGeneratorJob implements Runnable {
	String destHost;
	String name;
	String created;
	String status;
	int destPort;
	int id;
	
	long pps;
	int totalPackets;
	int packetsSend;
	List <IPFIXGeneratorJobHistoryEntry> history = new ArrayList<IPFIXGeneratorJobHistoryEntry>();


	private volatile boolean active = true;
	private volatile Thread executionThread;
	static int jobIDCounter = 1;


    public IPFIXGeneratorJob(IPFIXGeneratorJobRequest request) {
    	this.packetsSend=0;
    	this.status="Initializing";
    	this.created = new Date().toString();
        this.id=jobIDCounter;
        jobIDCounter++;
        this.destHost=request.getDestHost();
        this.destPort=Integer.valueOf(request.getDestPort());
        this.pps=Long.parseLong(request.getPps());
        this.totalPackets=Integer.valueOf(request.getTotalPackets());
        this.name=totalPackets == 0
        		? "Sending continuously to " + destHost + ":" + destPort + " (" + pps + " PPS)"
        		: "Sending " + totalPackets + " to " + destHost + ":" + destPort + " (" + pps + " PPS)";
	}

	@Override
    public void run() {
		executionThread = Thread.currentThread();
		this.status="Running";
		try (DatagramSocket socket = new DatagramSocket())
		{
		MessageHeader mh = IPFIXGeneratorManager.createRandomL2IPIPfixMessage();
		L2IPDataRecord l2ip = (L2IPDataRecord) mh.getSetHeaders().get(mh.getSetHeaders().size()-1).getDataRecords().get(0);
		long seqNumber = 0;
		boolean continuousMode = totalPackets == 0;

		while (active && (continuousMode || packetsSend < totalPackets)) {
			// Message header updating
			mh.setSequenceNumber(seqNumber);
			mh.setExportTime(new Date());
			seqNumber++;

			// L2IP updating
			BigInteger flowStartEnd = BigInteger.valueOf(new Date().getTime());
			l2ip.setFlowStartMilliseconds(flowStartEnd);
			l2ip.setFlowEndMilliseconds(flowStartEnd);


			DatagramPacket dp = new DatagramPacket(mh.getBytes(), mh.getBytes().length, InetAddress.getByName(destHost),
					destPort);

			int nextPacketNumber = packetsSend + 1;
			history.add(new IPFIXGeneratorJobHistoryEntry(nextPacketNumber, new Date().toString() , "Sending",l2ip.getSourceIPv4Address().toString(), 	l2ip.getSourceTransportPort(),	l2ip.getDestinationIPv4Address().toString(),	l2ip.getDestinationTransportPort(),mh.toString()));
			System.out.println("Sending: " + mh);
			socket.send(dp);
			packetsSend = nextPacketNumber;

			if (!active) {
				break;
			}

			this.status="Sleeping";
			Thread.sleep(1000/pps);
			this.status="Running";

		}
		}
		catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
			if (active) {
				ex.printStackTrace();
				this.status="Error";
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			this.status="Error";
		}
		finally
		{
			executionThread = null;
		}
		if (!"Error".equals(this.status)) {
			this.status = active ? "Completed" : "Stopped";
		}
    }
    public List<IPFIXGeneratorJobHistoryEntry> getHistory() {
		return history;
	}

	public void setHistory(List<IPFIXGeneratorJobHistoryEntry> history) {
		this.history = history;
	}

	public void stop() {
		active = false;
		this.status = "Stopped";
		if (executionThread != null) {
			executionThread.interrupt();
		}
	}
    public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDestHost() {
		return destHost;
	}

	public void setDestHost(String destHost) {
		this.destHost = destHost;
	}

	public int getDestPort() {
		return destPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getPps() {
		return pps;
	}

	public void setPps(long pps) {
		this.pps = pps;
	}

	public int getTotalPackets() {
		return totalPackets;
	}

	public void setTotalPackets(int totalPackets) {
		this.totalPackets = totalPackets;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	public int getPacketsSend() {
		return packetsSend;
	}

	public void setPacketsSend(int packetsSend) {
		this.packetsSend = packetsSend;
	}
}
