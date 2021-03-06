import java.util.ArrayList;

class Method {
    private int N = 200; // Определение размеров сетки

    private int img[][] = new int[N][N], a_t[][] = new int[N][N], z1[][] = new int[N][N],
            z2[][] = new int[N][N], P[][] = new int[N][N], img_1[][] = new int[N][N]; // Создаются двумерные массивы, хранят состояние сетки, выделяется память под них

    private int M1[][][] = new int[N][N][8], M2[][][] = new int[N][N][8], // Создаются трехмерные массивы,
            r_t1[][][] = new int[N][N][8], r_t2[][][] = new int[N][N][8], // вспомогательные при вычислениях,
            W_t1[][][] = new int[N][N][8], W_t2[][][] = new int[N][N][8]; // выделяется память под них

    private double r_t_metric[][][] = new double[N][N][8], // Создаются массивы, хранищие матрики,
            r_t_metric_W[][][] = new double[N][N][8]; // полученные из карты, выделяется память под них

    private ArrayList<Integer> arr6 = new ArrayList<>(), arr7 = new ArrayList<>(); // Два списка, хранят полученные вторичные источники
    private double[] temp = new double[8]; // Создается массив для определение наименьшей метрики для соседей, потенциальных источников
    private int count2; // Счетчик, используется в блоке вычисления потенциальных соседей
    private int y, x; // Координаты для обхода препятсвия по границе
    private int count3 = 1; // Счетчик из блока определения вторичных источников, считает источники
    private int count4 = 0; // Счетчик из блока определения вторичных источников, считает препятствия
    private int count5 = 1; // Считчик из блока вычисления векторов в опредлении вторичных источник, вспомогательный
    private int count6 = 0; // Счетчик потенциальных источников в перпятсвии
    private ArrayList<Integer> arr1 = new ArrayList<>(); // Список, хранит метки препятствий
    private ArrayList<Integer> arr2 = new ArrayList<>();
    private ArrayList<Integer> arr3 = new ArrayList<>(); // 2 списка, хранят последовательно все потенциальные вторичные источники
    private ArrayList<Integer> arr4 = new ArrayList<>();
    private ArrayList<Integer> arr5 = new ArrayList<>(); // 2 списка, хранят вычесленные вектора
    private int g[][] = new int[N][N], counter[][] = new int[N][N]; // Объявление g - метка для проверки потенциальных вторичных источников, counter - счетчик объектов

    Method() { // Конструктор, определяется начальное состояние сетки

        for (int i = 0; i < N; i++) // Блок инициализации массивов начальными значениями
            for (int j = 0; j < N; j++)
                for (int q = 0; q < 8; q++) {
                    M1[i][j][q] = M2[i][j][q] = r_t1[i][j][q] = r_t2[i][j][q] =
                            W_t1[i][j][q] = W_t2[i][j][q] = g[i][j] = -1;
                    r_t_metric[i][j][q] = r_t_metric_W[i][j][q] = -1;
                    counter[i][j] = P[i][j] = img_1[i][j] = 0;
                }

        neighbors();
        initialization();
        sources();
    }
    private void neighbors () // Метод отвечает за определение окрестности Мура
    {
        int count; // Счетчик, использующийся в блоке определения соседей текущей клетки
        for (int i = 0; i < N; i++) // Блок прохода и определения соседей
            for (int j = 0; j < N; j++) {
                count = 0; // Обнуление счетчика для каждой клетки
                int q = -1, r = -1; // Задание переменных для пробега соседей (создание маски)
                while (q < 2 || r < 2) { // Выполняем до тех пор, пока масска размером не больше 1х1
                    if ((i + q) >= 0 && (i + q) <= (N - 1)) { // Проверка того, что не выходим за границы сетки по строкам
                        if ((j + r) >= 0 && (j + r) <= (N - 1)) { // Проверка того, что вы не выходим за границы сетки по столбцам
                            if (max(i + q, i, j + r, j, N)) { // Проверка, что просматриваемая клетка соседняя для i,j, переход в метод max
                                M1[i][j][count] = i + q; // Сохранение координаты соседа вдоль строк
                                M2[i][j][count] = j + r; // Сохранение координаты соседа вдоль столбцов
                                r++; // Сдвиг справо
                                count++; // Увеличиваем счетчик обхода вокруг клетки
                            } else if (q == 0 && r == 0) {
                                r++;
                            } // Если сама клетка, то переходим на соседа справа
                            else {
                                r = -1;
                                q++;
                            } // Если r > 1, то переходим на новыю строчку и сбрасываем r
                        } else if (r < 2) {
                            r++;
                        } // Переходим на клетку вправо, если за границей сетки слева
                        else {
                            q++;
                            r = -1;
                        } // Переходим на новую строчку, если за границей сетки справа
                    } else {
                        q++;
                        r = -1;
                    } // Переходим на новую строчку, если на границе сверху, сбрасываем r
                    if (q == 2) r = 2; // Условие выхода из цикла
                }
            }
    }

