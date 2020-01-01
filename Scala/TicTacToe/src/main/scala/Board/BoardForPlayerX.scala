package Board

class BoardForPlayerX(board: Array[Char]) extends Board(board){
  override val player = 'X'

  override def getNextPlayerBoard(): Board = new BoardForPlayerO(this.toString().toCharArray)
}
