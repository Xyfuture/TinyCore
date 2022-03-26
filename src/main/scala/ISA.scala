import spinal.core.{Area, B, Bits, IntToBuilder}

object ISA {


  // instruction range
  def opcodeRange = 5 downto 0
  def rdRange = 11 downto 7
  def rs1Range = 16 downto 12
  def rs2Range = 21 downto 17
  def funct5Range = 26 downto 22
  def bitWidthRange = 31 downto 29

  case class IMM(instruction:Bits) extends Area {
    def s = instruction(31 downto 17)
    def c = instruction(31 downto 22)
    def v = instruction(31 downto 22)

    def s_imm = B((16 downto 0)-> s(14)) ## s
    def c_imm = B((21 downto 0)-> c(9)) ## c
    def v_imm = B((21 downto 0)-> v(9)) ## v
  }

}
