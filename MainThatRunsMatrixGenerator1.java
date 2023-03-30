public class Main {
    public static void main(String[] args) {
        int numRows = 4;
        int numCols = 4;
        float[][] matrix = new float[numRows][numCols];

        MatrixGenerator.generate(matrix);

        // Print out the generated matrix
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
