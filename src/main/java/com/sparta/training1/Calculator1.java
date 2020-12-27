package com.sparta.training1;

public class Calculator1 {

    String lastResult;
    double variable1;
    double variable2;
    double currentResult;

    public static void main(String[] args) {
        Calculator1 calculations = new Calculator1();

        calculations.addition(1,2);
        calculations.subtraction(1,2);
        calculations.multiplication(1,2);
        calculations.division(1,2);
        calculations.division(1,0); //деление на 0
        calculations.division(2,0); //деление на 0
    }

    public void addition(double variable1, double variable2) {    // метод сложения
        currentResult = variable1 + variable2;
        System.out.println("Result: "+variable1+" + "+variable2+" = " +currentResult+". Previous result = "+lastResult);
        lastResult = Double.toString(currentResult);    // Ковертация результата double в string
    }

    public void subtraction(double variable1, double variable2) { // метод вычитания
        currentResult = variable1 - variable2;
        System.out.println("Result: "+variable1+" - "+variable2+" = " +currentResult+". Previous result = "+lastResult);
        lastResult = Double.toString(currentResult);    // Ковертация результата double в string
    }

    public void multiplication(double variable1, double variable2) {  // метод умножения
        currentResult = variable1 * variable2;
        System.out.println("Result: "+variable1+" * "+variable2+" = " +currentResult+". Previous result = "+lastResult);
        lastResult = Double.toString(currentResult);    // Ковертация результата double в string
    }

    public void division(double variable1, double variable2) {    // метод деления
        if (variable2 == 0){    // деление на ноль
            System.out.println("Division by zero"+". Previous result = "+lastResult);
        }
        else {
            currentResult = variable1 / variable2;
            System.out.println("Result: "+variable1+" / "+variable2+" = " +currentResult+". Previous result = "+lastResult);
            lastResult = Double.toString(currentResult);    // Ковертация результата double в string
        }
    }
}