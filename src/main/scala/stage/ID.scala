package stage

import connection.{CtrlSignalMaster, ID_RI, IF_ID}
import spinal.core._
import spinal.lib.{master, slave}
import base.ISA._
import base.ARCH._

import scala.language.postfixOps

class ID extends Component {
  val io = new Bundle{
    val input = slave Flow new IF_ID
    val output = master Flow new ID_RI
    val ctrl = CtrlSignalMaster()
  }

  val inst = io.input.payload.instruction
  val opcode = inst(opcodeRange)
  val funct5 = inst(funct5Range)

  // 初始化
  io.output.payload.instruction := inst
  initOutput
  io.output.valid := False
  io.ctrl.toCtrl.valid := False

  switch(opcode){
    is(OPCODE.VVSET){VEUSetOp(VEU_OPCODE.VVSET,REG_READ_STRING.RD,bitWidth = True)}
    is(OPCODE.VVARITH) { vvarith() }
    is(OPCODE.VVSHIFT) { vvshift() }
    is(OPCODE.VVDMUL) {VEUSetOp(VEU_OPCODE.VVDMUL,REG_READ_STRING.RD_RS1_RS2)}
    is(OPCODE.VINVT) {VEUSetOp(VEU_OPCODE.VINVT,REG_READ_STRING.RD_RS1)}
    is(OPCODE.VRANDG) {VEUSetOp(VEU_OPCODE.VRANDG,REG_READ_STRING.RD)}
    is(OPCODE.VACT) { vact() }
    is(OPCODE.VMV) {VEUSetOp(VEU_OPCODE.VMV,REG_READ_STRING.RD_RS1_RS2,IMM_TYPE.V_TYPE)}

    is(OPCODE.SREG) { sreg() }
    is(OPCODE.SADDI) {SEUSetOp(SEU_OPCODE.SADD,REG_READ_STRING.RS1,IMM_TYPE.S_TYPE)}
    is(OPCODE.SSUBI) {SEUSetOp(SEU_OPCODE.SSUB,REG_READ_STRING.RS1,IMM_TYPE.S_TYPE)}
    is(OPCODE.SMULI) {SEUSetOp(SEU_OPCODE.SMUL,REG_READ_STRING.RS1,IMM_TYPE.S_TYPE)}

    is(OPCODE.SEND) {DTUSetOp(DTU_OPCODE.SEND,REG_READ_STRING.RS1_RS2,IMM_TYPE.C_TYPE)}
    is(OPCODE.RECV) {DTUSetOp(DTU_OPCODE.RECV,REG_READ_STRING.RS1_RS2,IMM_TYPE.C_TYPE)}
    is(OPCODE.LD) {DTUSetOp(DTU_OPCODE.LD,REG_READ_STRING.RS1)}
    is(OPCODE.ST) {DTUSetOp(DTU_OPCODE.ST,REG_READ_STRING.RD_RS1_RS2)}
    is(OPCODE.STI) {DTUSetOp(DTU_OPCODE.STI,REG_READ_STRING.RD_RS1,IMM_TYPE.C_TYPE)}


    is(OPCODE.BIND) {MEUSetOp(MEU_OPCODE.BIND,REG_READ_STRING.RD_RS1_RS2,IMM_TYPE.M_TYPE,True)}
    is(OPCODE.UNBIND) {MEUSetOp(MEU_OPCODE.UNBIND,REG_READ_STRING.RD)}
    is(OPCODE.GEMV) {MEUSetOp(MEU_OPCODE.GEMV,REG_READ_STRING.RD_RS1_RS2,bitWidth = True)}
    is(OPCODE.GVR) {MEUSetOp(MEU_OPCODE.GVR,REG_READ_STRING.RD_RS1_RS2)}
  }

  def vvarith():Unit = {
    regSet(REG_READ_STRING.RD_RS1_RS2)
    switch (funct5) {
      is (FUNCT5.VVADD) {VEUSetOp(VEU_OPCODE.VVADD)}
      is (FUNCT5.VVSUB) {VEUSetOp(VEU_OPCODE.VVSUB)}
      is (FUNCT5.VVMUL) {VEUSetOp(VEU_OPCODE.VVMUL)}
      is (FUNCT5.VVGTM) {VEUSetOp(VEU_OPCODE.VVGTM)}
      is (FUNCT5.VVGT) {VEUSetOp(VEU_OPCODE.VVGT)}
      is (FUNCT5.VVEQ) {VEUSetOp(VEU_OPCODE.VVEQ)}
      is (FUNCT5.VVAND) {VEUSetOp(VEU_OPCODE.VVAND)}
      is (FUNCT5.VVOR) {VEUSetOp(VEU_OPCODE.VVOR)}
    }
  }

  def vvshift():Unit = {
    regSet(REG_READ_STRING.RD_RS1_RS2)
    switch (funct5) {
      is (FUNCT5.VVSLL) {VEUSetOp(VEU_OPCODE.VVSLL,bitWidth = True)}
      is (FUNCT5.VVSRA) {VEUSetOp(VEU_OPCODE.VVSRL,bitWidth = True)}
    }
  }

