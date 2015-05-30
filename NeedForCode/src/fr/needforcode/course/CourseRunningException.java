package fr.needforcode.course;

public class CourseRunningException extends Exception {

	private static final long serialVersionUID = 7985642300018710909L;

	public CourseRunningException() {
		this("Cette action est impossible lorsque la course est en en train de se dérouler");
	}

	public CourseRunningException(String m) {
		super(m);
	}

}
