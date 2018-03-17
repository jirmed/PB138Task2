/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.task2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import static java.util.Collections.list;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author jirka
 */
@RunWith(Parameterized.class)
public class SchemaValidatorTest {

    @Parameters(name = "{index}: validate({0})={1}")
    public static Collection<Object[]> data() {

        List<Object[]> result;
        List<Object[]> list = new ArrayList<Object[]>();
        addFilesToTest(VALID, list, true);
        addFilesToTest(INVALID, list, false);

        result = Arrays.asList(list.toArray(new Object[][]{}));
        return result;
    }

    private static void addFilesToTest(String folderName, List<Object[]> list, Boolean expected) {
        File[] files;

        File folder = new File(folderName);
        files = folder.listFiles(
                (dir, name) -> {
                    return name.toLowerCase().endsWith(".xml");
                }
        );
        for (int i = 0; i < files.length; i++) {
            list.add(new Object[]{(expected ? VALID : INVALID) + '/' + files[i].getName(), expected});
        }
    }
    private static final String VALID = "src/valid";
    private static final String INVALID = "src/invalid";

    private String fInput;

    private Boolean fExpected;
    private SchemaValidator validator;

    public SchemaValidatorTest(String input, Boolean expected) {
        fInput = input;
        fExpected = expected;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
        validator = new SchemaValidator("src/valid/company.xsd");
    }

    @Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        String xmlFilename = fInput;
        Boolean expResult = fExpected;
        System.out.println(xmlFilename + "-" + validator.validate(xmlFilename));
        Boolean result = validator.validate(xmlFilename).equals(xmlFilename + " is valid");
//        String result = xmlFilename;
        assertEquals(expResult, result);
    }

}
