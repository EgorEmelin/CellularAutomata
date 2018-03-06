import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //объявление переменных для вывода в файлы
        int[][] a, d, g, h,l; // Результаты вычислений
        ArrayList<Integer> b, c; // Найденные вторичные источники
        Method meth = new Method(); // Создание объекта
        a = meth.modeling(); // Вызов метода вычисления клеточного автомата
        b = meth.back1(); // Получение первой координаты вторичного источника
        c = meth.back2(); // Получение второй координаты вторичного источника
        d = meth.back3(); // Получение изображения
        g = meth.back4(); // Получение накопителя P
        h = meth.back5(); // Получение накопителя
        l = meth.back6();

        try (BufferedWriter f = new BufferedWriter(new FileWriter("arr.txt"))) { // Вывод в файл результата вычислений
            f.flush();
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {
                    f.write(String.valueOf(a[i][j] + " "));
                }
                f.newLine();
            }
        } catch (IOException e) {
        }

        try (BufferedWriter f = new BufferedWriter(new FileWriter("arr1.txt"))) { // Вывод в файл втоичных источников
            f.flush();
            for (int i = 0; i < b.size(); i++) {
                f.write(String.valueOf(b.get(i) + " "));
            }
        } catch (IOException e) {
        }

        try (BufferedWriter f = new BufferedWriter(new FileWriter("arr2.txt"))) { // Вывод в файл втоичных источников
            f.flush();
            for (int i = 0; i < c.size(); i++) {
                f.write(String.valueOf(c.get(i) + " "));
            }
        } catch (IOException e) {
        }

        try (BufferedWriter f = new BufferedWriter(new FileWriter("arr3.txt"))) { // Вывод в файл втоичных источников
            f.flush();
            for (int i = 0; i < d.length; i++) {
                for (int j = 0; j < d.length; j++) {
                    f.write(String.valueOf(d[i][j] + " "));
                }
                f.newLine();
            }
        } catch (IOException e) {
        }

        try (BufferedWriter f = new BufferedWriter(new FileWriter("arr4.txt"))) { // Вывод в файл втоичных источников
            f.flush();
            for (int i = 0; i < g.length; i++) {
                for (int j = 0; j < g.length; j++) {
                    f.write(String.valueOf(g[i][j] + " "));
                }
                f.newLine();
            }
        } catch (IOException e) {
        }

        try (BufferedWriter f = new BufferedWriter(new FileWriter("arr5.txt"))) { // Вывод в файл втоичных источников
            f.flush();
            for (int i = 0; i < h.length; i++) {
                for (int j = 0; j < h.length; j++) {
                    f.write(String.valueOf(h[i][j] + " "));
                }
                f.newLine();
            }
        } catch (IOException e) {
        }

        try (BufferedWriter f = new BufferedWriter(new FileWriter("arr6.txt"))) { // Вывод в файл результата вычислений
            f.flush();
            for (int i = 0; i < l.length; i++) {
                for (int j = 0; j < l.length; j++) {
                    f.write(String.valueOf(l[i][j] + " "));
                }
                f.newLine();
            }
        } catch (IOException e) {
        }
    }
}