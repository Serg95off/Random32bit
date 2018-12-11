# Random32bit
Android apk 
Приложение на JAVA для смартфонов на ОС Android
Приложение получает данные с трех датчиков


1.	TYPE_ACCELEROMETER - Измеряет ускорение в пространстве по осям X, Y, Z
Количество значений – 3     Ускорение (м/с2) по трём осям.
value[0]:ось X (поперечная)
value[1]: ось Y (продольная)
value[2]:ось Y (вертикальная)

2.	TYPE_GYROSCOPE - Трёхосевой гироскоп, возвращающий текущее положение устройства в пространстве в градусах по трём осям. По другим данным, возвращает скорость вращения устройства по трём осям в радианах в секунду. 
Количество значений – 3     Скорость вращения (рад/с) по трём осям
value[0]:ось X (поперечная)
value[1]: ось Y (продольная)
value[2]:ось Y (вертикальная)

3.	TYPE_LIGHT - Измеряет степень освещённости. Датчик окружающей освещённости, который описывает внешнюю освещённость в люксах. Этот тип датчиков обычно используется для динамического изменения яркости экрана.
Количество значений – 1   Внешняя освещённость (лк)
value[0]:освещённость

Выполняет операцию XOR и в результате получается случайная последовательность из 32 бит.

![alt text](Screenshot_2018-12-11-22-37-25[1].png)
![alt text](Screenshot_2018-12-11-22-37-32[1].png)
