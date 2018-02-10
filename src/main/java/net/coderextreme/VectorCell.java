package net.coderextreme;

import java.util.Vector;
import java.util.HashSet;
import java.util.Set;

public class VectorCell extends Vector<CellInterface> implements CellInterface {
	static Set<Integer> stepWritten = new HashSet<Integer>();
	static Set<Integer> xmlWritten = new HashSet<Integer>();

	// category is the type, table or class of event
	private String category;
	public String getCategory() {
		return category;
	}
	public void setCategory(String c) {
		category = c;
	}

	// instant is the id or time of the event
	private Integer instant;
	public Integer getInstant() {
		return instant;
	}
	public void setInstant(Integer t) {
		instant = t;
	}
	public VectorCell(String c, Integer i) {
		setCategory(c);
		setInstant(i);
	}
	public String toString() {
		if (stepWritten.contains(instant)) {
			return "";
		} else {
			stepWritten.add(instant);
		}

		Integer els = this.size();
		StringBuffer sb = new StringBuffer();
		// print list children
		for (int i = 0; i < els; i++) {
			if (this.get(i) instanceof VectorCell) {
				sb.append(this.get(i).toString());
			}
		}
		// now print object
		sb.append("#");
		sb.append(instant);
		sb.append("=");
		sb.append(category);
		sb.append("(");
		for (int i = 0; i < els; i++) {
			if (i > 0) {
				sb.append(",");
			}
			if (this.get(i) instanceof VectorCell) {
				sb.append("#");
				sb.append(((VectorCell)this.get(i)).getInstant());
			} else {
				sb.append(this.get(i).toString());
			}
		}
		sb.append(")\n");
		return sb.toString();
	}
	public String toXML(int indent) {
		StringBuffer sb = new StringBuffer();
		// now print object
		for (int ind = 0; ind < indent+2; ind++) {
			sb.append(" ");
		}
		if (xmlWritten.contains(instant)) {
			sb.append("<");
			sb.append(category);
			sb.append(" USE='");
			sb.append(instant);
			sb.append("'/>\n");
		} else {
			xmlWritten.add(instant);
			sb.append("<");
			sb.append(category);
			sb.append(" DEF='");
			sb.append(instant);
			sb.append("'>\n");
			Integer els = this.size();
			for (int i = 0; i < els; i++) {
				sb.append(this.get(i).toXML(indent+2));
			}
			for (int ind = 0; ind < indent+2; ind++) {
				sb.append(" ");
			}
			sb.append("</");
			sb.append(category);
			sb.append(">\n");
		}
		return sb.toString();
	}

	public VectorCell multiplication(VectorCell right){ // this is left
		// unimplemented
		return null;
	}
	public VectorCell division(VectorCell right){ // this is left
		// unimplemented
		return null;
	}
	public VectorCell addition(VectorCell right){ // this is left
		// unimplemented
		return null;
	}
	public VectorCell subtractition(VectorCell right){ // this is left
		// unimplemented
		return null;
	}
	public VectorCell power(VectorCell exponent){ // this is base
		// unimplemented
		return null;
	}
	public VectorCell graphify(){ // create graph from matrix
		// unimplemented
		return null;
	}
	public VectorCell matrixify(){ // create matrix from graph
		// unimplemented
		return null;
	}
}
