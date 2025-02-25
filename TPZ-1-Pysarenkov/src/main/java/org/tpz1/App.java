package org.tpz1;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    private static final double rightBound = 120d;

    public static void main(String[] args) {
        String[] inputRequests = new String[]{
                "Введіть першу пару координат (точка на першій прямій) через пробіл: ",
                "Введіть другу пару координат (точка на першій прямій) через пробіл: ",
                "Введіть третю пару координат (точка на другій прямій) через пробіл: ",
                "Введіть четверту пару координат (точка на другій прямій) через пробіл: ",
                "Введіть k (третя пряма): ",
                "Введіть b (третя пряма): "
        };
        Scanner myObj = new Scanner(System.in);
        int i = 0;
        Double[][] pairs = new Double[4][2];
        Double[] thirdLineParams = new Double[2];
        while (i < inputRequests.length) {
            System.out.print(inputRequests[i]);
            try {
                if (i < 4) {
                    // Прямі з поданням 2

                    String pair = myObj.nextLine();
                    Double[] pairD = Arrays.stream(pair.split(" ")).map(Double::parseDouble).toArray(Double[]::new);

                    // Чи введена ПАРА координат
                    if (pairD.length != 2) {
                        System.out.printf("Введено некоректну кількість координат. Введіть 2 координати.\n");
                        continue;
                    }

                    if (!checkBounds(pairD[0]) || !checkBounds(pairD[1])) {
                        System.out.printf("Введіть коефіцієнти в межах [%.0f; %.0f]\n", -1 * rightBound, rightBound);
                        continue;
                    }

                    pairs[i] = pairD;
                    // Спробувати створити пряму
                    if (i % 2 == 1) {
                        Line.createLine(new Point2D.Double(pairs[i - 1][0], pairs[i - 1][1]),
                                new Point2D.Double(pairs[i][0], pairs[i][1]));
                    }
                } else {
                    // Прямі з поданням 5

                    String coef = myObj.nextLine();
                    thirdLineParams[i - 4] = Double.parseDouble(coef);

                    if (!checkBounds(thirdLineParams[i - 4])) {
                        System.out.printf("Введіть коефіцієнти в межах [%.0f; %.0f]\n", -1 * rightBound, rightBound);
                        continue;
                    }

                    // Спробувати створити пряму
                    if (i % 2 == 1) {
                        Line.createLine(thirdLineParams[0], thirdLineParams[1]);
                    }
                }
            }
            // Введене будь-що, але не число
            catch (NumberFormatException e) {
                System.out.printf("Введено некоректний рядок. Введіть %s з роздільником - крапкою.\n", i < 4 ? "одне число" : "два числа");
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                if (i != 5) // Якщо вводимо b, треба переввести лише b. Якщо дві пари координат - переввести обидві пари.
                    i--;
                continue;
            }
            i++;
        }

        Line line1 = Line.createLine(new Point2D.Double(pairs[0][0], pairs[0][1]), new Point2D.Double(pairs[1][0], pairs[1][1]));
        Line line2 = Line.createLine(new Point2D.Double(pairs[2][0], pairs[2][1]), new Point2D.Double(pairs[3][0], pairs[3][1]));
        Line line3 = Line.createLine(thirdLineParams[0], thirdLineParams[1]);

        System.out.println();
        System.out.println(Intersection.findIntersection(line1, line2, line3));
    }

    private static Boolean checkBounds(double a) {
        return Math.abs(a) <= rightBound;
    }
}
