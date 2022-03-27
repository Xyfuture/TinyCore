package base
import spinal.core._

object ARCH {
  def VEU_OP_LEN = 6
  def SEU_OP_LEN = 4
  def DTU_OP_LEN = 2
  def MEU_OP_LEN = 4

  object VEU_OPCODE {
    def NONE = U"000_000"

    def VVSET = U"000_001"
    def VVADD = U"100_001"
    def VVSUB = U"100_010"
    def VVMUL = U"100_011"
    def VVGTM = U"100_100"
    def VVGT = U"100_101"
    def VVEQ = U"100_110"
    def VVAND = U"100_111"
    def VVOR = U"101_000"
    def VVSLL = U"101_001"
    def VVSRL = U"101_010"
    def VVDMUL = U"101_011"
    def VINVT = U"101_100"
    def VRANDG = U"101_101"
    def VRELU = U"101_110"
    def VSIGMOID = U"101_111"
    def VTANH = U"110_000"
    def VMV = U"000_010"
  }

  object SEU_OPCODE{
    def NONE = U"0000"
    def SADD = U"0001"
    def SSUB = U"0010"
    def SMUL = U"0011"
    def SDIV = U"0100"

  }

  object DTU_OPCODE {
    def NONE = U"00"
    def SEND = U"01"
    def RECV = U"10"
  }

  object MEU_OPCODE {
    def NONE = U"0000"

    def BIND = U"0001"
    def UNBIND = U"0010"
    def GEMV = U"0101"
    def GVR = U"0110"
  }


  def EU_DISPATCH_LEN = 4
  def IMM_TYPE_LEN = 4
  def REG_READ_NUM_LEN = 3

  object EU_DISPATCH {
    def NONE = B"0000"

    def VEU = B"0001"
    def SEU = B"0010"
    def DTU = B"0011"
    def MEU = B"0100"
  }

  object IMM_TYPE{
    def NONE = U"0000"
    def V_TYPE = U"0001"
    def S_TYPE = U"0010"
    def C_TYPE = U"0011"
    def M_TYPE = U"0100"
  }

  object REG_READ{
    def NONE = U"000"
    def RD_RS1_RS2 = U"001"
    def RD_RS1 = U"010"
    def RS1_RS2 = U"011"
    def RD = U"100"
  }

  object REG_READ_STRING{
    // lj的语法特性
    def NONE = "000"
    def RD_RS1_RS2 = "001"
    def RD_RS1 = "010"
    def RS1_RS2 = "011"
    def RD = "100"
  }
}
