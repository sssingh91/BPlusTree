import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class treesearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		bplustree b=new bplustree();				// creating object of class bplustree
		String f=args[0];
	
        try {           

            File file = new File(f);					//creating file object			
            Scanner input = new Scanner(file);			// Scanner object for input from file
            b.initialise(Integer.parseInt(input.nextLine()));		// intialising tree with degree/order
            while (input.hasNextLine()) {
                String line = input.nextLine();						//reading lines from file
                System.out.println(line);
                if(line.charAt(0)== 'S'){							// for Searching key 
                    String res="";       	
                	String[] st= line.split(",");
                	if(st.length > 1)								// for range search
                	{
                		int i=0; 
                    	while(st[0].charAt(i)!='(') i++;
                		String ss=st[0].substring(i+1);
                		double key1= Double.parseDouble(ss);
                		ss=st[1].substring(0,st[1].length()-1 );
                		double key2= Double.parseDouble(ss);
                		res+=b.search(key1, key2) + "\n";      		//result in string format
                	}
                	else{											//for searching single key
                		int i=0; 
                    	while(st[0].charAt(i)!='(') i++;
                		String ss=st[0].substring(i+1, st[0].length()-1);
                		double key= Double.parseDouble(ss);
                		res+=b.search(key)+"\n";					//result in string format
                		
                	}
                    try{    
                        File ofile =new File("output_file.txt");      //creating output file object
                        if(!ofile.exists()){    
                            ofile.createNewFile();      
                        }       
                            FileWriter fileWritter = new FileWriter(ofile,true);      // opening in append mode  
                            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                            bufferWritter.write(res);										// writing to file
                            bufferWritter.close();
                            fileWritter.close();
                    }catch(Exception e){    
                        e.printStackTrace();    
                    }
               }
                if(line.charAt(0)== 'I'){		// for inserting key and value 
                	int i=0; 
                	String[] st= line.split(",");
                	while(st[0].charAt(i)!='(') i++;
                	double key= Double.parseDouble(st[0].substring(i+1));
                	String val= st[1].substring(0,st[1].length()-1);
                	b.insert(key, val);              	
                }              
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }	
	}
}
