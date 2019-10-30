import java.util.Scanner;
import java.lang.Math;
import java.math.BigInteger;

public class rsa1{

	public static int gcd(int a,int b)
	{
		int gcd=1;
		for(int i=2;i<=a&&i<=b;i++)
		{
			if((a%i==0)&&(b%i==0))
				gcd=i;
		}
		return gcd;
	}

	public static int encrypt(int e,int n,int num)
	{
		int enc;
		enc=(int)(Math.pow(num,e))%n;
		return enc;
	}

	public static void decrypt(int d,int n,int en)
	{
		BigInteger dec=new BigInteger("1");
		BigInteger t1=new BigInteger("1");
		t1=BigInteger.valueOf(en);
		BigInteger t2=new BigInteger("1");
		t2=BigInteger.valueOf(n);
        BigInteger t3=t1.pow(d);
        dec=t3.mod(t2);
        System.out.println("Decryption : " + dec);
	}

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		int p,q;
		System.out.println("Enter two prime numbers : ");
		p=scanner.nextInt();
		q=scanner.nextInt();
		int n=p*q;
		int phi=(p-1)*(q-1);
		int e=0;
		for(int i=2;i<phi;i++)
		{
			if (gcd(i,phi)==1)
			{	
				e=i;
				break;
			}
		}
		int d=(2*phi+1)/e;
		int num;
		System.out.println("Enter a number : ");
		num=scanner.nextInt();
		int en=encrypt(e,n,num);
		System.out.println("Encryption : " + en);
		decrypt(d,n,en);
	}
} 
