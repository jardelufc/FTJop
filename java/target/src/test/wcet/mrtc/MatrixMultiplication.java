package wcet.mrtc;
//import edu.uci.eecs.doc.clepsydra.loop.LoopBound;

/**
 * Matrix multiplication of two 20x20 square matrices. It tests a compiler's speed
 * in handling multidimensional arrays and simple arithmetic.
 *
 * WCET aspect: Multiple calls to the same function, nested function calls,
 * triple-nested loops.
 *
 * Ported from C code written by Jan Gustafsson for the <a
 * href="http://www.mrtc.mdh.se/projects/wcet/benchmarks.html">Maelardalen WCET
 * Benchmarks</a>. Originally developed from mm.c to matmult.c by Thomas Lundqvist
 * at Chalmers.
 */
public class MatrixMultiplication
{
    private static final int UPPER_LIMIT = 20;

    private int seed;
    private int[][] matrixA, matrixB, resultMatrix;

    public MatrixMultiplication()
    {
        matrixA = new int[UPPER_LIMIT][UPPER_LIMIT];
        matrixB = new int[UPPER_LIMIT][UPPER_LIMIT];
        resultMatrix = new int[UPPER_LIMIT][UPPER_LIMIT];

    	initSeed();
    	initialize(matrixA);
    	initialize(matrixB);
    }

    // Initializes the seed used in the random number generator.
    private void initSeed()
    {
    	seed = 0;
    }

    // Intializes the given matrix with random integers.
    private void initialize(int[][] matrix)
    {
        //@LoopBound(max=UPPER_LIMIT)
    	for (int outer = 0; outer < UPPER_LIMIT; outer++)
    	{
            //@LoopBound(max=UPPER_LIMIT)
    		for (int inner = 0; inner < UPPER_LIMIT; inner++)
    		{
    			matrix[outer][inner] = randomInteger();
    		}
    	}
    }

    // Generates random integers between 0 and 8095
    private int randomInteger()
    {
        // FIXME: Cannot do remainder because irem bytecode is implemented in software on JOP, causing problems for Clepsydra
    	// seed = ((seed * 133) + 81) % 8095;
    	seed = ((seed * 133) + 81) & 0x00001FFF;  // Simulate remainder by masking

    	return seed;
    }

    // Multiplies arrays a and b and stores the result in res.
    private void multiply(int[][] a, int[][] b, int[][] res)
    {
        //@LoopBound(max=UPPER_LIMIT)
    	for (int i = 0; i < UPPER_LIMIT; i++)
        {
            //@LoopBound(max=UPPER_LIMIT)
    		for (int j = 0; j < UPPER_LIMIT; j++)
    		{
    			res[i][j] = 0;
    			
                //@LoopBound(max=UPPER_LIMIT)
    			for (int k = 0; k < UPPER_LIMIT; k++)
    			{
    				res[i][j] += a[i][k] * b[k][j];
    			}
    		}
    	}
    }

    // For debugging only
    private void print(int[][] matrix)
    {
    	for (int outer = 0; outer < UPPER_LIMIT; outer++)
    	{
    		for (int inner = 0; inner < UPPER_LIMIT; inner++)
    			System.out.print(matrix[outer][inner] + " ");
    			
    		System.out.println();
    	}
    	
    	System.out.println();
    }
    
    public static void main(String[] args)
    {
        MatrixMultiplication m = new MatrixMultiplication();

        m.multiply(m.matrixA, m.matrixB, m.resultMatrix);
    }
}
