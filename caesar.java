import java.util.Scanner;

public class caesar
{
public static void main(String args[])
{
String encryptedMessage = "";
String decryptedMessage = "";

Scanner obj1=new Scanner(System.in);
System.out.println("Enter Message To Be Encrypted :");
String plainText = obj1.nextLine();
System.out.println("Enter Key :");
int key1 = obj1.nextInt();

for(int i=0;i<plainText.length();i++)
{
char ch = plainText.charAt(i);
if(ch >= 'a' && ch <= 'z')
{
ch = (char)(ch + key1);
           if(ch > 'z')
{
               ch = (char)(ch - 'z' + 'a' - 1);
}
encryptedMessage += ch;
}
else
{
encryptedMessage+=ch;
}
}
System.out.println("Encrypted Message Is : "+encryptedMessage);


Scanner obj2 = new Scanner(System.in);

for(int i=0 ; i < encryptedMessage.length() ; i++)
{
char ch = encryptedMessage.charAt(i);

if(ch >= 'a' && ch <= 'z')
{
ch = (char)(ch - key1);
if(ch < 'a')
{
ch = (char)(ch + 'z' - 'a' + 1);
}
decryptedMessage+=ch;
}
else
{
decryptedMessage+=ch;
}
}
System.out.println("Decrypted Message Is : "+decryptedMessage);
}
}