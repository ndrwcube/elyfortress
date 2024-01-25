package com.epizy.bastionmc.simpletools;

public class Calc {

	public void calculate(int num1, int num2, String operator) {
		int ans = 0;

		if (operator.equals("+")) {
			ans = num1 + num2;
		}
		if (operator.equals("-")) {
			ans = num1 - num2;
		}
		if (operator.equals("*")) {
			ans = num1 * num2;
		}
		if (operator.equals("/")) {
			ans = num1 / num2;
		}

		print(ans);
	}

	public static void print(String txt) {
		System.out.println(txt);
	}

	public static void print(int txt) {
		System.out.println(txt);
	}

	public static void print() {
		System.out.println();
	}

}
