# Ежедневник
Приложение состоит из четырех экранов:
1. Календарь
2. Список событий 
3. Новое событие
4. Существующее событие

## Экран «Календарь»

Экран представляет собой календарь, на котором отмечены дни, в которые есть добавленные пользователем события.

При нажатии на любой день откроется экран «Список событий».
При нажатии на кнопку «+» откроется экран «Новое событие».

## Экран «Список событий»

На экране выводятся все события в рамках дня: 
- текущего дня при переходе на экран с нижнего навигационного меню.
- выбранного пользователем дня во всех остальных случаях.

На экране предусмотрена возможность выбора дня из горизонтального календаря.

При нажатии кнопки «+» откроется экран «Новое событие».
При нажатии на событие в списке откроется экран «Существующее событие».

## Экран «Создание нового события».

Экран позволяет добавить событие в календарь. Пользователю предлагается ввести: 
- дату и время начала события.
- дату и время окончания события.
- название события.
- описание события.

При открытии экрана автоматически заполняются поля даты и времени:
- в соответствии с текущим временем при переходе из «Календаря».
- в соответствии с выбранным на экране временем при переходе с экрана «Список событий».

При нажатии кнопки «Добавить событие» событие добавится в базу данных, и откроется экран «Список событий» в день добавленного события.

На экране предусмотрены защиты:
- от выбора даты окончания меньше даты начала.
- от создания события с пустым названием.

*Примечание: описание события – не обязательное для заполнения поле.*

## Экран «Просмотр, редактирование и удаление существующего события»

Экран во многом аналогичен предыдущему.

При открытии экрана автоматически заполняются поля даты и времени.

При нажатии кнопки «Изменить событие» событие изменится в базе данных, и откроется экран «Список событий» в день измененного события.
При нажатии кнопки «Удалить событие» событие удалится из базы данных, и откроется экран «Список событий» в день удаленного события.

На экране предусмотрены защиты:
- от выбора даты окончания меньше даты начала.
- от изменения события с пустым названием.
Примечание: описание события – не обязательное для заполнения поле.

## Особенности приложения

Приложение работает с базой данных Realm.

Приложение предусматривает возможность смены часового пояса.
