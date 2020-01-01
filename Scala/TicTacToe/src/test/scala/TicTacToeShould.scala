import org.scalatest.FunSuite

class TicTacToeShould extends FunSuite {
  test("not be marked won when there is no winner") {
    val ticTacToe = new TicTacToe("_________")
    assert(ticTacToe.isWon() === false)
  }
  test("detect first row winner") {
    val ticTacToe = new TicTacToe("XXX______")
    assert(ticTacToe.isWon() === true)
  }
  test("detect second row winner") {
    val ticTacToe = new TicTacToe("___XXX___")
    assert(ticTacToe.isWon() === true)
  }
  test("detect third row winner") {
    val ticTacToe = new TicTacToe("______XXX")
    assert(ticTacToe.isWon() === true)
  }
  test("detect first column winner") {
    val ticTacToe = new TicTacToe("X__X__X__")
    assert(ticTacToe.isWon() === true)
  }
  test("detect second column winner") {
    val ticTacToe = new TicTacToe("_X__X__X_")
    assert(ticTacToe.isWon() === true)
  }
  test("detect third column winner") {
    val ticTacToe = new TicTacToe("__X__X__X")
    assert(ticTacToe.isWon() === true)
  }
  test("detect first diagonal winner") {
    val ticTacToe = new TicTacToe("X___X___X")
    assert(ticTacToe.isWon() === true)
  }
  test("detect second diagonal winner") {
    val ticTacToe = new TicTacToe("__X_X_X__")
    assert(ticTacToe.isWon() === true)
  }
  test("play X on first move") {
    val ticTacToe = new TicTacToe()
    ticTacToe.play(0)
    assert(ticTacToe.getBoard() === "X________")
  }
  test("play O on second move") {
    val ticTacToe = new TicTacToe()
    ticTacToe.play(0)
    ticTacToe.play(1)
    assert(ticTacToe.getBoard() === "XO_______")
  }
  test("ignore move in used location") {
    val ticTacToe = new TicTacToe()
    ticTacToe.play(0)
    ticTacToe.play(1)
    ticTacToe.play(1)
    ticTacToe.play(2)
    assert(ticTacToe.getBoard() === "XOX______")
  }
}
