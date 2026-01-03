package com.example.newcalculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat
import java.util.Stack

class MainViewModel : ViewModel() {

    // Holds the current expression shown on the screen
    private val _expression = MutableLiveData("")
    val expression: LiveData<String> = _expression

    // Holds the evaluation result
    private val _result = MutableLiveData("")
    val result: LiveData<String> = _result

    // Append a character (number or operator) to the expression
    fun onAppend(value: String) {
        _expression.value = (_expression.value ?: "") + value
    }

    // Clear the entire expression and result
    fun onClear() {
        _expression.value = ""
        _result.value = ""
    }

    // Delete the last character
    fun onDelete() {
        val current = _expression.value ?: ""
        if (current.isNotEmpty()) {
            _expression.value = current.dropLast(1)
        }
    }

    // Evaluate the expression when '=' is pressed
    fun onCalculate() {
        val expr = _expression.value ?: return
        try {
            val evalResult = evaluate(expr)
            // Format to avoid long decimal tails
            val df = DecimalFormat("#.########")
            _result.value = df.format(evalResult)
        } catch (e: Exception) {
            _result.value = "Error"
        }
    }

    /**
     * Very small expression evaluator supporting +, -, *, /, % and decimal numbers.
     * It uses the Shunting‑yard algorithm to convert to Reverse Polish Notation.
     */
    private fun evaluate(expression: String): Double {
        val output = mutableListOf<String>()
        val operators = Stack<Char>()
        var numberBuffer = StringBuilder()
        fun flushNumber() {
            if (numberBuffer.isNotEmpty()) {
                output.add(numberBuffer.toString())
                numberBuffer = StringBuilder()
            }
        }
        for (ch in expression) {
            when {
                ch.isDigit() || ch == '.' -> numberBuffer.append(ch)
                ch == '(' -> {
                    flushNumber()
                    operators.push(ch)
                }
                ch == ')' -> {
                    flushNumber()
                    while (operators.isNotEmpty() && operators.peek() != '(') {
                        output.add(operators.pop().toString())
                    }
                    if (operators.isNotEmpty() && operators.peek() == '(') {
                        operators.pop()
                    }
                }
                isOperator(ch) -> {
                    flushNumber()
                    while (operators.isNotEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                        output.add(operators.pop().toString())
                    }
                    operators.push(ch)
                }
                else -> throw IllegalArgumentException("Invalid character: $ch")
            }
        }
        flushNumber()
        while (operators.isNotEmpty()) {
            output.add(operators.pop().toString())
        }
        // Evaluate RPN
        val stack = Stack<Double>()
        for (token in output) {
            if (token.length == 1 && isOperator(token[0])) {
                val b = stack.pop()
                val a = stack.pop()
                val res = when (token[0]) {
                    '+' -> a + b
                    '-' -> a - b
                    '*' -> a * b
                    '/' -> a / b
                    '%' -> a % b
                    else -> throw IllegalArgumentException("Unsupported operator ${token[0]}")
                }
                stack.push(res)
            } else {
                stack.push(token.toDouble())
            }
        }
        return stack.pop()
    }

    private fun isOperator(ch: Char) = ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%'

    private fun precedence(ch: Char): Int = when (ch) {
        '+', '-' -> 1
        '*', '/', '%' -> 2
        else -> -1
    }
}
