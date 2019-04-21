import java.io.File;

public class Find {
    private boolean rFlag;
    private String directory;
    private String fileName;

    public Find(boolean rFlag, String directory, String fileName) {
        this.rFlag = rFlag;
        this.directory = directory;
        this.fileName = fileName;
    }

    public void find() {
        String result = search();
        System.out.println(result);
    }

    // Основная функция поиска
    public String search() {
        if (directory == null)                                   // Поиск текущей директории, если она не была задана
            directory = new File("").getAbsolutePath();

        // Поиск при !rFlag (может быть только один файл)
        if (!rFlag) {
            File file = new File(directory, fileName);           // Определяем объект
            if (file.isFile())                                   // и узнаём, есть ли такой файл.
                return "Файл найден: " + file.getAbsolutePath();
            else
                return "Файл не найден.";
        }

        // Поиск при rFlag (может быть несколько файлов)
        else {
            StringBuilder str = second(directory, fileName, new StringBuilder());   // Обращаемся к рекурсивному поиску.
            if (str.toString().equals(""))                       // Если после рекурсивного поиска в строку ничего не добавилось,
                return "Файл не найден.";                        // то файл не был найден.
            else {                                               // Если же был найден хотя бы один файл,
                int strL = str.length();
                str.delete(strL - 1, strL);                      // убираем последний перенос строки.
                if (str.toString().contains("\n"))               // Если есть ещё переносы строки, то файлов несколько,
                    return "Найденные файлы:\n" + str;
                else                                             // иначе файл только один.
                    return "Файл найден: " + str;
            }
        }
    }

    // Рекурсивный поиск для случая с rFlag
    public StringBuilder second(String dir, String fileName, StringBuilder data) {
        // Сразу ищем наш файл в указанной директории
        File file = new File(dir, fileName);
        if (file.isFile())                                               // Если такой файл существует,
            data.append(file.getAbsolutePath()).append("\n");            // заносим информацию о найденном файле.
        // Дальше ищем по поддиректориям
        file = new File(dir);                                            // (директория, данная на фход функции)
        File[] listFiles = file.listFiles();                             // (массив со всеми файлами и поддиректориями директории)
        if (listFiles != null && listFiles.length != 0)                  // Если в этой директории есть файлы,
            for (File obj: listFiles)                                    // прохоим по ним,
                if (obj.isDirectory())                                   // рассматриваем только поддиректории
                    second(obj.getAbsolutePath(), fileName, data);       // и рекурсивно их проверяем.
        return data;
    }
}