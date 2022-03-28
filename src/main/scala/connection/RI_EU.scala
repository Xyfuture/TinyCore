package connection

import spinal.core._
import base.ARCH._
import base.ISA._

import scala.language.postfixOps

class RI_VEU extends Bundle{
  val vALUop = UInt(VEU_OP_LEN bits)
  val rdValue = UInt(REGISTER_BITWIDTH bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)
  val bitWidth = UInt(REGISTER_BITWIDTH bits)
  val imm = UInt(REGISTER_BITWIDTH bits)

}

class RI_SEU extends Bundle{
  val sALUop = UInt(SEU_OP_LEN bits)
  val rdAddr = UInt(REGISTER_ADDR_LEN bits)
  val op1Value = UInt(REGISTER_BITWIDTH bits)
  val op2Value = UInt(REGISTER_BITWIDTH bits)
}

class RI_MEU extends Bundle{
  val mALUop = UInt(MEU_OP_LEN bits)
  val rdValue = UInt(REGISTER_BITWIDTH bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)
  val imm = UInt(REGISTER_BITWIDTH bits)
  val biwWidth = UInt(REGISTER_BITWIDTH bits)

}

class RI_DTU extends Bundle{
  val dALUop = UInt(DTU_OP_LEN bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)
  val imm = UInt(REGISTER_BITWIDTH bits)

}

