package vignere;

import java.util.*;
class VIGNERE
{
static String encode(String text, final String key)
{
String res = "";
text = text.toLowerCase();
for (int i = 0, j = 0; i < text.length(); i++)
{
char c = text.charAt(i);
/*/*if (c < 'a' || c > 'z')
{
continue;
}*/
res += (char)((c + key.charAt(j) - 2 * 'a') % 26 + 'a');
j = ++j % key.length();
}
return res;
}
static String decode(String text, final String key)
{
String res = "";
text = text.toLowerCase();
for (int i = 0, j = 0; i < text.length(); i++) {
char c = text.charAt(i);
if (c < 'a' || c > 'z')
{
continue;
}
res += (char)((c - key.charAt(j) + 26) % 26 + 'a');
j = ++j % key.length();
}
return res;
}
public static void main (String[] args) 
{
String key = "variety";
String msg = "symmetrickeycryptography";
System.out.println("simulation of Vigenere Cipher");
System.out.println("input message : " + msg);
String enc = encode(msg, key);
System.out.println("encoded message : " + enc);
System.out.println("decoded message : " + decode(enc, key));
}
}
