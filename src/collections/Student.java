package collections;

import java.util.Comparator;

public class Student{
	public Double GPA;
	public Student(Double GPA){
		this.GPA = GPA;
	}
	
	public static final Comparator<Student> GPAAscending = 
			new Comparator<Student>()
	{
		public int compare(Student s1, Student s2){
			return Double.compare(s1.GPA, s2.GPA);
		}
	};

}