  def vact():Unit = {
    regSet(REG_READ_STRING.RD_RS1)
    switch(funct5) {
      is (FUNCT5.VRELU) {VEUSetOp(VEU_OPCODE.VRELU)}
      is (FUNCT5.VSIGMOID) {VEUSetOp(VEU_OPCODE.VSIGMOID)}
      is (FUNCT5.VTANH) {VEUSetOp(VEU_OPCODE.VTANH)}
    }
  }

  def sreg():Unit = {
    regSet(REG_READ_STRING.RS1_RS2)
    switch(funct5){
      is (FUNCT5.SADD) {SEUSetOp(SEU_OPCODE.SADD)}
      is (FUNCT5.SSUB) {SEUSetOp(SEU_OPCODE.SSUB)}
      is (FUNCT5.SMUL) {SEUSetOp(SEU_OPCODE.SMUL)}
      is (FUNCT5.SDIV) {SEUSetOp(SEU_OPCODE.SDIV)}
    }
  }

  def regSet(regRead:String):Unit = {
    if (regRead != REG_READ_STRING.NONE) {
      io.output.payload.regRead := U(regRead)
    }
    //    switch(regRead) {
//      is (REG_READ.NONE) {}
//      is (REG_READ.RD_RS1_RS2) {
//        io.output.payload.rs1Addr := inst(rs1Range).asUInt
//        io.output.payload.rs2Addr := inst(rs2Range).asUInt
//        io.output.payload.rdAddr := inst(rdRange).asUInt
//      }
//      is (REG_READ.RD_RS1) {
//        io.output.payload.rdAddr := inst(rdRange).asUInt
//        io.output.payload.rs1Addr := inst(rs1Range).asUInt
//      }
//      is (REG_READ.RS1_RS2) {
//        io.output.payload.rs1Addr := inst(rs1Range).asUInt
//        io.output.payload.rs2Addr := inst(rs2Range).asUInt
//      }
//      is(REG_READ.RD) {
//        io.output.payload.rdAddr := inst(rdRange).asUInt
//      }
//    }
    if (regRead == REG_READ_STRING.RD_RS1_RS2) {
      io.output.payload.rs1Addr := inst(rs1Range).asUInt
      io.output.payload.rs2Addr := inst(rs2Range).asUInt
      io.output.payload.rdAddr := inst(rdRange).asUInt
    }
    else if (regRead == REG_READ_STRING.RD_RS1){
      io.output.payload.rdAddr := inst(rdRange).asUInt
      io.output.payload.rs1Addr := inst(rs1Range).asUInt
    }
    else if (regRead == REG_READ_STRING.RS1_RS2){
      io.output.payload.rs1Addr := inst(rs1Range).asUInt
      io.output.payload.rs2Addr := inst(rs2Range).asUInt
    }
    else if (regRead == REG_READ_STRING.RD){
      io.output.payload.rdAddr := inst(rdRange).asUInt
    }

  }

  def VEUSetOp(op:UInt,regRead:String=REG_READ_STRING.NONE,imm:UInt = IMM_TYPE.NONE,bitWidth:Bool=False):Unit = {
    io.output.payload.euDispatch := EU_DISPATCH.VEU
    io.output.payload.vALUop := op
    regSet(regRead)
    io.output.payload.immType := imm
    io.output.payload.bitwidthGen := bitWidth
  }

  def SEUSetOp(op:UInt,regRead:String=REG_READ_STRING.NONE,imm:UInt = IMM_TYPE.NONE):Unit = {
    io.output.payload.euDispatch := EU_DISPATCH.SEU
    io.output.payload.sALUop := op
    regSet(regRead)
    io.output.payload.immType := imm
  }

  def MEUSetOp(op:UInt,regRead:String=REG_READ_STRING.NONE,imm:UInt = IMM_TYPE.NONE,bitWidth:Bool=False):Unit = {
    io.output.payload.euDispatch := EU_DISPATCH.MEU
    io.output.payload.mALUop := op
    regSet(regRead)
    io.output.payload.immType := imm
    io.output.payload.bitwidthGen := bitWidth
  }

  def DTUSetOp(op:UInt,regRead:String=REG_READ_STRING.NONE,imm:UInt = IMM_TYPE.NONE,bitWidth:Bool=False):Unit = {
    io.output.payload.euDispatch := EU_DISPATCH.DTU
    io.output.payload.dALUop := op
    regSet(regRead)
    io.output.payload.immType := imm
    io.output.payload.bitwidthGen := bitWidth
  }

  def initOutput:Unit = {
    io.output.payload.regRead := REG_READ.NONE
    io.output.payload.immType := IMM_TYPE.NONE
    io.output.payload.euDispatch := EU_DISPATCH.NONE
    io.output.payload.bitwidthGen := False

    io.output.payload.sALUop := SEU_OPCODE.NONE
    io.output.payload.vALUop := VEU_OPCODE.NONE
    io.output.payload.mALUop := MEU_OPCODE.NONE
    io.output.payload.dALUop := DTU_OPCODE.NONE

    io.output.payload.rdAddr := U(0,REGISTER_ADDR_LEN bits)
    io.output.payload.rs1Addr := U(0,REGISTER_ADDR_LEN bits)
    io.output.payload.rs2Addr := U(0,REGISTER_ADDR_LEN bits)
  }
}
