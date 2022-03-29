package stage

import spinal.core._
import spinal.lib._
import connection.{ CtrlSignalMaster, IF_ID}
import base.ISA._

import scala.language.postfixOps

class IF extends Component{
  val io = new Bundle{
    val output = master Flow new IF_ID
    val ctrl = CtrlSignalMaster()
  }

  val pc_reg = Reg(UInt(64 bits))

  when(io.ctrl.no_stall){
    pc_reg := pc_reg + 1
  }

  val inst_buffer = Mem(Bits(INSTRUCTION_WORD_LEN bits), Array[Bits]())

  io.output.payload.instruction := inst_buffer.readSync(address = pc_reg)
  io.output.valid := io.ctrl.no_stall
}
