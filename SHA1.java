/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha1;
import java.nio.*;

/*
*   Created by Rosetta
*   Recreated by Logesh B, Logesh D
*
*   The below program works only for input length < 32
*/

public class SHA1 {
    private static int INIT_A = 0x67452301;
    private static int INIT_B = 0xEFCDAB89;
    private static int INIT_C = 0x98BADCFE;
    private static int INIT_D = 0x10325476;
    private static int INIT_E = 0xC3D2E1F0;

    public static void main(String[] args) {
        String plainText = "";
        int len = plainText.length();
        
        //Convert into 64-bit format & fill data for 63rd & len-th position
        byte[] TotalMessage = new byte[64];
        for(int i=0; i < len; i++)
            TotalMessage[i] = (byte)plainText.charAt(i);
        
        TotalMessage[len] = (byte)0x80; //10000000
        TotalMessage[63] = (byte)(len * 8);
        
        //Convert 64-bit data to 80-bit data
        int X[] = new int[80];
        for(int i=0, k=0; i < 16; i++) {
            byte[] temp = new byte[4];
            
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
        
        for(int j = 0; j < 80; j++) {
            int div20 = j/20;
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
            int temp = Integer.rotateLeft(a, 5) + f + e + k + X[j];
            e = d;
            d = c;
            c = Integer.rotateLeft(b, 30);
            b = a;
            a = temp;
        }
        
        INIT_A += a;
        INIT_B += b;
        INIT_C += c;
        INIT_D += d;
        INIT_E += e;
        
        byte[] encText = ByteBuffer.allocate(20)
                    .putInt(INIT_A)
                    .putInt(INIT_B)
                    .putInt(INIT_C)
                    .putInt(INIT_D)
                    .putInt(INIT_E)
                    .array();

        //Print the encrypted text in Hex format
        for(int i=0; i < encText.length; i++)
            System.out.print(String.format("%02X", encText[i]));
    }
}