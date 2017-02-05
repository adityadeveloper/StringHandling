package com.landmarkfinder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import antlr.StringUtils;

public class StringSubTest {

	public static void main(String[] args) {
		String example = "Shri Baldev Singh Niwas, , , Tehsil Shimla(hjgjhgT), Bharyal (6/87), Shimla, HP, IN, 171011";
	//	String example1 = new String("United, Arab, Emirates, Dirham (AED)ddfa,,kfgujsfth,665656dfd(dddd)");
		
		System.out.println("example = "+example);
		Matcher m1 = Pattern.compile("\\(([^)]+)\\)").matcher(example);
		
		while (m1.find()) {
			System.out.println(m1.group());
			String a = m1.group();
			
			Matcher m2 = Pattern.compile("[$&+//,:;=?@#|^0-9]").matcher(a);
			if (m2.find()) 
				{
				example = example.replace(a, " ");
				}
			else {
				example = example.replace(a, a.replace("(", " ").replace(")", ""));
			}
		}
		
		System.out.println("new exmple = " + example.toString());
		
		/*String[] ab=example1.split(",");
		System.out.println("ab="+ab.toString());
		String a1=example1.substring(example1.lastIndexOf(',')+1);
		System.out.println("a1="+a1.toString());
		
		String aaa=example1;
		for(int i=0;i<aaa.length() - aaa.replace(",", "").length();i++){
		example1=example1.substring(example1.indexOf(",")+1,example1.length());
		System.out.println("a2="+example1.toString());
		}*/
		
	}
}