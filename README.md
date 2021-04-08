# trpp-Card-Game
## Цифровая версия настольной игры Звездные Империи
«Звёздные империи» – колодостроительная игра на космическую тематику для 1-6 человек (Нынешняя реализация предполагает 2 игрока). 
Игрокам предстоит собрать свой флот, оборудовать базы и аванпосты, а затем атаковать друг друга для снижения очков влияния противника. 
Все игроки начинают в равных условиях с личной колодой карт кораблей.
Докупая карты в свою колоду и используя различные комбинации карт с уникальными свойствами, игроки сами придумывают стратегию во время партии: 
вырабатывают защиту от атак противника и ищут бреши в обороне противника. 
Можно пользоваться силой, хитростью или богатством, но для победы всем игрокам одинаково понадобятся мастерство, интуиция и хладнокровный расчёт!

<p align="center">
  <img src="https://cdn.cloudflare.steamstatic.com/steam/apps/438140/header.jpg?t=1615845303">
</p>

### Правила игры
Ознакомиться с правилами можно по ссылке: [Правила](https://hobbyworld.ru/download/rules/StarRealms_rules_rus_web.pdf)

## Использованные инструменты
### libGDX Cross-platform Game Development Framework
Проект построен при помощи фреймворка для геймдизайна [libGDX](https://github.com/libgdx/libgdx)
### KryoNet
Также в проекте используется KryoNet, библиотека Java, которая предоставляет чистый и простой API для эффективного сетевого взаимодействия TCP и UDP клиент
/ сервер с использованием NIO. 
KryoNet использует библиотеку сериализации Kryo для автоматической и эффективной передачи графов объектов по сети.

## Зависимости проекта
```gradle
api "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"
api "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
api "com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop"
api "com.badlogicgames.gdx:gdx:$gdxVersion"
api "com.badlogicgames.gdx:gdx-box2d:$gdxVersion"
```
## Команда для запуска
gradle run
