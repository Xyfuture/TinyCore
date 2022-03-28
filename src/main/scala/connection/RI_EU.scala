package connection

import spinal.core._
import base.ARCH._
import base.ISA._

import scala.language.postfixOps

class RI_VEU extends Bundle{
  val ALUop = UInt(VEU_OP_LEN bits)
  val rdValue = UInt(REGISTER_BITWIDTH bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)
  val bitWidth = UInt(REGISTER_BITWIDTH bits)
  val imm = UInt(REGISTER_BITWIDTH bits)

  def initOutput = {
    ALUop := U(0,VEU_OP_LEN bits)
    rdValue := U(0,REGISTER_BITWIDTH bits)
    rs1Value := U(0,REGISTER_BITWIDTH bits)
    rs2Value := U(0,REGISTER_BITWIDTH bits)
    bitWidth := U(0,REGISTER_BITWIDTH bits)
    imm := U(0,REGISTER_BITWIDTH bits)
  }

}

class RI_SEU extends Bundle{
  val ALUop = UInt(SEU_OP_LEN bits)
  val rdAddr = UInt(REGISTER_ADDR_LEN bits)
  val op1Value = UInt(REGISTER_BITWIDTH bits)
  val op2Value = UInt(REGISTER_BITWIDTH bits)

  def initOutput = {
    ALUop := U(0,SEU_OP_LEN bits)
    rdAddr := U(0,REGISTER_ADDR_LEN bits)
    op1Value := U(0,REGISTER_BITWIDTH bits)
    op2Value := U(0,REGISTER_BITWIDTH bits)
  }
}

class RI_MEU extends Bundle{
  val ALUop = UInt(MEU_OP_LEN bits)
  val rdValue = UInt(REGISTER_BITWIDTH bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)
  val imm = UInt(REGISTER_BITWIDTH bits)
  val bitWidth = UInt(REGISTER_BITWIDTH bits)


  def initOutput = {
    ALUop := U(0,MEU_OP_LEN bits)
    rdValue := U(0,REGISTER_BITWIDTH bits)
    rs1Value := U(0,REGISTER_BITWIDTH bits)
    rs2Value := U(0,REGISTER_BITWIDTH bits)
    imm := U(0,REGISTER_BITWIDTH bits)
    bitWidth := U(0,REGISTER_BITWIDTH bits)

  }
}

class RI_DTU extends Bundle{
  val ALUop = UInt(DTU_OP_LEN bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)
  val imm = UInt(REGISTER_BITWIDTH bits)

  def initOutput = {
    ALUop := U(0,MEU_OP_LEN bits)
    rs1Value := U(0,REGISTER_BITWIDTH bits)
    rs2Value := U(0,REGISTER_BITWIDTH bits)
    imm := U(0,REGISTER_BITWIDTH bits)
  }
}

