package connection

import spinal.core._
import base.ISA._
import base.ARCH._

import scala.language.postfixOps

class SEU_REG extends Bundle{
  val rdAddr = UInt(REGISTER_ADDR_LEN bits)
  val rdValue = UInt(REGISTER_BITWIDTH bits)
}
