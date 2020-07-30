
class DES {
    private static final String KEY = "MinaMina"; //Should be 8 chars
    private static final int NKEY = 16;
    private static final int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };
    private int[] IP1 = {40, 8, 48, 16, 56, 24, 64,
            32, 39, 7, 47, 15, 55,
            23, 63, 31, 38, 6, 46,
            14, 54, 22, 62, 30, 37,
            5, 45, 13, 53, 21, 61,
            29, 36, 4, 44, 12, 52,
            20, 60, 28, 35, 3, 43,
            11, 51, 19, 59, 27, 34,
            2, 42, 10, 50, 18, 58,
            26, 33, 1, 41, 9, 49,
            17, 57, 25
    };
    private static final int[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    private static final int[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };
    private static final int[] EP = {32, 1, 2, 3, 4, 5, 4,
            5, 6, 7, 8, 9, 8, 9, 10,
            11, 12, 13, 12, 13, 14, 15,
            16, 17, 16, 17, 18, 19, 20,
            21, 20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29, 28,
            29, 30, 31, 32, 1
    };
    private static final int[] P = {16, 7, 20, 21, 29, 12, 28,
            17, 1, 15, 23, 26, 5, 18,
            31, 10, 2, 8, 24, 14, 32,
            27, 3, 9, 19, 13, 30, 6,
            22, 11, 4, 25
    };
    private static final int[][][] SBOX = {
            {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},
            {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},
            {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},
            {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},
            {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},
            {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},
            {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},
            {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}
    };

    private String getPermutationChoice1(String key) {
        StringBuilder temp = new StringBuilder();
        for (int b : PC1) {
            temp.append(key.charAt(b));
        }
        return temp.toString();

    }

    private String[] getPermutationChoice2(String[] key) {
        String[] temp = new String[16];
        for (int i = 0; i < 16; i++)
            temp[i] = "";
        for (int i = 0; i < key.length; i++) {
            for (int b : PC2) {
                temp[i] += key[i].charAt(b - 1);
            }
        }
        return temp;
    }

    private String getInitialPermutation(String msg) {
        StringBuilder temp = new StringBuilder();
        for (int i : IP) {
            temp.append(msg.charAt(i - 1));
        }
        return temp.toString();
    }

    private String getPermutationInverse(String msg) {
        StringBuilder temp = new StringBuilder();
        for (int i : IP1) {
            temp.append(msg.charAt(i - 1));
        }
        return temp.toString();
    }

    private String getPermutation(String msg) {
        StringBuilder temp = new StringBuilder();
        for (int i : EP) {
            temp.append(msg.charAt(i - 1));
        }
        return temp.toString();
    }

    private String getPermutationSBox(String msg) {
        StringBuilder temp = new StringBuilder();
        for (int i : P) {
            temp.append(msg.charAt(i - 1));
        }
        return temp.toString();
    }

    private String getBinary8Bits(String key) {
        StringBuilder buffer = new StringBuilder();
        char c;
        for (int i = 0; i < key.length(); i++) {
            c = key.charAt(i);
            for (int j = 7; j >= 0; j--) {

                buffer.append((c >> j) & 1);
            }
        }
        return buffer.toString();
    }

    private String getBinary4Bits(int number) {
        StringBuilder buffer = new StringBuilder();
        int c;
        c = number;
        for (int j = 3; j >= 0; j--) {
            buffer.append((c >> j) & 1);
        }
        return buffer.toString();
    }

    private String getBinaryToString(String msg) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < msg.length(); i += 8) {
            String temp = msg.substring(i, i + 8);
            int num = Integer.parseInt(temp, 2);
            String str = Character.toString((char) num);
            buffer.append(str);
        }
        return buffer.toString();
    }

    private String getXOR(String block, String Key) {
        StringBuilder temp = new StringBuilder();
        for (int j = 0; j < block.length(); j++) {
            if (block.charAt(j) == Key.charAt(j)) {
                temp.append("0");
            } else {
                temp.append("1");
            }
        }
        return temp.toString();
    }

    private String swapHalf(String buffer) {
        String right = buffer.substring(0, 32);
        String left = buffer.substring(32, 64);
        return left + right;


    }

    private String getLeftPart(String buffer) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < buffer.length() / 2; i++) {
            temp.append(buffer.charAt(i));
        }
        return temp.toString();
    }

    private String getRightPart(String buffer) {
        StringBuilder temp = new StringBuilder();
        for (int i = buffer.length() / 2; i < buffer.length(); i++) {
            temp.append(buffer.charAt(i));
        }
        return temp.toString();
    }

    private String oneShift(String key) {
        StringBuilder temp = new StringBuilder();
        for (int i = 1; i < key.length(); i++) {
            temp.append(key.charAt(i));
        }
        temp.append(key.charAt(0));
        return temp.toString();
    }

    private String twoShift(String key) {
        StringBuilder temp = new StringBuilder();
        for (int i = 2; i < key.length(); i++)
            temp.append(key.charAt(i));

        temp.append(key.charAt(0));
        temp.append(key.charAt(1));
        return temp.toString();
    }

    private String sBox(String msg) {
        StringBuilder temp = new StringBuilder();
        int startIndex = 0;
        int endIndex = 6;
        String rowStr;
        String colStr;
        String substring;
        for (int i = 0; i < 8; i++) {
            rowStr = "";
            substring = msg.substring(startIndex, endIndex);
            rowStr += substring.charAt(0);
            rowStr += substring.charAt(5);
            int rowDecimal = Integer.parseInt(rowStr, 2);
            colStr = substring.substring(1, 5);
            int colDecimal = Integer.parseInt(colStr, 2);
            temp.append(getBinary4Bits(SBOX[i][rowDecimal][colDecimal]));
            startIndex += 6;
            endIndex += 6;
        }
        return temp.toString();
    }

    private String round(String msg, String key, int index) {
        String leftMsg = getLeftPart(msg);
        String rightMsg = getRightPart(msg);
        String temp = leftMsg;
        leftMsg = rightMsg;
        rightMsg = getPermutation(rightMsg);
        rightMsg = getXOR(rightMsg, key);
        rightMsg = sBox(rightMsg);
        rightMsg = getPermutationSBox(rightMsg);
        rightMsg = getXOR(rightMsg, temp);
        System.out.println("L" + (index + 1) + "= " + leftMsg);
        System.out.println("R" + (index + 1) + "= " + rightMsg);
        return rightMsg + leftMsg;
    }

    private String[] shiftKey(String key) {
        String[] temp = new String[NKEY];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 1 || i == 8 || i == 15) {
                key = oneShift(key);
                temp[i] = key;
            } else {
                key = twoShift(key);
                temp[i] = key;
            }
        }
        return temp;
    }

    private String[] concatenateKeys(String[] left, String[] right) {
        String[] temp = new String[NKEY];
        for (int i = 0; i < left.length; i++) {
            temp[i] = left[i] + right[i];
        }
        return temp;
    }

    String encryptMsg(String msg) {
        /*KEYS*/
        String key = getBinary8Bits(KEY);
        key = getPermutationChoice1(key);
        String leftKey = getLeftPart(key);
        String rightKey = getRightPart(key);
        String[] leftShiftedKeys = shiftKey(leftKey);
        String[] rightShiftedKeys = shiftKey(rightKey);
        String[] concatenatedKeys = concatenateKeys(leftShiftedKeys, rightShiftedKeys);
        concatenatedKeys = getPermutationChoice2(concatenatedKeys); /*KEYS*/
        /*MSG*/
        msg = getBinary8Bits(msg);
        msg = getInitialPermutation(msg);
        for (int i = 0; i < 16; i++) {
            msg = round(msg, concatenatedKeys[i], i);
        }
        msg = swapHalf(msg);
        msg = getPermutationInverse(msg);
        System.out.println("Cypher: " + msg); /*MSG*/
        return msg;
    }

    String decryptMsg(String cypher) {
        String key = getBinary8Bits(KEY);
        key = getPermutationChoice1(key);
        String leftKey = getLeftPart(key);
        String rightKey = getRightPart(key);
        String[] leftShiftedKeys = shiftKey(leftKey);
        String[] rightShiftedKeys = shiftKey(rightKey);
        String[] concatenatedKeys = concatenateKeys(leftShiftedKeys, rightShiftedKeys);
        concatenatedKeys = getPermutationChoice2(concatenatedKeys);
        /*MSG*/
        //cypher = getBinary8Bits(cypher);
        cypher = getInitialPermutation(cypher);
        cypher = swapHalf(cypher);
        for (int i = 15; i >= 0; i--) {
            cypher = round(cypher, concatenatedKeys[i], i);
        }
        cypher = getPermutationInverse(cypher);
        cypher = getBinaryToString(cypher);
        System.out.println("Plain Text = " + cypher);
        return cypher;
    }
}
