package stage
import connection.{CtrlSignalMaster, IF_ID}
import spinal.core._
import spinal.lib.slave
import base.ISA._

class ID extends Component {
  val io = new Bundle{
    val input = slave Flow new IF_ID
    val ctrl = CtrlSignalMaster()
  }

  val inst = io.input.payload.instruction
  val opcode = inst(opcodeRange)
  val funct5 = inst(funct5Range)
  switch(opcode){
    is(OPCODE.VVSET){}
    is(OPCODE.VVARITH) {}
    is(OPCODE.VVSHIFT) {}
    is(OPCODE.VVDMUL) {}
    is(OPCODE.VINVT) {}
    is(OPCODE.VRANDG) {}
    is(OPCODE.VACT) {}
    is(OPCODE.VMV) {}

    is(OPCODE.SREG) {}
    is(OPCODE.SADDI) {}
    is(OPCODE.SSUBI) {}
    is(OPCODE.SMULI) {}

    is(OPCODE.SEND) {}
    is(OPCODE.RECV) {}

    is(OPCODE.BIND) {}
    is(OPCODE.UNBIND) {}
    is(OPCODE.GEMV) {}
    is(OPCODE.GVR) {}
  }

  def vvarith:Unit = {
    switch (funct5) {
      is (FUNCT5.VVADD) {}
      is (FUNCT5.VVSUB) {}
      is (FUNCT5.VVMUL) {}
      is (FUNCT5.VVGTM) {}
      is (FUNCT5.VVGT) {}
      is (FUNCT5.VVEQ) {}
      is (FUNCT5.VVAND) {}
      is (FUNCT5.VVOR) {}

      is (FUNCT5.VVSLL) {}
      is (FUNCT5.VVSRA) {}

      is (FUNCT5.VRELU) {}
      is (FUNCT5.VSIGMOID) {}
      is (FUNCT5.VTANH) {}

      is (FUNCT5.SADD) {}
      is (FUNCT5.SSUB) {}
      is (FUNCT5.SMUL) {}
      is (FUNCT5.SDIV) {}

    }
  }


}
