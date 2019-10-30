import java.util.Scanner;
import java.math.BigInteger;

class HexOperations
{
	String format(BigInteger bi)
	{
		String s=String.format("%08x",bi);
		if(s.length()>8)
		{
			s=s.substring(s.length()-8,s.length());
		}
		return s;
	}

	String circularRotateLeft(String s,int bits)
	{
		BigInteger b=new BigInteger(s,16);
		for(int i=0;i<bits;i++)
		{
			b=rotateLeftPrimitive(b);
		}
		return format(b);
	}

	private static BigInteger rotateLeftPrimitive(BigInteger bi)
	{
		BigInteger ret=bi.shiftLeft(1);
		if(ret.testBit(32))
		{
			ret=ret.clearBit(32).setBit(0);
		}
		return ret;
	}

	String add(String b1,String b2)
	{
		BigInteger a=new BigInteger(b1,16);
		BigInteger b=new BigInteger(b2,16);
		BigInteger ans=a.add(b);
		return format(ans);
	}

	String AND(String b1,String b2)
	{
		BigInteger ans=new BigInteger(b1,16).and(new BigInteger(b2,16));
		return format(ans);
	}

	String OR(String b1,String b2)
	{
		BigInteger ans=new BigInteger(b1,16).or(new BigInteger(b2,16));
		return format(ans);
	}

	String XOR(String b1,String b2)
	{
		BigInteger ans=new BigInteger(b1,16).xor(new BigInteger(b2,16));
		return format(ans);
	}

	String NOT(String b1)
	{
		BigInteger ans=new BigInteger("FFFFFFFF",16).subtract(new BigInteger(b1,16));
		return format(ans);
	}
}

class MD5Utility
{
	HexOperations hexoperations;
	String[] X=new String[16];
	String bufferA,bufferB,bufferC,bufferD;
	String bufferAA,bufferBB,bufferCC,bufferDD;
	String [] T= { "d76aa478", "e8c7b756", "242070db", "c1bdceee",         
					"f57c0faf", "4787c62a", "a8304613", "fd469501",         
					"698098d8", "8b44f7af", "ffff5bb1", "895cd7be",         
					"6b901122", "fd987193", "a679438e", "49b40821",         
					"f61e2562", "c040b340", "265e5a51", "e9b6c7aa",         
					"d62f105d", "02441453", "d8a1e681", "e7d3fbc8",         
					"21e1cde6", "c33707d6", "f4d50d87", "455a14ed",         
					"a9e3e905", "fcefa3f8", "676f02d9", "8d2a4c8a",         
					"fffa3942", "8771f681", "6d9d6122", "fde5380c",         
					"a4beea44", "4bdecfa9", "f6bb4b60", "bebfbc70",         
					"289b7ec6", "eaa127fa", "d4ef3085", "04881d05",        
				    "d9d4d039", "e6db99e5", "1fa27cf8", "c4ac5665",         
				    "f4292244", "432aff97", "ab9423a7", "fc93a039",         
				    "655b59c3", "8f0ccc92", "ffeff47d", "85845dd1",         
				    "6fa87e4f", "fe2ce6e0", "a3014314", "4e0811a1",         
				    "f7537e82", "bd3af235", "2ad7d2bb", "eb86d391" 
				 };
	int [] shift= { 7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,                      
					5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,                      
					4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,                      
					6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21 
				  };
	int [] k_block= {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,                       
					 1, 6, 11, 0, 5, 10, 15, 4, 9, 14, 3, 8, 13, 2, 7, 12,                       
					 5, 8, 11, 14, 1, 4, 7, 10, 13, 0, 3, 6, 9, 12, 15, 2,                       
					 0, 7, 14, 5, 12, 3, 10, 1, 8, 15, 6, 13, 4, 11, 2, 9
					}; 

	public MD5Utility()
	{
		hexoperations=new HexOperations();
		InitializeBuffers();
	}

	private void InitializeBuffers()
	{
		bufferA = "67452301";         
		bufferB = "EFCDAB89";         
		bufferC = "98BADCFE";         
		bufferD = "10325476";         
		bufferAA = bufferA;         
		bufferBB = bufferB;         
		bufferCC = bufferC;         
		bufferDD = bufferD;
	}

	void print(String display,String obj)
	{
		System.out.println(display+" : "+obj);
		System.out.println(display+" Length : "+obj.length()+" hexa chars");
		System.out.println(display+" Length : "+obj.length()/2+" bytes");
		System.out.println("------------------------------------------------------------------");
	}

	void printBuffers()
	{
		System.out.print("Buffer A : "+bufferA+" ,");
		System.out.print("Buffer B : "+bufferB+" ,");
		System.out.print("Buffer C : "+bufferC+" ,");
		System.out.println("Buffer D : "+bufferD);
	}

