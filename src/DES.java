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

    private static String plainText;
    private static String binaryPlainText;
    private static String initialKey;
    private static String binaryKey;
    private static String permutedKey;
    private static String[] cKeys = new String[17];
    private static String[] dKeys = new String[17];
    private static String[] keysBeforePC2Permutation = new String[16];
    private static String[] finalKeys = new String[16];
    private static String permutedPlainText;
    private static String[] lTexts = new String[17];
    private static String[] rTexts = new String[17];
    private static String binaryCipherText;
    private static String permutedBinaryCipherText;
    private static String cipherText;

    public static void main(String[] args) {

        plainText = inputPlainText();
        binaryPlainText = convertStringToBinary(plainText);

        initialKey = inputKey();
        binaryKey = hexToBinary(initialKey);
        permutedKey = keyPc1Permutation(binaryKey);
        System.out.println("The plaintext " +plainText +" converted to binary is: \n" + binaryPlainText +"\n");
        System.out.println("The given key " + initialKey +" converted to binary is: \n" +binaryKey
                            +"\nThe permuted with PC1 table key is: \n" +permutedKey +"\n");

        cdKeysCreation(permutedKey);
        System.out.println("The C and D subkeys are: ");
        for (int i=0; i< cKeys.length; i++){
            System.out.println("C" +(i+1) +" = " +cKeys[i]
                                +" -- D" +(i+1) +" = " +dKeys[i]);
        }

        keysBeforePC2Permutation = keysBeforePC2PermutationCreation();
        System.out.println("\nThe keys after C and D concatenation are: ");
        for (int i=0; i< keysBeforePC2Permutation.length; i++){
            System.out.println((i+1) +": " +keysBeforePC2Permutation[i]);
        }

        finalKeys = pc2Permutation(keysBeforePC2Permutation);
        System.out.println("\nThe final keys that will be used for the encryption are: ");
        for (int i=0; i< finalKeys.length; i++){
            System.out.println("Key" +(i+1) +": " +finalKeys[i]);
        }

        permutedPlainText = ipPermutation(binaryPlainText);
        System.out.println("\nThe permuted with IP table plaintext is: \n" +permutedPlainText +"\n");

        lrTextsCreation(permutedPlainText);
        System.out.println("The L and R subtexts are: ");
        for (int i=0; i< cKeys.length; i++){
            System.out.println("L" +(i+1) +" = " +lTexts[i]
                    +" -- R" +(i+1) +" = " +rTexts[i]);
        }

        binaryCipherText = createBinaryCipherText();
        System.out.println("\nThe ciphertext in binary is: \n" + binaryCipherText);

        permutedBinaryCipherText = ipReversePermutation(binaryCipherText);
        System.out.println("\nThe permuted with IP_REVERSE table ciphertext is: \n" +permutedBinaryCipherText);

        cipherText = binaryToHex(permutedBinaryCipherText);
        cipherText = cipherText.toUpperCase();
        System.out.println("\n\nEncrypting the plaintext: " +plainText +" \nwith the key: " +initialKey
                            +" \nwith the use of DES(ECB) cipher \nproduces the ciphertext: " +cipherText +"\n");
    }

    //This method handles the plaintext input from the user.
    private static String inputPlainText(){
        String input = "";

        //Check for correct input of key. Key length must be at maximum 64 bits.
        while(true){
            System.out.println("Please insert plaintext with size of 64 bits maximum.");
            input = in.nextLine();
            if (input.length() <= 8)
                break;
        }

        if (input.length() < 8)
            for (int i=input.length(); i<8; i++)
                input += "0";

        return input;
    }

    //Plaintext inserted from user is converted to binary.
    //If a character is less than 4 bits long, it is padded with zeros
    //so that the final text is always 64 bits long as DES needs.
    private static String convertStringToBinary(String str){
        String binaryPlainText = "";
        String s = ""; //Every 1 character of the plaintext is saved here and converted to 4 bit binary.
        char[] c = new char[str.length()];

        for (int i=0; i<str.length(); i++){
            c[i] = str.charAt(i);
            int n = c[i];
            s = Integer.toBinaryString(n);
            if (s.length() < 8) {
                for (int j=s.length(); j<8; j++) {
                    s = "0" + s;
                }
            }
            binaryPlainText += s;
        }
        return binaryPlainText;
    }

    //This method handles the key input from the user. The key entered must
    //be 64 bits long or it isn't accepted.
    private static String inputKey(){
        String inputKey = "";

        while(true) {
            System.out.println("Insert a 64bit hexadecimal key: ");
            inputKey = in.nextLine();
            if (inputKey.length() == 16 && isHex(inputKey))
                break;
        }

        return inputKey;
    }

    //This method checks if the key inserted is a hexadecimal number.
    private static boolean isHex(String str){

        str = str.toUpperCase();

        for (int i=0; i<str.length(); i++){

            if (str.charAt(i) != '0' && str.charAt(i) != '1'
                && str.charAt(i) != '2' && str.charAt(i) != '3'
                    && str.charAt(i) != '4' && str.charAt(i) != '5'
                    && str.charAt(i) != '6' && str.charAt(i) != '7'
                    && str.charAt(i) != '8' && str.charAt(i) != '9'
                    && str.charAt(i) != 'A' && str.charAt(i) != 'B'
                    && str.charAt(i) != 'C' && str.charAt(i) != 'D'
                    && str.charAt(i) != 'E' && str.charAt(i) != 'F'){
                return false;
            }
        }

        return true;

    }

    //Hexadecimal key inserted from user is converter to binary.
    private static String hexToBinary(String str){
        str = str.replaceAll("0", "0000");
        str = str.replaceAll("1", "0001");
        str = str.replaceAll("2", "0010");
        str = str.replaceAll("3", "0011");
        str = str.replaceAll("4", "0100");
        str = str.replaceAll("5", "0101");
        str = str.replaceAll("6", "0110");
        str = str.replaceAll("7", "0111");
        str = str.replaceAll("8", "1000");
        str = str.replaceAll("9", "1001");
        str = str.replaceAll("A", "1010");
        str = str.replaceAll("B", "1011");
        str = str.replaceAll("C", "1100");
        str = str.replaceAll("D", "1101");
        str = str.replaceAll("E", "1110");
        str = str.replaceAll("F", "1111");
        return str;
    }

    //Permutation of the key according to the PC1 table.
    private static String keyPc1Permutation(String key){
        String pKey = "";

        for (int i=0; i< PC1.length; i++) {
            pKey += key.charAt(PC1[i] - 1);
        }

        return pKey;
    }

    //This method splits the permuted key in half and creates the
    //C, D tables. Every new C, D key is created according to DES(ECB)
    //shift table. In the end 17 are created of which the last 16
    //will be used for the final keys creation.
    private static void cdKeysCreation(String key){
        cKeys[0] = key.substring(0, key.length()/2);
        dKeys[0] = key.substring(key.length()/2);

        for (int i=1; i<17; i++){
            if (i==1 || i==2 || i==9 || i==16){
                cKeys[i] = cKeys[i-1].substring(1) + cKeys[i-1].substring(0,1);
                dKeys[i] = dKeys[i-1].substring(1) + dKeys[i-1].substring(0,1);
            }else{
                cKeys[i] = cKeys[i-1].substring(2) + cKeys[i-1].substring(0,2);
                dKeys[i] = dKeys[i-1].substring(2) + dKeys[i-1].substring(0,2);
            }
        }
    }

    //This method concatenates the C, D keys and prepares them
    //for the permutation according to the PC2 table.
    private static String[] keysBeforePC2PermutationCreation(){
        String[] keys = new String[16];
        for (int i=0; i<16; i++){
            keys[i] = "";
            keys[i] = cKeys[i+1].toString() + dKeys[i+1].toString();
        }

        return keys;
    }

    //This method creates the 16 final keys which will be used for the encryption.
    private static String[] pc2Permutation(String[] keys){
        String[] fKeys = new String[16];
        for (int i=0; i<16; i++) {
            fKeys[i] = "";
            for (int j=0; j<PC2.length; j++) {
                fKeys[i] += keys[i].charAt(PC2[j] -1);
            }
        }

        return fKeys;
    }

    //This method permutes the plaintext entered by the user
    //according to the IP table.
    private static String ipPermutation(String str){
        String text = "";
        for (int i=0; i<IP.length; i++) {
            text += str.charAt(IP[i]-1);
        }

        return text;
    }

    //This method splits the permuted initial text and then creates
    //the left and right tables of text.
    private static void lrTextsCreation(String text){
        for (int i=0; i<17; i++){
            lTexts[i] = "";
            rTexts[i] = "";
        }

        lTexts[0] = text.substring(0, text.length()/2);
        rTexts[0] = text.substring(text.length()/2);

        String functionTable = "";

        for (int i=1; i<17; i++){
            lTexts[i] = rTexts[i-1];
            functionTable = function(rTexts[i-1], finalKeys[i-1]);
            for (int j=0; j<functionTable.length(); j++){
                rTexts[i] += lTexts[i-1].charAt(j) ^ functionTable.charAt(j);
            }
        }
    }

    //This method concatenates the 16th right and left texts
    //and the binary ciphertext is created.
    private static String createBinaryCipherText(){
        String text = "";
        text = rTexts[16].concat(lTexts[16]);

        return text;
    }

    //This methods permuts the binary ciphertext according
    //to the IP_REVERSE table.
    private static String ipReversePermutation(String str){
        String text = "";
        for (int i=0; i<IP_REVERSE.length; i++){
            text += str.charAt(IP_REVERSE[i]-1);
        }

        return text;
    }

    //This method converts the permutedBinaryCiphertext to hexadecimal.
    private static String binaryToHex(String str){
        String text = "";
        text = new BigInteger(str, 2).toString(16);

        return text;
    }

    private static String expandedText;
    private static String xorTable;
    private static String sBoxesOutput;
    private static String functionResult;

    //This method implements the expansion of each right block from 32 bits to 48 bits.
    //Next there is an XOR of the EXPANDED RIGHT BLOCK with the KEY.
    //The result from the XOR above is shrinked again to 32bits
    //with the use of the SBOXES.
    //Finally it is permuted
    private static String function(String rText, String key){

        expandedText = rightTextExpansion(rText);

        xorTable = calculateXORTable(expandedText, key);

        sBoxesOutput = calculateSBoxesOutput(xorTable);

        functionResult = sBoxesOutputPermutation(sBoxesOutput);

        return functionResult;
    }

    //This method expands the right text from 32 bits to 48 bits.
    private static String rightTextExpansion(String str){
        String e = "";
        for (int i=0; i< EBIT.length; i++){
            e += str.charAt(EBIT[i]-1);
        }

        return e;
    }

    //This method calculates the XOR of the EXPANDED RIGHT BLOCK with the KEY
    private static String calculateXORTable(String str, String key){
        String xor = "";
        for (int i=0; i<str.length(); i++){
            xor += str.charAt(i) ^ key.charAt(i);
        }

        return xor;
    }

    //This method calculates the 32 bits output of the SBOXES.
    private static String calculateSBoxesOutput(String xorT){

        String afterSBoxTable = "";
        String temp = "";
        String firstBit = "";
        String lastBit = "";
        int row;
        int column;
        int whichSBox = 1;

        String[] sBoxValues = new String[8];

        for (int i=0; i<48; i=i+6){
            temp = xorT.substring(i, i+6);

            firstBit = "";
            lastBit = "";
            firstBit += temp.charAt(i%6);
            lastBit += temp.charAt(i%6+5);

            row = Integer.parseInt(firstBit.concat(lastBit),2);
            column = Integer.parseInt(temp.substring(i%6+1, i%6+5),2);

            if (whichSBox == 1){
                sBoxValues[0] = Integer.toBinaryString(S1[row][column]);
            }else if(whichSBox == 2){
                sBoxValues[1] = Integer.toBinaryString(S2[row][column]);
            }else if(whichSBox == 3){
                sBoxValues[2] = Integer.toBinaryString(S3[row][column]);
            }else if(whichSBox == 4){
                sBoxValues[3] = Integer.toBinaryString(S4[row][column]);
            }else if(whichSBox == 5){
                sBoxValues[4] = Integer.toBinaryString(S5[row][column]);
            }else if(whichSBox == 6){
                sBoxValues[5] = Integer.toBinaryString(S6[row][column]);
            }else if(whichSBox == 7){
                sBoxValues[6] = Integer.toBinaryString(S7[row][column]);
            }else if(whichSBox == 8){
                sBoxValues[7] = Integer.toBinaryString(S8[row][column]);
            }

            whichSBox++;

        }

        //Any value of the sboxes that doesn't isn't 4 bits in size
        //is padded with zeros in front so the outcome will be 32 bits.
        for (int i=0; i<8; i++){
            if (sBoxValues[i].length() < 4){
                for (int j=sBoxValues[i].length(); j<4; j++){
                    sBoxValues[i] = "0" + sBoxValues[i];
                }
            }
            afterSBoxTable += sBoxValues[i];
        }

        return afterSBoxTable;

    }

    //This method permutes the sBoxesOutput according to the PF table.
    private static String sBoxesOutputPermutation(String str){

        String permutationResult = "";
        for (int i=0; i<PF.length; i++){
            permutationResult += str.charAt(PF[i]-1);
        }

        return permutationResult;

    }
}
