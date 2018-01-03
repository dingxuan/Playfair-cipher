import java.io.*;
import java.util.*;

import java.lang.Object;
import java.security.SecureRandom;
import java.math.BigInteger;
public class Playfair {
	public static String ptextName;
	public static String ctextName;
	public static String typee;
	public static  String keyFile  = new String();
	public static  String key  = new String();
	public static  String keyword  = new String();
	public static String ptext = new String();
	public static String ctext = new String();
	public static String dtext = new String();
	public static ArrayList<Integer> space = new ArrayList<Integer>(); 
	public static ArrayList<Integer> space2 = new ArrayList<Integer>(); 
	public static char box[][] = new char[5][5];
	
	Playfair()
	{
		
	}
	
	
	public static void main(String[] args)
	{
		Playfair p = new Playfair();	
		keyFile = "keyfile.txt";
		keyword = p.readFile(keyFile);
		p.genKey();
		p.makeBox();
		typee = args[0];
		keyFile = args[1];
		
			
		keyword = p.readFile(keyFile);
		p.genKey();
		p.makeBox();
		if(typee.equals("-e"))
		{
			ptextName = args[2];
			ctextName = args[3];
			ptext = p.readFile(ptextName);
			ptext = formatPtext(ptext);		
			ptext.trim();
			p.encryptIT();
			//ctext.replaceAll("\\s+","");
			//ctext.replaceAll("\n+","");
			ctext = spaceCtext(ctext);
			System.out.println();
			System.out.println("Cipher text is:");
			System.out.println(ctext);
			System.out.println();
			p.writeToFile(ctextName, ctext);
			
		}
		if(typee.equals("-d"))
		{
			ptextName = args[3];
			ctextName = args[2];
			ctext = p.readFile(ctextName);
			p.decryptIT();
			dtext = reformatPtext(dtext);
			System.out.println();
			System.out.println("Plain text is:");
			System.out.println(dtext);
			p.writeToFile(ptextName, dtext);
			
		}
		
	}
	static String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}

			return sb.toString();
		} catch (IOException e) {
			System.out.println("File not found");
		}
		String x = sb.toString();
		x.trim();
		return x;
	}
	public void printBox()
	{
	 int counter = 0;
     for (int i = 0; i < 5; i++)
     {
         for (int j = 0; j < 5; j++)
         {
             box[i][j] = key.charAt(counter);
             System.out.print(box[i][j] + " ");
             counter++;
         }
         System.out.println();
     }
	}
	
	
	
	private void writeToFile(String fName, String textData) {

        try {
            File f = new File(fName);
            BufferedWriter b = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
            b.write(textData);
            b.close();
        } catch (IOException e) {
            System.out.println(fName + " is not found!");
        }
    }
	
	// make keyword + key, make it 26 letter
	public void  genKey()
	{
        boolean check = true;
        char current;
        char[] chars = keyword.toCharArray();
        Set<Character> charSet = new LinkedHashSet<Character>();
        for (char c : chars) {
            charSet.add(c);
        }

        StringBuilder sb = new StringBuilder();
        for (Character character : charSet) {
            sb.append(character);
        }
       keyword =sb.toString();
        key = keyword;
        for (int i = 0; i < 26; i++)
        {
            current = (char) (i + 97);
            if (current == 'j')
                continue;
            for (int j = 0; j < keyword.length(); j++)
            {
                if (current == keyword.charAt(j))
                {
                    check = false;
                    break;
                }
            }
            if (check)
                key = key + current;
            
            check = true;
        }
	}

	//generate random keyword
	public void genKeyword()
	{
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		
		Random random = new Random();
		int i = 0;
		while(i<6)
		{
		    char c = chars[random.nextInt(chars.length)];  
		    for(int x = 0; x <sb.length();x++)
		    {
		    	 
		        if (sb.charAt(x) == c)
		        {
		             flag = true;
		        }
		     }
		    if (flag == false)
		    {
		    	sb.append(c);
		    	i++;
		    }
		    flag = false;
		}
		String output = sb.toString();
		keyword = output;
	}
	
	// make the 5/5 box to store the key
	public void makeBox()
	{
	    int counter = 0;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                box[i][j] = key.charAt(counter);
               // System.out.print(matrix_arr[i][j] + " ");
                counter++;
            }
        }
	}
	
	public void decryptIT()
	{
		String tmp = ctext;
       if((tmp.length() %2 == 0) && (tmp.charAt(tmp.length()-1) =='x'))
       {
    	   
       }
		for (int i = 0; i < tmp.length(); i++)
		{
		if ( (i+1)<tmp.length() && tmp.charAt(i+1) == ' '){
            space2.add(i+1+space2.size());
            tmp = tmp.substring(0,i+1) + tmp.substring(i+2);
		}
	
		}
		 String decrypt = new String();
		  for (int i = 0; i < tmp.length(); i += 2) {
	            char a = tmp.charAt(i);
	            char b = tmp.charAt(i + 1);
	            int[] a_position = getPosition(a);
	            int[] b_position = getPosition(b);
	            if(b_position[1] == a_position[1] )
	            {
	            	if(b_position[0]>0)
	            		b_position[0]--;
	            	else
	            		b_position[0]=4;
	            	if(a_position[0]>0)
	            		a_position[0]--;
	            	else
	            		a_position[0]=4;
	            }
	            else if (b_position[0] == a_position[0] )
	            {
	            	if(b_position[1]>0)
	            		b_position[1]--;
	            	else
	            		b_position[1]=4;
	            	
	            	if(a_position[1]>0)
	            		a_position[1]--;
	            	else
	            		a_position[1]=4;
	            }
	            //anti rotation
	            else if (a_position[0] > b_position[0] && a_position[1] < b_position[1]){
	                int temp = a_position[0];
	                a_position[0] = b_position[0];
	                b_position[0] = temp;
	            } 
	            else if (a_position[0] < b_position[0] && a_position[1] > b_position[1]){
	                int temp = a_position[0];
	                a_position[0] = b_position[0];
	                b_position[0] = temp;
	            } 
	            else if (a_position[0] < b_position[0] && a_position[1] < b_position[1]){
	                int temp = a_position[1];
	                a_position[1] = b_position[1];
	                b_position[1] = temp;
	            }
	            else if (a_position[0] > b_position[0] && a_position[1] > b_position[1]){
	                int temp = a_position[1];
	                a_position[1] = b_position[1];
	                b_position[1] = temp;
	            }
	            decrypt = decrypt + box[a_position[0]][a_position[1]]
	                    + box[b_position[0]][b_position[1]];
		  }
		dtext= decrypt;	
		
	}
	
	public void encryptIT()
	{
		  String encrypt = new String();
		  for (int i = 0; i < ptext.length(); i += 2) {
	            char a = ptext.charAt(i);
	            char b = ptext.charAt(i + 1);
	            int[] a_position = getPosition(a);
	         
	            int[] b_position = getPosition(b);
	           
	            if(b_position[1] == a_position[1] )
	            {
	            	if(b_position[0]<4)
	            		b_position[0]++;
	            	else
	            		b_position[0]=0;
	            	if(a_position[0]<4)
	            		a_position[0]++;
	            	else
	            		a_position[0]=0;
	            }
	            else if (b_position[0] == a_position[0] )
	            {
	            	if(b_position[1]<4)
	            		b_position[1]++;
	            	else
	            		b_position[1]=0;
	            	
	            	if(a_position[1]<4)
	            		a_position[1]++;
	            	else
	            		a_position[1]=0;
	            }
	            //anti rotation
	            else if (a_position[0] > b_position[0] && a_position[1] < b_position[1]){
	                int temp = a_position[1];
	                a_position[1] = b_position[1];
	                b_position[1] = temp;
	            } 
	            else if (a_position[0] < b_position[0] && a_position[1] > b_position[1]){
	                int temp = a_position[1];
	                a_position[1] = b_position[1];
	                b_position[1] = temp;
	            } 
	            else if (a_position[0] < b_position[0] && a_position[1] < b_position[1]){
	                int temp = a_position[0];
	                a_position[0] = b_position[0];
	                b_position[0] = temp;
	            }
	            else if (a_position[0] > b_position[0] && a_position[1] > b_position[1]){
	                int temp = a_position[0];
	                a_position[0] = b_position[0];
	                b_position[0] = temp;
	            }
	            encrypt = encrypt + box[a_position[0]][a_position[1]]
	                    + box[b_position[0]][b_position[1]];
		  }
		ctext= encrypt;
	}
	
	//get the position of the char in the box
	public static int[] getPosition (char a)
	{
	      int[] k = new int[2];
	        for (int i = 0; i < 5; i++)
	        {
	            for (int j = 0; j < 5; j++)
	            {
	                if (box[i][j] == a)
	                {
	                    k[0] = i;
	                    k[1] = j;
	                    break;
	                }
	            }
	        }
	        return k;
	}
	
	public static String spaceCtext (String ctext)
	{
		StringBuilder sb = new StringBuilder(ctext);
			for (int x = 0; x < space.size(); x++ )
			{
				if(space.get(x)<=sb.length())
				{
				String temp = sb.charAt(space.get(x)-1) + " ";
				sb.replace(space.get(x)-1,space.get(x), temp);
				}
			}
		
		return sb.toString();
	}

	public static String formatPtext(String ptext)
	{
		String temp = new String();
		for(int i = 0; i < ptext.length(); i++)
		{
			if (ptext.charAt(i) == 'j')
            {
				// i use double ii , easy to change back
                temp = temp + 'i' + 'x' + 'x';
            }
			else
				temp = temp + ptext.charAt(i);
		}
		
		// check for duplicate, i will check x inbetween n remove it, xxxx 
		for (int i = 0; i < temp.length(); i++)
        {
			// check this in it.
			if(i%2== 1)
			{
	            if (temp.charAt(i) == temp.charAt(i-1))
	            {
	                temp = temp.substring(0, i) + 'x' + temp.substring(i);
	            }
			}
			if ( (i+1)<temp.length() && temp.charAt(i+1) == ' '){
                space.add(i+1+space.size());
                temp = temp.substring(0,i+1) + temp.substring(i+2);
            }
        }
		if(temp.length()%2==1)
		{
			temp = temp + "x";
		}
		return temp;	
	}
	
	public static String reformatPtext(String ptext)
	{
		
		StringBuilder sb = new StringBuilder(ptext);
	    if((sb.length() %2 == 0) && (sb.charAt(sb.length()-1) =='x'))
	    {
	    	   sb.deleteCharAt(sb.length()-1);
	    }
		for (int x = 0; x < space2.size(); x++ )
		{
			String temp = sb.charAt(space2.get(x)-1) + " ";
			sb.replace(space2.get(x)-1,space2.get(x), temp);
		}
		for(int i = 0; i < sb.length(); i++)
		{
			if((i+2)<sb.length())
			{
				if (sb.charAt(i) == sb.charAt(i+2) && sb.charAt(i+1) == 'x' )
				{
					 sb.deleteCharAt(i+1);
				}
			}
		}
		for(int i = 0; i < sb.length(); i++)
		{
			if((i+2)<sb.length())
			{
				
				if (sb.charAt(i) == 'i' && sb.charAt(i+1) == 'x' && sb.charAt(i+2) == 'x' )
	            {
					// i use double ii , easy to change back
					String tmp = "j";
	                sb.replace(i,i+1,tmp);
	                sb.deleteCharAt(i+1);
	                sb.deleteCharAt(i+1);
	            }
			
			}
		}
		
		return sb.toString();
	}
	
	public String getPtextName() {
		return ptextName;
	}
	
	public void setPtextName(String ptextName) {
		this.ptextName = ptextName;
	}
	
	public String getCtextName() {
		return ctextName;
	}
	
	public void setCtextName(String ctextName) {
		this.ctextName = ctextName;
	}
}
