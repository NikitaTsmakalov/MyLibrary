package org.example.mylibrary.model;

import java.util.Scanner;

import java.util.Scanner;

public class DiscountCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Введите количество товара (целое число): ");
        int quantity = scanner.nextInt();


        System.out.print("Введите цену за единицу товара (в рублях): ");
        double pricePerUnit = scanner.nextDouble();


        double totalAmount = quantity * pricePerUnit;
        double finalAmount = totalAmount;

        // Проверяем условие для скидки
        if (quantity > 50) {
            // Вычисляем скидку 15%
            double discount = totalAmount * 0.15;
            finalAmount = totalAmount - discount;

            System.out.printf("Итоговая сумма: %.2f рублей (скидка 15%% применена)%n", finalAmount);
            System.out.printf("Сумма без скидки: %.2f рублей%n", totalAmount);
            System.out.printf("Размер скидки: %.2f рублей%n", discount);
        } else {
            System.out.printf("Без скидки, сумма: %.2f рублей%n", finalAmount);
        }

        scanner.close();
    }
}