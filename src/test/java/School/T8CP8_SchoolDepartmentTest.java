package School;

import School.Model.School;
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

public class T8CP8_SchoolDepartmentTest {
    Cookies cookies;
    String departmentID;
    String name;
    String code;
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
    @Test
    public void createDepartment(){
        name = getRandomName();
        code = getRandomCode();

        Department department  = new Department(name,code);
        department.setSchool("6390f3207a3bcb6a7ac977f9");
       departmentID =
               given()
                       .cookies(cookies)
                       .contentType(ContentType.JSON)
                       .body(department)
                       .log().body()

                       .when()
                       .post("school-service/api/department")

                       .then()
                       .log().body()
                       .statusCode(201)
                       .extract().jsonPath().getString("id")

       ;


    }
    @Test(dependsOnMethods = "createDepartment",priority = 2)
    public void updateDepartment(){
        name = getRandomName();
        code = getRandomCode();

        Department department  = new Department(name,code);
        department.setSchool("6390f3207a3bcb6a7ac977f9");
        department.setId(departmentID);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(department)

                .when()
                .put("school-service/api/department")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(name))

        ;
    }

    @Test(dependsOnMethods = "updateDepartment")
    public void deleteDepartment(){
        given()
                .cookies(cookies)
                .pathParam("departmentID",departmentID)
                .log().uri()
                .when()
                .delete("school-service/api/department/{departmentID}")
                .then()
                .log().body()
                .statusCode(204)
        ;
    }


}
class Department{
    private String id;
    private String name;
    private String code;
    private String school;
    private boolean active=true;

    public Department(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}