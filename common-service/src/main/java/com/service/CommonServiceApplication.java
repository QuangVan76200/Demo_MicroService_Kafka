package com.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonServiceApplication {

	static SecureRandom random = new SecureRandom();

	public static String randomPinCode(String[] randomPinCode) {
		int pinLength = 6;

		String[] generatedPinCode = new String[pinLength];
		for (int i = 0; i < pinLength; i++) {
			int index = random.nextInt(randomPinCode.length);
			generatedPinCode[i] = randomPinCode[index];
		}

		String pinCode = String.join("", generatedPinCode);

		return pinCode;

	}

	// LeQuangVan
	// eQua
	public static boolean checkConstain(String str1, String str2) {
		int maxLength = str1.length();
		int subLength = str2.length();

		for (int i = 0; i < maxLength - subLength; i++) {
			boolean isChecked = false;
			for (int j = 0; j < subLength; j++) {
				if (str1.charAt(i + j) != str2.charAt(j)) {
					isChecked = false;
					break;
				} else {
					isChecked = true;
				}
			}
			if (isChecked) {
				return true;
			}
		}
		return false;
	}

	public static String reverseString(String str) {
		char[] charS = str.toCharArray();
		int left = 0;
		int right = charS.length - 1;
		char teamp;
		while (left <= right) {
			teamp = charS[left];
			charS[left] = charS[right];
			charS[right] = teamp;
			left++;
			right--;

		}
		String res = String.valueOf(charS);
		return res;
	}

	// abcdeabcbb
	public static int lengthOfLongestSubstring(String s) {
		int longest = 0;
		for (int i = 0; i < s.length(); i++) {
			int count = 1;
			for (int j = i + 1; j < s.length(); j++) {
				String value = String.valueOf(s.charAt(j));
				if (s.substring(i, j).contains(value)) {
					break;
				}
				count++;
			}
			if (longest < count) {
				longest = count;
			}
		}
		return longest;
	}

	// abbcd
	public static String removeDuplicate(String s) {
		LinkedHashSet<Character> set = new LinkedHashSet<>();
		String res = "";
		for (int i = 0; i < s.length(); i++) {
			set.add(s.charAt(i));
		}
		for (Character val : set) {
			res = res + String.valueOf(val);
		}
		return res;
	}

	public static boolean checkPalidorm(String str) {
		int left = 0;
		int right = str.length() - 1;
		boolean isChecked = false;
		while (left < right) {
			if (str.charAt(left) != str.charAt(right)) {
				break;
			}
			isChecked = true;
			left++;
			right--;
		}
		if (isChecked) {
			return true;
		}
		return false;
	}

	public static void checkPrimeNumber(int n) {
		for (int i = 2; i <= n; i++) {
			if (isPrime(i)) {
				System.out.print(i + " ");
			}
		}
	}

	// Lietke n SNT dau tien
	public static void displayPrimeNumber(int n) {
		int count = 0;
		int number = 2;
		while (count < n) {
			if (isPrime(number)) {
				System.out.print(number + " ");
				count++;
			}
			number++;
		}
	}

	private static boolean isPrime(int n) {
		if (n < 2) {
			System.out.println("SNT have to greater than 2");
		} else {
			for (int i = 2; i <= Math.sqrt(n); i++) {
				if (n % i == 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	// 1
	public static void fibonaci(int n) {
		int first = 0;
		int second = 1;
		System.out.print(first + " " + second);
		for (int i = 0; i < n; i++) {
			int max = first + second;
			System.out.print(" " + max);
			first = second;
			second = max;
		}
	}

	public static int[] bubbleSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					int teamp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = teamp;
				}
			}
		}
		return arr;
	}

	// 6, 5, 1, 8, 7, 2, 4
	public static int[] insertionSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int teamp = arr[i];
			int j = i - 1;
			while (j >= 0 && arr[j] > teamp) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = teamp;
		}
		return arr;
	}

	// 1 4 2 5 6 9
	public static int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int res = target - nums[i];
			if (map.containsKey(res)) {
				return new int[] { map.get(res), i };
			}
			map.put(nums[i], i);
		}
		return new int[0];
	}

	// 1001
	public static int reverse(int x) {
		int result = 0;
		int absX = Math.abs(x);

		while (absX > 0) {
			int lastDigit = absX % 10;
			absX /= 10;
			result = result * 10 + lastDigit;
		}
		return result;
	}

	// 12221
	public static boolean isPalindrome(int x) {
		int absX = Math.abs(x);
		int result = 0;
		while (absX > 0) {
			int lastDigit = absX % 10;
			absX /= 10;
			result = result * 10 + lastDigit;
		}
		if (result == x) {
			return true;
		}
		return false;
	}

	public static String longestCommonPrefix(String[] strs) {

		return null;
	}

	public static boolean checkPrefixDigit(char st1, char st2, char st3) {
		return false;
	}

	public static int[] mergeSort(int[] a, int L, int R) {
		if (L > R) {
			return new int[0];
		}
		if (L == R) {
			int[] singlArr = new int[] { a[L] };
			return singlArr;
		}
		
		int k = (L + R) /2;
		int[] a1 = mergeSort(a, L, k);
		int[] a2 = mergeSort(a, k+1, R);
		
		int i = 0, i1 =0, i2 = 0;
		int n = a1.length + a2.length;
		int[] arr = new int[n];
		
		while(i < n) {
			if(i1 < a1.length && i2 < a2.length) {
				if(a1[i1] < a2[i2]) {
					arr[i] = a1[i1];
					i++;
					i1++;
				}
				else {
					arr[i] = a2[i2];
					i++;
					i2++;
				}
			}
			else {
				if(i1 < a1.length) {
					arr[i] = a1[i1];
					i++;
					i1++;
				} else {
					arr[i] = a2[i2];
					i++;
					i2++;
				}
			}
		}
		
		return arr;
	}

	// mississippi
	// issipi
	public static int strStr(String haystack, String needle) {
		if (needle.isEmpty() || haystack.equals(needle)) {
			return 0;
		} else if (haystack.length() < needle.length() || haystack.length() == needle.length()) {
			return -1;
		}
		
		for(int i = 0 ; i < haystack.length() - needle.length(); i++) {
			int j = 0;
			while(j < needle.length() && haystack.charAt(i+j) == needle.charAt(j)) {
				j++;
			}
			if(j == needle.length()) {
				return i;
			}
		}
		return -1;
	}

	public static int giaiThua(int n) {
		return 0;
	}

	// 100
	public static void phanTichSoNguyen(int n) {
		int soChia = 2;
		List<Integer> list = new ArrayList<>();
		while(n > 1) {
			if(n % soChia == 0) {
				n = n/soChia;
				list.add(soChia);
			}
			else {
				soChia++;
			}
			
		}
		int index  =0;
		for(Integer val : list) {
			if(index <  list.size()-1) {
				index++;
				System.out.print(val + " x " );
			}
			else {
				System.out.print(val);
			}
		}
	}

	public static int timSoConThieuTrongMang(int[] strs) {
		return 0;

	}

	public static int[] timSoTrungLap(int[] strs) {
		return strs;
	}

	public static char[] findDuplicateChar(String s) {
		Map<Character, Integer> map = new HashMap<>();
		List<Character> list = new ArrayList<>();
		for(int i = 0; i < s.length(); i++) {
			char digit = s.charAt(i);
			if(map.containsKey(digit)) {
				map.put(digit, map.get(digit)+1);
			} else {
				map.put(digit, 1);
			}
		}
		
		for(Character val : map.keySet()) {
			if(map.get(val) > 1) {
				list.add(val);
			}
		}
		
		char[] arr = new char[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	public static void swap(String str) {
	}

	public static int[] chenPhanTuVaoMang(int arr[], int n) {
		int[] newArr = new int[arr.length+1];
		int index = 0;
		for(int i = 0 ; i < arr.length; i++) {
			if(arr[i] > n) {
				index = i;
			}
		}
		
		for(int i = 0 ; i < newArr.length; i++) {
			if(i == index) {
				newArr[i] = n;
			} else {
				newArr[i] = arr[i];
			}
		}
		return null;
	}

	public static void main(String[] args) {
//		SpringApplication.run(CommonServiceApplication.class, args);
		String[] randomPinCode = { "1", "2", "3", "4", "5", "6", "8", "9", "0" };
		String str1 = "LeQuangVan";
		String str2 = "eQuang";
		int[] arr = { 6, 5, 1, 8, 7, 2, 4 };
		int[] twoSum = { 1, 4, 2, 5, 7 };
		String[] strs = { "fliower", "fliow", "fliight" };
//		String activeCode = String.valueOf(new Random().nextInt(900000) + 100000);
//		System.out.println(checkConstain(str1, str2));
//		System.out.println(reverseString(str1));
//		System.out.println(lengthOfLongestSubstring("abcdeabcbb"));
//		System.out.println(removeDuplicate("LeQuangVan"));
//		System.out.println(checkPalidorm("AvAvAv"));
//		checkPrimeNumber(10);
//		displayPrimeNumber(10);
//		fibonaci(10);
//		System.out.println(Arrays.toString(bubbleSort(arr)));
//		System.out.println(Arrays.toString(insertionSort(arr)));
//		System.out.println(Arrays.toString(twoSum(twoSum,9)));
//		System.out.println(reverse(123456));
//		System.out.println(isPalindrome(122212));
//		System.out.println(longestCommonPrefix(strs));
		
//		System.out.println(Arrays.toString(mergeSort(arr, 0, arr.length - 1)));
//		System.out.println(strStr("leetcode", "et"));

//		listChia7(200);
//		System.out.println(giaiThua(8));
		phanTichSoNguyen(100);
		int[] test = { 1, 3, 3, 4, 5, 6, 7, 7, 10 };
//		System.out.println(timSoConThieuTrongMang(test));
//		System.out.println(Arrays.toString(timSoTrungLap(test)));
		System.out.println(Arrays.toString(findDuplicateChar("ABACCDFD")));
//		swap("LeQuangVan");
//		System.out.println(Arrays.toString(chenPhanTuVaoMang(twoSum, 6)));

//		
//

//		System.out.println(activeCode);

//		List<Integer> sums = List.of(2, 4, 8, 6, 7, 9, 5, 1, 3);
//		int total = sums.stream().reduce(0, Integer::sum);
//		int totals = sums.stream().reduce(0, (x,y) -> x<y ? x:y);
//		int totalOdd = sums.stream().filter(x -> x%2 == 0).map(x -> x*x).reduce(0, Integer::sum);
//		sums.stream().distinct().forEach(e-> System.out.println(e));
//		sums.stream().sorted().forEach(e-> System.out.println(e));
//		System.out.println(totalOdd);
	}

}
