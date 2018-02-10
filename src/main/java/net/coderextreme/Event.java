package net.coderextreme;

public class Event {
	public static void main(String args[]) {
		DoubleCell n1 = new DoubleCell("x", 1.0);
		DoubleCell n2 = new DoubleCell("y", 2.0);
		DoubleCell n3 = new DoubleCell("z", 3.0);
		DoubleCell n4 = new DoubleCell("x", 4.0);
		DoubleCell n5 = new DoubleCell("y", 5.0);
		DoubleCell n6 = new DoubleCell("z", 6.0);
		DoubleCell n7 = new DoubleCell("x", 7.0);
		DoubleCell n8 = new DoubleCell("y", 8.0);
		DoubleCell n9 = new DoubleCell("z", 9.0);

		StringCell c1 = new StringCell("color", "&red\"");
		StringCell c2 = new StringCell("color", ">green\"\"");
		StringCell c3 = new StringCell("color", "<blue\"\"\"");

		VectorCell pt1 = new VectorCell("POINT", 1);
		VectorCell pt2 = new VectorCell("POINT", 2);
		VectorCell pt3 = new VectorCell("POINT", 3);

		VectorCell ed1 = new VectorCell("EDGE", 4);
		VectorCell ed2 = new VectorCell("EDGE", 5);
		VectorCell ed3 = new VectorCell("EDGE", 6);

		VectorCell triangle = new VectorCell("TRIANGLE", 7);
		VectorCell event = new VectorCell("TRIANGLES", 8);

		pt1.add(n1);
		pt1.add(n2);
		pt1.add(n3);
		pt1.add(c1);

		pt2.add(n4);
		pt2.add(n5);
		pt2.add(n6);
		pt2.add(c2);

		pt3.add(n7);
		pt3.add(n8);
		pt3.add(n9);
		pt3.add(c3);

		ed1.add(pt1);
		ed1.add(pt2);

		ed2.add(pt2);
		ed2.add(pt3);

		ed3.add(pt3);
		ed3.add(pt1);

		triangle.add(pt1);
		triangle.add(pt2);
		triangle.add(pt3);

		triangle.add(ed1);
		triangle.add(ed2);
		triangle.add(ed3);

		event.add(triangle);

		try {
			System.out.println(event.toString());
			System.out.println(event.toXML(-2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
