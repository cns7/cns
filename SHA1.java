import java.nio.*;
import java.util.Scanner;

/*
*   Created by Rosetta
*   Recreated by Logesh B, Logesh D
*
*   The below program works only for input length < 56
*/

public class Sha1 {
    private static int INIT_A = 0x67452301;
    private static int INIT_B = 0xEFCDAB89;
    private static int INIT_C = 0x98BADCFE;
    private static int INIT_D = 0x10325476;
    private static int INIT_E = 0xC3D2E1F0;

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
        
        byte[] temp = ByteBuffer.allocate(8).putLong(len * 8).array();
        for(int i=0; i<8; i++)
            TotalMessage[56 + i] = temp[i];
        
        //Convert 64-byte data to 80-byte data
        int X[] = new int[80];
        for(int i=0, k=0; i < 16; i++) {
            temp = new byte[4];
            
            for(int j=0; j < 4; j++)
                temp[j] = TotalMessage[k++];
            
            X[i] = ByteBuffer.wrap(temp).getInt();
        }

        for(int i = 16; i < 80; i++)
            X[i] = Integer.rotateLeft(X[i - 3] ^ X[i - 8] ^ X[i - 14] ^ X[i - 16], 1);

        //Initialise variables
        int a = INIT_A;
        int b = INIT_B;
        int c = INIT_C;
        int d = INIT_D;
        int e = INIT_E;
        
        for(int i = 0; i < 80; i++) {
            int div20 = i/20;
            int f = 0;
            int k = 0;
            
            switch(div20){
                case 0:
                    f = (b & c) | (~b & d);
                    k = 0x5A827999;
                    break;
                case 1:
                    f = b ^ c ^ d;
                    k = 0x6ED9EBA1;
                    break;
                case 2:
                    f = (b & c) | (b & d) | (c & d);
                    k = 0x8F1BBCDC;
                    break;
                case 3:
                    f = b ^ c ^ d;
                    k = 0xCA62C1D6;
                    break;
            }
          
            //Interchange values
            int tmp = Integer.rotateLeft(a, 5) + f + e + k + X[i];
            e = d;
            d = c;
            c = Integer.rotateLeft(b, 30);
            b = a;
            a = tmp;
        }
        
        a += INIT_A;
        b += INIT_B;
        c += INIT_C;
        d += INIT_D;
        e += INIT_E;
        
        byte[] encText = ByteBuffer.allocate(20)
                    .putInt(a)
                    .putInt(b)
                    .putInt(c)
                    .putInt(d)
                    .putInt(e)
                    .array();

        //Print the encrypted text in Hex format
        for(int i=0; i < encText.length; i++)
            System.out.print(String.format("%02X", encText[i]));
    }
}
