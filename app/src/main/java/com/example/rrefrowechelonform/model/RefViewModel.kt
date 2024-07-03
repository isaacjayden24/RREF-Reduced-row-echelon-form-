package com.example.rrefrowechelonform.model

import androidx.lifecycle.ViewModel

class RefViewModel:ViewModel() {


    //the formula to calculate the Row echelon form



     fun toRowEchelonForm(matrix: Array<DoubleArray>): Array<DoubleArray> {
        val numRows = matrix.size
        val numCols = matrix[0].size

        for (i in 0 until numRows) {
            var pivot = i
            for (j in i + 1 until numRows) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[pivot][i])) {
                    pivot = j
                }
            }

            if (pivot != i) {
                val temp = matrix[i]
                matrix[i] = matrix[pivot]
                matrix[pivot] = temp
            }

            val pivotValue = matrix[i][i]
            if (pivotValue != 0.0) {
                for (j in i until numCols) {
                    matrix[i][j] /= pivotValue
                }
            }

            for (j in i + 1 until numRows) {
                val factor = matrix[j][i]
                for (k in i until numCols) {
                    matrix[j][k] -= factor * matrix[i][k]
                }
            }
        }

        return matrix
    }

    //formats the matrix into a string for display

      fun matrixToString(matrix: Array<DoubleArray>): String {
        val builder = StringBuilder()
        for (row in matrix) {
            for (value in row) {
                builder.append(String.format("%.2f ", value))
            }
            builder.append("\n")
        }
        return builder.toString()
    }






}