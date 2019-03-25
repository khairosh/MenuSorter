package common;

import java.util.Date;

import bilguun.Bilguun;
import eegii.Eegii;
import khairosh.Khairosh;

public class MenuSorter {

	private static final int TEST = 10000;

	public static void main(String[] args) {
		try {
			MenuCommon.readMenu();

			System.out.println("Base menu items: " + MenuCommon.jaMenu.length());

			MenuCommon.readMenuWeight();
			System.out.println("Menu items: " + MenuCommon.menuWeight.size());

			ISort sorter = new Eegii();
			Date start = new Date();
			for (int i = 0; i < TEST; i++) {
				sorter.sort();
			}
			Date end = new Date();
			System.out.println("Eegii: " + (end.getTime() - start.getTime()));

			sorter = new Khairosh();
			start = new Date();
			for (int i = 0; i < TEST; i++) {
				sorter.sort();
			}
			end = new Date();
			System.out.println("Khairosh: " + (end.getTime() - start.getTime()));

			sorter = new Bilguun();
			start = new Date();
			for (int i = 0; i < TEST; i++) {
				sorter.sort();
			}
			end = new Date();
			System.out.println("Bilguun: " + (end.getTime() - start.getTime()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
