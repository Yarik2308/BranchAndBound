package com.project;

import java.io.IOException;


public class Main {

	/*
	Main Function
	Main - Input - Pathname of Input data
	Return - Void - Prints results on screen
	*/

    public static void main(String[] args) throws IOException{

        //String file = "src/com/project/resources/testData";
        String file = "src/com/project/resources/ftv70";

        int[][] array = FileReader.getMatrix(file);


        for(int i=0; i<array.length; i++){
            for(int j=0; j<array.length; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("=========================");
        System.out.println();

        long startTime = System.nanoTime();
        BranchAndBound.main(array);
        long endTime = System.nanoTime();
        System.out.println("Execution time in nanoseconds  : " + (endTime - startTime));
    }
}
