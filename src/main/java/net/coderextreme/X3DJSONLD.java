package net.coderextreme;

import javax.json.*;
import java.util.*;
import java.io.*;

public class X3DJSONLD {
	public String stripQuotes(String value) {
		if (value.charAt(0) == '"' && value.charAt(value.length()-1) == '"') {
			return value.substring(1, value.length()-1);
		} else {
			return value;
		}
	}
	public void elementSetAttribute(VectorCell element, String key, List<JsonValue> value) {
		VectorCell cell = new VectorCell("attribute", instance++);
		StringCell atname = new StringCell("attributeName", key);
		cell.add(atname);
		for (int i = 0; i < value.size(); i++) {
			StringCell atvalue = new StringCell("attributeValue", value.get(i).toString());
			cell.add(atvalue);
		}
		element.add(cell);
	}
	public void elementSetAttribute(VectorCell element, String key, String value) {
		if (key.equals("SON schema")) {
			// JSON Schema
		} else if (key.equals("ncoding")) {
			// encoding, UTF-8, UTF-16 or UTF-32
		} else {
			VectorCell cell = new VectorCell("attribute", instance++);
			StringCell atname = new StringCell("attributeName", key);
			StringCell atvalue = new StringCell("attributeValue", stripQuotes(value));
			cell.add(atname);
			cell.add(atvalue);
			element.add(cell);
		}
	}

	static Integer instance = 1;
	public VectorCell CreateElement(VectorCell document, String key, String containerField) {
		VectorCell child = new VectorCell(key, instance++);
		if (containerField != null && !containerField.equals("children")) {
			elementSetAttribute(child, "containerField", containerField);
		}
		return child;
	}

