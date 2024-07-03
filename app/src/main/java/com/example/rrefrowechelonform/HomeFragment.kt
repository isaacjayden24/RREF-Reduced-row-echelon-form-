package com.example.rrefrowechelonform

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rrefrowechelonform.model.RefViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private val refViewModel: RefViewModel by viewModels()

    private lateinit var editTextRows:EditText
    private lateinit var editTextCols:EditText
    private lateinit var buttonCreateMatrix:Button
    private lateinit var buttonResetMatrix:Button
    private lateinit var tableLayoutMatrix:TableLayout
    private lateinit var buttonRrefCalculator:Button
    private lateinit var resultText:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_home, container, false)
        editTextRows=view.findViewById(R.id.editTextRows)
        editTextCols=view.findViewById(R.id.editTextCols)
        buttonCreateMatrix=view.findViewById(R.id.buttonCreateMatrix)
        buttonResetMatrix=view.findViewById(R.id.buttonResetMatrix)
        tableLayoutMatrix=view.findViewById(R.id.tableLayoutMatrix)
        buttonRrefCalculator=view.findViewById(R.id.buttonRrefCalculator)
        resultText=view.findViewById(R.id.resultText)


        buttonCreateMatrix.setOnClickListener(){
            Log.d("HomeFragment", "Create Matrix button clicked")
            createMatrixInputFields()
        }

        buttonResetMatrix.setOnClickListener(){
            resetMatrixInputFields()
        }
        buttonRrefCalculator.setOnClickListener(){
            calculateRREF()
        }


        return view
    }

//generate a grid of editTexts
    private fun createMatrixInputFields() {
        val numRows = editTextRows.text.toString().toIntOrNull()
        val numCols = editTextCols.text.toString().toIntOrNull()

        if (numRows != null && numCols != null && numRows > 0 && numCols > 0) {
            tableLayoutMatrix.removeAllViews()

            for (i in 0 until numRows) {
                val tableRow = TableRow(requireContext())
                tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                for (j in 0 until numCols) {
                    val editText = EditText(requireContext())
                    editText.layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    editText.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
                    editText.gravity = android.view.Gravity.CENTER
                    tableRow.addView(editText)
                }
                tableLayoutMatrix.addView(tableRow)
            }
        } else {
            Toast.makeText(requireContext(), "Please enter valid dimensions", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetMatrixInputFields() {
        editTextRows.text.clear()
        editTextCols.text.clear()
        tableLayoutMatrix.removeAllViews()
    }


    private fun calculateRREF() {
        val numRows = tableLayoutMatrix.childCount
        val numCols = if (numRows > 0) (tableLayoutMatrix.getChildAt(0) as TableRow).childCount else 0

        if (numRows == 0 || numCols == 0) {
            Toast.makeText(requireContext(), "Please create a matrix first", Toast.LENGTH_SHORT).show()
            return
        }

        val matrix = Array(numRows) { DoubleArray(numCols) }

        for (i in 0 until numRows) {
            val tableRow = tableLayoutMatrix.getChildAt(i) as TableRow
            for (j in 0 until numCols) {
                val editText = tableRow.getChildAt(j) as EditText
                matrix[i][j] = editText.text.toString().toDoubleOrNull() ?: 0.0
            }
        }


        val refMatrix=refViewModel.toRowEchelonForm(matrix)
        //val refMatrix = toRowEchelonForm(matrix)
        resultText.text = refViewModel.matrixToString(refMatrix)
    }



}