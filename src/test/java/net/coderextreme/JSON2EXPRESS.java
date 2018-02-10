package net.coderextreme;

import javax.json.*;
import java.io.*;

public class JSON2EXPRESS {
	public static void main(String args[]) {
		try {
			X3DJSONLD loader = new X3DJSONLD();
			JsonObject jsobj = loader.readJsonFile(new File(args[0]));
			VectorCell document = loader.loadJsonIntoDocument(jsobj);
			System.out.println(loader.serializeDOM(loader.getX3DVersion(jsobj), document));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
