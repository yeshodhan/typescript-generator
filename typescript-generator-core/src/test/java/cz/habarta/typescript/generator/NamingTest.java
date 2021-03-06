
package cz.habarta.typescript.generator;

import cz.habarta.typescript.generator.compiler.SymbolTable;
import java.util.LinkedHashMap;
import org.junit.Assert;
import org.junit.Test;


public class NamingTest {

    @Test(expected = SymbolTable.NameConflictException.class)
    public void testConflictReport() {
        final Settings settings = TestUtils.settings();
        new TypeScriptGenerator(settings).generateTypeScript(Input.from(A.ConflictingClass.class, B.ConflictingClass.class));
    }

    @Test
    public void testConflictResolved() {
        final Settings settings = TestUtils.settings();
        settings.customTypeNaming = new LinkedHashMap<>();
        settings.customTypeNaming.put("cz.habarta.typescript.generator.NamingTest$A$ConflictingClass", "A$ConflictingClass");
        settings.customTypeNaming.put("cz.habarta.typescript.generator.NamingTest$B$ConflictingClass", "B$ConflictingClass");
        final String output = new TypeScriptGenerator(settings).generateTypeScript(Input.from(A.ConflictingClass.class, B.ConflictingClass.class));
        Assert.assertTrue(output.contains("A$ConflictingClass"));
        Assert.assertTrue(output.contains("B$ConflictingClass"));
    }

    private static class A {
        private static class ConflictingClass {
            public String conflictingProperty;
        }
    }

    private static class B {
        private static class ConflictingClass {
            public String conflictingProperty;
        }
    }

}
