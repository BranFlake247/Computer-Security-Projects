public class TinyEncryptionAlgorithm {
    private static int rounds  = 32;                //Assuming 32 rounds
    private  static final int delta =  0x9e3779b9;  //2^32 golden ratio, key scheduling constant

    //Encryption method
    public static int[]  encrypt(int[] message, int[] key){
        int L = message[0];    //Left 32-bits of block 
        int R = message[1];    //Right 32-bits of block

        int sum = 0;                        
        for (int i = 0; i < rounds; i++) {                                  //loop that itterates through selected amount of rounds
            sum += delta;                                                   //Increments the sum by the keyy scheduling constant

            L += ((R << 4) + key[0] ^ (R + sum) ^ ((R >> 5) + key[1]));     //Left shift of right 32-bits of block by 4 and adds the first 32 bits of the key.
                                                                            //Then XOR the result of  the Sum and right 32-bits of block and XOR's again the right shift 
                                                                            //of the right 32 bit block by 5 and adds the the next 32 bits of the key.

            R += ((L <<  4) + key[2] ^  (L + sum) ^ ((L >> 5) + key[3]));   //Left shift of the left 32-bits of block by 4 adn adds the next 32 bits of the key.
                                                                            //Then Xor the results of the Sum and left 32-bits and XOR's again the Left shift of
                                                                            //the left 32 bit block by 5 and adds the last 32 bits of the key.  
        }

        return new int[]{L, R}; //Returns encrypted 64-bit block

    }

    //Decryption method
    public static int[] decrypt (int[] message, int[] key) {
        int L = message[0];    //Left 32-bits of block
        int R = message[1];    //Right 32-bits of block

        int sum = delta * rounds;
        for (int i = 0; i  < rounds; i++) {
            R -= ((L << 4) + key[2]) ^ (L + sum) ^ ((L >> 5) + key [3]);    //Decryption uses the same equation except you subtract each iteration instead of adding to get the plaintext
            L -= ((R << 4) + key[0]) ^ (R + sum) ^ ((R >> 5) + key[1]);
            sum -= delta;
        }

        return new int[]{L, R}; //Returns decrypted 64-bit block
    }

    public static void main(String[] args){

        int[] key = {0xAF6BABCD, 0xEF00F000, 0xFEAFAFAF, 0xACCDEF01};       //128 bit key
        int[] message = {0x01CA4567, 0x0CABCDEF};                           //64-bit plaintext block
        
        System.out.println("Original Message: " + Integer.toHexString(message[0]) + " " + Integer.toHexString(message[1]));
        
        int[] encryptedMessage = encrypt(message, key);
        System.out.println("Encrypted Message: " + Integer.toHexString(encryptedMessage[0]) + " " + Integer.toHexString(encryptedMessage[1]));
        
        int[] decryptedMessage = decrypt(encryptedMessage, key);
        System.out.println("Decrypted Message: " + Integer.toHexString(decryptedMessage[0]) + " " + Integer.toHexString(decryptedMessage[1]));

    }

}