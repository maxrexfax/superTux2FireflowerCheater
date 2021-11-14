/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package workClassesPackage;


import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.charset.StandardCharsets;
/**
 *
 * @author maxim
 */
public class TuxFireflowCheat {
    
    public final String ICE = "IceFlower";
    public final String FIRE = "FireFlower";
    private String choice;
    private String OS;
    private int quantity;
    private String bonus;
    private int offset;
    
    public TuxFireflowCheat(String choice, String OS, int quantity) {
        this.choice = choice;
        this.OS = OS;
        this.quantity = quantity;
        if (this.choice.equals(FIRE)){
            this.bonus = "fireflower";
        } else if(this.choice.equals(ICE)){
            this.bonus = "iceflower";
        }
    }
    
    public void startWork() throws Throwable {
        findPathToSettings();
    }        
    
    public void findPathToSettings() throws Throwable {
        System.out.println("Quantity inside class=" + String.valueOf(quantity));
        //get path to jar executable
        String pathToExec = new File(TuxFireflowCheat.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        int secondSlash = pathToExec.indexOf("/", pathToExec.indexOf("home/") + 5);//slash after username in path
        String pathPlusNameOfUser = pathToExec.substring(0, secondSlash + 1);//->/home/username/
        work(pathPlusNameOfUser) ;
    }
	  
    public void work(String pathPlusNameOfUser) {
        //Creating a File object for directory	
            String settingsFolder = ".local/share/supertux2/profile1/";
            String pathToDir = pathPlusNameOfUser + settingsFolder;//path to settings for current user
        File directoryPath = new File(pathToDir);	      
        File filesList[] = directoryPath.listFiles();
        System.out.println("List of files and directories in the specified directory:");
        System.out.println(pathToDir);
        for(File file : filesList) {
            System.out.println("File with name: " + file.getName() + " checked...");	         
            String filePath = file.getAbsolutePath();	   
            StringBuilder strToCorrect = new StringBuilder(readAllBytesJava7( filePath ));
            int offsetForBonus = 12;
            int resOfCheck = strToCorrect.indexOf("bonus \"none\"");
            if(resOfCheck == -1) {
                resOfCheck = strToCorrect.indexOf("bonus \"growup\"");
                offsetForBonus = 14;
            }            
            int resOfCheckQuantity;
            if(resOfCheck != -1) {
                System.out.println("This file " + file.getName() + " should be corrected  " + resOfCheck);
                strToCorrect.replace(resOfCheck, resOfCheck + offsetForBonus, "bonus \"" + bonus + "\"");
                
                if(this.choice.equals(FIRE)){
                    resOfCheckQuantity = strToCorrect.indexOf("fireflowers");
                    strToCorrect.replace((resOfCheckQuantity + 12), (resOfCheckQuantity + 13), String.valueOf(quantity));
                } else {
                    resOfCheckQuantity = strToCorrect.indexOf("iceflowers");
                    strToCorrect.replace((resOfCheckQuantity + 11), (resOfCheckQuantity + 12), String.valueOf(quantity));
                }
                
                
                writeStringToFile(file.getAbsolutePath(), strToCorrect.toString());
            }
        }
    }  
	  
    private String readAllBytesJava7(String filePath) 
    {
        String content = "";

        try {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        } catch (IOException e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return content;
    }
	  
    private void writeStringToFile(String filePath, String content) 
    {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully edit the file with path " + filePath);
        } 
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
