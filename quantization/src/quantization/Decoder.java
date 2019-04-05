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
public class Decoder {
    
    public static int[][] dequantizer(int arr[][],int h, int w, int max, int newMax){
        float interval = newMax / max;
        int[][] returnArr = new int[h][w];
        
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
        for(i=0, j=0; j<max ;i = i+interval, j++){
            if(currFl == j){
                returnInt = (int)(i+(i+interval)/max);
                break;
            }
        }
        return returnInt;
    }
    
}
