package connection

import spinal.core.{Bits, Bundle, IntToBuilder}

import scala.language.postfixOps

class IF_ID extends Bundle{
  val instruction: Bits = Bits(32 bits)
}
