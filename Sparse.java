// Faisal Shaikh, Fshaikh1@ucsc.edu
// Student ID #: 1463626
// Due Date: 11/5/17
// PA3

import java.io.*;
import java.util.Scanner;

class Sparse
{
    public static void main(String[] args) throws IOException
    {
        if(args.length < 2)
        {
            System.err.println("Error");
            System.exit(1);
        }

        Scanner inFile = null;
        PrintWriter outFile = null;
        String line = null;
        String[] token = null;

        int firstEntry, SecondEntry, matrixSize = 0;
        int row, column = 0;
        double val = 0;

        inFile = new Scanner(new File(args[0]));
        outFile = new PrintWriter(new FileWriter(args[1]));
        Matrix A = new Matrix(matrixSize);
        Matrix B = new Matrix(matrixSize);

        while(inFile.hasNextLine())
        {
            line = inFile.nextLine() +" ";
        }
        inFile.close();
        outFile.close();
    }
}
