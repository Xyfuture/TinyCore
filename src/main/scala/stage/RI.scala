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


    }
    is(EU_DISPATCH.MEU){

    }
    is(EU_DISPATCH.DTU){

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
    io.regOutput.payload.rdAddr := inSignal.rdAddr
    io.regOutput.payload.rs1Addr := inSignal.rs1Addr
    io.regOutput.payload.rs2Addr := inSignal.rs2Addr


    rdValue := 0
    rs1Value := 0
    rs2Value := 0
    switch(inSignal.regRead){
      is(REG_READ.RD_RS1_RS2){
        rdValue := io.regInput.payload.rdValue
        rs1Value := io.regInput.payload.rs1Value
        rs2Value := io.regInput.payload.rs2Value
      }
      is(REG_READ.RD_RS1){
        rdValue := io.regInput.payload.rdValue
        rs1Value := io.regInput.payload.rs1Value
      }
      is(REG_READ.RS1_RS2){
        rs1Value := io.regInput.payload.rs1Value
        rs2Value := io.regInput.payload.rs2Value
      }
      is (REG_READ.RD){
        rdValue := io.regInput.payload.rdValue
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
