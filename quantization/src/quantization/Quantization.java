
package quantization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Quantization {

    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
			
        File file = new File("baboon.pgma");

        Scanner scan = new Scanner(file);
        String imgInfo = null;
        int width=0;
        int height=0;
        int maxValue=0;
        int data[][] = null;
        int original[][] = null;
        if(scan.nextLine().equals("P2")){
            if(scan.next().equals("#"))
                imgInfo = "#" + scan.nextLine();
                width = Integer.parseInt(scan.next());
                height = Integer.parseInt(scan.next());
                maxValue = Integer.parseInt(scan.next());
                data = new int[width][height];

            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                        data[i][j] = Integer.parseInt(scan.next());
                }
            }
            original = new int[height][width];
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    original[i][j] = data[i][j];
                }
            }
             
        }
        else{
                System.out.println("Not the right kind of file.");
        }
        
        //*****************************************************************
        //Encode Image
        //*****************************************************************
        int [][] temp = Encoder.quantizer(data, width, height, maxValue, 12);
 
        FileWriter outFile = new FileWriter("ENC_baboon"+ "_12" + ".pgma");
        BufferedWriter writer = new BufferedWriter(outFile);

        writer.write("P2\n");
        writer.write(imgInfo+"\n");
        writer.append(String.valueOf(width) + " "); 
        writer.append(String.valueOf(height)+"\n");
        writer.append(String.valueOf(12)+"\n");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                //System.out.print(temp[i][j] + " " + j + " ");
                writer.append(String.valueOf(temp[i][j])+ " ");
            }
        }
        writer.close();
        temp = Encoder.quantizer(data, width, height, maxValue, 2);
        outFile = new FileWriter("ENC_baboon"+ "_2" + ".pgma");
        writer = new BufferedWriter(outFile);

        writer.write("P2\n");
        writer.write(imgInfo+"\n");
        writer.append(String.valueOf(width) + " "); 
        writer.append(String.valueOf(height)+"\n");
        writer.append(String.valueOf(2)+"\n");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                //System.out.print(temp[i][j] + " ");
                writer.append(String.valueOf(temp[i][j])+ " ");
            }
            //System.out.println(); 
        }
        writer.close();
        
        
        //*****************************************************************
        file = new File("ENC_baboon"+ "_12" + ".pgma");
        scan = new Scanner(file);
        if(scan.nextLine().equals("P2")){
            if(scan.next().equals("#"))
                imgInfo = "#" + scan.nextLine();
                width = Integer.parseInt(scan.next());
                height = Integer.parseInt(scan.next());
                maxValue = Integer.parseInt(scan.next());
                data = new int[width][height];

            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                        data[i][j] = Integer.parseInt(scan.next());
                }
            }
        }
        
        int [][] recon12 = Decoder.dequantizer(data, width, height, 12, 256);
        outFile = new FileWriter("DEC_baboon"+ "_12" + ".pgma");
        writer = new BufferedWriter(outFile);

        writer.write("P2\n");
        writer.write(imgInfo+"\n");
        writer.append(String.valueOf(width) + " "); 
        writer.append(String.valueOf(height)+"\n");
        writer.append(String.valueOf(256)+"\n");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                //System.out.print(temp[i][j] + " ");
                writer.append(String.valueOf(recon12[i][j])+ " ");
            }
            //System.out.println(); 
        }
        writer.close();
        
        file = new File("ENC_baboon"+ "_2" + ".pgma");
        scan = new Scanner(file);
        if(scan.nextLine().equals("P2")){
            if(scan.next().equals("#"))
                imgInfo = "#" + scan.nextLine();
                width = Integer.parseInt(scan.next());
                height = Integer.parseInt(scan.next());
                maxValue = Integer.parseInt(scan.next());
                data = new int[width][height];

            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                        data[i][j] = Integer.parseInt(scan.next());
                }
            }
        }
        
        int[][] recon2 = Decoder.dequantizer(data, width, height, 2, 256);
        outFile = new FileWriter("DEC_baboon"+ "_2" + ".pgma");
        writer = new BufferedWriter(outFile);

        writer.write("P2\n");
        writer.write(imgInfo+"\n");
        writer.append(String.valueOf(width) + " "); 
        writer.append(String.valueOf(height)+"\n");
        writer.append(String.valueOf(256)+"\n");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                //System.out.print(temp[i][j] + " ");
                writer.append(String.valueOf(recon2[i][j])+ " ");
            }
            //System.out.println(); 
        }
        writer.close();
        
        //*****************************************************************
        //Display Distortion
        //*****************************************************************
        System.out.println("Orginal vs Reconstruction of gray value 12:" 
                + findDistortion(original,recon12,height,width));
        System.out.println("Orginal vs Reconstruction of gray value 2:" 
                + findDistortion(original,recon2,height,width));
        
        //*****************************************************************
        //Create Error Images
        //*****************************************************************
        int [][] err12 = createErrorImage(original,recon12,height,width);
        int [][] err2 = createErrorImage(original,recon2,height,width);
        
        outFile = new FileWriter("ERR_baboon"+ "_12" + ".pgma");
        writer = new BufferedWriter(outFile);

        writer.write("P2\n");
        writer.write(imgInfo+"\n");
        writer.append(String.valueOf(width) + " "); 
        writer.append(String.valueOf(height)+"\n");
        writer.append(String.valueOf(findMax(err12,height,width))+"\n");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                //System.out.print(temp[i][j] + " ");
                writer.append(String.valueOf(err12[i][j])+ " ");
            }
            //System.out.println(); 
        }
        writer.close();
        
        outFile = new FileWriter("ERR_baboon"+ "_2" + ".pgma");
        writer = new BufferedWriter(outFile);

        writer.write("P2\n");
        writer.write(imgInfo+"\n");
        writer.append(String.valueOf(width) + " "); 
        writer.append(String.valueOf(height)+"\n");
        writer.append(String.valueOf(findMax(err2,height,width))+"\n");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                //System.out.print(temp[i][j] + " ");
                writer.append(String.valueOf(err2[i][j])+ " ");
            }
            //System.out.println(); 
        }
        writer.close();
    }
    private static int findDistortion(int [][] original, int [][] constructed, int h,int w){
        int sumOfDif = 0;
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                sumOfDif += sumOfDif + Math.pow((original[i][j] - constructed[i][j]), 2);
            }
        }
        return sumOfDif/h*w;
    }
    private static int [][] createErrorImage(int [][] original, int [][] constructed, int h,int w){
        int [][] sendArr=new int [h][w];
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                sendArr[i][j] = Math.abs(original[i][j] - constructed[i][j]);
            }
        }
        return sendArr;
    }
    private static int findMax(int [][] a,int h,int w){
        int max = 0;
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                if(a[i][j]>max)
                    max=a[i][j];
            }
        }
        return max;
    }
}
