import java.math.BigInteger;
import java.util.Scanner;

public class DES {

    private static final Scanner in = new Scanner(System.in);
    private static final int[] PC1 = {57, 49, 41, 33, 25, 17,  9,
            1, 58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13,  5, 28, 20, 12,  4 };
    private static final int[] PC2 = {14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32};

    private static final int[] IP = {58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7};

    private static final int[] EBIT = {32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1};

    private static final int[][] S1 = {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}};

    private static final int[][] S2 = {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}};

    private static final int[][] S3 = {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}};

    private static final int[][] S4= {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}};

    private static final int[][] S5 = {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}};

    private static final int[][] S6 = {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}};

    private static final int[][] S7 = {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}};

    private static final int[][] S8 = {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};

    private static final int[] PF = {16, 7, 20, 21,
                                29, 12, 28, 17,
                                1, 15, 23, 26,
                                5, 18, 31, 10,
                                2, 8, 24, 14,
                                32, 27, 3, 9,
                                19, 13, 30, 6,
                                22, 11, 4, 25};

    private static final int[] IP_REVERSE = {40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25};

    public static void main(String[] args) {

        String initialKey = "133457799BBCDFF1";

        /*String initialKey = "";

        while(true) {
            System.out.println("Insert a 64bit hexadecical key: ");
            initialKey = in.nextLine();
            if (initialKey.length() == 16)
                break;
        }*/

        initialKey = hexToBin(initialKey);


        System.out.println("The initial key converted to binary is:\n" +initialKey
                +"\nIts size is " +initialKey.length() +" bits.\n");

        String permutedKey = "";

        for (int i=0; i< PC1.length; i++) {
            permutedKey += initialKey.charAt(PC1[i] - 1);
        }

        System.out.println("The permuted key is:\n" +permutedKey
                +"\nIts size is " +permutedKey.length() +" bits.\n");

        if (permutedKey.equals("11110000110011001010101011110101010101100110011110001111")){
            System.out.println("Permuted key is OK");
        }

        String[] cKeys = new String[17];
        String[] dKeys = new String[17];

        cKeys[0] = permutedKey.substring(0, permutedKey.length()/2);
        dKeys[0] = permutedKey.substring(permutedKey.length()/2);

        for (int i=1; i<17; i++){
            if (i==1 || i==2 || i==9 || i==16){
                cKeys[i] = cKeys[i-1].substring(1) + cKeys[i-1].substring(0,1);
                dKeys[i] = dKeys[i-1].substring(1) + dKeys[i-1].substring(0,1);
            }else{
                cKeys[i] = cKeys[i-1].substring(2) + cKeys[i-1].substring(0,2);
                dKeys[i] = dKeys[i-1].substring(2) + dKeys[i-1].substring(0,2);
            }
        }

        String[] C = {"1111000011001100101010101111",
                "1110000110011001010101011111",
                "1100001100110010101010111111",
                "0000110011001010101011111111",
                "0011001100101010101111111100",
                "1100110010101010111111110000",
                "0011001010101011111111000011",
                "1100101010101111111100001100",
                "0010101010111111110000110011",
                "0101010101111111100001100110",
                "0101010111111110000110011001",
                "0101011111111000011001100101",
                "0101111111100001100110010101",
                "0111111110000110011001010101",
                "1111111000011001100101010101",
                "1111100001100110010101010111",
                "1111000011001100101010101111"};

        String[] D = {"0101010101100110011110001111",
                "1010101011001100111100011110",
                "0101010110011001111000111101",
                "0101011001100111100011110101",
                "0101100110011110001111010101",
                "0110011001111000111101010101",
                "1001100111100011110101010101",
                "0110011110001111010101010110",
                "1001111000111101010101011001",
                "0011110001111010101010110011",
                "1111000111101010101011001100",
                "1100011110101010101100110011",
                "0001111010101010110011001111",
                "0111101010101011001100111100",
                "1110101010101100110011110001",
                "1010101010110011001111000111",
                "0101010101100110011110001111"};

        for (int i=0; i<17; i++){
            if (cKeys[i].equals(C[i])){
                System.out.println("C" +i +" IS OK");
            }
            if (dKeys[i].equals(D[i])){
                System.out.println("D" +i +" IS OK");
            }
        }

        for (int i=0; i<17; i++){
            System.out.println("C" +i +" = " +cKeys[i] +" -- D" +i +" = " +dKeys[i]);
        }

        String[] almostFinalKeys = new String[16];

        for (int i=0; i<16; i++){
            almostFinalKeys[i] = "";
            almostFinalKeys[i] = cKeys[i+1].toString() + dKeys[i+1].toString();
        }

        String[] finalKeys = new String[16];

        for (int i=0; i<16; i++) {
            finalKeys[i] = "";
            for (int j=0; j<PC2.length; j++) {
                finalKeys[i] += almostFinalKeys[i].charAt(PC2[j] -1);
            }
        }

        String[] K = {"000110110000001011101111111111000111000001110010",
                "011110011010111011011001110110111100100111100101",
                "010101011111110010001010010000101100111110011001",
                "011100101010110111010110110110110011010100011101",
                "011111001110110000000111111010110101001110101000",
                "011000111010010100111110010100000111101100101111",
                "111011001000010010110111111101100001100010111100",
                "111101111000101000111010110000010011101111111011",
                "111000001101101111101011111011011110011110000001",
                "101100011111001101000111101110100100011001001111",
                "001000010101111111010011110111101101001110000110",
                "011101010111000111110101100101000110011111101001",
                "100101111100010111010001111110101011101001000001",
                "010111110100001110110111111100101110011100111010",
                "101111111001000110001101001111010011111100001010",
                "110010110011110110001011000011100001011111110101"};

        for (int i=0; i<16; i++){
            if(finalKeys[i].equals(K[i])){
                System.out.println("Final Key" +(i+1) +" IS OK");
            }
        }

        for (int i=0; i<16; i++){
            System.out.println("K" +(i+1) +" = " +finalKeys[i]);
        }



        //String plainText = "BARKASPA";
        String plainText = "TYCHALAP";

        String binaryPlainText = "";
        String s = "";
        char[] c = new char[plainText.length()];

        for (int i=0; i<plainText.length(); i++){
            c[i] = plainText.charAt(i);
            int n = c[i];
            s = Integer.toBinaryString(n);
            if (s.length() < 8) {
                for (int j=s.length(); j<8; j++) {
                    s = "0" + s;
                }
            }
            binaryPlainText += s;
        }

        //plainText = hexToBin(plainText);

        System.out.println("\nThe plainText converted to binary is: \n" +binaryPlainText);

        String permutedPlainText = "";

        for (int i=0; i<IP.length; i++) {
            permutedPlainText += binaryPlainText.charAt(IP[i]-1);
        }

        if (permutedPlainText.equals("1100110000000000110011001111111111110000101010101111000010101010")){
            System.out.println("Permuted PlainText IS OK");
        }

        String[] lTexts = new String[17];
        String[] rTexts = new String[17];

        for (int i=0; i<17; i++){
            lTexts[i] = "";
            rTexts[i] = "";
        }

        lTexts[0] = permutedPlainText.substring(0, permutedPlainText.length()/2);
        rTexts[0] = permutedPlainText.substring(permutedPlainText.length()/2);

        if (lTexts[0].equals("11001100000000001100110011111111")){
            System.out.println("L0 IS OK");
        }

        if (rTexts[0].equals("11110000101010101111000010101010")) {
            System.out.println("R0 IS OK");
        }

        String functionTable = "";

        for (int i=1; i<17; i++){
            lTexts[i] = rTexts[i-1];
            functionTable = function(rTexts[i-1], finalKeys[i-1]);
            for (int j=0; j<functionTable.length(); j++){
                rTexts[i] += lTexts[i-1].charAt(j) ^ functionTable.charAt(j);
            }
        }

        if (lTexts[16].equals("01000011010000100011001000110100")){
            System.out.println("L16 IS OK");
        }

        if (rTexts[16].equals("00001010010011001101100110010101")){
            System.out.println("R16 IS OK");
        }

        String cipherTextBinary = "";
        cipherTextBinary = rTexts[16].concat(lTexts[16]);

        if (cipherTextBinary.equals("0000101001001100110110011001010101000011010000100011001000110100")){
            System.out.println("CIPHERTEXT BINARY IS OK");
        }

        String cipherTextBinaryFinal = "";
        for (int i=0; i<IP_REVERSE.length; i++){
            cipherTextBinaryFinal += cipherTextBinary.charAt(IP_REVERSE[i]-1);
        }

        if (cipherTextBinaryFinal.equals("1000010111101000000100110101010000001111000010101011010000000101")){
            System.out.println("IP_REVERSE IS OK");
        }

        String cipherText = new BigInteger(cipherTextBinaryFinal, 2).toString(16);

        cipherText = cipherText.toUpperCase();

        System.out.println(cipherText);
        if (cipherText.equals("85E813540F0AB405")){
            System.out.println("CIPHERTEXT IS OK");
        }

    }

    private static String hexToBin(String s){
        s = s.replaceAll("0", "0000");
        s = s.replaceAll("1", "0001");
        s = s.replaceAll("2", "0010");
        s = s.replaceAll("3", "0011");
        s = s.replaceAll("4", "0100");
        s = s.replaceAll("5", "0101");
        s = s.replaceAll("6", "0110");
        s = s.replaceAll("7", "0111");
        s = s.replaceAll("8", "1000");
        s = s.replaceAll("9", "1001");
        s = s.replaceAll("A", "1010");
        s = s.replaceAll("B", "1011");
        s = s.replaceAll("C", "1100");
        s = s.replaceAll("D", "1101");
        s = s.replaceAll("E", "1110");
        s = s.replaceAll("F", "1111");
        return s;
    }

    private static String convertToHex(String s){
        return String.format("%040x", new BigInteger(1, s.getBytes(/*YOUR_CHARSET?*/)));
    }

    private static String function(String rText, String key){
        String e = "";

        for (int i=0; i< EBIT.length; i++){
            e += rText.charAt(EBIT[i]-1);
        }

        System.out.println("E size is " +e.length());

        String xorTable = "";

        for (int i=0; i<e.length(); i++){
            xorTable += e.charAt(i) ^ key.charAt(i);
        }

        System.out.println("K1 XOR E(R0) = " +xorTable
                +"\nIts length is: " +xorTable.length());

        if (xorTable.equals("011000010001011110111010100001100110010100100111")){
            System.out.println("K1 XOR E(R0) IS OK");
        }

        String afterSBoxTable = "";
        String temp = "";
        String firstBit = "";
        String lastBit = "";
        int row;
        int column;
        int whichSBox = 1;

        String[] sBoxValues = new String[8];

        for (int i=0; i<48; i=i+6){
            temp = xorTable.substring(i, i+6);

            firstBit = "";
            lastBit = "";
            System.out.println(temp);
            firstBit += temp.charAt(i%6);
            lastBit += temp.charAt(i%6+5);
            System.out.println(firstBit +"  " +lastBit);

            row = Integer.parseInt(firstBit.concat(lastBit),2);
            column = Integer.parseInt(temp.substring(i%6+1, i%6+5),2);

            System.out.println(row +"   " +column);

            System.out.println("SBOX: " +whichSBox);

            if (whichSBox == 1){
                sBoxValues[0] = Integer.toBinaryString(S1[row][column]);
                System.out.println("SboxValue is:  " + sBoxValues[0]);
            }else if(whichSBox == 2){
                sBoxValues[1] = Integer.toBinaryString(S2[row][column]);
                System.out.println("SboxValue is:  " +sBoxValues[1]);
            }else if(whichSBox == 3){
                sBoxValues[2] = Integer.toBinaryString(S3[row][column]);
                System.out.println("SboxValue is:  " +sBoxValues[2]);
            }else if(whichSBox == 4){
                sBoxValues[3] = Integer.toBinaryString(S4[row][column]);
                System.out.println("SboxValue is:  " +sBoxValues[3]);
            }else if(whichSBox == 5){
                sBoxValues[4] = Integer.toBinaryString(S5[row][column]);
                System.out.println("SboxValue is:  " +sBoxValues[4]);
            }else if(whichSBox == 6){
                sBoxValues[5] = Integer.toBinaryString(S6[row][column]);
                System.out.println("SboxValue is:  " +sBoxValues[5]);
            }else if(whichSBox == 7){
                sBoxValues[6] = Integer.toBinaryString(S7[row][column]);
                System.out.println("SboxValue is:  " +sBoxValues[6]);
            }else if(whichSBox == 8){
                sBoxValues[7] = Integer.toBinaryString(S8[row][column]);
                System.out.println("SboxValue is:  " +sBoxValues[7]);
            }

            whichSBox++;

        }

        for (int i=0; i<8; i++){
            if (sBoxValues[i].length() < 4){
                for (int j=sBoxValues[i].length(); j<4; j++){
                    sBoxValues[i] = "0" + sBoxValues[i];
                }
            }
            System.out.println("SBOX Value " +(i+1) +"=" +sBoxValues[i]);
            afterSBoxTable += sBoxValues[i];
        }

        System.out.println("\n" +afterSBoxTable +"\nLength = " +afterSBoxTable.length());

        if (afterSBoxTable.equals("01011100100000101011010110010111")){
            System.out.println("AfterSboxTable IS OK");
        }

        String functionResult = "";
        for (int i=0; i<PF.length; i++){
            functionResult += afterSBoxTable.charAt(PF[i]-1);
        }

        System.out.println("Function Table is = \n" +functionResult);
        if (functionResult.equals("00100011010010101010100110111011")){
            System.out.println("FUNCTION TABLE IS OK");
        }

        return functionResult;
    }

}
