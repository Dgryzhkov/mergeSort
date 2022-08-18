# mergeSort

Параметры программы задаются при запуске через аргументы командной строки:
режим сортировки (-a или -d), необязательный, по умолчанию сортируем по возрастанию;
тип данных (-s или -i), обязательный;
имя выходного файла, обязательное;
остальные параметры – имена входных файлов, не менее одного.


Примеры запуска из командной строки для Ubuntu (для приложенного файла "mergeSort.jar"):

java -jar merge_sort.jar -i -a out.txt in1.txt (для целых чисел по возрастанию)
java -jar merge_sort.jar -s out.txt in1.txt in2.txt in3.txt (для строк по возрастанию)
java -jar merge_sort.jar -d -s out.txt in1.txt in2.txt (для строк по убыванию)
