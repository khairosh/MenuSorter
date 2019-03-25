package bilguun;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.ISort;
import common.MenuCommon;

public class Bilguun implements ISort {

	public void sort() throws Exception {
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonObj = (JsonArray) jsonParser.parse(MenuCommon.jaMenu.toString());
		JsonObject temp = (JsonObject) jsonObj.get(0);
		JsonArray items = (JsonArray) temp.get("items");
		PriorityQueue<compareObj> objects = new PriorityQueue<>(jsonComparator);
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof JsonObject) {
				JsonObject subItem = (JsonObject) items.get(i);
				int value = 0;
				compareObj c = new compareObj();
				if (subItem.keySet().contains("items")) {
					value = jsonCompare(subItem, c);
					c.groupVal = value;
				} else {
					String k = subItem.get("code").getAsString();
					if (MenuCommon.menuWeight.containsKey(k))
						value = MenuCommon.menuWeight.get(k);
					else
						value = 0;
					c.max = value;
				}
				c.obj = subItem;
				objects.add(c);
			} else {
				throw new Exception("JSON FORMAT BURUU");
			}
		}
		String js = "";
		int t = objects.size();
		items = new JsonArray();
		for (int i = 0; i < t; i++) {
			items.add(objects.remove().obj);
		}
		temp.remove("items");
		temp.add("items", items);
//		System.out.println(jsonObj.toString());
	}

	static Comparator<compareObj> jsonComparator = new Comparator<compareObj>() {
		@Override
		public int compare(compareObj s1, compareObj s2) {
			if (s1.groupVal > 0 && s2.groupVal > 0) {
				return s2.groupVal - s1.groupVal;
			} else {
				return s2.max - s1.max;
			}

		}
	};

	private static int jsonCompare(JsonObject json, compareObj cc) throws Exception {
		JsonArray items = (JsonArray) json.get("items");
		PriorityQueue<compareObj> objects = new PriorityQueue<>(jsonComparator);
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof JsonObject) {
				JsonObject subItem = (JsonObject) items.get(i);
				int value = 0;
				compareObj c = new compareObj();
				if (subItem.keySet().contains("items")) {
					value = jsonCompare(subItem, c);
					c.groupVal = value;
				} else {
					String k = subItem.get("code").getAsString();
					if (MenuCommon.menuWeight.containsKey(k))
						value = MenuCommon.menuWeight.get(k);
					else
						value = 0;
					c.max = value;
				}
				c.obj = subItem;
				objects.add(c);
			} else {
				throw new Exception("JSON FORMAT BURUU");
			}
		}
		int sum = 0;
		int t = objects.size();
		items = new JsonArray();
		for (int i = 0; i < t; i++) {
			compareObj tmp = objects.remove();
			sum += tmp.max;
			items.add(tmp.obj);
			if (cc.max < tmp.max) {
				cc.max = tmp.max;
			}
		}
		json.remove("items");
		json.add("items", items);
		return sum;
	}
}

class compareObj {
	public int max = -1;
	public JsonElement obj;
	public int groupVal;
}