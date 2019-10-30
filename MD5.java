import java.nio.*;
import java.util.Scanner;

/*
*   Created by Rosetta
*   Recreated by Logesh B, Logesh D
*
*   The below program works only for input length < 56
*/

public class Md5 {
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
        Scanner sc = new Scanner(System.in);
        String plainText = sc.nextLine();
        //String plainText = "geeksforgeeks";
        
        int len = plainText.length();
        
        //Convert into 64-byte format & fill data for len-th & 56th byte
        byte[] TotalMessage = new byte[64];
        for(int i=0; i < len; i++)
            TotalMessage[i] = (byte)plainText.charAt(i);
        
        TotalMessage[len] = (byte)0x80; //10000000
       
        byte[] temp = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(len * 8).array();
        for(int i=0; i<8; i++)
            TotalMessage[56 + i] = temp[i];
        
        //Convert 64-byte data to 4 * 16-byte data
        int X[] = new int[16];
        for(int i=0, k=0; i<16; i++) {
            temp = new byte[4];
            
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

        //64 equals 4 rounds * 16-byte data
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
            int tmp = b + Integer.rotateLeft(a + f + X[k] + T[i], S[i]);
            a = d;
            d = c;
            c = b;
            b = tmp;
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
