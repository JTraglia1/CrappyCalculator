package com.hfad.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 *  MainActivity.java - Crappy Calculator
 *  This program represents a crappy calculator that attempts to mimic the
 *  functionality of a real calculator that could be found on a smartphone.
 *  This application possesses the ability to run on Android devices
 *  with an application programming interface (API) of at least 21.
 *
 *  @author Joseph Traglia
 *
 */
public class MainActivity extends AppCompatActivity {

    //Fields
    String lastNumber = "";
    boolean operationJustPressed;

    //Initialize ArrayList used to track the equation being computed
    ArrayList<String> equation = new ArrayList<String>();

    /**
     * Main program that builds and displays the application on the device's screen.
     * @param savedInstanceState default parameter used to save the application's data
     *                           once the screen has been rotated to landscape mode.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content view to be displayed on the device.
        setContentView(R.layout.activity_main);

        //Initialize button numbers
        Button btnZero = findViewById(R.id.btnZero);
        Button btnOne = findViewById(R.id.btnOne);
        Button btnTwo = findViewById(R.id.btnTwo);
        Button btnThree = findViewById(R.id.btnThree);
        Button btnFour = findViewById(R.id.btnFour);
        Button btnFive = findViewById(R.id.btnFive);
        Button btnSix = findViewById(R.id.btnSix);
        Button btnSeven = findViewById(R.id.btnSeven);
        Button btnEight = findViewById(R.id.btnEight);
        Button btnNine = findViewById(R.id.btnNine);

        //Initialize button operations
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);

        //Initialize miscellaneous buttons
        Button btnAllClear = findViewById(R.id.btnAllClear);
        Button btnPositiveNegative = findViewById(R.id.btnPositiveNegative);
        Button btnPercentage = findViewById(R.id.btnPercentage);
        Button btnDecimal = findViewById(R.id.btnDecimal);
        Button btnEquals = findViewById(R.id.btnEquals);

        //Initialize the textview for the solution
        TextView tvSolution = findViewById(R.id.tvSolution);

        //Instantiate functionality for the button numbers utilizing the setOnClickListener
        //lambda function and the buttonLogic() method
        btnZero.setOnClickListener(view -> buttonLogic(0, tvSolution));
        btnOne.setOnClickListener(view -> buttonLogic(1, tvSolution));
        btnTwo.setOnClickListener(view -> buttonLogic(2, tvSolution));
        btnThree.setOnClickListener(view -> buttonLogic(3, tvSolution));
        btnFour.setOnClickListener(view -> buttonLogic(4, tvSolution));
        btnFive.setOnClickListener(view -> buttonLogic(5, tvSolution));
        btnSix.setOnClickListener(view -> buttonLogic(6, tvSolution));
        btnSeven.setOnClickListener(view -> buttonLogic(7, tvSolution));
        btnEight.setOnClickListener(view -> buttonLogic(8, tvSolution));
        btnNine.setOnClickListener(view -> buttonLogic(9, tvSolution));

        //Instantiate functionality for the operations utilizing the setOnClickListener
        //lambda function and the AddNumberOrOperation() method
        btnAdd.setOnClickListener(view -> AddNumberOrOperation(lastNumber, "+"));
        btnSubtract.setOnClickListener(view -> AddNumberOrOperation(lastNumber, "-"));
        btnMultiply.setOnClickListener(view -> AddNumberOrOperation(lastNumber, "X"));
        btnDivide.setOnClickListener(view -> AddNumberOrOperation(lastNumber, "/"));

        btnAllClear.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Method utilized to provide functionality to the all clear (AC) button,
             * which clears the ArrayList of all numbers and operations and sets the
             * textview to 0.
             * @param view default parameter.
             */
            @Override
            public void onClick(View view)
            {
                lastNumber = "";
                equation.clear();
                tvSolution.setText("0");
            }
        });

        btnPositiveNegative.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Method utilized to provide functionality to the positive/negative (+/-) button,
             * which sets a number to negative if it is positive and sets a number to positive
             * if it is negative.
             * @param view default parameter.
             */
            @Override
            public void onClick(View view)
            {
                if (lastNumber.contains("-"))
                {
                    lastNumber = lastNumber.replace("-", "");
                }
                else
                {
                    lastNumber += "-";
                }
                tvSolution.setText(lastNumber);
            }
        });

        btnPercentage.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Method utilized to provide functionality to the percentage (%) button, which
             * divides the last number by 100 to calculate the percentage of that number.
             * @param view default parameter.
             */
            @Override
            public void onClick(View view)
            {
                double num = Double.parseDouble(lastNumber);

                lastNumber = String.format("%.2f", num / 100);
                tvSolution.setText(lastNumber);
            }
        });

        btnDecimal.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Method utilized to provide functionality to the decimal (.) button,
             * which inputs a decimal into the current number being typed. An error
             * is displayed if the user attempts to enter more than one decimal point.
             * @param view default parameter.
             */
            @Override
            public void onClick(View view)
            {
                if (lastNumber.contains("."))
                {
                    int duration = Toast.LENGTH_SHORT;

                    Context context = getApplicationContext();
                    CharSequence text = "Decimal already exists.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else
                {
                    lastNumber += ".";
                }
                tvSolution.setText(lastNumber);
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Method utilized to provide functionality to the equals (=) button,
             * which calculates the current equation stored into the ArrayList.
             * @param view default parameter.
             */
            @Override
            public void onClick(View view)
            {
                equalsEquation(lastNumber);
                tvSolution.setText(equation.get(0).toString());
            }
        });
    }

    /**
     * The buttonLogic method utilizes a for loop to display in the textview
     * the correct number typed on the calculator by the user.
     * @param num calculator numbers 0-9 based on which button is pressed.
     * @param tvSolution textview that displays the number just pressed.
     */
    public void buttonLogic(int num, TextView tvSolution)
    {
        for (int i = 0; i < 10; i++)
        {
            if (num == i)
            {
                lastNumber += i;
            }
        }
        tvSolution.setText(lastNumber);
    }

    /**
     * The AddNumberOrOperation method adds a number or operation to the ArrayList
     * based on which button is pressed by the user. (Thank you, Emanuel Luna, for ideas).
     * @param number the last number (or operation) input by the user.
     * @param operation the operation input by the user based on the button pressed.
     */
    public void AddNumberOrOperation(String number, String operation)
    {
        //If last input was blank, add the operation to the ArrayList
        if (number.equals(""))
        {
            equation.add(operation);

            //If size of ArrayList is 1, then add the appropriate operation to the ArrayList.
            if (equation.size() == 1)
            {
                if (equation.get(0).toString().equals("+") || equation.get(0).toString().equals("-") || equation.get(0).toString().equals("X") || equation.get(0).toString().equals("/"))
                {
                    equation.remove(0);
                    equation.add("0");
                    equation.add(operation);
                }
            }
        }
        else
        {
            equation.add(number);
            equation.add(operation);
        }

        //If last input was a number, remove the first number at ArrayList index 0 from the ArrayList
        //and set the last number at ArrayList index 2 to a blank string.
        if (isPreviousNumber(equation))
        {
            equation.remove(equation.size() - 2);
            lastNumber = "";
        }
        else
        {
            lastNumber = "";
        }
    }

    /**
     * The isPreviousNumber method checks whether the last input into the ArrayList
     * was a number or an operation. (Thank you, Emanuel Luna, for ideas).
     * @param equation the ArrayList which tracks the current equation being calculated.
     * @return true or false based on the last input into the ArrayList.
     */
    public boolean isPreviousNumber(ArrayList<String> equation)
    {
        //Fields
        int size = equation.size();
        operationJustPressed = false;

        //If the size of the equation is 1, then only a number has been input, not an operation.
        if (equation.size() == 1)
        {
            operationJustPressed = false;
        }
        else if (equation.get(equation.size() - 2).toString().equals("+") || equation.get(equation.size() - 2).toString().equals("-") || equation.get(equation.size() - 2).toString().equals("X") || equation.get(equation.size() - 2).toString().equals("/"))
        {
            operationJustPressed = true;
        }

        return operationJustPressed;
    }

    /**
     * The mdas method calculates the current equation input into the ArrayList. The method
     * then replaces the current equation with its solution.
     * @param i the index of the operation in the ArrayList.
     */
    public void mdas(int i)
    {
        //Fields
        String num1 = equation.get(i - 1).toString();
        String num2 = equation.get(i + 1).toString();
        String op = equation.get(i).toString();
        String totalOutput = "";
        double total = 0;

        //Check which operation was input and calculate the current ArrayList equation.
        //Multiplication (m) is checked first, division (d) second, addition (a) third, and subtraction (s) last.
        if (op.equals("X"))
        {
            total = Double.parseDouble(num1) * Double.parseDouble(num2);
        }
        else if (op.equals("/"))
        {
            total = Double.parseDouble(num1) / Double.parseDouble(num2);

            //If the user attempts to divide by 0, display an error.
            if (Double.parseDouble(num2) == 0)
            {
                int duration = Toast.LENGTH_SHORT;

                Context context = getApplicationContext();
                CharSequence text = "Cannot divide by 0.";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else if (op.equals("+"))
        {
            total = Double.parseDouble(num1) + Double.parseDouble(num2);
        }
        else
        {
            total = Double.parseDouble(num1) - Double.parseDouble(num2);
        }

        //Remove the numbers of the equation just calculated from the ArrayList at indices 0 and 2.
        equation.remove(i + 1);
        equation.remove(i - 1);

        //If either number contains a decimal, then output the solution as a decimal.
        if (num1.contains(".") || num2.contains("."))
        {
            totalOutput = String.format("%,.2f", total);
            equation.set(i - 1, totalOutput);
        }
        else
        {
            totalOutput = String.format("%,.0f", total);
            equation.set(i - 1, totalOutput);
        }
    }

    /**
     * The findMinimumIndex method compares two operations and returns the smallest operation
     * to be calculated first. This method assists in aligning with the operation rules of PEMDAS.
     * (Thank you, Emanuel Luna, for ideas).
     * @param op1 The first operation for comparison.
     * @param op2 The second operation for comparison.
     * @return The minimum (first) operation to be calculated.
     */
    public int findMinimumIndex(String op1, String op2)
    {
        int indexM = 0;
        int indexD = 0;
        int indexSmall = 0;

        indexM = equation.indexOf(op1);
        indexD = equation.indexOf(op2);

        if (indexM == -1)
        {
            indexSmall = indexD;
        }
        else if (indexD == -1)
        {
            indexSmall = indexM;
        }
        else if (indexM < indexD)
        {
            indexSmall = indexM;
        }
        else
        {
            indexSmall = indexD;
        }

        return indexSmall;
    }

    /**
     * The equalsEquation method tracks the index of the operation in the ArrayList and passes that index
     * to the mdas method for the equation to be calculated properly based on the operation pressed
     * by the user. (Thank you, Emanuel Luna, for ideas).
     * @param input The last input by the user, could be a number or operation.
     */
    public void equalsEquation(String input)
    {
        if (input.equals(""))
        {
            if (equation.size() == 1)
            {

            }
            else
            {
                equation.remove(equation.size() - 1);
            }
        }
        else
        {
            equation.add(input);
        }

        int indexM = 0;
        int indexD = 0;
        int indexSmall = 0;

        indexM = equation.indexOf("X");
        indexD = equation.indexOf("/");

        while (indexM != -1 || indexD != -1)
        {
            indexSmall = findMinimumIndex("X", "/");

            mdas(indexSmall);

            indexM = equation.indexOf("X");
            indexD = equation.indexOf("/");
        }

        int indexA = 0;
        int indexS = 0;

        indexA = equation.indexOf("+");
        indexS = equation.indexOf("-");

        while (indexA != -1 || indexS != -1)
        {
            indexSmall = findMinimumIndex("+", "-");

            mdas(indexSmall);

            indexA = equation.indexOf("+");
            indexS = equation.indexOf("-");
        }
        lastNumber = "";
    }
}