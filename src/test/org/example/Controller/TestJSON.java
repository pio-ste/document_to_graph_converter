package org.example.Controller;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.BaseTest;
import org.junit.Test;
import org.testfx.api.FxAssert;

public class TestJSON extends BaseTest {

    @Test
    public void testJsonOption(){
        clickOn("#dataFromJsonRadio");
        clickOn("#selectJsonFileBtn").doubleClickOn(1200,400).clickOn(1000,340).clickOn(1300,610);
        FxAssert.verifyThat("#directoryFileField", (TextField textField) -> textField.getText().contains("C:\\Users\\Piotr\\Desktop\\students.json"));
        clickOn("#selectDirectoryID").clickOn(1200,400).clickOn(1300,610);
        FxAssert.verifyThat("#directoryCatalogField", (TextField textField) -> textField.getText().contains("C:\\Users\\Piotr\\Desktop"));
        clickOn("#uriNeo4jField").write("bolt://localhost:7687");
        clickOn("#userNameNeo4jField").write("neo4j");
        clickOn("#passwordNoe4jField").write("admin");
        clickOn("#testConnNeo4jID");
        FxAssert.verifyThat("#statusNeo4jLabel", (Label label) -> label.isVisible());
        clickOn("#secondStepBtn");
        FxAssert.verifyThat("#jsonContentTextField", (TextArea textArea) -> textArea.getText()
                .contains("[{\n" +
                        "  \"_id\": 1,\n" +
                        "  \"imie\": \"Adam\",\n" +
                        "  \"nazwisko\": \"Nowak\",\n" +
                        "  \"kierunek\": \"Informatyka\",\n" +
                        "  \"oceny\": [\n" +
                        "    {\n" +
                        "      \"matematyka\": 4,\n" +
                        "      \"fizyka\": 4,\n" +
                        "      \"angielski\": 5\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"matematyka\": 3,\n" +
                        "      \"fizyka\": 5,\n" +
                        "      \"angielski\": 4\n" +
                        "    }\n" +
                        "  ]\n" +
                        "},{\n" +
                        "  \"_id\": 2,\n" +
                        "  \"imie\": \"Robert\",\n" +
                        "  \"nazwisko\": \"Nowak\",\n" +
                        "  \"kierunek\": \"Informatyka\",\n" +
                        "  \"oceny\": [\n" +
                        "    {\n" +
                        "      \"matematyka\": 5,\n" +
                        "      \"fizyka\": 5,\n" +
                        "      \"angielski\": 5\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"matematyka\": 4,\n" +
                        "      \"fizyka\": 4,\n" +
                        "      \"angielski\": 4\n" +
                        "    }\n" +
                        "  ]\n" +
                        "},{\n" +
                        "  \"_id\": 3,\n" +
                        "  \"imie\": \"Rafał\",\n" +
                        "  \"nazwisko\": \"Kowalczyk\",\n" +
                        "  \"kierunek\": \"Informatyka\",\n" +
                        "  \"oceny\": [\n" +
                        "    {\n" +
                        "      \"matematyka\": 4,\n" +
                        "      \"fizyka\": 4,\n" +
                        "      \"angielski\": 5\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"matematyka\": 3,\n" +
                        "      \"fizyka\": 5,\n" +
                        "      \"angielski\": 4\n" +
                        "    }\n" +
                        "  ]\n" +
                        "},{\n" +
                        "  \"_id\": 4,\n" +
                        "  \"imie\": \"Kamil\",\n" +
                        "  \"nazwisko\": \"Nowak\",\n" +
                        "  \"kierunek\": \"Informatyka\",\n" +
                        "  \"oceny\": [\n" +
                        "    {\n" +
                        "      \"matematyka\": 4,\n" +
                        "      \"fizyka\": 4,\n" +
                        "      \"angielski\": 5\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"matematyka\": 3,\n" +
                        "      \"fizyka\": 5,\n" +
                        "      \"angielski\": 4\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}]"));
        clickOn((Node) lookup("#idColumn").nth(1).query());
        clickOn("#relationField").write("otrzymal");
        clickOn("#relationParameterField").write("data");
        clickOn("#relationValueField").write("01.12.2020");
        clickOn("#setRelationBtn");
        clickOn((Node) lookup("#idColumn").nth(2).query());
        clickOn("#relationField").write("otrzymal");
        clickOn("#relationParameterField").write("data");
        clickOn("#relationValueField").write("02.12.2020");
        clickOn("#setRelationBtn");
        clickOn((Node) lookup("#idColumn").nth(3).query());
        clickOn("#relationField").write("otrzymal");
        clickOn("#relationParameterField").write("data");
        clickOn("#relationValueField").write("03.12.2020");
        clickOn("#setRelationBtn");
        clickOn((Node) lookup("#idColumn").nth(4).query());
        clickOn("#relationField").write("otrzymal");
        clickOn("#relationParameterField").write("data");
        clickOn("#relationValueField").write("04.12.2020");
        clickOn("#setRelationBtn");
        clickOn((Node) lookup("#idColumn").nth(5).query());
        clickOn("#relationField").write("otrzymal");
        clickOn("#relationParameterField").write("data");
        clickOn("#relationValueField").write("05.12.2020");
        clickOn("#setRelationBtn");
        clickOn((Node) lookup("#idColumn").nth(6).query());
        clickOn("#relationField").write("otrzymal");
        clickOn("#setRelationBtn");
        clickOn((Node) lookup("#idColumn").nth(7).query());
        clickOn("#relationField").write("otrzymal");
        clickOn("#setRelationBtn");
        clickOn((Node) lookup("#idColumn").nth(8).query());
        clickOn("#relationField").write("otrzymal");
        clickOn("#setRelationBtn");

        clickOn("#thirdStepBtn");

        FxAssert.verifyThat("#queryTextArea", (TextArea textArea) -> textArea.getText()
                .contains("CREATE (students_1:students {imie:'Adam', nazwisko:'Nowak', kierunek:'Informatyka', _id:1})\n" +
                        "CREATE (oceny_2:oceny {fizyka:4, matematyka:4, angielski:5})\n" +
                        "CREATE (oceny_3:oceny {fizyka:5, matematyka:3, angielski:4})\n" +
                        "CREATE (students_4:students {imie:'Robert', nazwisko:'Nowak', kierunek:'Informatyka', _id:2})\n" +
                        "CREATE (oceny_5:oceny {fizyka:5, matematyka:5, angielski:5})\n" +
                        "CREATE (oceny_6:oceny {fizyka:4, matematyka:4, angielski:4})\n" +
                        "CREATE (students_7:students {imie:'Rafał', nazwisko:'Kowalczyk', kierunek:'Informatyka', _id:3})\n" +
                        "CREATE (oceny_8:oceny {fizyka:4, matematyka:4, angielski:5})\n" +
                        "CREATE (oceny_9:oceny {fizyka:5, matematyka:3, angielski:4})\n" +
                        "CREATE (students_10:students {imie:'Kamil', nazwisko:'Nowak', kierunek:'Informatyka', _id:4})\n" +
                        "CREATE (oceny_11:oceny {fizyka:4, matematyka:4, angielski:5})\n" +
                        "CREATE (oceny_12:oceny {fizyka:5, matematyka:3, angielski:4})\n" +
                        "\n" +
                        "CREATE (oceny_2)-[:OTRZYMAL {data:['01.12.2020']}]->(students_1), (oceny_3)-[:OTRZYMAL {data:['02.12.2020']}]->(students_1)\n" +
                        "CREATE (oceny_5)-[:OTRZYMAL {data:['03.12.2020']}]->(students_4), (oceny_6)-[:OTRZYMAL {data:['04.12.2020']}]->(students_4)\n" +
                        "CREATE (oceny_8)-[:OTRZYMAL {data:['05.12.2020']}]->(students_7), (oceny_9)-[:OTRZYMAL]->(students_7)\n" +
                        "CREATE (oceny_11)-[:OTRZYMAL]->(students_10), (oceny_12)-[:OTRZYMAL]->(students_10);"));
        clickOn("#runQueryBtn");
        FxAssert.verifyThat("#statusNeo4jLabel", (Label label) -> label.isVisible());
    }
}
