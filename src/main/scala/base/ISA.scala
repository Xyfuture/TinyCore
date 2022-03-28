package base

import spinal.core._

object ISA {

  def INSTRUCTION_WORD_LEN = 32
  def REGISTER_CNT = 32
  def REGISTER_ADDR_LEN = 5
  def REGISTER_BITWIDTH = 64

  // instruction range
  def opcodeRange = 6 downto 0
  def rdRange = 11 downto 7
  def rs1Range = 16 downto 12
  def rs2Range = 21 downto 17
  def funct5Range = 26 downto 22
  def bitWidthRange = 31 downto 29

  case class IMM(instruction: Bits) extends Area {
    def s = instruction(31 downto 17)
    def c = instruction(31 downto 22)
    def v = instruction(31 downto 22)
    def m = instruction(28 downto 22)

    def s_imm = B((48 downto 0) -> s(14)) ## s
    def c_imm = B((53 downto 0) -> c(9)) ## c
    def v_imm = B((53 downto 0) -> 0) ## v
    def m_imm = B((56 downto 0) -> m(6)) ## m
  }

  object OPCODE {
    def VVSET = B"001_0001"
    def VVARITH = B"001_0010"
    def VVSHIFT = B"001_0011"
    def VVDMUL = B"001_0100"
    def VINVT = B"001_0101"
    def VRANDG = B"001_0111"
    def VACT = B"001_1000"
    def VMV = B"001_1001"

    def SREG = B"010_0001"
    def SADDI = B"010_0010"
    def SSUBI = B"010_0011"
    def SMULI = B"010_0100"

    def SEND = B"011_0001"
    def RECV = B"011_0010"

    def BIND = B"100_0001"
    def UNBIND = B"100_0010"
    def GEMV = B"100_0011"
    def GVR = B"100_0100"
  }

  object FUNCT5 {
    def VVADD = B"00001"
    def VVSUB = B"00010"
    def VVMUL = B"00011"
    def VVGTM = B"10001"
    def VVGT = B"10010"
    def VVEQ = B"10011"
    def VVAND = B"10100"
    def VVOR = B"10101"

    def VVSLL = B"10101"
    def VVSRA = B"00010"

    def VRELU = B"00001"
    def VSIGMOID = B"00010"
    def VTANH = B"00011"

    def SADD = B"00001"
    def SSUB = B"00010"
    def SMUL = B"00011"
    def SDIV = B"00100"

  }

}
