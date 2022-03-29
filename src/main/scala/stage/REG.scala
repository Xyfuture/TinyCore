package stage
import spinal.core._
import spinal.lib._
import base.ISA._
import base.ARCH._
import connection.{REG_TO_RI, RI_TO_REG, SEU_REG}

import scala.language.postfixOps

class REG extends Component {
  val io = new Bundle{
    val fromSEU = slave Flow new SEU_REG
    val fromRI = slave Flow new RI_TO_REG
    val toRI = master Flow new REG_TO_RI
  }

  val regFile = Mem(Bits(REGISTER_BITWIDTH bits),31)

  val rdValue = io.toRI.payload.rdValue
  val rs1Value = io.toRI.payload.rs1Value
  val rs2Value = io.toRI.payload.rs2Value

  val rdAddr = io.fromRI.payload.rdAddr
  val rs1Addr = io.fromRI.payload.rs1Addr
  val rs2Addr = io.fromRI.payload.rs2Addr

  genRegValue(rdAddr,rdValue)
  genRegValue(rs1Addr,rs1Value)
  genRegValue(rs2Addr,rs2Value)

  regFile.write(io.fromSEU.rdAddr,io.fromSEU.rdValue.asBits)

  def genRegValue(addr:UInt,value:UInt): Unit = {
    when(addr === U(0,REGISTER_ADDR_LEN bits)) {
      value := 0
    }.elsewhen(addr === io.fromSEU.payload.rdAddr) {
      value := io.fromSEU.payload.rdValue
    }.otherwise{
      value := regFile.readSync(addr).asUInt
    }
  }



}
