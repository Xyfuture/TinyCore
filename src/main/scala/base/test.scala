package base

import spinal.core._
import stage.{ID, IF, REG, RI, RI_Reduced, SEU}

class TopLevel extends Component {
  val bitWidth = 7 bits
  val io = new Bundle{
    val input = in Bits(bitWidth)
    val output = out Bits(bitWidth)
  }
  io.output := B"000_0000"

}

//class Top extends Component {
//  val ifStage = new IF()
//  val idStage = new ID()
//
//  ifStage.io.output >-> idStage.io.input
//
//}

class Top extends Component {
  val ifStage = clockDomain(new IF())
  val idStage = clockDomain(new ID())
  val riStage = clockDomain(new RI())
  val regStage = clockDomain(new REG())
  val seuStage = clockDomain(new SEU())



  ifStage.io.output >-> idStage.io.input
  idStage.io.output >-> riStage.io.input

  riStage.io.SEUout >-> seuStage.io.input

  riStage.io.regInput << regStage.io.toRI
  riStage.io.regOutput >> regStage.io.fromRI

  seuStage.io.output >> regStage.io.fromSEU

}

object TopLevelVerilog{
  def main(args:Array[String]): Unit ={
//    SpinalVerilog(new IF("E:\\code\\TinyCore\\inst.txt")).printPruned()
    SpinalVerilog(new Top).printPruned()
  }

}
