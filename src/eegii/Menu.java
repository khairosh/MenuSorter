package eegii;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;

	private String code;

	private List<Menu> items;

	private int order;

	private int grpOrder;

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

	public List<Menu> getItems() {
		return items;
	}

	public void setItems(List<Menu> items) {
		this.items = items;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getGrpOrder() {
		return grpOrder;
	}

	public void setGrpOrder(int grpOrder) {
		this.grpOrder = grpOrder;
	}
}