    private void initialization() // Метод отвечает за инициализацию изначального состояния
        {
        for (int i = 0; i < N; i++) // Задание начального состояния сетки (t = 0)
            for (int j = 0; j < N; j++) {
/*                if ((j <= 80) && (i <= 80)) { // 1 четверть
                    a_t[i][j] = 0;
                    img[i][j] = -1;
                }
                else if ((j >= 120) && (i <= 80)) { // 2 четверть
                    a_t[i][j] = 0;
                    img[i][j] = -1;
                }

                else if ( (i >= 120)&& (j <= 80)) { // 3 четверть
                    a_t[i][j] = 0;
                    img[i][j] = -1;
                }
                else if ((j >= 120) &&  (i >= 120)) { // 4 четверть
                    a_t[i][j] = 0;
                    img[i][j] = -1;
                }
                else if ((j >= 110) &&(j<=111) && (i >=125)&&(i<=128) || (j >= 48) &&(j<=50) && (i >=90)&&(i<=91) ||(j >= 150) &&(j<=152) && (i >=110)&&(i<=111) ||(j >= 90) &&(j<=91) && (i >=48)&&(i<=50)) { // "автомобили"
                    a_t[i][j] = 0;
                    img[i][j] = -1;
                }*/
if ((j >= 30) &&(j<=36) && (i >=100)&&(i<=130) )
{ // "автомобили"
    a_t[i][j] = 0;
    img[i][j] = -1;
}
                else if (i == 150 && j == 110) { // Начальный источник
                    a_t[i][j] = 1;
                    img[i][j] = 0;
                }

                else { // Пустые клетки
                    a_t[i][j] = 0;
                    img[i][j] = 0;
                }
                z1[i][j] = z2[i][j] = 0;
            }
    }

