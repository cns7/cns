/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package railfence;

import java.io.*;
import java.util.*;

public class Railfence {

    /**
     * @param args the command line arguments
     */
    
     public static String encryption(int key,int length,String str)
    {
        int inc=0;
        char[][] matrix = new char[key][length];
        String res="";
         for(int i=0;i<length;i++)
        {
            for(int j=0;j<key;j++)
            {
                if(inc<str.length())
                {
                    matrix[j][i]=str.charAt(inc);
                }
                else
                    matrix[j][i]='$';
                inc++;
            }
        }
        for(int i=0;i<key;i++)
        {
            for(int j=0;j<length;j++)
                    res+=matrix[i][j];
        }
        return res;
    }
    public static void decryption(int key,int length,String str)
    {
        int inc=0;
        char[][] matrix = new char[length][key];
         for(int i=0;i<key;i++)
        {
            for(int j=0;j<length;j++)
            {
                if(inc<str.length())
                {
                    matrix[j][i]=str.charAt(inc);
                }
                else
                    matrix[j][i]='$';
                inc++;
            }
        }
        System.out.printf("Decrypted Result is : "); 
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<key;j++)
                if(matrix[i][j]!='$')
                    System.out.printf("%c",matrix[i][j]);
        }
        System.out.println();
    }
    public static void main(String[] args) {
        // TODO code application logic here
        
        String str;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the plaintext : ");
        str=input.nextLine();
        int key;
        System.out.println("Enter the key : "); 
        key=input.nextInt();
      int inc=0,length;
      if(str.length()%key==0)
        length=str.length()/key;
      else
          length=str.length()/key+1;
       String result=encryption(key,length,str);
        System.out.println("Encrypted Result is : "+result); 
        decryption(key,length,result);
    }
    
}
