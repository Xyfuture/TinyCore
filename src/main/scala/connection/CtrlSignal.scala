package connection

import spinal.core._
import spinal.lib.master

import scala.language.postfixOps

class CtrlSignal extends Bundle{

}


case class CtrlSignalMaster() extends Bundle {
  val stall = in Bool()
  val toCtrl = master Flow new CtrlSignal

  def no_stall:Bool = !stall
}
