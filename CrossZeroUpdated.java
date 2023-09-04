package ru.geekbrains.seminar2;

import java.util.Random;
import java.util.Scanner;

public class CrossZeroUpdated {
    private static final char DOT_HUMAN = 'X';                     // Фишка игрока - человек.
    private static final char DOT_AI = '0';                        // Фишка игрока - компьютер.
    private static final char DOT_EMPTY = '*';                     // Признак пустого поля.
    private static final Scanner scanner = new Scanner(System.in); // Инициализация сканера.
    private static final Random random = new Random();             // Генератор случайных чисел.
    private static char[][] field;                                 // Двумерный массив, хранящий теущее состояние игрового поля.
    private static int fieldSizeX;                                 // Размерность игрового поля.
    private static int fieldSizeY;                                 // Размерность игрового поля.

    private static final int win = 4;                               // Условие победы.

    public static void main(String[] args) {
        do {
            setField();
            printField();
            while (true) {
                humanMove();
                printField();
                if (checkGameState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiMove();
                printField();
                if (checkGameState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
        } while (scanner.next().equalsIgnoreCase("Y"));
    }

    /**
     * setField - метод инициализации игрового поля.
     */
    private static void setField() {
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * printField - метод отрисовки игрового поля.
     */
    private static void printField() {
        System.out.print("+");
        for (int x = 0; x < fieldSizeX * 2 + 1; x++) {
            System.out.print((x % 2 == 0) ? "-" : x / 2 + 1);
        }
        System.out.println();
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }
        for (int x = 0; x < fieldSizeX * 2 + 2; x++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private static void humanMove() {
        int x, y;
        do {
            while (true) {
                System.out.print("Введите координату X от 1 до 5: ");
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt() - 1;
                    scanner.nextLine();
                    break;
                } else {
                    System.out.print("Некорректное значение. Повторите попытку ввода.");
                    scanner.nextLine();
                }
            }
            while (true) {
                System.out.print("Введите координату Y от 1 до 5: ");
                if (scanner.hasNextInt()) {
                    y = scanner.nextInt() - 1;
                    scanner.nextLine();
                    break;
                } else {
                    System.out.print("Некорректное значение. Повторите попытку ввода.");
                    scanner.nextLine();
                }
            }
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * isCellValid - метод проверки нахождения введенных координат в границах
     * игрового поля.
     *
     * @param x - координата ячейки по горизонтали.
     * @param y - координата ячейки по вертикали.
     * @return - возвращаем true, если ячейка в границах игрового поля.
     */
    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * isCellEmpty - метод проверки незанятости ячейки игрового поля.
     *
     * @param x - координата ячейки по горизонтали.
     * @param y - координата ячейки по вертикали.
     * @return - возврат true, если ячейка является пустой.
     */
    private static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * aiMove - метод обработки хода компьютера.
     */
    private static void aiMove() {
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }


    /**
     * checkWin - метод проверки на соответствие выигрышной комбинации.
     *
     * @param c - аргумент - крестики или нолик.
     * @return - true, если текущее положение на поле соответствует выигрышной комбинации.
     */
    private static boolean checkWin(char c) {
        // Проверка по горизонталям
        if (checkWinHorizontal(c)) return true;
        // Проверка по вертикалям
        if (checkWinVertical(c)) return true;
        // Проверка по двум диагоналям
        if (checkWinDiagonal1(c)) return true;
        return checkWinDiagonal2(c);
    }

    /**
     * checkWinHorizontal - метод проверки выигрышной комбинации по горизонтали
     *
     * @param c - фишка игрока
     * @return - result (true / false)
     */
    private static boolean checkWinHorizontal(char c) {
        boolean result = false;
        int count = 0;
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (field[x][y] == c) {
                    count++;
                }
                if (count == CrossZeroUpdated.win) {
                    result = true;
                }
            }
            count = 0;
        }
        return result;
    }

    /**
     * checkWinVertical - метод проверки выигрышной комбинации по вертикали
     *
     * @param c - фишка игрока
     * @return - result (true / false)
     */
    private static boolean checkWinVertical(char c) {
        boolean result = false;
        int count = 0;
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (field[y][x] == c) {
                    count++;
                }
                if (count == CrossZeroUpdated.win) {
                    result = true;
                }
            }
            count = 0;
        }
        return result;
    }

    /**
     * checkWinVertical - метод проверки выигрышной комбинации по диагонали № 1
     *
     * @param c - фишка игрока
     * @return - result (true / false)
     */
    private static boolean checkWinDiagonal1(char c) {
        boolean result = false;
        int count = 0;
        for (int x = 0; x < fieldSizeX; x++) {
            if (field[x][x] == c) {
                count++;
            }
        }
        if (count == CrossZeroUpdated.win) {
            result = true;
        }
        return result;
    }

    /**
     * checkWinVertical - метод проверки выигрышной комбинации по диагонали № 2
     *
     * @param c - фишка игрока
     * @return - result (true / false)
     */
    private static boolean checkWinDiagonal2(char c) {
        boolean result = false;
        int count = 0;
        for (int x = 0; x < fieldSizeX; x++) {
            if (field[fieldSizeX - x - 1][x] == c) {
                count++;
            }
        }
        if (count == CrossZeroUpdated.win) {
            result = true;
        }
        return result;
    }


    /**
     * checkDraw - метод провекрки на соответствие ничейной позиции
     *
     * @return - true, если все поле занято, но выигрышной комбинации нет.
     */
    private static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * checkGameState - проверка состояния игры.
     *
     * @param c - фишка игрока.
     * @param s - победный слоган.
     * @return - false, если не выполнены условия выигрыша или ничьи.
     */
    private static boolean checkGameState(char c, String s) {
        if (checkWin(c)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается.
    }
}
