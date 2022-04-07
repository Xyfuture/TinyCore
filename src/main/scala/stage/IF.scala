package stage

import scala.io.Source
import scala.math

import spinal.core._
import spinal.lib._
import connection.{ CtrlSignalMaster, IF_ID}
import base.ISA._

import scala.language.postfixOps

class IF(instFileName:String="None") extends Component{
  val io = new Bundle{
    val output = master Flow new IF_ID
//    val ctrl = CtrlSignalMaster()
  }

  val pcReg = Reg(UInt(64 bits)) init(0)


  pcReg := pcReg + 1

  val programString = getFileString(instFileName)
  val addrLen = scala.math.ceil(scala.math.log(programString.length)/scala.math.log(2)).toInt
  val convertBits = (strArray:Array[String]) => for (ele <- strArray) yield B(ele.toInt, INSTRUCTION_WORD_LEN bits)

  val instBuffer = Mem(Bits(INSTRUCTION_WORD_LEN bits),convertBits(programString))

  io.output.payload.instruction := instBuffer.readSync(address = pcReg(addrLen-1 downto 0))
  io.output.valid := True

  def getFileString(fileName:String) = {
    if (fileName != "None"){
      val source = Source.fromFile(fileName,"UTF-8")
      val strArray = source.getLines().toArray
//      val programData = for (elem <- strArray) yield B(elem.toInt,INSTRUCTION_WORD_LEN bits)
//      programData
      strArray
    }
    else {
      Array[String] ("312","2313","543")
    }
  }
}
