
import java.io.*;
import java.util.Random;

public class FileTools {
    public static void generate() {
        for (int i = 1; i <= 100; i++) {
            try (OutputStream fos = new FileOutputStream("test" + i + ".txt")) {
                Random random = new Random();
                int textLen = 100 + random.nextInt(9901);
                int tempLen = 1 + random.nextInt(10);
                StringBuilder sb = new StringBuilder(textLen);

                for (int j = 0; j < textLen; j++) {
                    sb.append((char) ('a' + random.nextInt(26)));
                }

                sb.append("\n");

                if (i > 50) {
                    int start = random.nextInt(textLen - tempLen + 1);
                    sb.append(sb, start, start + tempLen);
                } else {
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
                    int posFound = BM.find();
                    long end = System.nanoTime();
                    long resTime = end - start;
                    int resIter = BM.getIterations();
                    int resTextLen = BM.getTextLen();
                    int resTempLen = BM.getTempLen();
                    sb.append(i).append(";").append(resTextLen).append(";").append(resTempLen).append(";").append(resIter).append(";").append(resTime).append(";").append(posFound).append("\n");
                }
                fos.write(sb.toString().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
