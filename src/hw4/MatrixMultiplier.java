package hw4;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.Date;
import java.util.concurrent.Callable;

 class MatrixMultiplier implements Callable<Integer>
{
    private int n;
    private int matrix_1[][];
    private int matrix_2[][];
    private int sum;
    private int i;
    private int j;


    public MatrixMultiplier(int n, int matrix_1[][], int matrix_2[][], int i , int j )
    {
            this.n = n;
            this.matrix_1 = matrix_1;
            this.matrix_2 = matrix_2;
            this.i = i;
            this.j = j;
    }

    public Integer call() throws Exception
    {
        for (int k = 0 ; k < n ; k++)
         {
             sum =  matrix_1[i][k] * matrix_2[k][j] + sum;

         }
         return sum;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException 
    {

        int n;
        int[][] matrix_1, matrix_2, result;

        n = 1000;
        matrix_1 = new int[n][n];
        matrix_2 = new int[n][n];
        result = new int[n][n];

         int threads = 16;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                matrix_1[i][j] = 1;
            }
        }

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {

                matrix_2[i][j] = 1;

            }
        }
          int sum = 0;
          Date startDate = new Date();
         Future<Integer> futures[][] = new Future[n][n];

      for (int i = 0; i < n; i++) {
    	    for (int j = 0; j < n; j++) {
    	        var future = executor.submit(new MatrixMultiplier(n, matrix_1, matrix_2, i, j));
    	        futures[i][j] = future;
    	    }
    	}

    	for (int i = 0; i < n; i++) {
    	    for (int j = 0; j < n; j++) {
    	        result[i][j] = futures[i][j].get();
    	        sum = 0;
    	    }
    	}
		Date endDate = new Date();
		float timeIntervalInSeconds = (float) ((endDate.getTime() - startDate.getTime()) / 1000.0);
		System.out.println("n= " + n);
		System.out.println("time in seconds: " + timeIntervalInSeconds);
        executor.shutdown();

    }
}

