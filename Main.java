class Main {
  public static void main(String[] args) {
    (new Main()).init();
  }

  void print(Object o) {
    System.out.println(o);
  }

  void init() {
    // Substitution mappings
    char[] sub = new char[26];
    sub[0] = 'a';
    sub[1] = 'b';
    sub[2] = 'c';
    sub[3] = 'd';
    sub[4] = 'e';
    sub[5] = 'f';
    sub[6] = 'g';
    sub[7] = 'h';
    sub[8] = 'i';
    sub[9] = 'j';
    sub[10] = 'k';
    sub[11] = 'l';
    sub[12] = 'm';
    sub[13] = 'n';
    sub[14] = 'o';
    sub[15] = 'p';
    sub[16] = 'q';
    sub[17] = 'r';
    sub[18] = 's';
    sub[19] = 't';
    sub[20] = 'u';
    sub[21] = 'v';
    sub[22] = 'w';
    sub[23] = 'x';
    sub[24] = 'y';
    sub[25] = 'z';

    // Full Arabic alphabet substitution
    char[] sub2 = {
      'ا', // Arabic A
      'ب', // Arabic B
      'ت', // Arabic T
      'ث', // Arabic TH
      'ج', // Arabic J
      'ح', // Arabic H
      'خ', // Arabic KH
      'د', // Arabic D
      'ذ', // Arabic DH
      'ر', // Arabic R
      'ز', // Arabic Z
      'س', // Arabic S
      'ش', // Arabic SH
      'ص', // Arabic S (emphatic)
      'ض', // Arabic D (emphatic)
      'ط', // Arabic T (emphatic)
      'ظ', // Arabic DH (emphatic)
      'ع', // Arabic AIN
      'غ', // Arabic GH
      'ف', // Arabic F
      'ق', // Arabic Q
      'ك', // Arabic K
      'ل', // Arabic L
      'م', // Arabic M
      'ن', // Arabic N
      'ه', // Arabic H
      'و', // Arabic W
    };

    // Messages
    String message1 = "Hi how are you";
    String message2 = "This is me and Logans project";
    String message3 = "Thank you for listening";

    // Encode and decode message 1
    print("\nMessage 1: " + message1);
    processMessage(message1, sub, sub2);

    // Encode and decode message 2
    print("\nMessage 2: " + message2);
    processMessage(message2, sub, sub2);

    // Encode and decode message 3
    print("\nMessage 3: " + message3);
    processMessage(message3, sub, sub2);
  }

  // Process a single message (encrypt and decrypt it)
  void processMessage(String originalText, char[] sub, char[] sub2) {
    // Encoding process
    String encodedMsg1 = encode(originalText);  // Step 1: Caesar cipher
    print("Encoded Step 1 (Caesar Cipher): " + encodedMsg1);

    String encodedMsg2 = subEncryption(encodedMsg1, sub, sub2);  // Step 2: Substitution
    print("Encoded Step 2 (Substitution): " + encodedMsg2);

    String encodedMsg3 = transpositionCipher(encodedMsg2, 5);  // Step 3: Transposition Cipher
    print("Encoded Step 3 (Transposition Cipher): " + encodedMsg3);

    // Decoding process
    String decodedMsg1 = reverseTranspositionCipher(encodedMsg3, 5);  // Step 1: Reverse Transposition Cipher
    print("Decoded Step 1 (Reversed Transposition Cipher): " + decodedMsg1);

    String decodedMsg2 = subEncryption(decodedMsg1, sub2, sub);  // Step 2: Reverse substitution
    print("Decoded Step 2 (Reverse Substitution): " + decodedMsg2);

    String decodedMsg3 = decode(decodedMsg2);  // Step 3: Reverse Caesar cipher
    print("Decoded Step 3 (Caesar Cipher Reversed): " + decodedMsg3);
  }

  // Transposition Cipher (chunk-based scrambling)
  String transpositionCipher(String txt, int chunkSize) {
    String bld = "";
    for (int i = 0; i < txt.length(); i += chunkSize) {
      int end = Math.min(i + chunkSize, txt.length());
      String chunk = txt.substring(i, end);
      bld += new StringBuilder(chunk).reverse().toString();
    }
    return bld;
  }

  // Reverse Transposition Cipher (decoding transposition)
  String reverseTranspositionCipher(String txt, int chunkSize) {
    return transpositionCipher(txt, chunkSize); // Same process as encoding
  }

  // Caesar cipher encoding with shift +4, only for English alphabet
  String encode(String txt) {
    StringBuilder bld = new StringBuilder();
    for (int x = 0; x < txt.length(); x++) {
      char ch = txt.charAt(x);
      if (Character.isLetter(ch) && ch >= 'a' && ch <= 'z') {
        int ascii = (int) ch;
        ascii += 4;  // Shift by +4
        if (ascii > 'z') ascii -= 26;  // Wrap around if past 'z'
        bld.append((char) ascii);
      } else if (Character.isLetter(ch) && ch >= 'A' && ch <= 'Z') {
        // Handle uppercase letters
        int ascii = (int) ch;
        ascii += 4;  // Shift by +4
        if (ascii > 'Z') ascii -= 26;  // Wrap around if past 'Z'
        bld.append((char) ascii);
      } else {
        bld.append(ch); // Keep non-letter characters unchanged
      }
    }
    return bld.toString();
  }

  // Caesar cipher decoding with shift -4, only for English alphabet
  String decode(String txt) {
    StringBuilder bld = new StringBuilder();
    for (int x = 0; x < txt.length(); x++) {
      char ch = txt.charAt(x);
      if (Character.isLetter(ch) && ch >= 'a' && ch <= 'z') {
        int ascii = (int) ch;
        ascii -= 4;  // Shift by -4
        if (ascii < 'a') ascii += 26;  // Wrap around if past 'a'
        bld.append((char) ascii);
      } else if (Character.isLetter(ch) && ch >= 'A' && ch <= 'Z') {
        // Handle uppercase letters
        int ascii = (int) ch;
        ascii -= 4;  // Shift by -4
        if (ascii < 'A') ascii += 26;  // Wrap around if past 'A'
        bld.append((char) ascii);
      } else {
        bld.append(ch); // Keep non-letter characters unchanged
      }
    }
    return bld.toString();
  }

  // Substitution encryption
  String subEncryption(String s, char[] sub, char[] sub2) {
    StringBuilder bld = new StringBuilder();
    for (int x = 0; x < s.length(); x++) {
      char ch = s.charAt(x);
      int index = indexOf(Character.toLowerCase(ch), sub);
      if (index != -1) {
        bld.append(sub2[index]); // Substitute if found
      } else {
        bld.append(ch); // Keep the character if not in substitution array
      }
    }
    return bld.toString();
  }

  // Find the index of a character in an array
  int indexOf(char ch, char[] arry) {
    for (int x = 0; x < arry.length; x++) {
      if (arry[x] == ch) {
        return x;
      }
    }
    return -1; // Return -1 if character not found
  }
}
