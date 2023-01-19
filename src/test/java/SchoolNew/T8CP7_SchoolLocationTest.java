package SchoolNew;


import School.Model.T8CP7_SchoolLocation;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class T8CP7_SchoolLocationTest {

Cookies cookies;
    String locationId;
    String name;
    String shortName;

    @BeforeClass
    public void loginCampus() {
        baseURI = "https://test.mersys.io/";
        //diğer testler çalışmadan önce login olup cookies i alınması gerekiyor
        // bu yüzden beforeClass annotation ı  eklendi

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "turkeyts");
        credential.put("password", "TechnoStudy123");
        credential.put("rememberMe", "true");

        cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)
                        .log().body()

                        .when()
                        .post("auth/login")

                        .then()
                       // .log().body()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;

       //  System.out.println("cookies = " + cookies);
    }

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3);
    }
    public String getRandomNum() {return RandomStringUtils.randomNumeric(3);
    }


    @Test
    public void createlocation(){
        name = getRandomName();
        shortName = getRandomCode();

        schoolApiLocation location = new schoolApiLocation(name, shortName);
        location.setSchool("6390f3207a3bcb6a7ac977f9");


        locationId =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(location)
                        .log().body()

                        .when()
                        .post("school-service/api/location")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")

        ;
    }
    @Test(dependsOnMethods = "createlocation",priority = 2)
    public void updatelocation(){
        name = getRandomName();
        shortName = getRandomCode();

        T8CP7_SchoolLocation location = new T8CP7_SchoolLocation(name, shortName);
        location.setSchool("6390f3207a3bcb6a7ac977f9");
        location.setId(locationId);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(location)

                .when()
                .put("school-service/api/location")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(name))

        ;
    }

    @Test(dependsOnMethods = "updatelocation")
    public void deleteLocation(){
        given()
                .cookies(cookies)
                .pathParam("locationId",locationId)
                .log().uri()
                .when()
                .delete("school-service/api/location/{locationId}")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }


}
class schoolApiLocation{

    private String id =null;
    private String name;
    private String shortName;
    private int capacity=20;
    // private String schoolId="6390f3207a3bcb6a7ac977f9";
    private String type ="LABORATORY";
    private boolean active=true;
    private String School;

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public schoolApiLocation(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
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


