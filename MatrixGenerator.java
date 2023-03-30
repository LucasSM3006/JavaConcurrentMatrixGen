import java.util.Random;

public class MatrixGenerator {
    public static void generate(float[][] matrix) {
        int numThreads = Runtime.getRuntime().availableProcessors(); //This gets the available number of threads.
        Thread[] threads = new Thread[numThreads]; //This creates a vector which starts all threads with the number of available threads.

        int numRowsPerThread = matrix.length / numThreads; //This divices the length of the matrix by the number of threads to allow them to take an even number of rows.
        int remainingRows = matrix.length % numThreads; //Remaining rows is the length divided by the number of threads. It's basically the rows that remain were the number to be uneven.

        int startRow = 0; //Start of the row. Matrixes and vectors start at index 0.

        for (int i = 0; i < numThreads; i++) { //This is responsible for running the threads.
            int numRows = numRowsPerThread; //Number of rows is equal the number of rows per thread.

            if (i < remainingRows) { //If the number is uneven, the number of rows is raised to account for the uneven ones.
                numRows++;
            }

            int endRow = startRow + numRows - 1; //Since 0 is a valid row, need to reduce by one.
            threads[i] = new Thread(new MatrixGeneratorRunnable(matrix, startRow, endRow)); //This creates an instance of the class that runs it.
            //It'll create the class, pass the already existing matrix, the startRow and the endRow, which will be used as parameters.
            threads[i].start(); //This then starts the thread.

            startRow = endRow + 1; //This is for the next thread to take into account. It's so that the next thread processes the next set of rows that haven't been processed by the previous thread.
            //Thread[0].startRow = 1;
            //Thread[1].startRow = not that;
        }

        //This iterates over ALL threads.
        //Since it's out of the loop, but all of the threads were already created. Once that for finishes, this one executes.
        for (Thread thread : threads) {
            try {
                thread.join(); //Tries to join all the threads which were created. Basically the 'main' has to wait for all the worker threads to do their thing beforehand.
                // thread.join blocks the main thread until it finishes.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class MatrixGeneratorRunnable implements Runnable {
        private final float[][] matrix; //Each thread that is created from a MatrixGeneratorRunnable instance will operate on its own copy of the matrix variable, and will not modify the copies used by other threads. This is because each thread is assigned a unique range of rows to process, and does not overlap with the rows processed by other threads.
        private final int startRow; //Startrow variable.
        private final int endRow; //Endrow variable.

        public MatrixGeneratorRunnable(float[][] matrix, int startRow, int endRow) { //Constructor. This initializes the class.
            this.matrix = matrix; //Matrix will be set at the very start of the previous one. But it's different everytime. Doesn't matter much.
            this.startRow = startRow; //Starting row. Value set on previous function.
            this.endRow = endRow; //Ending row. Value set on previous function.
        }

        @Override
        public void run() {
            Random generator = new Random(); //New random instance.
            for (int i = startRow; i <= endRow; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j] = generator.nextFloat() * 100; //Generate the matrix.
                }
            }
        }
    }
}
