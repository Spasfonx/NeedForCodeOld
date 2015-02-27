package fr.needforcode.course;

public class CourseRunningException extends Exception {

	public CourseRunningException() {
		this("Cette action est impossible lorsque la course est en en train de se d�rouler");
	}

	public CourseRunningException(String m) {
		super(m);
	}

}