    private void sources () // Метод отвечает за поск вторичных источников
    {
        boolean check1; // Логическая переменная для работы в цикле блока определения потенциальных вторичных источников
        for (int i = 0; i < N; i++) // Блок определения потенциальных вторичных источников и их нумерации против часовой стрелки
            for (int j = 0; j < N; j++) {
                y = i;
                x = j; // Сброс переменных обхода препятсвия
                check1 = true; // Сброс переменной, для работы в цикле
                do { // Цикл обхода найденного препятсвия
                    if (img[y][x] <= -1 && g[y][x] == -1) { // Проверка на пренадлежность препятсвию, и то что клетка еще не проверина на источник
                        count2 = 0; // Обнуление счетчика клеток, удовлетворяющих условию ниже
                        for (int q = 0; q < 8; q++) // Проверка соседей на удовлетворение условию
                            if (M1[y][x][q] != -1 && img[M1[y][x][q]][M2[y][x][q]] <= -1) // Сосед нарасчивает счетчик, если он препятсвие
                                count2++; // Увеличение счетчика
                        if (count2 <= 4) { // Если мощность множества меньше/равна 4, то клетка - потенциальный вторичный источник
                            g[y][x] = count3; // Сохранение номера
                            counter[y][x] = count4; // Метка препятсвия
                            count3++; // Увеличиваем счетчик источников
                        } else g[y][x] = 0; // Объявляем клетку проверенной
                    }
                    // Меняем координаты для обхода против часовой стрелки препятсвия
                    if (((y+1)<=(N-1) && img[y+1][x]<=-1 && g[y+1][x]==-1 && (x-1)>=0 && (img[y][x-1]>-1 || img[y][x-1]<=-1 && g[y][x-1]!=-1))||(x==0&&(y+1)<=(N-1) && img[y+1][x]<=-1 && g[y+1][x]==-1)) //Идем вниз, если это возможно и слева не препятствие
                        y = y + 1; // Меняем координату обхода
                    else if (((x-1)>=0 && img[y][x-1]<=-1 && g[y][x-1]==-1 && (y-1)>=0 && (img[y-1][x]>-1 || img[y-1][x]<=-1 && g[y-1][x]!=-1))||(y==0&&(x-1)>=0 && img[y][x-1]<=-1 && g[y][x-1]==-1)) //Идем влево, если это возможно и сверху не препятсвие
                        x = x - 1; // Меняем координату обхода
                    else if (((y-1)>=0 && img[y-1][x]<=-1 && g[y-1][x]==-1 && (x+1)<=(N-1) && (img[y][x+1]>-1 || img[y][x+1]<=-1 && g[y][x+1]!=-1))||(x==N-1 && (y-1)>=0 && img[y-1][x]<=-1 && g[y-1][x]==-1 && (x+1)<=(N-1))) //Идем вверх, если это возможно и справа не препятствие
                        y = y - 1; // Меняем координату обхода
                    else if (((x+1)<=(N-1) && img[y][x+1]<=-1 && g[y][x+1]==-1 && (y+1)<=(N-1) && (img[y+1][x]>-1 || img[y+1][x]<=-1 && g[y+1][x]!=-1))||(y==N-1 && (x+1)<=(N-1) && img[y][x+1]<=-1 && g[y][x+1]==-1))// Идем вправо, если это возможно и снижу не препятствие
                        x = x + 1; // Меняем координату обхода
                    else
                        check1 = false; // Условие выхода из цикла, если закончили обход, или уже были в этом препятствии
                }
                while (check1);
                count4++; // Накапиливаем счетчик для индетификации препятсвия
            }

        for (int i = 0; i < N; i++) // Блок, в котором последовательно, друг за другом выставляются потенциальные источники и метка препятствия соответственно
            for (int j = 0; j < N; j++)
                if (g[i][j] == count5) { // определяем координаты 1, 2 .. источника
                    arr1.add(counter[i][j]); // Запоминаем метку
                    arr2.add(i); // Запоминаем координату вдоль строк
                    arr3.add(j); // Запоминаем координату вдоль столбцов
                    count5++; // Переходим к следующему источнику
                    i = arr2.get(0); // Возвращаем координату i  к началу препятсвия, чтобы не потерять другие источники
                    j = arr3.get(0); // Возвращаем координату j к началу препятсвия, чтобы не потерять другие источники
                }

        for (int i = 0; i < arr1.size(); i++) // Блок определения вторичных источников
            if ((i + 1) < arr1.size() && arr1.get(i).equals(arr1.get(i + 1))) { // Проверка на пренадлежность одному препятсвию
                arr4.add(arr2.get(i + 1) - arr2.get(i)); // Вычисление первой компоненты вектора
                arr5.add(arr3.get(i + 1) - arr3.get(i)); // Вычисление второй компоненты вектора
                count6++; // Считаем число вершин в препятсвии
            } else {
                arr4.add(arr2.get(i - count6) - arr2.get(i)); // Вычисления для крайних вершин
                arr5.add(arr3.get(i - count6) - arr3.get(i)); // Вычисления для крайних вершин

                for (int j = 0; j < arr4.size(); j++) // Определение вторичных источников для каждого из объектов
                {
                    if ((j - 1) >= 0 && arr1.get(j).equals(arr1.get(j - 1)) && // Проверка условия на вторичный источник
                            ((5 * (arr4.get(j - 1) * arr5.get(j) - arr4.get(j) * arr5.get(j - 1))) > max1(arr4.get(j - 1), arr4.get(j), arr5.get(j - 1), arr5.get(j)))) {
                        arr6.add(arr2.get(j)); // Запомнание полученного источника, первая компонента
                        arr7.add(arr3.get(j)); // Запомнание полученного источника, вторая компонента
                    } else if ((j + count6) < arr1.size() && arr1.get(j).equals(arr1.get(j + count6)) && // В случае, если рассматриваетсякрайняя точка
                            ((5 * (arr4.get(j + count6) * arr5.get(j) - arr4.get(j) * arr5.get(j + count6))) > max1(arr4.get(j + count6), arr4.get(j), arr5.get(j + count6), arr5.get(j)))) {
                        arr6.add(arr2.get(j)); // Запомнание полученного источника, первая компонента
                        arr7.add(arr3.get(j)); // Запомнание полученного источника, вторая компонента
                    }

                }
                count6 = 0; // Обнуление счетчика
            }
    }

