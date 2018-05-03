// Faisal Shaikh, Fshaikh1@ucsc.edu
// Student ID #: 1463626
// Due Date: 11/5/17
// PA3

public class Matrix
{
    // Matrix data
    private List[] rows;
    private int matrixSize;
    private int nnz;

    // Matrix Constructor
    Matrix(int n) // Makes a new n x n zero Matrix. pre: n>=1
    {
        nnz = 0;
        rows = new List[n+1];
        matrixSize = n;
        for (int i = 0; i <= n; i++)
        {
            rows[i] = new List();
        }
    }

    private class Entry
    {
        // fields
        int column;
        double val;

        // constructor
        Entry(int column, double val)
        {
            this.column = column;
            this.val = val;
        }

        // Overwritten ToString Function
        public String toString()
        {
            return ("(" + column + ", " + val + ")");
        }

        // Overwritten Equals function
        public boolean equals (Object entry)
        {
            Entry castedEntry = (Entry) entry;
            if (this.val != castedEntry.val)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    // Access functions
    int getSize() // Returns n, the number of rows and columns of this Matrix
    {
        return this.matrixSize;
    }

    int getNNZ() // Returns the number of non-zero entries in this Matrix
    {
        return this.nnz;
    }

    public boolean equals(Object x) // overrides Object's equals() method
    {
        Matrix castedMatrix = (Matrix) x;
        if (castedMatrix.nnz != this.nnz)
        {
            return false;
        }
        else if (castedMatrix.matrixSize != this.matrixSize)
        {
            return false;
        }
        else
            for (int i = 0; i <= matrixSize; i++) // loop through and check each element.
            {
                if ((castedMatrix.rows[i].equals(this.rows[i])))
                {
                    return true;
                }
            }
        return false;
    }

    // Manipulation procedures
    void makeZero() // sets this Matrix to the zero state
    {
        for (int i = 0; i <= matrixSize; i++ )
        {
            rows[i].clear();
        }
        this.nnz = 0;
    }

    Matrix copy()// returns a new Matrix having the same entries as this Matrix
    {
        double val;
        int column;
        Matrix copiedMatrix = new Matrix(matrixSize);
        for (int i = 0; i <= matrixSize; i++)
        {
            rows[i].moveFront();
            while(rows[i].index() != -1)
            {
               val = ((Entry)rows[i].get()).val;
               column = ((Entry)rows[i].get()).column;

               copiedMatrix.rows[i].append(new Entry(column, val));
               copiedMatrix.nnz++;
               rows[i].moveNext();
            }
        }
        return copiedMatrix;
    }

    void changeEntry(int i, int j, double x)
    // changes ith row, jth column of this Matrix to x
    // pre: 1<=i<=getSize(), 1<=j<=getSize()
    {
        Entry newEntry;
        if (rows[i].length() == 0 && x != 0) // case when empty list and input value is not zero.
        {
            rows[i].append(new Entry(j, x));
            nnz++;
        }
        else
        {
            rows[i].moveFront();
            if (x == 0) // case when the input value is 0.
            {
                while (rows[i].index() != -1)
                {
                    newEntry = (Entry) rows[i].get();
                    if (newEntry.column < j)
                    {
                        rows[i].moveNext();
                    }
                    else if (newEntry.column == j)
                    {
                        rows[i].delete();
                        nnz--;
                        return;
                    }
                }
            }
            else if (x != 0) // case when the input value is not zero.
            {
                while (rows[i].index() != -1)
                {
                    newEntry = (Entry) rows[i].get();
                    if (newEntry.column < j)
                    {
                        rows[i].moveNext();
                    }
                    else if (newEntry.column > j)
                    {
                        rows[i].insertBefore(new Entry(j, x));
                        nnz++;
                        break;
                    }
                    else if (newEntry.column == j)
                    {
                        rows[i].insertBefore(new Entry(j, x)); // insertBefore then delete next element. nnz++/-- not necessary.
                        rows[i].delete();
                        return;
                    }
                }
                if (rows[i].index() == -1)
                {
                    rows[i].append(new Entry(j, x));
                    nnz++;
                }
            }
        }
    }

    Matrix scalarMult(double x)
    // returns a new Matrix that is the scalar product of this Matrix with x
    {
        Matrix scalarMatrix = new Matrix(matrixSize);
        for(int i = 0; i <= matrixSize; i++)
        {
           rows[i].moveFront();
           while(rows[i].index() != -1)
           {
               scalarMatrix.changeEntry(i,((Entry)rows[i].get()).column, x * ((Entry)rows[i].get()).val);
               rows[i].moveNext();
           }
        }
        scalarMatrix.nnz = this.nnz;  // ensure the scalarMatrix has the same number of NNz's.
        return scalarMatrix;
    }

    Matrix add(Matrix M)
    // returns a new Matrix that is the sum of this Matrix with M
    // pre: getSize()==M.getSize()
    {
        Matrix addMatrix = new Matrix(matrixSize);
        if(this.equals(M)) // If adding with itself we can scalar multiply by two.
        {
            addMatrix = this.scalarMult(2);
            return addMatrix;
        }
        if (this.getSize() == M.getSize()) // only execute when the sizes of both Matrices are the same.
        {
            for(int i=0; i<this.getSize(); i++)
            {
                for (int a = 0; a < rows[i].length(); a++)
                {
                    if (this.rows[i].length() == 0 && M.rows[i].length() == 0) //when both are empty.
                    {
                        continue;
                    }
                    else if (this.rows[i].length() > 0 && M.rows[i].length() == 0)
                    {
                        this.rows[i].moveFront();
                        while (this.rows[i].index() != -1)
                        {
                            Entry addEntry = (Entry) this.rows[i].get();
                            addMatrix.rows[i].append(addEntry);
                            addMatrix.nnz++;
                            this.rows[i].moveNext();
                        }
                    }
                    else if (this.rows[i].length() == 0 && M.rows[i].length() > 0)
                    {
                        M.rows[i].moveFront();
                        while (M.rows[i].index() != -1)
                        {
                            Entry addEntry = (Entry) M.rows[i].get();
                            addMatrix.rows[i].append(addEntry);
                            addMatrix.nnz++;
                            M.rows[i].moveNext();
                        }
                    }
                    else if (this.rows[i].length() > 0 && M.rows[i].length() > 0)
                    {
                        this.rows[i].moveFront();
                        M.rows[i].moveFront();

                        Entry addEntry1 = (Entry) this.rows[i].get();
                        Entry addEntry2 = (Entry) M.rows[i].get();
                        while (this.rows[i].index() != -1 && M.rows[i].index() != -1)
                        {
                            if (addEntry1.column < addEntry2.column)
                            {
                                Entry addEntry3 = new Entry(addEntry1.column, addEntry1.val);
                                addMatrix.rows[i].append(addEntry3);
                                addMatrix.nnz++;
                                this.rows[i].moveNext();
                            }
                            else if (addEntry1.column > addEntry2.column)
                            {
                                Entry addEntry3 = new Entry(addEntry2.column, addEntry2.val);
                                addMatrix.rows[i].append(addEntry3);
                                addMatrix.nnz++;
                                M.rows[i].moveNext();
                            }
                            else if (addEntry1.column == addEntry2.column) // only add the two when columns align.
                            {
                                double tempVal = addEntry1.val + addEntry2.val;
                                Entry addEntry3 = new Entry(addEntry1.column, tempVal);
                                if (addEntry3.val != 0) // Adding a + and - can result in a negative, if so do nothing.
                                {
                                    addMatrix.rows[i].append(addEntry3);
                                    addMatrix.nnz++;
                                }
                                this.rows[i].moveNext();
                                M.rows[i].moveNext();
                            }
                        }
                    }
                }
            }
        }
        return addMatrix;
    }

    Matrix sub(Matrix M)
    // returns a new Matrix that is the difference of this Matrix with M
    // pre: getSize()==M.getSize()
    {
        if (this.getSize() == M.getSize())
        {
            Matrix subvalue = M.scalarMult(-1); // multiplying by -1 will result in additive inverse.
            Matrix subMatrix = this.add(subvalue);
            return subMatrix;
        }
        else
            return this;
    }

    Matrix transpose()
    // returns a new Matrix that is the transpose of this Matrix
    {
        if (this.getSize() == 0)
        {
            return this;
        }
        else
        {
            Matrix transposedMatrix = new Matrix(matrixSize);
            for (int i = 0; i <= matrixSize; i++)
            {
                rows[i].moveFront();
                while (rows[i].index() != -1)
                {
                    Entry tempMatrix = (Entry) rows[i].get();
                    tempMatrix = new Entry(tempMatrix.column, tempMatrix.val);
                    transposedMatrix.changeEntry(tempMatrix.column, i, tempMatrix.val);
                    rows[i].moveNext();
                }
            }
            return transposedMatrix;
        }
    }

    Matrix mult(Matrix M)
    // returns a new Matrix that is the product of this Matrix with M
    // pre: getSize()==M.getSize()
    {
        if (getSize() == M.getSize()) // only multiply when both matrices are of the same length.
        {
            Matrix currentM = new Matrix(matrixSize);
            Matrix transposedM = M.transpose();

            for (int i = 0; i < rows.length; i++)
            {
				for (int a = 0; a < rows[i].length(); a++) {
					if (rows[i].length() != 0)
					{
						this.rows[i].moveFront();
					}
					currentM.changeEntry(i, a, dotProduct(this.rows[i], transposedM.rows[a]));
				} 
			}
			currentM.nnz = this.nnz; // ensure the multiplied matrix has the same number of NNZ's.
			return currentM;
        }
        else
        {
            return this;
        }
    }

    // Other functions
    public String toString() // overrides Object's toString() method
    {
        String output = "";
        for (int i = 0; i < this.getSize(); i++)
        {
            if(rows[i].length() > 0)
                output = output + (i +": " + rows[i] + "\n");
        }
        return output;
    }

    // helper dotProduct
    private static double dotProduct(List A, List B)
    {
        double runningTotal = 0;
        if (A.length() == B.length()) //only execute when both lists are of the same length.
        {
            A.moveFront();
            B.moveFront();

            while (A.index() != -1 && B.index() != -1)
            {
                Entry listA = (Entry) A.get();
                Entry listB = (Entry) B.get();
                if (listA.column < listB.column) // listA is behind listB.
                {
                    A.moveNext();
                }
                else if (listA.column > listB.column) // listA is ahead of listB
                {
                    B.moveNext();
                }
                else if (listA.column == listB.column) // when aligned, iterate through both equally.
                {
                    runningTotal += listA.val * listB.val;
                    A.moveNext();
                    B.moveNext();
                }
            }
        }
        return runningTotal;
    }
}