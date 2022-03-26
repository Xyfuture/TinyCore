package base

import spinal.core._


class TopLevel extends Component {
  val bitWidth = 7 bits
  val io = new Bundle{
    val input = in Bits(bitWidth)
    val output = out Bits(bitWidth)
  }
  io.output := B"000_0000"

}

object TopLevelVerilog{
  def main(args:Array[String]): Unit ={
    SpinalVerilog(new TopLevel)
  }
}