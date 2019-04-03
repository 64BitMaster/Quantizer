/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantization;

/**
 *
 * @author cornb
 */
public class Encoder {
    
    public static int[][] quantizer(int arr[][],int h, int w, int max, int newMax){
        //Convert to Float
        
        float interval = max / newMax;
        int[][] returnArr = new int[h][w];
        /*
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println(i);
        }*/
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                float currFl = (float)arr[i][j];
                returnArr[i][j] = getNewVal(max,interval,currFl);
                //System.out.print(returnArr[i][j] + " ");
            }
            //System.out.println();
           // System.out.println(i);
        }
        return returnArr;
    }
    private static int getNewVal(int max, float interval, float currFl){
        int returnInt = 0;
        int j; 
        float i;
        for(i=0, j=0; i <=max-interval;i = i+interval, j++){
            if(currFl >= i && currFl <= i + interval){
                returnInt = j;
                break;
            }
        }
        return returnInt;
    }
    
}
