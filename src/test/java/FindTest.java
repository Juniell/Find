import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;

public class FindTest {
    private String dir = new File("").getAbsolutePath();
    private Find find1 = new Find(false, dir + File.separator + "input" + File.separator + "folder1" + File.separator + "empty folder", "test.txt");
    private Find find2 = new Find(true, null, "test2");
    private Find find3 = new Find(false, dir + File.separator + "input" + File.separator + "folder1", "test1.txt");
    private Find find4 = new Find(true, dir + File.separator + "input", "test1.txt");
    private Find find5 = new Find(true, null, "test.txt");

    @Test
    public void search() {
        // 1) Поиск в пустой директории, т.е. поиск файла, которого заведомо нет в этой директории
        assertEquals("Файл не найден.", find1.search());
        // 2) Поиск по всем поддиректориям файла, которого заведомо нет
        assertEquals("Файл не найден.", find2.search());
        // 3) Поиск в определённой директории без поиска в поддиректориях
        assertEquals("Файл найден: " + dir + File.separator + "input" + File.separator + "folder1" + File.separator + "test1.txt", find3.search());
        // 4) Поиск в определённой директории с поиском в поддиректориях единственного файла
        assertEquals("Файл найден: " + dir + File.separator + "input" + File.separator + "folder1" + File.separator + "test1.txt", find4.search());
        // 5) Поиск без указания директории с поиском в поддиректориях файла, который встречается несколько раз
        assertEquals("Найденные файлы:\n" +
                dir + File.separator + "input" + File.separator + "folder1" + File.separator + "test.txt\n" +
                dir + File.separator + "input" + File.separator + "folder2" + File.separator + "test.txt", find5.search());
    }
}