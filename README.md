# Box Take Home
## Usage
- To run with test runner:
    1. Relocate to testcases folder
    2. Run command to invoke test runner
    - Note: `box-take-home.jar` must be in the same folder as the test cases and the runner for the runner to work.
- To run in IDE:
    1. Run BoxShogi.java with your specified command line arguments. 
    
    - Note: To run in file mode, your command line argument should be in the syntax of `testcases/"YOUR TEST CASE HERE"
    `. Program assumes the runnable file is in the same directory as the test cases and the test runner
 ---
## Take Aways
 - Study better design patterns and be more consistent from the get go with method parameters when dealing with a large projext
 - Start documenting thought process sooner into the project. 
 - Push smaller commits more often to better document progress. 
 - Start earlier
 ---
## Game Design

### BoxShogi.java
`BoxShogi.java` is the main driver for the project. It holds the main method and handling user inputs and creating an instances of a game depending on the command line flags.

 ---
### Board.java
The `board.java` represents the board on which the game will be played on.
#### Design Decisions
The board holds the state of the players, their pieces, and the relationship between them and their opponent. 
Although it seems more intuitive to have the players hold the state of themselves and their pieces, i ran into a brick wall when trying to access an opponents piece. 
This lead me down the road of having most of the state held by the board itself, using the pieces as somewhat of a secondary state holder knowing its owner and its own valid moves and using the player just to hold certain data such as captured pieces.  
This design decision made it much easier to check for checks and checkmates as I have access to every piece and their owner. 

 ---
### Game.java, FileMode.java, InteractiveMode.java
`Game.java` represents a certain game mode played by the user and gets extended by two different files `FileMode.java` and `InteractiveMode.java` representing the two different game modes you can play. 
#### Design Decisions
The main difference between the two modes is their use of their Helper Classes. These helper classes facilitate communication to the user. FileMode only outputs to the user once the test case is over, while Interactive mode outputs every action. 

 ---
### GameHelper.java, FileHelper.java, InteractiveHelper.java
`GameHelper.java` is the interface for the BoxShogi implementation. 
#### Design Decisions
GameHelper and its children classes, FileHelper and InteractiveHelper, use an `observer pattern` to communicate to the user, only being called when the state of the game changes. 

 ---
### Piece.java
`Piece.java` and its children represent the possible BoxShogi Pieces. Each piece can check if a move or a drop is valid but cannot actually perform the action of dropping of moving. 
#### Design Decisions
The pieces by far were the easiest to design and implement. I used a `template method` design patterns to build the overall structure of each piece. All pieces build off of the Abstract class `Piece.java` Override the necessary methods to fulfil the correct functionality.  
I chose to separate each of the Piece's moves into a separate class so that they can all access each others move methods. This structure made it much easier to check the moves for Promoted pieces.   
I also included a PieceFactory class that uses a `factory method` design pattern to create pieces based on the symbol given. This was made for initializing the boards of each test case and was used in conjunction with the parseTestCase util.

---
### Coordinate.java
`Coordinate.java` is a helper class that represents a certain space in a two dimensional array. 
#### Design Decisions
I originally created to class to aid with piece moves as I needed a way to encapsulate the row and column index of a particular spot. 
Although I didnt end up using the class very much for its intended purpose, it proved to be very useful when turning user input from string to its respective row and column indexes. 
