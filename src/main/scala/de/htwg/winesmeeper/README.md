## Project Structure:

### [View:](https://github.com/Atomarverseucht/minesweeper/tree/main/src/main/scala/de/htwg/winesmeeper/aView)
    the code, which is dependent on how you want to see the game, only TUI or (if it is supported) also in the GUI

### [Controller:](https://github.com/Atomarverseucht/minesweeper/tree/main/src/main/scala/de/htwg/winesmeeper/Controller)
    the game logic (executing commands like "open", "save", "load" and few more, game-states) 

### [Model:](https://github.com/Atomarverseucht/minesweeper/tree/main/src/main/scala/de/htwg/winesmeeper/Model)
    the data structure of the game (the Board)
    
#### Main.scala: 
    starting point of the game

#### Config.scala:
    a list of constructors and values used to make Interface Objects or important values to configure the project

#### Observer.scala: 
    the code how the Controller communicates with the view (abstract)
