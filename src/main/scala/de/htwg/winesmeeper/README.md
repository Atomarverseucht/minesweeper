## Project Structure:

Main.scala: starting point of the game

### View: 
    the code, which is dependent on how you want to see the game, only TUI or (if it is supported) also in the GUI

### Controller:
    the game logic (executing commands like "open", "save", "load" and few more, game-states) 

### Model
    the data structure of the game (the Board)
