public class Main {
    public static void main(String[] args) {
        FileTools.generate(); //генерируем тестовые файлы
        FileTools.write("resultsRandom.csv", 1, 50); //запускаем тесты на рандомных данных и записываем результат
        FileTools.write("resultsReal.csv", 51, 100); //запускаем тесты на реальных данных и записываем результат
    }
}