    private int max1(int a, int b, int c, int d) {
        return Math.max(0, a * b + c * d);
    } // Вычисление максимума из двух числе при опеределении вторичного источника

    private boolean max(int a, int b, int c, int d, int e) {
        int raz1 = a - b, raz2 = c - d; // Вычисление разности координат
        return (a >= 0 && a <= (e - 1) && c >= 0 && c <= (e - 1) && (raz1 != 0 || raz2 != 0) && Math.abs(raz1) <= 1 && Math.abs(raz2) <= 1); // Проверка на соседство
    }

    private boolean diagonal(int a, int b, int c, int d, int e) {
        int raz1 = a - b, raz2 = c - d; // Вычисление разности координат
        return (a >= 0 && a <= (e - 1) && c >= 0 && c <= (e - 1) && (Math.abs(raz1) * Math.abs(raz2)) == 1);  // Определение диагональных соседей
    }

    private int indicator(int a, int b, int c, int d, int e) {
        if (diagonal(a, b, c, d, e)) return 1; // Вычисление пренадлежности текущего соседа к диагональтому типу
        else return 0;
    }

    private double distance(int a, int b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)); // Вычисление метрики r_t
    }

    private int argmin(double a, int k) // Определение наименьшей метрики для соседей, потенциальных источников
    {
        if (k == 0) { // Инициализация массива при первом заходе
            for (int i = 0; i < 8; ++i)
                temp[i] = 99999;
        }
        temp[k] = a;
        int numb = 0;
        double min = temp[0];
        for (int i = 0; i < 8; i++) // Нахождение минимального элемента
            if (min > temp[i]) min = temp[i];
        for (int i = 0; i < 8; i++) // Вывод номера минимального элемента
            if (min == temp[i]) numb = i;
        return numb;
    }

    private int check_a (int a){
        if (a > 0) return 1;
        else return 0;
    }

    private void clearence ()
    {
        arr1.clear();
        arr2.clear();
        arr3.clear();
        arr4.clear();
        arr5.clear();
        arr6.clear();
        arr7.clear();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                g[i][j] = -1;
                counter[i][j] = 0;
            }

        count2 = 0;
        count3 = 1;
        count4 = 0;
        count5 = 1;
        x = 0;
        y = 0;
    }

    private void mobile_moving (int a, int b)
    {
        byte [][] obj1,obj2,obj3,obj4;
        obj1 = new byte[N][N]; obj2 = new byte[N][N]; obj3 = new byte[N][N]; obj4 = new byte[N][N];
        if (a<b){
/*        if (a%2 == 0 && 125 - a/2 >=0) {
            for (int i = 125 - a / 2; i <= 133 - a / 2; i++) // Задание начального состояния сетки (t = 0)
                for (int j = 107; j <= 112; j++) {
                    img[i][j] = -10;
                    obj1[i][j] = 1;
                    if (i == 133 - a/2 && obj2[i+1][j]!=1 && obj3[i+1][j]!=1 && obj4[i+1][j]!=1)
                        img[i+2][j] = 0;
                }
        }
            if (57 + a<=N-1) {
                for (int i = 90; i <= 96; i++) // Задание начального состояния сетки (t = 0)
                    for (int j = 48 + a; j <= 57 + a; j++) {
                        img[i][j] = -20;
                        obj2[i][j] = 1;
                        if (j == 48+a && obj1[i][j-1]!=1 && obj3[i][j-1]!=1 && obj4[i][j-1]!=1)
                            img[i][j-2] = 0;
                    }
            }
            if (145-a>=0) {
                for (int i = 107; i <= 113; i++) // Задание начального состояния сетки (t = 0)
                    for (int j = 145 - a; j <= 152 - a; j++) {
                        img[i][j] = -30;
                        obj3[i][j] = 1;
                        if (j == 152-a && obj2[i][j+1]!=1 && obj1[i][j+1]!=1 && obj4[i][j+1]!=1)
                            img[i][j+2] = 0;
                    }
            }
            if (57+2*a<=N-1) {
                for (int i = 48 + 2 * a; i <= 57 + 2 * a; i++) // Задание начального состояния сетки (t = 0)
                    for (int j = 88; j <= 94; j++) {
                        img[i][j] = -40;
                        obj4[i][j] = 1;
                        if (i == 48 + a*2 && obj2[i- 1][j]!=1 && obj3[i- 1][j]!=1 && obj1[i- 1][j]!=1) {
                            img[i - 3][j] = 0;
                            img[i - 4][j] = 0;
                        }
                    }
            }*/
            if (36 +2* a<=N-1) {
                for (int i = 100; i <= 130; i++) // Задание начального состояния сетки (t = 0)
                    for (int j = 30 + 2*a; j <= 36 + 2*a; j++) {
                        //a_t[i][j] = 0;
                        img[i][j] = -20;
                        if ( j == 30+2*a )
                            img[i][j-3] = 0;
                        img[i][j-4] = 0;

                    }
            }
            clearence();
            sources();
        }
    }



    ArrayList<Integer> back1() { // Вывод первой компоненты вторичного источника
        return arr6;
    }

    ArrayList<Integer> back2() { // Вывод второй компоненты вторичного источника
        return arr7;
    }

    int[][] back3() { // Вывод изображения
        return img;
    }
    int[][] back4() { // Вывод изображения
        return P;
    }
    int[][] back5() { // Вывод изображения
        return img_1;
    }
    int[][] back6() { // Вывод изображения
        return g;
    }

    int[][] modeling() { // Метод, происходит вычисление состояний клеток во времени

        int new_i, new_j; // Переменные для 1 и 2 координаты переноса возбуждения
        int count1; // Счетчик, используемый в блоке вычисления карты r и потенциальных источников активации из числа соседей
        int t = 1; // Время, число циклов вычислений
        int end = 100; // Время, конечное число циклов вычисления
        boolean check1;
        while (t <= end) { // Цикл вычислений состояний клеток
            mobile_moving(t,end);
            for (int i = 0; i < N; i++) // Пробегаем всю сетку
                for (int j = 0; j < N; j++) {
                    count1 = 0; // Счетчик для заполнение последовательно потенциальных источников активации
                    for (int q = 0; q < 8; q++) { // Пробегаем всех соседей

                        check1 = true;
                        for (int e = 0; e < arr7.size(); e++)
                            if (i == arr6.get(e) && j == arr7.get(e))
                                check1 = false;

                        if (M1[i][j][q] != -1 && M2[i][j][q] != -1) { // Условие того, что используем только заполненые ячейки
                            if ((a_t[i][j] > 0 && img[i][j] > -1) || (img[i][j] <= -1 && check1) || a_t[M1[i][j][q]][M2[i][j][q]] == 0) { // Постороение карты и проверка условий для присвоения новых значений
                                r_t1[i][j][q] = 0;
                                r_t2[i][j][q] = 0;
                            } else {
                                r_t1[i][j][q] = z1[M1[i][j][q]][M2[i][j][q]] + 1;
                                r_t2[i][j][q] = z2[M1[i][j][q]][M2[i][j][q]] + indicator(M1[i][j][q], i, M2[i][j][q], j, N);
                            }

                            r_t_metric[i][j][q] = distance(r_t1[i][j][q], r_t2[i][j][q]); // Вычисление метрики r_t для текущей точки для всех соседей

                            if ((a_t[M1[i][j][q]][M2[i][j][q]] + r_t_metric[i][j][q]) > t &&
                                    (a_t[M1[i][j][q]][M2[i][j][q]] + r_t_metric[i][j][q]) <= t + 1) { // Определение потенциальных источников активации и заполнение соответсвующих массивов
                                W_t1[i][j][count1] = M1[i][j][q];
                                W_t2[i][j][count1] = M2[i][j][q];
                                r_t_metric_W[i][j][count1] = r_t_metric[i][j][q];
                                count1++;
                            }
                        }
                    }
                }
            boolean check3;
            int temp[][] = new int[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) { // Определяем координаты передаваемого возбуждения
                    int numb = 0;
                    if (W_t1[i][j][0] != -1 && W_t2[i][j][0] != -1) { // Проверка на то, что есть потенциальные источники активации
                        int k = 0;
                        while (r_t_metric_W[i][j][k] != -1.0 && W_t1[i][j][k] != -1 && W_t2[i][j][k] != -1) { // Определение клетки с минимальнйо метрикой
                            numb = argmin(r_t_metric_W[i][j][k], k);
                            k++;
                        }
                        new_i = W_t1[i][j][numb]; // Получение координат источника активации
                        new_j = W_t2[i][j][numb]; // Получение координат источника активации
                    } else {
                        new_i = i; // Получение координат источника активации
                        new_j = j; // Получение координат источника активации
                    }

                    if (a_t[i][j] > 0 || img[i][j] <= -1 || W_t1[i][j][0] == -1) { // Определяем новые z для следующего шага вычислений
                        z1[i][j] = z1[i][j];
                        z2[i][j] = z2[i][j];
                    } else {
                        for (int q = 0; q < 8; q++)
                            if ((M1[i][j][q] == new_i) && M2[i][j][q] == new_j) {
                                z1[i][j] = r_t1[i][j][q];
                                z2[i][j] = r_t2[i][j][q];
                            }
                    }

                    check3 = false;
                    temp[i][j] = a_t[i][j];
                    for (int q = 0; q < arr7.size(); q++)
                        if (i == arr6.get(q) && j == arr7.get(q))
                            check3 = true;
                    // Переопределяем a_t для следующего шага вычислений
                    for (int e = 0; e < 8; e++)
                        if (a_t[i][j] == 0 && check3 && M1[i][j][e] != -1 && a_t[M1[i][j][e]][M2[i][j][e]] > 0) {
                            temp[i][j] = t + 1;
                            for (int z = 0; z < 8; z++)
                            {
                                if (M1[i][j][z]!=-1 && g[M1[i][j][z]][M2[i][j][z]]>=0 && temp[i][j]>0)
                                img_1[M1[i][j][z]][M2[i][j][z]] = 50;
                            }
                            break;
                        } else if (a_t[i][j] ==0){

                            temp[i][j] = a_t[new_i][new_j];
                            for (int r = 0; r < 8; r++)
                            {
                                if (M1[i][j][r]!=-1 && g[M1[i][j][r]][M2[i][j][r]]>=0 && temp[i][j]>0 )
                                    img_1[M1[i][j][r]][M2[i][j][r]] = 50;
                            }
                        }

                }

            for (int i = 0; i < N; i++) // Получение нового массива a_t
                for (int j = 0; j < N; j++) {
                a_t[i][j] = temp[i][j];
                }

            t++; // следующий шаг выполнения
            for (int i = 0; i < N; i++) // Cчетчик накопителя P
                for (int j = 0; j < N; j++) {
                    P[i][j] = P[i][j] + check_a(a_t[i][j]);
                }

        }
        return a_t; // Возврат финальной карты
    }
}