	public void CDATACreateFunction(VectorCell document, VectorCell element, JsonArray value) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.size(); i++) {
			if (i > 0) {
				sb.append("\r\n");
			}
			sb.append(value.get(i));
		}
		String y = sb.toString();
		String str = stripQuotes(y);
		str = str
			.replaceAll("&lt;", "<")
			.replaceAll("&gt;", ">")
			.replaceAll("&amp;", "&")
			.replaceAll("&quot;", "\"")
			.replaceAll("'([^'\r\n]*)\n([^']*)'", "'$1\\n$2'");
		if (!str.equals(y)) {
		} else {
		}

		StringCell cdata = new StringCell("![CDATA[", str, "]]");
		element.add(cdata);
	}


	public void convertProperty(VectorCell document, String key, JsonObject object, VectorCell element, String containerField) {
		if (object != null && object.get(key) instanceof JsonObject) {
			if (key.substring(0,1).equals("@")) {
				convertJsonValue(document, object.get(key), key, element, containerField);
			} else if (key.substring(0,1).equals("-")) {
				convertJsonValue(document, object.get(key), key, element, key.substring(1));
			} else if (key.equals("#comment")) {
				if (object.get(key) instanceof JsonArray) {
					JsonArray array = (JsonArray)object.get(key);
					for (int childkey = 0; childkey <  array.size(); childkey++) {  // for each field
						StringCell comment = new StringCell("!--", CommentStringToXML(array.get(childkey).toString()), "--");
						element.add(comment);
					}
				} else {
						StringCell comment = new StringCell("!--", CommentStringToXML(object.get(key).toString()), "--");
						element.add(comment);
				}
			} else if (key.equals("#sourceText")) {
				CDATACreateFunction(document, element, (JsonArray)object.get(key));
			} else if (key.equals("connect") || key.equals("fieldValue") || key.equals("field") || key.equals("meta") || key.equals("component") || key.equals("unit")) {
				JsonArray array = (JsonArray)object.get(key);
				convertJsonArray(document, array, key, element, containerField);
			} else {
				convertJsonValue(document, object.get(key), key, element, containerField);
			}
		}
	}

	public String CommentStringToXML(String str) {
		String y = str;
		str = stripQuotes(y);
		str = " "+str+" ";
		String x;
		do {
			x = str;
			str = x.replaceAll("(.*)\\\\\"(.*)\\\\\"(.*)", "$1\"$2\"$3");
		} while (!x.equals(str));
		do {
			x = str;
			str = x.replaceAll("(.*)\\\\\"(.*)", "$1\"$2");
		} while (!x.equals(str));
		do {
			x = str;
			str = x.replaceAll("\"\"", "\" \"");
		} while (!x.equals(str));
		if (!y.equals(str)) {
		} else {
		}
		return str;
	}

	public String JSONStringToXML(String str) {
		String y = str;
		if (!y.equals(str)) {
		} else {
		}
		return str;
	}

	public String fixXML(String str, String version) {
		String y = str;
		str = str.replace("?>", "?>\n<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D "+version+"//EN\" \"http://www.web3d.org/specifications/x3d-"+version+".dtd\">\n");
		if (!y.equals(str)) {
		} else {
		}
		return str;
	}

	public void convertJsonObject(VectorCell document, JsonObject object, String parentkey, VectorCell element, String containerField) {
		Boolean kii;
		try {
			Integer.parseInt(parentkey);
			kii = true;
		} catch (Exception e) {
			kii = false;
		}
		VectorCell child;
		if (kii || parentkey.startsWith("-")) {
			child = element;
		} else {
			child = CreateElement(document, parentkey, containerField);
		}
		Iterator<String> keyiter = object.keySet().iterator();
		while (keyiter.hasNext()) {
			String key = keyiter.next();
			JsonValue ok = object.get(key);
			if (ok instanceof JsonObject) {
				if (key.substring(0,1).equals("@")) {
					convertProperty(document, key, (JsonObject)ok, child, containerField);
				} else if (key.substring(0,1).equals("-")) {
					convertJsonObject(document, (JsonObject)ok, key, child, key.substring(1));
				} else {
					convertJsonObject(document, (JsonObject)ok, key, child, containerField);
				}
			} else if (ok instanceof JsonArray) {
				convertJsonArray(document, (JsonArray)ok, key, child, containerField);
			} else if (ok instanceof JsonNumber) {
				elementSetAttribute(child, key.substring(1),ok.toString());
			} else if (ok instanceof JsonString) {
				if (key.equals("#comment")) {
					StringCell comment = new StringCell("!--", CommentStringToXML(ok.toString()), "--");
					child.add(comment);
				} else {
					// ordinary string attributes
					elementSetAttribute(child, key.substring(1), JSONStringToXML(ok.toString()));
				}
			} else if (ok == JsonValue.FALSE || ok == JsonValue.TRUE || ok == JsonValue.NULL) {
				elementSetAttribute(child, key.substring(1),ok.toString());
			} else if (ok == null) {
			} else {
			}
		}
		if (!kii && !parentkey.startsWith("-")) {
			element.add(child);
			// element.add(document.createTextNode("\n"));
		}
	}

	public void convertJsonArray(VectorCell document, JsonArray array, String parentkey, VectorCell element, String containerField) {
		Boolean arrayOfStrings = false;
		List<JsonValue> localArray = new ArrayList<JsonValue>();
		for (int key = 0; key < array.size(); key++) {
			JsonValue ok = array.get(key);
			if (ok instanceof JsonNumber) {
				localArray.add(ok);
			} else if (ok instanceof JsonString) {
				localArray.add(ok);
				arrayOfStrings = true;
			} else if (ok == JsonValue.TRUE || ok == JsonValue.FALSE || ok == JsonValue.NULL) {
				localArray.add(ok);
			} else if (ok instanceof JsonObject) {
				Boolean kii;
				try {
					Integer.parseInt(""+key);
					kii = true;
				} catch (Exception e) {
					kii = false;
				}
				if (!parentkey.startsWith("-") && kii) {
					convertJsonValue(document, ok, parentkey, element, containerField);
				} else {
					convertJsonValue(document, ok, ""+key, element, parentkey.substring(1));
				}
			} else if (ok instanceof JsonArray) {
				convertJsonValue(document, ok, ""+key, element, containerField);
			} else if (ok == null) {
			} else {
			}
		}
		if (parentkey.substring(0,1).equals("@")) {
			elementSetAttribute(element, parentkey.substring(1), localArray);
		} else if (parentkey.equals("#sourceText")) {
			CDATACreateFunction(document, element, array);
		}
	}

	public VectorCell convertJsonValue(VectorCell document, JsonValue object, String parentkey, VectorCell element, String containerField) {
		if (object instanceof JsonArray) {
			convertJsonArray(document, (JsonArray)object, parentkey, element, containerField);
		} else {
			convertJsonObject(document, (JsonObject)object, parentkey, element, containerField);
		}
		return element;
	}

	public VectorCell loadJsonIntoDocument(JsonObject jsobj) {
		VectorCell document = new VectorCell("X3D", 0);
		VectorCell element = CreateElement(document, "X3D", null);
		elementSetAttribute(element,  "xmlns:xsd",  "http://www.w3.org/2001/XMLSchema-instance");
		convertJsonObject(document, (JsonObject)jsobj.get("X3D"), "-", element, null);
		// convertProperty(document, "X3D", (JsonObject)(jsobj.get("X3D")), element, null);
		document.add(element);
		return document;
	}

	public JsonObject readJsonFile(File jsonFile) throws FileNotFoundException {
		InputStream is = new FileInputStream(jsonFile);
		JsonReader reader = Json.createReader(is);
		JsonObject jsobj = reader.readObject();
		return jsobj;
	}

	public String getX3DVersion(JsonObject jsobj) {
		String version = "3.3";
		if (jsobj != null) {
			version = ((JsonObject)jsobj.get("X3D")).get("@version").toString();
			version = stripQuotes(version);
		}
		return version;
	}
	public String serializeDOM(String x3dVersion, VectorCell document) {
		return document.toString();
	}
}
