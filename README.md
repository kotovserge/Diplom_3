Дипломная работа
----------------
Тестирование веб-приложения Stellar Burgers для заказов бургеров.

Тестируются следующий функционал:

    Регистрация

    Вход

    Переход в личный кабинет

    Выход из аккаунта

    Раздел «Конструктор», переходы к разделам: «Булки», «Соусы», «Начинки».

Тестирование выполнено в двух браузерах: Chrom, Yandex.
Для Yandex нужно прописать путь к  браузеру в
src/test/java/Browser в строке
options.setBinary("C:/Users/serge/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");

При написании тестов использовалось:

    Java 11.0.24
    Maven 3.9.9
    Junit 4.13.2
    Аllure

