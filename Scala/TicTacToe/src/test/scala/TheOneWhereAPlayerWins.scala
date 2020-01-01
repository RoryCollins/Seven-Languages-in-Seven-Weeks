import org.scalatest.FunSuite

class TheOneWhereAPlayerWins extends FunSuite {
  test("detect when Player X wins the game") {
    val ticTacToe = new TicTacToe()
    ticTacToe.play(0)
    ticTacToe.play(3)
    ticTacToe.play(1)
    ticTacToe.play(4)
    ticTacToe.play(2)
    assert(ticTacToe.getWinner() === "Player X")
  }
  test("detect when Player O wins the game") {
    val ticTacToe = new TicTacToe()
    ticTacToe.play(1)
    ticTacToe.play(0)
    ticTacToe.play(2)
    ticTacToe.play(4)
    ticTacToe.play(5)
    ticTacToe.play(8)
    assert(ticTacToe.getBoard() === "OXX_OX__O")
    assert(ticTacToe.getWinner() === "Player O")
  }
}