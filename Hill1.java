import java.util.Scanner;

class Hill1
{
	public static int[][] stringmatrix(String s)
	{
		int sm[][]=new int[3][1];
		int l=s.length();
		int j=0;
		int[] val=new int[l];
		for(int i=0;i<l;i++)
		{
			val[j++]=(s.charAt(i)-97)%26;
		}
		j=0;
		for(int x=0;x<3;x++)
		{
			for(int y=0;y<1;y++)
			{
				sm[x][y]=val[j++];
			}
		}
		/*System.out.println("STRING MATRIX : ");
    	for(int x=0;x<3;x++)
    	{
    		for(int y=0;y<1;y++)
    		{
    			System.out.print(sm[x][y]+"  ");
    		}
    		System.out.println();
    	}*/
		return sm; 
	}

	public static int[][] multiplication(int[][] key,int[][] s)
	{
		int mul[][]=new int[3][1];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<1;j++)
			{
				mul[i][j]=0;
				for(int k=0;k<3;k++)
				{
					mul[i][j]=(mul[i][j]+key[i][k]*s[k][j])%26;
				}
			}
		}
		/*System.out.println("MULTIPLICATION : ");
    	for(int x=0;x<3;x++)
    	{
    		for(int y=0;y<1;y++)
    		{
    			System.out.print(mul[x][y]+"  ");
    		}
    		System.out.println();
    	}*/
		return mul;
	}

    public static String encrypt(int[][] m)
    {
    	String e="";
    	for(int i=0;i<3;i++)
    	{
    		for(int j=0;j<1;j++)
    		{
    			e+=(char)(m[i][j]+97);
    		}
    	}
    	return e;
    }

    public static int[][] inverse(int[][] k)
    {
		int det=((k[0][0]*(k[1][1]*k[2][2]-k[2][1]*k[1][2]))
			    -(k[0][1]*(k[1][0]*k[2][2]-k[2][0]*k[1][2]))
			    +(k[0][2]*(k[1][0]*k[2][1]-k[2][0]*k[1][1])))%26;
		if (det<0)
		{
			det=det+26;
		}    	
		int detInv;
		for(detInv=1;detInv<25;detInv+=2)
		{
			if((det*detInv)%26==1)
				break;
		}
		int inv[][]=new int[3][3];
		System.out.println("INVERSE : ");
		for(int i=0;i<3;++i)
		{
			for(int j=0;j<3;++j)
			{
				inv[i][j]=((((k[(j+1)%3][(i+1)%3]*k[(j+2)%3][(i+2)%3])-(k[(j+1)%3][(i+2)%3]*k[(j+2)%3][(i+1)%3]))*detInv)%26);
				if(inv[i][j]<0)
				{
					inv[i][j]+=26;
				}
			}
		}
		for(int x=0;x<3;x++)
        {
            for(int y=0;y<3;y++)
            {
                System.out.print(inv[x][y]+"  ");
            }
            System.out.println();
        }
        return inv;
    }

	public static void main(String[] args)
	{
		Scanner scanner=new Scanner(System.in);
		int km[][]=new int[3][3];
		String str;
		System.out.println("Enter a plain text : ");
		str=scanner.nextLine();
		System.out.println("Enter the key matrix : ");
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				km[i][j]=scanner.nextInt();
			}
		}
		System.out.println("KEY MATRIX : ");
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(km[i][j]+"  ");
			}
			System.out.println();
		}
		int l=str.length();
		int sm[][];
		int mul[][];
		String en="";
		for(int i=0;i<l-1;i=i+3)
		{
			sm=stringmatrix(str.substring(i,i+3));
			mul=multiplication(km,sm);
			en+=encrypt(mul);
		}
		System.out.println("ENCRYPTION : "+ en);
		int inv[][];
		inv=inverse(km);
		String de="";
        for(int i=0;i<l-1;i=i+3)
		{
			sm=stringmatrix(en.substring(i,i+3));
			mul=multiplication(inv,sm);
			de+=encrypt(mul);
		}
		System.out.println("DECRYPTION : "+ de.substring(0,l-1));
	}
}