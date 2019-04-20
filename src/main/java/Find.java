import java.io.File;
import java.util.Scanner;

public class Find {

    // Запуск поиска
    public static void main(String[] args) {
        String str = input();
        int an = analysis(str);
        search(str, an);
        System.out.print("Результат: " + search(str, an));
    }

    // Ввод командной строки с клавиатуры
    public static String input() {
        System.out.println("Введите командную строку вида \"find [-r] [-d directory] filename.txt\"");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();                              // Считываем строку.
        in.close();
        return str;
    }

    // Проверка формата
    public static int analysis(String str) {
        // a - вариант без [-r], b - вариант с ним.
        boolean a = str.matches("find \\[-d .*] .*\\..*");
        boolean b = str.matches("find \\[-r] \\[-d .*] .*\\..*");
        // Случаи, когда не указана директория,
        // т.е. строки вида "find [-r] filename.txt" и "find filename.txt".
        boolean d = str.matches("find \\[-r] [^:]*\\..*");
        boolean c = str.matches("find [^:]*\\..*");

        int res = 0;
        if (a && !b && !c && !d) res = 1;
        if (!a && b && !c && !d) res = 2;
        if (!a && !b && c && !d) res = 3;
        if (!a && !b && c && d) res = 4;
        if (res == 0) throw new IllegalArgumentException("format mismatch"); // Исключение, если не соответствует формату
        return res;
    }

    // Общий поиск файла
    public static String search(String str, int an) {
        // Вариант 1, при котором не надо искать в поддиректориях.
        // (объединяем 1 и 3 случаи, т.к. они отличаются только выделением директории и имени из строки)
        if (an == 1 || an == 3) {
            StringBuilder dir = new StringBuilder();
            String name;
            if (an == 1) {                                       // Если an == 1,
                str = str.substring(9);                          // убираем из строки "find [-d ",
                // остаётся строка вида "directory] filename.txt", выделяем директорию:
                for (char ch : str.toCharArray()) {              // переписываем строку до тех пор,
                    if (ch != ']')                               // пока не наткнёмся на ']'.
                        dir.append(ch);
                    else
                        break;
                }
                name = str.substring(dir.length() + 2);          // Убираем из строки дирикторию, ']' и пробел.
            }
            else {                                               // Если an == 3,
                dir.append(new File("").getAbsolutePath());      // определяем текущую директорию,
                name = str.substring(5);                         // убираем из строки "find ".
            }

            File file = new File(dir.toString(), name);          // Определяем объект для каталога
            if (file.isFile())                                   // и определяем, есть ли такой файл.
                return "Файл найден.";
            else
                return "Файл не найден.";
        }

        // Вариант 2, при котором надо искать в поддиректориях.
        // (объединяем 2 и 4 случаи, т.к. они отличаются только выделением директории и имени из строки)
        if (an == 2 || an == 4){
            StringBuilder dir = new StringBuilder();
            String name;
            if (an == 2) {                                       // Если an == 1,
                str = str.substring(14);                         // Убираем из строки "find [-r] [-d ".
                // Выделяем из этой строки директорию и имя файла так же, как и в первом варианте.
                for (char ch : str.toCharArray()) {
                    if (ch != ']')
                        dir.append(ch);
                    else
                        break;
                }
                name = str.substring(dir.length() + 2);
            }
            else {                                               // Если an == 4,
                dir.append(new File("").getAbsolutePath());      // определяем текущую директорию,
                name = str.substring(10);                        // убираем из строки "find [-r] ".
            }

            if (second(dir.toString(), name))                    // Обращаемся к рекурсивной функции поиска.
                return "Файл найден.";                           // Если она вернула true - файл найден,
            else                                                 // иначе - нет.
                return "Файл не найден.";
        }
        // Если же an принимает другие значение, бросаем исключение.
        throw new IllegalArgumentException("format mismatch");
    }

    // Рекурсивная функция поиска файла в поддирикториях
    public static boolean second(String dir, String name) {
        boolean check = false;                                      // (переменная для проверки)
        File file = new File(dir, name);                            // Определяем объект для каталога.
        if (file.isFile())                                          // Если это файл (т.е. он существет),
            check = true;                                           // заносим информацию о том, что файл найден.
        else {                                                      // Иначе
            File directory = new File(dir);                         // этот объект - директория.
            File[] listFiles = directory.listFiles();               // (массив со всеми файлами и поддиректориями первоначальной директории)
            if (listFiles != null && listFiles.length != 0)         // Если в этой директории есть файлы,
                for (File obj: listFiles)                           // прохоим по ним,
                    if (obj.isDirectory())                          // но рассматриваем только поддиректории
                        if (second(obj.getAbsolutePath(), name))    // и рекурсивно их проверяем.
                            check = true;
        }
        // Если же после того, как мы рассмотрели все поддиректории, нужный файл не был найден,
        // возвращаем сообщение о том, что файл отсутствует.
            return check;
    }
}