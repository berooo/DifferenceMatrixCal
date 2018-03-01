package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 2017/11/7.
 */

public class attribute_node {

	// 标称节点
	public static int nominal = 0;
	// 序数节点
	public static int ordinal = 1;
	// 数值节点
	public static int numeric = 2;
	// 二元不对称节点
	public static int binary_asymmetric = 3;

	private String name;
	private int type;
	private List<String> value;

	public attribute_node(String name, int type) {
		this.name = name;
		this.type = type;
		value = new ArrayList<String>();
	}

	public void setValues(String[] values) {
		int i = 0;

		for (i = 0; i < values.length; i++) {
			value.add(values[i].toString());
		}
	}

	public List<String> getValue() {
		return value;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
