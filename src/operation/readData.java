package operation;

import entity.attribute_node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 2017/11/7.
 */
public class readData {

	private static List<attribute_node> columns;
	private static String[][] data;
	private static String filename;

	public static String[][] read(String file) {
		try {
			filename = file;
			FileReader fileReader = new FileReader(filename);
			BufferedReader buf = new BufferedReader(fileReader);
			int n = getFileLineCounts();
			int m = getColumns();
			data = new String[n][m];
			String linetxt = null;

			int i = 0;

			while ((linetxt = buf.readLine()) != null) {

				String[] linedata = linetxt.split(" ");
				for (int j = 0; j < linedata.length; j++) {
					data[i][j] = linedata[j];
				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static List<attribute_node> getData(String[][] data) {
		columns = new ArrayList<attribute_node>();
		for (int i = 0; i < data[0].length; i++) {
			switch (data[1][i]) {
			case "nominal":
				attribute_node a = new attribute_node(data[0][i], attribute_node.nominal);
				String[] sdata = new String[getFileLineCounts() - 2];
				for (int j = 0; j < getFileLineCounts() - 2; j++) {
					sdata[j] = data[j + 2][i];
				}
				a.setValues(sdata);
				columns.add(a);
				break;
			case "ordinal":
				attribute_node b = new attribute_node(data[0][i], attribute_node.ordinal);
				String[] bdata = new String[getFileLineCounts() - 2];
				for (int j = 0; j < getFileLineCounts() - 2; j++) {
					bdata[j] = data[j + 2][i];
				}
				b.setValues(bdata);
				columns.add(b);
				break;
			case "numeric":
				attribute_node c = new attribute_node(data[0][i], attribute_node.numeric);
				String[] cdata = new String[getFileLineCounts() - 2];
				for (int j = 0; j < getFileLineCounts() - 2; j++) {
					cdata[j] = data[j + 2][i];
				}
				c.setValues(cdata);
				columns.add(c);
				break;
			case "binary_asymmetric":
				attribute_node d = new attribute_node(data[0][i], attribute_node.binary_asymmetric);
				String[] ddata = new String[getFileLineCounts() - 2];
				for (int j = 0; j < getFileLineCounts() - 2; j++) {
					ddata[j] = data[j + 2][i];
				}
				d.setValues(ddata);
				columns.add(d);
				break;
			}
		}
		return columns;
	}

	public static int getFileLineCounts() {
		int cnt = 0;
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(filename));
			byte[] c = new byte[1024];
			int readChars = 0;
			while ((readChars = is.read(c)) != -1) {
				for (int i = 0; i < readChars; i++) {
					if (c[i] == '\n') {
						++cnt;
					}
				}
			}
		} catch (Exception e) {
			cnt = -1;
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cnt + 1;
	}

	public static int getColumns() {
		int i = 0;
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader buf = new BufferedReader(fileReader);
			String linetxt = null;

			if ((linetxt = buf.readLine()) != null) {
				String[] linedata = linetxt.split(" ");
				i = linedata.length;
			}
		} catch (Exception e) {
			i = -1;
		}
		return i;
	}

}
