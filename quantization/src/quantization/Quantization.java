
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
        else{
                System.out.println("Not the right kind of file.");
        }
        int [][] temp = Encoder.quantizer(data, width, height, maxValue, 12);

        FileWriter outFile = new FileWriter("out_baboon.pgma");
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
            writer.append("\n");
        }
    }
    
}
