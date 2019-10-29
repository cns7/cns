/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playfair1;

/**
 *
 * @author MY-PC
 */


//package playfair;

/**
 *
 * @author Senthil
 */
import java.io.*;
import java.util.*;
public class Playfair1 {

   
    public static void main(String[] args) {
        // TODO code application logic here
          String x,y;
   int g=0,u=0,h=0,v=0,flag1=0,s=0;
   int alphacheck=0,check;
   int strcount=0,i,j,k,m=97;
       int[][] matrix = new int[10][10];
       System.out.println("keyword:");
    Scanner scan = new Scanner(System.in);
   x=scan.nextLine();
   StringBuilder sk = new StringBuilder();
x.chars().distinct().forEach(c -> sk.append((char) c));
       System.out.println("input:");
   y=scan.nextLine();
   String key=new String(sk);
   System.out.println(key);
   StringBuilder sb= new StringBuilder(y);
   char[] str=key.toCharArray();
       
       for(j=0;j<5;j++)
       {
           for(k=0;k<5;k++)
           {
               if(strcount<key.length())
               {
                   matrix[j][k]=str[strcount];
                   strcount++;
               }
               else{
                   for(check=0;check<key.length();check++)
                    {
                       if(m==(int)str[check])
                       {
                           m++;
                          check=0;
                       }

                   }
                        matrix[j][k]=m;
                        m++;
               }
           }
           
       }
       

       
       
       
for (i=0;i<sb.length(); )
{
   if(sb.charAt(i)==sb.charAt(i+1))
   {
      sb.insert(i+1,'x');
      i=i+2;
   }
   else{
       i=i+2;
   }
}
if((sb.length()%2)!=0)
{
   sb.append('x');
}
   
System.out.println(sb);  
     
       for(j=0;j<5;j++)
       {
           for(k=0;k<5;k++)
           {
              System.out.print((char)matrix[j][k]+" ");
           }
           
              System.out.println("\n");
       }
       
       
        ////////////////////////////////////////////////
    for (i=0;i<sb.length(); i+=2)
   {
      // ////////////
     
       for(j=0;j<5;j++)
       {
           for(k=0;k<5;k++)
           {
               if((char)matrix[j][k]==sb.charAt(i))
               {
               g=j;
               u=k;
               flag1=1;
             
               break;
               }
           }
           if(flag1==1)
           { flag1=0;
               break;
           }
       }
     
       ///////////
       
        for(j=0;j<5;j++)
       {
           for(k=0;k<5;k++)
           {
               if((char)matrix[j][k]==sb.charAt(i+1))
               {h=j;
               v=k;
               flag1=1;
               
               break;}
           }
           if(flag1==1)
           { flag1=0;
               break;
           }
       }
       
       if(g==h || u==v)
       {
           if(g==h)
           {
              u+=1;
              v+=1;
              if(u>4)
              {
                 u=u-5;
              }
              if(v>4)
              {
                 v=v-5;
              }
                sb.setCharAt(i, (char)matrix[g][u]);
                sb.setCharAt(i+1,(char)matrix[h][v]);
           }
           if(u==v)
           {
                g+=1;
              h+=1;
               
              if(g>4)
              {
                 g=g-5;
               }
              if(h>4)
              {
                 h=h-5;
              }
               sb.setCharAt(i, (char)matrix[g][u]);
               sb.setCharAt(i+1,(char)matrix[h][v]);
           }
       }
       else{
       sb.setCharAt(i, (char)matrix[g][v]);
       sb.setCharAt(i+1,(char)matrix[h][u]);
       }
       
       
       ///////////
   }
   
   
 System.out.println("\n"+sb);  
 
 //////Decrypt
 for (i=0;i<sb.length(); i+=2)
   {
      // ////////////
     
       for(j=0;j<5;j++)
       {
           for(k=0;k<5;k++)
           {
               if((char)matrix[j][k]==sb.charAt(i))
               {g=j;
               u=k;
               flag1=1;
             
               break;}
           }
           if(flag1==1)
           { flag1=0;
               break;
           }
       }
     
       ///////////
       
        for(j=0;j<5;j++)
       {
           for(k=0;k<5;k++)
           {
               if((char)matrix[j][k]==sb.charAt(i+1))
               {h=j;
               v=k;
               flag1=1;
               
               break;}
           }
           if(flag1==1)
           { flag1=0;
               break;
           }
       }
       
       if(g==h || u==v)
       {
           if(g==h)
           {
              u-=1;
              v-=1;
              if(u<0)
              {
                 u=u+5;
              }
              if(v<0)
              {
                 v=v+5;
              }
                sb.setCharAt(i, (char)matrix[g][u]);
                sb.setCharAt(i+1,(char)matrix[h][v]);
           }
           if(u==v)
           {
                g-=1;
              h-=1;
               
              if(g<0)
              {
                 g=g+5;
               }
              if(h<0)
              {
                 h=h+5;
              }
               sb.setCharAt(i, (char)matrix[g][u]);
               sb.setCharAt(i+1,(char)matrix[h][v]);
           }
       }
       else{
       sb.setCharAt(i, (char)matrix[g][v]);
       sb.setCharAt(i+1,(char)matrix[h][u]);
       }
       
       
       ///////////
       
   }
 
 String sb1=new String(sb);
 char[] res=sb1.toCharArray();
 String n="";
 for(int l=0;l<res.length;l++)
 {
     if(res[l]=='x')
         continue;
     else
         n+=res[l];
 
 }
 System.out.println("\n"+n);
}
}

