/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rowcolumn;

import java.io.*;
import java.util.*;
public class ROWCOLUMN {

    public static String encryption(char[][] matrix,int[] key,String
str,int length,int inc)
    {

        String result="";
        int counter=0;

        for(int i=0;i<length;i++)
        {
            for(int j=0;j<inc;j++)
            {
                if(counter<str.length())
                {
                    matrix[i][j]=str.charAt(counter);
                }
                else{
                     matrix[i][j]='x';
                     }

                counter++;
            }
        }
        
        
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<inc;j++)
                System.out.printf("%c ",matrix[i][j]);
            System.out.printf("\n");
        }
        
        
       int w;
               for(int i=1;i<=inc;i++)
               {
                   for( w=0;w<inc;w++)
                   {
                       if(key[w]==i)
                           break;

                   }
                   for(int j=0;j<length;j++)
            {
                result+=matrix[j][w];
            }

               }

        System.out.println();
        return result;
    }
   public static String decryption(char[][] matrix,int[] key,String  str,int length,int inc)
    {
        String result="";
        int counter=0;

        //int col = str.length()/inc;

        char[][] matrix1 = new char [inc][length];

        for(int i=0;i<inc;i++)
        {
            for(int j=0;j<length;j++)
            {
                if(counter<str.length())
                {
                    matrix1[i][j]=str.charAt(counter);
                }
                else
                    matrix1[i][j]='x';
                counter++;
            }
        }
     //   System.out.println(key.length);
        for(int i=0;i<inc;i++)
        {
            for(int j=0;j<length;j++)
            {
                            //System.out.printf("%d %d\n",i,j);

                System.out.printf("%c ",matrix1[i][j]);
            }
            System.out.printf("\n");
        }


            for(int i=0;i<length;i++)
            {
                 for(int j=0;j<inc;j++)
                {

                    result += matrix1[key[j]-1][i];
                }
            }
        return result;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        String str,key;
        Scanner input=new Scanner(System.in);
        System.out.println("Enter the plaintext:");
        str=input.nextLine();
        System.out.println("Enter the keys:");
        key=input.nextLine();
        int keys[],inc=0,length=0;
        inc=key.length();
        keys = new int[key.length()];
        for(int i=0;i<key.length();i++)
                keys[i]=key.charAt(i)-'0';
        if(str.length()%inc==0)
            length=str.length()/inc;
        else
          length=str.length()/inc+1;
        char[][] matrix = new char[length][inc];
        String result=encryption(matrix,keys,str,length,inc);
        System.out.printf("Encrypted String is\n");
        System.out.println(result);
        System.out.println();
        matrix = new char[inc][length];
        result=decryption(matrix,keys,result,length,inc);
        System.out.println("Decrypted result is:" +result);

    }
    
}