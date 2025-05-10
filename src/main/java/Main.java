public class Main {
    public static void main(String[] args) {
        FileTools.generate();
        FileTools.write("resultsRandom.csv", 1, 50);
        FileTools.write("resultsReal.csv", 51, 100);
    }
}