package Board

abstract class Board(board: Array[Char]){
  val player: Char

  def play(location: Int): Board = {
    if(board(location) == '_'){
      board.update(location, player)
      getNextBoard()
    }
    else this
  }

  def getNextBoard(): Board =
    if(isWon()) new GameCompleteBoard(board, player)
    else getNextPlayerBoard()


  def getNextPlayerBoard(): Board

  override def toString: String = board.mkString("")

  def isWon() = {
    List(new Row(board(0), board(1), board(2)),
      new Row(board(3), board(4), board(5)),
      new Row(board(6), board(7), board(8)),
      new Row(board(0), board(3), board(6)),
      new Row(board(1), board(4), board(7)),
      new Row(board(2), board(5), board(8)),
      new Row(board(0), board(4), board(8)),
      new Row(board(2), board(4), board(6))
    ).exists(row => row.isWon())
  }

}







