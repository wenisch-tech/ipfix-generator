package tech.wenisch.ipfix.generator.datastructures;

public final class IPFIXTemplateType {
	public static final String L2IP = "L2IP";
	public static final String IPV4_FIVE_TUPLE = "IPV4_FIVE_TUPLE";

	private IPFIXTemplateType() {
	}

	public static String normalize(String template) {
		if (template == null || template.isBlank()) {
			return L2IP;
		}

		String normalized = template.trim().toUpperCase().replace('-', '_').replace(' ', '_');
		if (IPV4_FIVE_TUPLE.equals(normalized) || "IPV4_5_TUPLE".equals(normalized)) {
			return IPV4_FIVE_TUPLE;
		}
		if (L2IP.equals(normalized)) {
			return L2IP;
		}
		return L2IP;
	}

	public static String getDisplayName(String template) {
		return IPV4_FIVE_TUPLE.equals(normalize(template)) ? "IPv4 5-tuple" : "L2IP";
	}
}
