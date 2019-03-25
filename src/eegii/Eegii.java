package eegii;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import common.ISort;
import common.MenuCommon;

public class Eegii implements ISort {

	private static String firstToLow(String pVal) {
		return pVal.substring(0, 1).toLowerCase() + pVal.substring(1);
	}

	private static Object jtoP(Class<?> type, Object jData)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException, JSONException {

		Object obj = null;

		if (null == jData)
			return null;

		if (jData.toString().equals("null"))
			return null;

		if (type == String.class) {
			return jData.toString();
		}

		if (jData.toString().trim().length() < 1) {
			return null;
		}

		if (type == Boolean.TYPE || type == Boolean.class) {
			obj = Boolean.parseBoolean(jData.toString());
		} else if (type == Character.TYPE || type == Character.class) {
			obj = jData.toString().charAt(0);
		} else if (type == Byte.TYPE || type == Byte.class) {
			obj = Byte.parseByte(jData.toString());
		} else if (type == Short.TYPE || type == Short.class) {
			obj = Short.parseShort(jData.toString());
		} else if (type == Integer.TYPE || type == Integer.class) {
			obj = Integer.parseInt(jData.toString());
		} else if (type == Long.TYPE || type == Long.class) {
			obj = Long.parseLong(jData.toString());
		} else if (type == Float.TYPE || type == Float.class) {
			obj = Float.parseFloat(jData.toString());
		} else if (type == Double.TYPE || type == Double.class) {
			obj = Double.parseDouble(jData.toString());
		} else if (type == BigDecimal.class) {
			obj = new BigDecimal(jData.toString());
		} else if (type.isArray()) { // array
			Class<?> cType = type.getComponentType();
			org.json.JSONArray jArray = new org.json.JSONArray(jData.toString());
			Object array = Array.newInstance(cType, jArray.length());

			for (int i = 0; i < jArray.length(); i++) {
				Array.set(array, i, jtoP(cType, jArray.get(i)));
			}

			obj = array;
		} else if (type == List.class || type == ArrayList.class) {
			org.json.JSONArray jArray = (org.json.JSONArray) jData;
			List<Object> list = new ArrayList<>();

			for (int i = 0; i < jArray.length(); i++) {
				list.add(jtoP(Menu.class, jArray.get(i)));
			}

			obj = list;
		} else { /* complex type */
			Object cobj = type.newInstance();
			org.json.JSONObject jobj = (org.json.JSONObject) jData;

			for (Method m : type.getMethods()) {
				if (m.getName().startsWith("set") && m.getParameterTypes().length == 1 && m.getName().length() > 3) {
					try {
						String key = firstToLow(m.getName().substring(3));
						if (jobj.has(key))
							m.invoke(cobj, jtoP(m.getParameterTypes()[0], jobj.get(key)));
						else {
							key = m.getName().substring(3);
							if (jobj.has(key))
								m.invoke(cobj, jtoP(m.getParameterTypes()[0], jobj.get(key)));
						}
					} catch (IllegalArgumentException | InvocationTargetException | JSONException e) {
						System.out.println(e.getMessage());
					}
				}
			}

			obj = cobj;
		}

		return obj;
	}

	private static String toJSON(Object obj) throws JSONException {
		if (null == obj)
			return null;

		if (obj instanceof Object[]) {
			return new JSONArray(obj).toString();
		} else if (obj instanceof List) {
			return new JSONArray(((List<?>) obj).toArray()).toString();
		} else if (obj instanceof String) {
			return "\"" + obj.toString() + "\"";
		} else if (obj instanceof BigInteger || obj instanceof Integer || obj instanceof Long
				|| obj instanceof BigDecimal) {
			return obj.toString();
		} else if (obj instanceof Boolean) {
			return obj.toString();
		} else {
			return new JSONObject(obj).toString();
		}
	}

	private static int[] setOrder(List<Menu> lstMenu) {

		int[] orders = new int[2];

		int order = 0;
		int grpOrder = 0;

		if (null != lstMenu && !lstMenu.isEmpty()) {
			for (Menu menu : lstMenu) {
				if (null != menu.getCode()) {
					menu.setOrder(MenuCommon.menuWeight.get(menu.getCode()));
					if (menu.getOrder() > order)
						order = menu.getOrder();
					grpOrder += menu.getOrder();
				} else {
					int[] tmpOrder = setOrder(menu.getItems());
					menu.setOrder(tmpOrder[0]);
					menu.setGrpOrder(tmpOrder[1]);
					grpOrder += menu.getOrder();
				}
			}
		}

		orders[0] = order;
		orders[1] = grpOrder;

		return orders;
	}

	public static String format(String value) {

		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(value);
			value = gson.toJson(je);
		} catch (Exception e) {
			// Do nothing
		}
		return value;
	}

	public void sort() throws Exception {

		Menu[] arrMenu = new Menu[] {};

		arrMenu = (Menu[]) jtoP(arrMenu.getClass(), MenuCommon.jaMenu);

		List<Menu> lstMenu = Arrays.asList(arrMenu);

		setOrder(lstMenu);

		for (Menu menu : lstMenu) {
			menu.getItems().sort((o1, o2) -> {
				int order;
				order = o2.getOrder() - o1.getOrder();
				if (order == 0)
					order = o2.getGrpOrder() - o1.getGrpOrder();
				return order;
			});
		}

		String jsonStr = "[" + toJSON(lstMenu.get(0)) + "]";

//		System.out.println(jsonStr);
	}
}