	void encrypt(String mes)
	{
		String hexmes=plainToHexa(mes);
		print("Message",hexmes);
		int mesLenBytes=hexmes.length()/2;
		String padOnezero=getOneZeroPad(mesLenBytes);
		print("Zero Pad",padOnezero);
		String padOnezeroLen=getOneZeroPadLen(mesLenBytes);
		print("Pad Length",padOnezeroLen);
		String block=flipMD5(hexmes+padOnezero+padOnezeroLen);
		print("Block",block);
		populateBlocks(block);
		printBuffers();
		System.out.println("------------------------------------------------------------------");
		for(int step=0;step<64;step++)
		{
			int k=k_block[step];
			int s=shift[step];
			int i=step;
			String ans=perform_round(bufferA,bufferB,bufferC,bufferD,k,s,i,step);
			bufferA=bufferD;
			bufferD=bufferC;
			bufferC=bufferB;
			bufferB=ans;
		}
		String res=hexoperations.add(bufferA,bufferAA)+hexoperations.add(bufferB,bufferBB)+hexoperations.add(bufferC,bufferCC)+hexoperations.add(bufferD,bufferDD);
		System.out.println("MD5 Hash : "+flipMD5(res));
	}

	String plainToHexa(String input)
	{
		String hex="";
		for(int i=0;i<input.length();i++)
		{
			int a=input.charAt(i);
			hex+=Integer.toHexString(a);
		}
		return hex;
	}

	String getOneZeroPad(int lenBytes)
	{
		int reqbytes=56-lenBytes;
		int reqpadBytes=reqbytes*2;
		String zeropad="8";
		for(int i=0;i<reqpadBytes-1;i++)
		{
			zeropad+="0";
		}
		return zeropad;
	}

	String getOneZeroPadLen(int lenBytes)
	{
		int r_bytes=lenBytes*8;
		String lengthPadInitial=DecToHexa(""+r_bytes);
		while(lengthPadInitial.length()<8)
		{
			lengthPadInitial="0"+lengthPadInitial;
		}
		String flippedString=flipMD5(lengthPadInitial);
		String block2="00000000";
		return(flippedString+block2);
	}

	String DecToHexa(String input)
	{
		String hex="";
		int a=Integer.parseInt(input);
		hex+=Integer.toHexString(a);
		return hex;
	}

	String flipMD5(String padlen)
	{
		String flippedMD5Output="";
		while(padlen.length()%8!=0)
		{
			padlen+="0";
		}
		for(int i=0;i+7<padlen.length();i+=8)
		{
			String input=padlen.substring(i,i+8);
			String flipmd5="";
			for(int j=0;j<input.length();j+=2)
			{
				flipmd5=input.substring(j,j+2)+flipmd5;
			}
			flippedMD5Output+=flipmd5;
		}
		return flippedMD5Output;
	}

	void populateBlocks(String input)
	{
		for(int i=0;i<16;i++)
		{
			int start=i*8;
			X[i]=input.substring(start,start+8);
			System.out.print("X["+i+"] : "+X[i]+" , ");
			if((i+1)%4==0)
			{
				System.out.println();
			}
		}
		System.out.println("------------------------------------------------------------------");
	}

	String perform_round(String a,String b,String c,String d,int k,int s,int i,int step)
	{
		String X_k=X[k];
		String T_i=T[i];
		String s1="";
		if(step<16)
			s1=hexoperations.add(operationF(b,c,d),a);
		else if(step>=16&&step<32)
			s1=hexoperations.add(operationG(b,c,d),a);
		else if(step>=32&&step<48)
			s1=hexoperations.add(operationH(b,c,d),a);
		else
			s1=hexoperations.add(operationI(b,c,d),a);
		String s2=hexoperations.add(s1,X_k);
		String s3=hexoperations.add(s2,T_i);
		String s4=hexoperations.circularRotateLeft(s3,s);
		String s5=hexoperations.add(s4,b);
		return s5;
	}

	String operationF(String x,String y,String z)
	{
		String t1=hexoperations.AND(x,y);
		String t2=hexoperations.AND(hexoperations.NOT(x),z);
		String t3=hexoperations.OR(t1,t2);
		return t3;
	}

	String operationG(String x,String y,String z)
	{
		String t1=hexoperations.AND(x,z);
		String t2=hexoperations.AND(y,hexoperations.NOT(z));
		String t3=hexoperations.OR(t1,t2);
		return t3;
	}

	String operationH(String x,String y,String z)
	{
		String t1=hexoperations.XOR(x,y);
		String t2=hexoperations.XOR(t1,z);
		return t2;
	}

	String operationI(String x,String y,String z)
	{
		String t1=hexoperations.OR(x,hexoperations.NOT(z));
		String t2=hexoperations.XOR(y,t1);
		return t2;
	}
}

public class MD5try
{
	public static void main(String[] args) 
	{
		MD5Utility md5=new MD5Utility();
		String mes="abcdefghijklmnopqrstuvwxyz";
		System.out.println("Enter a message : "+mes);
		System.out.println("------------------------------------------------------------------");
		md5.encrypt(mes);	
	}	
}