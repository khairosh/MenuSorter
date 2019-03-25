package khairosh;

import org.json.JSONArray;
import org.json.JSONObject;

import common.ISort;
import common.MenuCommon;

public class Khairosh implements ISort {

	public void sort() throws Exception {

		JSONObject mainJO = MenuCommon.jaMenu.getJSONObject(0);
		MenuItem rootItem = new MenuItem();
		rootItem.setParent(true);
		if (mainJO.has("items")) {
			if (mainJO.has("title")) {
				rootItem.setTitle(mainJO.getString("title"));
			}

			buildAndSort(rootItem, mainJO);
		}

		JSONArray result = new JSONArray();
		toJson(rootItem, result);
//		System.out.println(result);
	}

	private static void buildAndSort(MenuItem parentItem, JSONObject joParent) {
		JSONArray ja = joParent.getJSONArray("items");
		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = ja.getJSONObject(i);
			if (jo.has("code")) {
				MenuItem item = new MenuItem(jo.getString("code"));
				if (MenuCommon.menuWeight.containsKey(item.getCode())) {
					item.setValue(MenuCommon.menuWeight.get(item.getCode()));
				}

				if (parentItem.getMaxItemValue() < item.getValue()) {
					parentItem.setMaxItemValue(item.getValue());
				}
				parentItem.setSum(parentItem.getSum() + item.getValue());
				boolean added = false;
				for (int j = 0; j < parentItem.getChildren().size(); j++) {
					if (parentItem.getChildren().get(j).isParent()) {
						if (parentItem.getChildren().get(j).getMaxItemValue() < item.getValue()) {
							parentItem.getChildren().add(j, item);
							added = true;
							break;
						}
					} else {
						if (parentItem.getChildren().get(j).getValue() < item.getValue()) {
							parentItem.getChildren().add(j, item);
							added = true;
							break;
						}
					}
				}
				if (!added) {
					parentItem.getChildren().add(item);
				}

			} else {
				if (jo.has("items")) {
					MenuItem item = new MenuItem();
					item.setParent(true);
					if (jo.has("title")) {
						item.setTitle(jo.getString("title"));
					}

					buildAndSort(item, jo);

					parentItem.setSum(parentItem.getSum() + item.getSum());
					boolean added = false;
					for (int j = 0; j < parentItem.getChildren().size(); j++) {
						if (parentItem.getChildren().get(j).isParent()) {
							if (parentItem.getChildren().get(j).getSum() < item.getSum()) {
								parentItem.getChildren().add(j, item);
								added = true;
								break;
							}
						} else {
							if (parentItem.getChildren().get(j).getValue() < item.getMaxItemValue()) {
								parentItem.getChildren().add(j, item);
								added = true;
								break;
							}
						}
					}
					if (!added) {
						parentItem.getChildren().add(item);
					}
				}
			}
		}
	}

	private static void toJson(MenuItem item, JSONArray parentArr) {
		if (item.isParent()) {
			JSONObject jo = new JSONObject();
			jo.put("title", item.getTitle());
			JSONArray arr = new JSONArray();
			for (int i = 0; i < item.getChildren().size(); i++) {
				toJson(item.getChildren().get(i), arr);
			}
			jo.put("items", arr);
			parentArr.put(jo);
		} else {
			JSONObject jo = new JSONObject();
			jo.put("code", item.getCode());
			parentArr.put(jo);
		}
	}
}
