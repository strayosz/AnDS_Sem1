
import java.io.*;
import java.util.Random;

public class FileTools {
    public static void generate() { //метод, который генерирует 100 тестовых файлов: первые 50 на случайных данных, последние 50 на реальных данных
        for (int i = 1; i <= 100; i++) {
            try (OutputStream fos = new FileOutputStream("test" + i + ".txt")) {
                Random random = new Random();
                int textLen = 100 + random.nextInt(9901);
                int tempLen = 1 + random.nextInt(10);
                StringBuilder sb = new StringBuilder(textLen);

                //создание случайного текста
                for (int j = 0; j < textLen; j++) {
                    sb.append((char) ('a' + random.nextInt(26)));
                }

                sb.append("\n");

                if (i > 50) { // Реальные данные: вторые 50 файлов содержат в себе шаблон, который точно есть в тексте,
                    int start = random.nextInt(textLen - tempLen + 1);
                    sb.append(sb, start, start + tempLen);
                } else { //Случайные данные: первые 50 файлов содержат в себе шаблон, сгенерированный случайным образом
                    for (int j = 0; j < tempLen; j++) {
                        sb.append((char) ('a' + random.nextInt(26)));
                    }
                }

                fos.write(sb.toString().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String[] read(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String text = reader.readLine();
            String template = reader.readLine();
            return new String[] {text, template};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String fileName, int startI, int endI) {
            try (OutputStream fos = new FileOutputStream(fileName)) {
                StringBuilder sb = new StringBuilder();
                sb.append("№").append(";").append("TextLen").append(";").append("TempLen").append(";").append("Iter").append(";").append("Time").append(";").append("posFound").append("\n");
                for (int i = startI; i <= endI; i++) {
                    String[] array = read("test" + i + ".txt");
                    long start = System.nanoTime();
                    FindSubstring BM = new FindSubstring(array[0], array[1]);
                    int posFound = BM.find(); //позиция, в которой нашли совпадение
                    long end = System.nanoTime();
                    long resTime = end - start; //время выполнения программы
                    int resIter = BM.getIterations(); //количество итераций
                    int resTextLen = BM.getTextLen(); //длина текста
                    int resTempLen = BM.getTempLen(); //длина шаблона
                    sb.append(i).append(";").append(resTextLen).append(";").append(resTempLen).append(";").append(resIter).append(";").append(resTime).append(";").append(posFound).append("\n");
                }
                fos.write(sb.toString().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
