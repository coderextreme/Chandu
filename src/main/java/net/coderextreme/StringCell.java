package net.coderextreme;

public class StringCell extends AttributeName implements CellInterface {
	private String value = null;
	private String closeKey = null;
	public StringCell(String openKey, String v) {
		setAttributeName(openKey);
		value = v;
	}
	public StringCell(String openKey, String v, String closeKey) {
		this(openKey, v);
		this.closeKey = closeKey;
	}
	public String toString() {
		if (value == null) {
			return "null";
		} else {
			return '"'+value.toString().replaceAll("\"", "\\\\\\\"")+'"';
		}
	}
	public String toXML(int indent) {
		StringBuffer sb = new StringBuffer();
		for (int ind = 0; ind < indent+2; ind++) {
			sb.append(" ");
		}
		sb.append("<"+getAttributeName());
		if (closeKey != null) {
			sb.append(">");
		}
		sb.append(value.toString()
				.replaceAll("&", "&amp;")
				.replaceAll("\"", "&quot;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;"));
		if (closeKey != null) {
			sb.append(closeKey+">\n");
		} else {
			sb.append("</"+getAttributeName()+">\n");
		}
		return sb.toString();
	}
}
