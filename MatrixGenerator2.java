public class MatrixGenerator {
    private static final Random random = new Random();
    private static float[][] matrix;

    public static float[][] generate(int nrows, int ncols) {
        matrix = new float[nrows][ncols];

        int numThreads = Runtime.getRuntime().availableProcessors();
        int numRows = nrows / numThreads;
        int remainingRows = nrows % numThreads;
        int startRow = 0;

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int endRow = startRow + numRows - 1;
            if (i < remainingRows) {
                endRow++;
            }
            threads[i] = new Thread(new MatrixGeneratorRunnable(startRow, endRow, ncols));
            threads[i].start();
            startRow = endRow + 1;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return matrix;
    }

    private static class MatrixGeneratorRunnable implements Runnable {
        private final int startRow;
        private final int endRow;
        private final int ncols;

        public MatrixGeneratorRunnable(int startRow, int endRow, int ncols) {
            this.startRow = startRow;
            this.endRow = endRow;
            this.ncols = ncols;
        }

        @Override
        public void run() {
            for (int i = startRow; i <= endRow; i++) {
                for (int j = 0; j < ncols; j++) {
                    matrix[i][j] = random.nextFloat();
                }
            }
        }
    }
}
