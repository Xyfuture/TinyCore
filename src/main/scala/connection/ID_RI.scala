package connection
import spinal.core._

import scala.language.postfixOps

class ID_RI extends Bundle{
  val instruction = Bits(32 bits)
  val euDispatch = Bits(4 bits)
  val sALUop = UInt(32 bits)
  val vALUop = UInt(32 bits)
  val mALUop = UInt(32 bits)
  val rdAddr = UInt( 5 bits)
  val rs1Aaddr = UInt(5 bits)
  val rs2Aaddr = UInt(5 bits)

  val immGen = UInt(2 bits)
  val bitwidthGen = Bits(1 bits)

}
