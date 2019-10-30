import java.util.Scanner;
import java.math.BigInteger;

class DHell1
{
	public static void computeDHell(BigInteger p,BigInteger g,BigInteger Xa,BigInteger Xb)
	{
		BigInteger Ya,Yb,Ka,Kb;
		Ya=g.modPow(Xa,p);
		Yb=g.modPow(Xb,p);
		System.out.println("PRIVATE KEY : ");
		System.out.print("A : "+Xa.toString()+"   B : "+Xb.toString()+"\n\n");
		System.out.println("PUBLIC KEY : ");
		System.out.print("A : "+Ya.toString()+"   B : "+Yb.toString()+"\n\n");
		Ka=Yb.modPow(Xa,p);
		Kb=Ya.modPow(Xb,p);
		System.out.println("SHARED KEY : ");
		System.out.print("Sender : "+Ka.toString()+"   Reciever : "+Kb.toString());
	}

	public static void main(String[] args)
	{
		BigInteger p,g,Xa,Xb,Ya,Yb;
		p=new BigInteger("11113");
		g=new BigInteger("3");
		Xa=new BigInteger("97");
		Xb=new BigInteger("233");
		System.out.println("P : "+p.toString());
		System.out.println("g : "+g.toString()+"\n");
		computeDHell(p,g,Xa,Xb);
	}
}