package course.model;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private String status;

    public Enrollment(int enrollmentId, int studentId, int courseId, String status) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
    }

    public int getEnrollmentId() { return enrollmentId; }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public String getStatus() { return status; }

    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setStatus(String status) { this.status = status; }
}
