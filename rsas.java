package rsa;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Rsa {
    public static void main(String[] args) {
        String str;
        Scanner input=new Scanner(System.in);
        System.out.println("Enter Text:\t");
        str=input.nextLine();
        String str2="";
        for(int i=0; i<str.length(); i++){
            int num=((int)str.charAt(i))-22;
            if(num == 100)
                num=40;
            str2+=num;
        }
        System.out.println("Message="+str2);
        BigInteger p;//15487039//15487049
        BigInteger q;
        Random rand = new Random();
        p=BigInteger.probablePrime(2048, rand);
        q=p.nextProbablePrime();
        BigInteger n = p.multiply(q);
        System.out.println("p="+p+"  q="+q);
        System.out.println("n="+n);
        BigInteger phi = new BigInteger("1");
        phi = (p.subtract(phi)).multiply(q.subtract(phi));
        System.out.println("phi="+phi);
       
       
        BigInteger e =BigInteger.probablePrime(1024, rand);
        while(phi.gcd(e).compareTo(BigInteger.ONE)>0 && e.compareTo(phi)<0){
            e.add(BigInteger.ONE);
        }
        System.out.println("e="+e);
        BigInteger d=e.modInverse(phi);
        System.out.println("d="+d);
        BigInteger m=new BigInteger(str2),c=m.modPow(e, n),dec_m;
        System.out.println("C="+c);
        dec_m=c.modPow(d, n);
        System.out.println("M="+dec_m);
        String str3=dec_m.toString();
        for(int i=0; i<str3.length(); i+=2){
            int num = Integer.parseInt(str3.substring(i, i+2));
            char ch;
            if(num==40)
                ch='z';
            else
                ch=(char) (Integer.parseInt(str3.substring(i, i+2))+22);
            System.out.print(ch);
    }
       
    }
}
