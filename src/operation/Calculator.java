package operation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import entity.attribute_node;

public class Calculator {

	List<attribute_node> mylist;

	List<List<String>> nominalList = new ArrayList<List<String>>();
	List<List<String>> asymmetricList = new ArrayList<List<String>>();

	List<float[][]> calElement = new ArrayList<float[][]>();

	public Calculator(List<attribute_node> mylist) {
		this.mylist = mylist;
	}

	public void run() {
		int ncnt = 0, acnt = 0;
		for (int i = 0; i < mylist.size(); i++) {
			switch (mylist.get(i).getType()) {
			case 0:
				nominalList.add(mylist.get(i).getValue());
				ncnt++;
				break;
			case 1:
				// ordinalList.add(mylist.get(i).getValue());
				float[][] data = calculateOrdinal(mylist.get(i).getValue());
				calElement.add(data);
				break;
			case 2:
				// numericList.add(mylist.get(i).getValue());
				float[][] dat = calculateNumeric(mylist.get(i).getValue());
				calElement.add(dat);
				break;
			case 3:
				asymmetricList.add(mylist.get(i).getValue());
				acnt++;
				break;
			}
		}
		if (ncnt != 0) {
			calElement.add(calculateNominal(nominalList));
		}
		if (acnt != 0) {
			calElement.add(calculateAsymmetric(asymmetricList));
		}

		calculate();
	}

	public float[][] calculateNominal(List<List<String>> value) {

		String[] data = new String[value.size()];
		int n = value.get(0).size();
		float[][] d = new float[n][n];
		for (int i = 0; i < n; i++) {
			d[i][i] = 0;
			for (int j = i; j < n; j++) {
				int count = 0;
				for (int k = 0; k < value.size(); k++) {
					if (value.get(k).get(i).toString().equals(value.get(k).get(j).toString()))
						count++;
				}
				d[j][i] = (value.size() - count) / value.size();
			}

		}

		return d;

	}

	public float[][] calculateOrdinal(List<String> value) {

		int max = 0, n = value.size();
		int[] dat = new int[n];
		float[][] d = new float[n][n];

		for (int i = 0; i < n; i++) {
			dat[i] = Integer.valueOf(value.get(i));
			if (dat[i] >= max) {
				max = dat[i];
			}
		}

		for (int i = 0; i < n; i++) {
			d[i][i] = 0;
			for (int j = i + 1; j < n; j++) {
				d[j][i] = Math.abs((dat[j] - dat[i])) / (float) (max - 1);
			}
		}

		return d;
	}

	public float[][] calculateAsymmetric(List<List<String>> value) {
		String[] data = new String[value.size()];
		int n = value.get(0).size();
		float[][] d = new float[n][n];
		for (int i = 0; i < n; i++) {
			d[i][i] = 0;
			for (int j = i; j < n; j++) {
				int numerator = 0;
				int denominator = 0;
				boolean flag = false;
				for (int k = 0; k < value.size(); k++) {
					if (value.get(k).get(i).toString().equals(value.get(k).get(j).toString())) {
						if (value.get(k).get(i).toString().equals("1")) {
							denominator++;
						} else {
							flag = true;
						}
					} else {
						numerator++;
						denominator++;
					}
				}
				if (!flag) {
					d[j][i] = numerator / denominator;
				}
			}
		}

		return d;
	}

	public float[][] calculateNumeric(List<String> value) {

		int max = 0, min = Integer.valueOf(value.get(0)), n = value.size();
		int[] dat = new int[n];
		float[][] d = new float[n][n];

		for (int i = 0; i < n; i++) {
			dat[i] = Integer.valueOf(value.get(i));
			if (dat[i] >= max) {
				max = dat[i];
			}
			if (dat[i] <= min) {
				min = dat[i];
			}
		}

		for (int i = 0; i < n; i++) {
			d[i][i] = 0;
			for (int j = i + 1; j < n; j++) {

				float f = Math.abs((dat[j] - dat[i])) / (float) (max - min);
				BigDecimal b = new BigDecimal(f);
				float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				d[j][i] = f1;
			}
		}

		return d;
	}

	public void calculate() {
		int n = mylist.get(0).getValue().size();
		float[][] d = new float[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				float sum = 0;
				for (int k = 0; k < calElement.size(); k++) {
					float[][] num = calElement.get(k);
					sum += num[j][i];

				}
				// 保留两位小数
				float f = sum / calElement.size();
				BigDecimal b = new BigDecimal(f);
				float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				d[j][i] = f1;
			}
		}
		System.out.println("混合相异矩阵为：");
		prints(d);
	}

	public static void prints(float[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}
}
