package com.github.markyc.applicationcenter.test;

import com.github.markyc.applicationcenter.DisplayAllPanel;
import com.github.markyc.applicationcenter.Student;
import com.github.markyc.applicationcenter.Undergrad;

public class BubbleSortTest {

	public static void main(String[] args) {
		Student[] students = new Student[10];
		students[0] = new Undergrad("H", "CSE", 34);
		students[1] = new Undergrad("H", "CSE", 34);
		students[2] = new Undergrad("G", "CSE", 37);
		students[3] = new Undergrad("A", "CSE", 99);
		students[4] = new Undergrad("B", "CSE", 88);
		students[5] = new Undergrad("C", "CSE", 87);
		students[6] = new Undergrad("D", "CSE", 86);
		students[7] = new Undergrad("C", "CSE", 87);
		students[8] = new Undergrad("E", "CSE", 66);
		students[9] = new Undergrad("F", "CSE", 50);
		
		System.out.println ("Before compare: ");
		print(students);
		
		students = DisplayAllPanel.BubbleSort.sort(students, Student.AverageComparator);
		
		System.out.println ("After compare: ");
		print(students);

	}
	
	public static void print(Student[] students) {
		int i = 0;
		do {
			System.out.println(students[i++].toString());
		} while (i < students.length);
		
		System.out.println();
	}

}
