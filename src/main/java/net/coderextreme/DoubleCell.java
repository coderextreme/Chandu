package net.coderextreme;

public class DoubleCell extends AttributeName implements CellInterface {
	private Double value = null;
	public DoubleCell(String key, Double v) {
		setAttributeName(key);
		value = v;
	}
	public String toString() {
		if (value == null) {
			return "null";
		} else {
			return value.toString();
		}
	}
	public String toXML(int indent) {
		StringBuffer sb = new StringBuffer();
		for (int ind = 0; ind < indent+2; ind++) {
			sb.append(" ");
		}
		sb.append("<"+getAttributeName()+">");
		sb.append(value.toString());
		sb.append("</"+getAttributeName()+">\n");
		return sb.toString();
	}
}
