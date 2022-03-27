package connection
import spinal.core._
import base.ARCH._
import base.ISA._
import scala.language.postfixOps

class ID_RI extends Bundle{
  val instruction = Bits(INSTRUCTION_WORD_LEN bits)

  val sALUop = UInt(SEU_OP_LEN bits)
  val vALUop = UInt(VEU_OP_LEN bits)
  val mALUop = UInt(MEU_OP_LEN bits)
  val dALUop = UInt(DTU_OP_LEN bits)
  val rdAddr = UInt( REGISTER_ADDR_LEN bits)
  val rs1Addr = UInt(REGISTER_ADDR_LEN bits)
  val rs2Addr = UInt(REGISTER_ADDR_LEN bits)

  val euDispatch = Bits(EU_DISPATCH_LEN bits)
  val immType = UInt(IMM_TYPE_LEN bits)
  val bitwidthGen = Bool()
  val regRead = UInt(REG_READ_NUM_LEN bits)

}
