package stage

import connection.{CtrlSignalMaster, ID_RI, REG_TO_RI, RI_DTU, RI_MEU, RI_SEU, RI_TO_REG, RI_VEU}
import spinal.core._
import spinal.lib.{master, slave}

import scala.language.postfixOps
import base.ISA._
import base.ARCH._


class RI extends Component {
  val io = new Bundle{
    val input = slave Flow new ID_RI
    val ctrl = CtrlSignalMaster()

    val VEUout = master Flow new RI_VEU
    val SEUout = master Flow new RI_SEU
    val MEUout = master Flow new RI_MEU
    val DTUout = master Flow new RI_DTU

    val regOutput = master Flow new RI_TO_REG
    val regInput = slave Flow new REG_TO_RI
    // bypass part
  }
  val VEUout = io.VEUout.payload
  val SEUout = io.SEUout.payload
  val MEUout = io.MEUout.payload
  val DTUout = io.DTUout.payload
  val inSignal = io.input.payload

  val euDispatch = io.input.payload.euDispatch

  val rdValue = UInt(REGISTER_BITWIDTH bits)
  val rs1Value = UInt(REGISTER_BITWIDTH bits)
  val rs2Value = UInt(REGISTER_BITWIDTH bits)
  val bitWidth = UInt(REGISTER_BITWIDTH bits)
  val imm = UInt(REGISTER_BITWIDTH bits)


  initOutput()
  regSet()
  immSet()
  bitwidthSet()


  switch(euDispatch){
    is(EU_DISPATCH.VEU){
      io.VEUout.valid := True

      VEUout.ALUop := inSignal.vALUop
      VEUout.rdValue := rdValue
      VEUout.rs1Value := rs1Value
      VEUout.rs2Value := rs2Value
      VEUout.imm := imm
      VEUout.bitWidth := bitWidth
    }
    is(EU_DISPATCH.SEU){
      io.SEUout.valid := True

      SEUout.ALUop := inSignal.sALUop
      SEUout.rdAddr := inSignal.rdAddr
      SEUout.op1Value := rs1Value
      SEUout.op2Value :=  rs2Value
      when(inSignal.immType =/= IMM_TYPE.NONE){
        SEUout.op2Value := imm
      }

    }
    is(EU_DISPATCH.MEU){
      io.MEUout.valid := True

      MEUout.ALUop := inSignal.mALUop
      MEUout.rdValue := rdValue
      MEUout.rs1Value := rs1Value
      MEUout.rs2Value := rs2Value
      MEUout.imm := imm
      MEUout.bitWidth := bitWidth


    }
    is(EU_DISPATCH.DTU){
      io.DTUout.valid := True

      DTUout.ALUop := inSignal.dALUop
      DTUout.rdAddr := inSignal.rdAddr
      DTUout.rs1Value := rs1Value
      DTUout.rs2Value := rs2Value
      DTUout.imm := imm
    }
  }



  def initOutput():Unit = {
    VEUout.initOutput
    SEUout.initOutput
    MEUout.initOutput
    DTUout.initOutput
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
