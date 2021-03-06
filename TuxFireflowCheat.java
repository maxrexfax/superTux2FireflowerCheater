package mainPack;

import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.charset.StandardCharsets;

public class TuxFireflowCheat {		
	  
	  public static void main(String args[]) throws IOException, Throwable {
		  findPathToSettings();
	   }
	  
	  public static void findPathToSettings() throws Throwable {
		  //get path to jar executable
		  String pathToExec = new File(TuxFireflowCheat.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
		  int secondSlash = pathToExec.indexOf("/", pathToExec.indexOf("home/")+5);//slash after username in path
		  String pathPlusNameOfUser = pathToExec.substring(0, secondSlash+1);//->/home/username/
		  work(pathPlusNameOfUser) ;
	  }
	  
	  public static void work(String pathPlusNameOfUser) {
	      //Creating a File object for directory	
		  String settingsFolder = ".local/share/supertux2/profile1/";
		  String pathToDir = pathPlusNameOfUser + settingsFolder;//path to settings for current user
	      File directoryPath = new File(pathToDir);	      
	      File filesList[] = directoryPath.listFiles();
	      System.out.println("List of files and directories in the specified directory:");
	      System.out.println(pathToDir);
	      for(File file : filesList) {
	         System.out.println("File with name: "+file.getName() + " checked...");	         
	         String filePath = file.getAbsolutePath();	   
             StringBuilder strToCorrect = new StringBuilder(readAllBytesJava7( filePath ));
             int resOfCheck = strToCorrect.indexOf("bonus \"none\"");
             int resOfCheckQuantity = strToCorrect.indexOf("fireflowers");
			 if(resOfCheck!=-1) {
             System.out.println("This file " + file.getName() + " should be corrected  " + resOfCheck + "    " + resOfCheckQuantity);
             strToCorrect.replace(resOfCheck, resOfCheck+12, "bonus \"fireflower\"");
             strToCorrect.replace((resOfCheckQuantity+18), (resOfCheckQuantity+19), "3");
                 writeStringToFile(file.getAbsolutePath(), strToCorrect.toString());
           }
	      }
		  
	  }
	  
	  
	  
	  private static String readAllBytesJava7(String filePath) 
	    {
	        String content = "";
	 
	        try
	        {
	            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }
	 
	        return content;
	    }
	  
	  private static void writeStringToFile(String filePath, String content) 
	  {
		  
		  try
	        {
			  FileWriter myWriter = new FileWriter(filePath);
			  myWriter.write(content);
			  myWriter.close();
			  System.out.println("Successfully edit the file with path " + filePath);
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }
	  }

}
