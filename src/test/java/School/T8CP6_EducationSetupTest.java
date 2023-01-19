package School;

import School.Model.T8CP6_EducationSetup;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class T8CP6_EducationSetupTest {
    Cookies cookies;
    String subjectCategorieID;
    String subjectCategorieName;
    String subjectCategorieCode;
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

                        .when()
                        .post("auth/login")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;

        // System.out.println("cookies = " + cookies);
    }

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3);
    }



    @Test
    public void createSubjectCategorie(){
        subjectCategorieName = getRandomName();
        subjectCategorieCode = getRandomCode();
        subjectCategories categorie = new subjectCategories();
        categorie.setName(subjectCategorieName);
        categorie.setCode(subjectCategorieCode);

        subjectCategorieID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(categorie)

                        .when()
                        .post("school-service/api/subject-categories")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")

        ;
    }

@Test(dependsOnMethods = "createSubjectCategorie",priority = 1)
    public void createSubjectCategorieNegative(){
    subjectCategories categorie = new subjectCategories();
    categorie.setName(subjectCategorieName);
    categorie.setCode(subjectCategorieCode);

    given()
            .cookies(cookies)
            .contentType(ContentType.JSON)
            .body(categorie)

            .when()
            .post("school-service/api/subject-categories")

            .then()
            .log().body()
            .statusCode(400)
            .body("message",equalTo("The Subject Category with Name \""+subjectCategorieName+"\" already exists."))
            .body("message", containsString("already exists"))
    ;


}
@Test(dependsOnMethods = "createSubjectCategorie",priority = 2)
public void updateSubjectCategorie(){
    subjectCategorieName = getRandomName();
    subjectCategorieCode = getRandomCode();

    subjectCategories categorie = new subjectCategories();
    categorie.setId(subjectCategorieID);
    categorie.setName(subjectCategorieName);
    categorie.setCode(subjectCategorieCode);

    given()
            .cookies(cookies)
            .contentType(ContentType.JSON)
            .body(categorie)

            .when()
            .put("school-service/api/subject-categories")

            .then()
            .log().body()
            .statusCode(200)
            .body("name",equalTo(subjectCategorieName))

            ;
}
@Test(dependsOnMethods = "updateSubjectCategorie")
    public void deleteSubjectCategorieById(){
given()
        .cookies(cookies)
        .pathParam("subjectCategorieID",subjectCategorieID)
        .log().uri()
        .when()
        .delete("school-service/api/subject-categories/{subjectCategorieID}")
        .then()
        .log().body()
        .statusCode(200)
;
}
}
class subjectCategories{
    private String name;
    private String code;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "T8CP6_EducationSetup{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}