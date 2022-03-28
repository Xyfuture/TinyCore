package connection
import spinal.core._
import base.ISA._
import base.ARCH._

import scala.language.postfixOps


class RI_TO_REG extends Bundle {
  val rdAddr = UInt(REGISTER_ADDR_LEN bits)
  val rs1Addr = UInt(REGISTER_ADDR_LEN bits)
  val rs2Addr = UInt(REGISTER_ADDR_LEN bits)
}

class REG_TO_RI extends Bundle{
  val rdValue = UInt(REGISTER_BITWIDTH bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)

}
