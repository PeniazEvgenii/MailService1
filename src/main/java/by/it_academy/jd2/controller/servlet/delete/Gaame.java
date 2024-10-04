package by.it_academy.jd2.controller.servlet.delete;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Scanner;

public class Gaame {
    public static void main(String[] args) {
        String name = "Nepobedimui";
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        Scanner console = new Scanner(System.in);
        while (true) {
            System.out.print("Введите действие (или 'q' для выхода): ");
            String action = console.nextLine();
            if ("q".equalsIgnoreCase(action)) {
                System.out.println("выход из программы");
                break;
            }

            String requestBody = "";

            if (action.equalsIgnoreCase("move")) {
                System.out.print("Введите координаты x: ");
                int x = readInt3(console);

                System.out.print("Введите координаты y: ");
                int y = readInt3(console);

               requestBody = "{\"x\": " + x + ", \"y\": " + y + "}";
            } else if (action.equalsIgnoreCase("craft")) {
                System.out.print("Введите код предмета: ");
                String code = console.nextLine();

                System.out.print("Введите количество: ");
                int quantity = readInt(console);

                requestBody = "{\"code\": \"" + code + "\", \"quantity\": " + quantity + "}";
            }


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.artifactsmmo.com/my/" + name + "/action/" + action))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IldvbGFuZCIsInBhc3N3b3JkX2NoYW5nZWQiOiIifQ.3Zjsd68wTp4p6NTaaXrK-Xd3zPC4EKI83RN51YXqZhM")
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();

            try {

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                String formattedResponse = formatResponse(response.body());

                if (response.statusCode() == 200) {
                    System.out.println("Response: " + formattedResponse);
                } else {
                    System.out.println("Error Response: " + formattedResponse);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }



    }

    private static int readInt(Scanner scanner) {
        while(!scanner.hasNextInt()) {
            System.out.println("Неверный ввод. ВВедите число");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();    //очистка буфера
        return value;
    }


    private static int readInt3(Scanner scanner) {
        int value = 0;
        while (true) {
            if(scanner.hasNextInt()) {
                value = scanner.nextInt();
                System.out.println(value);
                return value;
            } else {
                scanner.nextLine();
            }
        }
    }


    private static String formatResponse(String response) {
        return response.replace(",", ",\n");
    }
}
