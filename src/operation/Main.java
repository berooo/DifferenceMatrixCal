package operation;

import java.util.List;

import entity.attribute_node;

public class Main {
	public static void main(String args[]) {

		String fileName = args[0];
		String[][] data = readData.read(fileName);
		List<attribute_node> mylist = readData.getData(data);
		Calculator c = new Calculator(mylist);
		c.run();
	}
}
