package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class FileReader {
    public static int[][] getMatrix(String filePath) throws IOException {
        File f1 = new File(filePath);
        BufferedReader input =  new BufferedReader(new java.io.FileReader(f1));

        String DIMENSION = input.readLine();
        int NUMBER_CITIES = Integer.parseInt(DIMENSION.split(" ")[1]);
        System.out.println("NUMBER_CITIES: " + NUMBER_CITIES);

        int[][] array = new int[NUMBER_CITIES][NUMBER_CITIES];

        String line = null;
        int i=0;
        int j=0;
        while (( line = input.readLine()) != null)
        {
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens())
            {
                array[i][j] = Integer.parseInt(st.nextToken());
                j=j+1;
                if(j==NUMBER_CITIES){
                    i++;
                    j=0;
                }
            }
        }

        return array;
    }
}
