package com.medizine.backend.utils;

public class Problem {

  public static int N = 3;

  public static void swapDiagonal(int[][] matrix, boolean toSwapEven) {

    for (int i = 0; i < N; i++) {
      if (toSwapEven) {
        int temp = matrix[i][N - i - 1];
        matrix[i][N - i - 1] = matrix[i][i];
        matrix[i][i] = temp;
      } else {
        int temp = matrix[i][i];
        matrix[i][i] = matrix[i][N - i - 1];
        matrix[i][N - i - 1] = temp;
      }

    }
  }

  public static void main(String[] args) {
    int[][] matrix = {
        {11, 13, 12},
        {22, 21, 19},
        {14, 12, 13}
    };

    solveProblem(matrix);

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++)
        System.out.print(matrix[i][j] + " ");
      System.out.println();
    }
  }

  private static void solveProblem(int[][] matrix) {
    int evenCount = 0, oddCount = 0;

    for (int[] arr : matrix) {
      for (int i : arr) {
        if (i % 2 == 0) evenCount += 1;
        else oddCount += 1;
      }
    }
    swapDiagonal(matrix, oddCount > evenCount);
  }
}
