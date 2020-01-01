import Board.{Board, BoardForPlayerX}

class TicTacToe(stringBoard: String) {
  def getBoard() = board.toString()

  var board: Board = new BoardForPlayerX(stringBoard.toCharArray)

  def isWon() = board.isWon()

  def this(){
    this("_________")
  }

  def getWinner(): String =
    if(isWon())"Player " + board.player
    else "No Winner"

  def play(location: Int) = {
    board = board.play(location)
  }
}




