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

class SHAUtility
{
    HexOperations hexoperations;
    String bufferA, bufferB, bufferC, bufferD, bufferE;
    String bufferAA, bufferBB, bufferCC, bufferDD, bufferEE;
    String [] X = new String[80];
    String [] T = { "d76aa478", "e8c7b756", "242070db", "c1bdceee",
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
    				"f7537e82", "bd3af235", "2ad7d2bb", "eb86d391" };
    String K = "";
    
	public SHAUtility()
	{
    	intializeRegisters();
    	hexoperations = new HexOperations();
	}

	void intializeRegisters()
	{
    	bufferA = "67452301";
    	bufferB = "EFCDAB89";
    	bufferC = "98BADCFE";
    	bufferD = "10325476";
    	bufferE = "C3D2E1F0";

    	bufferAA = bufferA;
    	bufferBB = bufferB;
    	bufferCC = bufferC;
    	bufferDD = bufferD;
    	bufferEE = bufferE;
	}

	void print(String display,String obj)
	{
    	System.out.println(display + " : "+ obj);
    	System.out.println(display + " Length : " + obj.length() + " hexa chars");
    	System.out.println(display + " Length : " + obj.length()/2 + " bytes");
    	System.out.println("-----------------------------------------------------------------");
	}

	void printBuffers()
	{
    	System.out.print("A = " + bufferA + "  ");
    	System.out.print("B = " + bufferB + "  ");
    	System.out.print("C = " + bufferC + "  ");
    	System.out.print("D = " + bufferD + "  " );
    	System.out.println("E = " + bufferE + "  ");
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
		for(int step=0;step<80;step++)
		{
			int i=step;
			String ans=perform_round(bufferA,bufferB,bufferC,bufferD,bufferE,i,step);
			bufferE=bufferD;
			bufferD=bufferC;
			bufferC=hexoperations.circularRotateLeft(bufferB,30);
			bufferB=bufferA;
			bufferA=ans;
		}
		String h0 = hexoperations.add(bufferAA, bufferA);
    	String h1 = hexoperations.add(bufferBB, bufferB);
    	String h2 = hexoperations.add(bufferCC, bufferC);
    	String h3 = hexoperations.add(bufferDD, bufferD);
    	String h4 = hexoperations.add(bufferEE, bufferE);
    	System.out.println("H0 : "+h0 + " H1 : " + h1 + " H2 : "+h2 + " H3 : "+h3+" H4 : "+h4 );
    	String hh = h0+h1+h2+h3+h4;
    	System.out.println("\n\nFINAL SHA1 HASH : " + hh + "\n\n\n\n");
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
		String block2="00000000";
		return(block2+lengthPadInitial);
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
			String temp=input.substring(start,start+8);
			X[i]=this.flipMD5(temp);
		}
		for(int j=16;j<80;j++)
		{
			String t1=hexoperations.XOR(X[j-3],X[j-8]);
			String t2=hexoperations.XOR(X[j-14],X[j-16]);
			String t3=hexoperations.XOR(t1,t2);
			X[j]=hexoperations.circularRotateLeft(t3,1);
		}
	}

	String perform_round(String a,String b,String c,String d,String e,int i,int step)
	{
		String s1="";
		if(step>=0&&step<=19)// (b and c) or ((not)b and d)
		{
			K = "5A827999";
			String t1=hexoperations.AND(b,c);
			String t2=hexoperations.AND(hexoperations.NOT(b),d);
			s1=hexoperations.OR(t1,t2);
		}
		else if(step>=20&&step<=39)// b xor c xor d
		{
			K ="6ED9EBA1";
        	String t1 = hexoperations.XOR(b, c);
        	s1 = hexoperations.XOR(t1, d);
		}	
		else if(step>=40&&step<=59)// (b and c) or (b and d) or (c and d)
		{
			K ="8F1BBCDC";
        	String t1 = hexoperations.AND(b, c);
        	String t2 = hexoperations.AND(b, d);
        	String t3 = hexoperations.AND(c, d);
        	String t4 = hexoperations.OR(t1, t2);
        	s1 = hexoperations.OR(t4, t3);
		}	
		else// b xor c xor d
		{
			K ="CA62C1D6";
        	String t1 = hexoperations.XOR(b, c);
        	s1 = hexoperations.XOR(t1, d);
		}
		String s2=hexoperations.circularRotateLeft(a,5);
		String s3=hexoperations.add(s1,s2);
		String s4=hexoperations.add(s3,e);
		String s5=hexoperations.add(s4,K);
		String s6=hexoperations.add(s5,X[step]);
		return s6;
	}
}

class SHAtry
{
	public static void main(String[] args) 
	{
		SHAUtility sha=new SHAUtility();
		String mes="abc";
		System.out.println("Enter a message : "+mes);
		System.out.println("------------------------------------------------------------------");
		sha.encrypt(mes);	
	}	
}