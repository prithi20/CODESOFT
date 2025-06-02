package project;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    // Hardcoded exchange rates relative to USD
    private static final Map<String, Double> rates = new HashMap<>();

    static {
        rates.put("USD", 1.0);
        rates.put("INR", 83.0);
        rates.put("EUR", 0.92);
        rates.put("GBP", 0.81);
        rates.put("JPY", 140.0);
        rates.put("AUD", 1.48);
        rates.put("CAD", 1.34);
        // Add more currencies as needed
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Offline Currency Converter ---");

        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter target currency (e.g., INR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        if (!rates.containsKey(baseCurrency)) {
            System.out.println("Sorry, we don't support the base currency: " + baseCurrency);
            scanner.close();
            return;
        }

        if (!rates.containsKey(targetCurrency)) {
            System.out.println("Sorry, we don't support the target currency: " + targetCurrency);
            scanner.close();
            return;
        }

        // Convert amount from base currency to USD first
        double amountInUSD = amount / rates.get(baseCurrency);

        // Convert USD to target currency
        double convertedAmount = amountInUSD * rates.get(targetCurrency);

        System.out.printf("%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);

        scanner.close();
    }
}
