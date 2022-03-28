package stage
import base.ISA._
import base.ARCH._
import connection.{CtrlSignalMaster, ID_RI, REG_TO_RI, RI_DTU, RI_EU, RI_MEU, RI_SEU, RI_TO_REG, RI_VEU}
import spinal.core._
import spinal.lib.{master, slave}

import scala.language.postfixOps


class RI_Reduced extends Component {
  val io = new Bundle{
    val input = slave Flow new ID_RI
    val ctrl = CtrlSignalMaster()

    val output = master Flow new RI_EU

    val regOutput = master Flow new RI_TO_REG
    val regInput = slave Flow new REG_TO_RI
    // bypass part
  }

  val euDispatch = io.input.payload.euDispatch
  val inSignal = io.input.payload

  val rdValue = UInt(REGISTER_BITWIDTH bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)
  val bitWidth = UInt(REGISTER_BITWIDTH bits)
  val imm = UInt(REGISTER_BITWIDTH bits)

  io.output.payload.initOutput
  immSet()
  bitwidthSet()
  regSet()

  io.output.payload.euDispatch := euDispatch
  io.output.payload.rdAddr := io.input.payload.rdAddr
  io.output.payload.rdValue := rdValue
  io.output.payload.rs1Value := rs1Value
  io.output.payload.rs2Value := rs2Value
  io.output.payload.bitWidth := bitWidth
  io.output.payload.imm := imm

  when(euDispatch === EU_DISPATCH.SEU){
    when(io.input.payload.immType =/= IMM_TYPE.NONE){
      io.output.payload.rs2Value := imm
    }
  }


  def regSet():Unit = {
    // read from register file
    io.regOutput.payload.rdAddr := 0
    io.regOutput.payload.rs1Addr := 0
    io.regOutput.payload.rs2Addr := 0


    rdValue := 0
    rs1Value := 0
    rs2Value := 0
    switch(inSignal.regRead){
      is(REG_READ.RD_RS1_RS2){
        io.regOutput.payload.rdAddr := inSignal.rdAddr
        io.regOutput.payload.rs1Addr := inSignal.rs1Addr
        io.regOutput.payload.rs2Addr := inSignal.rs2Addr

        rdValue := io.regInput.payload.rdValue
        rs1Value := io.regInput.payload.rs1Value
        rs2Value := io.regInput.payload.rs2Value
      }
      is(REG_READ.RD_RS1){
        io.regOutput.payload.rdAddr := inSignal.rdAddr
        io.regOutput.payload.rs1Addr := inSignal.rs1Addr

        rdValue := io.regInput.payload.rdValue
        rs1Value := io.regInput.payload.rs1Value
      }
      is(REG_READ.RS1_RS2){
        io.regOutput.payload.rs1Addr := inSignal.rs1Addr
        io.regOutput.payload.rs2Addr := inSignal.rs2Addr

        rs1Value := io.regInput.payload.rs1Value
        rs2Value := io.regInput.payload.rs2Value
      }
      is (REG_READ.RD){
        io.regOutput.payload.rdAddr := inSignal.rdAddr

        rdValue := io.regInput.payload.rdValue
      }
      is(REG_READ.RS1){
        io.regOutput.payload.rs1Addr := inSignal.rs1Addr

        rs1Value := io.regInput.payload.rs1Value
      }
    }
  }

  def immSet():Unit = {
    val tmpImm = IMM(inSignal.instruction)
    imm := 0
    switch (inSignal.immType){
      is(IMM_TYPE.V_TYPE) {imm := tmpImm.v_imm.asUInt}
      is(IMM_TYPE.S_TYPE) {imm := tmpImm.s_imm.asUInt}
      is(IMM_TYPE.M_TYPE) {imm := tmpImm.m_imm.asUInt}
      is(IMM_TYPE.C_TYPE) {imm := tmpImm.c_imm.asUInt}
    }
  }

  def bitwidthSet():Unit = {
    bitWidth := 0
    when(inSignal.bitwidthGen){ bitWidth := inSignal.instruction(bitWidthRange).asUInt}
  }

}
