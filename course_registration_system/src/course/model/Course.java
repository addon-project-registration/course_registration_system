package course.model;

public class Course {
    private int courseId;
    private String courseName;
    private int capacity;

    public Course(int courseId, String courseName, int capacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.capacity = capacity;
    }

    public int getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCapacity() { return capacity; }

    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}
