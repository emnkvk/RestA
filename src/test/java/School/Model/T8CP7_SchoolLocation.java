package School.Model;

public class T8CP7_SchoolLocation {

    private String id =null;
    private String name;
    private String shortName;
    private int capacity=20;
   // private String schoolId="6390f3207a3bcb6a7ac977f9";
    private String type ="LABORATORY";
    private boolean active=true;
    private School school1;
    private String School;

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public T8CP7_SchoolLocation(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public School getSchool1() {
        return school1;
    }

    public void setSchool1(School school1) {
        this.school1 = school1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

   // public String getSchoolId() {
//        return schoolId;
//    }

//    public void setSchoolId(String schoolId) {
//        this.schoolId = schoolId;
//    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "SchoolLocation{" +
                "locationId='" + id + '\'' +
                ", locationName='" + name + '\'' +
                ", locationShortName='" + shortName + '\'' +
                ", capacity='" + capacity + '\'' +
//                ", schoolGet='" + schoolId + '\'' +
                ", typeGet='" + type + '\'' +
                '}';
    }
}
