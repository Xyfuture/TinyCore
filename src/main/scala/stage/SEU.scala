package stage
import base.ARCH.SEU_OPCODE
import base.ISA._
import connection.{CtrlSignalMaster, RI_SEU, SEU_REG}
import spinal.core._
import spinal.lib._


class SEU extends Component {
  val io = new Bundle{
    val input = slave Flow new RI_SEU
//    val ctrl = CtrlSignalMaster()

    val output = master Flow new SEU_REG
  }
  val inSignal = io.input.payload
  val rdValue = io.output.payload.rdValue

  rdValue := 0
  io.output.valid := True
  io.output.payload.rdAddr := inSignal.rdAddr




  switch(inSignal.ALUop){
    is(SEU_OPCODE.SADD) {rdValue := inSignal.op1Value + inSignal.op2Value}
    is(SEU_OPCODE.SSUB) {rdValue := inSignal.op1Value - inSignal.op2Value}
    is(SEU_OPCODE.SMUL) {rdValue := (inSignal.op1Value * inSignal.op2Value)(REGISTER_BITWIDTH-1 downto 0)}
    is(SEU_OPCODE.SDIV) {rdValue := inSignal.op1Value / inSignal.op2Value}
  }

}
