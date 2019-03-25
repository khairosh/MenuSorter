package khairosh;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {

	private String title = null;
	private String code = null;
	private boolean isParent = false;

	private int value = 0;
	private int sum = 0;
	private int maxItemValue = 0;

	private List<MenuItem> children = new ArrayList<>();

	public MenuItem() {
	}

	public MenuItem(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getMaxItemValue() {
		return maxItemValue;
	}

	public void setMaxItemValue(int maxItemValue) {
		this.maxItemValue = maxItemValue;
	}

	@Override
	public String toString() {
		return String.format("Title: %s, code: %s, Value: %d, Max: %d, Sum: %d, \n\t%s", this.title, this.code,
				this.value, this.maxItemValue, this.sum, this.children);
	}

}
