package common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

public class MenuCommon {

	public static JSONArray jaMenu;
	public static Map<String, Integer> menuWeight;

	public static void readMenu() throws Exception {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data\\menu.json")))) {

			StringBuilder data = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() < 1) {
					continue;
				}
				if (data.length() > 0) {
					data.append("\n");
				}
				data.append(line);
			}

			jaMenu = new JSONArray(data.toString());
		} catch (Exception e) {
			throw e;
		}
	}

	public static void readMenuWeight() throws Exception {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("data\\menu_weight.txt")))) {

			menuWeight = new HashMap<>();

			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() < 1 || !line.contains("=")) {
					continue;
				}

				line = line.trim();
				String key = line.substring(0, line.indexOf('='));
				int value = Integer.parseInt(line.substring(key.length() + 1));
				menuWeight.put(key, value);
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
