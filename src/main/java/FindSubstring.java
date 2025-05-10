import java.util.Arrays;

public class FindSubstring {
    public int iterations = 0;
    private final String text;
    private final String template;
    private final int textLen;
    private final int tempLen;

    public FindSubstring(String text, String template) {
        this.text = text;
        this.template = template;
        this.tempLen = template.length();
        this.textLen = text.length();
    }

    public int getIterations() {
        return iterations;
    }

    public int getTextLen() {
        return textLen;
    }

    public int getTempLen() {
        return tempLen;
    }



    private int[] offsetTable() {
        int[] offsetTable = new int[256];
        Arrays.fill(offsetTable, tempLen);
        for(int i = 0; i < tempLen - 1; i++) {
            offsetTable[template.charAt(i)] = tempLen - i - 1;
        }
        return offsetTable;
    }

    private int[] shiftTable() {
        int[] shiftTable = new int[tempLen + 1];
        int[] border = new int[tempLen + 1];

        int i = tempLen, j = tempLen + 1;
        border[i] = j;

        while (i > 0) {
            while (j <= tempLen && template.charAt(i - 1) != template.charAt(j - 1)) {
                if (shiftTable[j] == 0) {
                    shiftTable[j] = j - i;
                }
                j = border[j];
                iterations++;
            }
            i--;
            j--;
            border[i] = j;
        }

        j = border[0];
        for (i = 0; i <= tempLen; i++) {
            if (shiftTable[i] == 0) {
                shiftTable[i] = j;
            }
            if (i == j) {
                j = border[j];
            }
        }
        return shiftTable;
    }

    public int find() {
        if (template == null) return -1;
        if (textLen < tempLen) return -1;

        int[] offsetTable = offsetTable();
        int[] shiftTable = shiftTable();


        int textIndex = 0;
        while (textIndex <= textLen - tempLen) {
            int tempIndex = tempLen - 1;
            while (tempIndex >= 0 && template.charAt(tempIndex) == text.charAt(textIndex + tempIndex)) {
                tempIndex--;
                iterations++;
            }

            if (tempIndex < 0) {
                return textIndex;
            }
            textIndex += Math.max(offsetTable[text.charAt(textIndex + tempLen - 1)], shiftTable[tempIndex + 1]);
            iterations++;
        }
        return -1;
    }
}