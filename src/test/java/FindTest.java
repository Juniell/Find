import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindTest {

    @Test
    public void analysis() {
        assertEquals(Find.analysis("find [-d directory] filename.txt"), 1);         // Без [-r]
        assertEquals(Find.analysis("find [-r] [-d directory] filename.txt"), 2);    // С [-r]
        assertThrows(IllegalArgumentException.class, () -> Find.analysis("find"));  // Исключение
    }

    @Test
    public void search() {
        assertEquals(Find.search("find [-d " + new File("").getAbsolutePath() + "\\src\\main\\java] Find.java", 1), "Файл найден.");
        assertEquals(Find.search("find [-r] [-d " + new File("").getAbsolutePath() + "\\src] FindTest.java", 2), "Файл найден.");
        assertEquals(Find.search("find Find.iml", 3), "Файл найден.");
        assertEquals(Find.search("find nonexistent.txt", 3), "Файл не найден.");
        assertEquals(Find.search("find [-r] test1.txt", 4), "Файл найден.");
        assertEquals(Find.search("find [-r] nonexistent.txt", 4), "Файл не найден.");
    }

    @Test
    public void second() {
        assertTrue(Find.second(new File("").getAbsolutePath() + "\\src", "FindTest.java"));
        assertTrue(Find.second(new File("").getAbsolutePath(), "test1.txt"));
        assertTrue(Find.second(new File("").getAbsolutePath(), "Find.iml"));
    }
}