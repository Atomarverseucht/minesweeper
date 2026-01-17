# Structure:

## [Controller:](https://github.com/Atomarverseucht/minesweeper/tree/main/src/main/scala/de/htwg/winesmeeper/Controller/ImplCommands)
  - gets the input from the View and pass it to the correct command manager (Command folder)
  - reads the data from the Model
  - communicates with the observer pattern with the View (with all or individually per ID)
  - the actual gamestate("running", "win", "lost", "start")

## [Command:](https://github.com/Atomarverseucht/minesweeper/tree/main/src/main/scala/de/htwg/winesmeeper/Controller/Commands)
  - **Turn-Commands:**
        Commands with coordinates, which is undoable callable with the Turn-Command-Manager
  - **System-Commands:**
        Command with sometimes parameters, which is callable with the SysCmd-Manager
  - For an overview type: "help" in the TUI or press the "help" button (GUI) or press STRG+H (GUI)

## [Save:](https://github.com/Atomarverseucht/minesweeper/tree/main/src/main/scala/de/htwg/winesmeeper/Controller/Save)
  - the code which is called to save or load from/to a file (JSON or XML) called by the Save-System-Command
