/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5;
import java.nio.*;



public class MD5 {
    private static final int INIT_A = 0x67452301;
    private static final int INIT_B = 0xEFCDAB89;
    private static final int INIT_C = 0x98BADCFE;
    private static final int INIT_D = 0x10325476;
    
    /*
        7, 12, 17, 22
        5, 9, 14, 20
        4, 11, 16, 23
        6, 10, 15, 21
    */

    private static final int S[] =  {   7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
                                        5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 
                                        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
                                        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
                                    };

    public static void main(String[] args) {
        String plainText = "abcdefghij";
        int len = plainText.length();
        
        //Convert into 64-bit format & fill data for 56th & len-th position
        byte[] TotalMessage = new byte[64];
        for(int i=0; i < len; i++)
            TotalMessage[i] = (byte)plainText.charAt(i);
        
        TotalMessage[len] = (byte)0x80; //10000000
        TotalMessage[56] = (byte)(len * 8);
        
        //Convert 64-bit data to 4 * 16-bit data
        int X[] = new int[16];
        for(int i=0, k=0; i<16; i++) {
            byte[] temp = new byte[4];
            
            for(int j=0; j<4; j++)
                temp[j] = TotalMessage[k++];
            
            X[i] = ByteBuffer.wrap(temp).order(ByteOrder.LITTLE_ENDIAN).getInt();
        }
        
        //Generating T[] table using sin()
        int T[] = new int[64];
        for (int i = 0; i < 64; i++)
            T[i] = (int) (long) (Math.pow(2, 32) * Math.abs(Math.sin(i + 1)));
        
        //Initialise variables
        int a = INIT_A;
        int b = INIT_B;
        int c = INIT_C;
        int d = INIT_D;

        //64 equals 4 rounds * 16-bit data
        for(int i=0; i < 64; i++) {
            int div16 = i / 16;
            int f = 0;
            int k = i;
            
            switch (div16) {
                case 0:
                    f = (b & c) | (~b & d);
                    break;
                case 1:
                    f = (b & d) | (c & ~d);
                    k = (k * 5 + 1) % 16;
                    break;
                case 2:
                    f = b ^ c ^ d;
                    k = (k * 3 + 5) % 16;
                    break;
                case 3:
                    f = c ^ (b | ~d);
                    k = (k * 7) % 16;
                    break;
            }
            
            //Interchange values
            //temp = b + (a + g(b,c,d) + X[k] + T[i]) <<< s (formula)
            int temp = b + Integer.rotateLeft(a + f + X[k] + T[i], S[i]);
            a = d;
            d = c;
            c = b;
            b = temp;
        }
        
        a += INIT_A;
        b += INIT_B;
        c += INIT_C;
        d += INIT_D;

        byte[] encText = ByteBuffer.allocate(16)
                    .order(ByteOrder.LITTLE_ENDIAN).putInt(a)
                    .order(ByteOrder.LITTLE_ENDIAN).putInt(b)
                    .order(ByteOrder.LITTLE_ENDIAN).putInt(c)
                    .order(ByteOrder.LITTLE_ENDIAN).putInt(d)
                    .array();

        //Print the encrypted text in Hex format
        for(int i=0; i < encText.length; i++)
            System.out.print(String.format("%02X", encText[i]));
    }
}



