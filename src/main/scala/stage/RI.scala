package stage

import connection.{CtrlSignalMaster, ID_RI, RI_DTU, RI_MEU, RI_SEU, RI_VEU}
import spinal.core._
import spinal.lib.{master, slave}

import scala.language.postfixOps



class RI extends Component {
  val io = new Bundle{
    val input = slave Flow new ID_RI
    val ctrl = CtrlSignalMaster()

    val VEUout = master Flow new RI_VEU
    val SEUout = master Flow new RI_SEU
    val MEUout = master Flow new RI_MEU
    val DTUout = master Flow new RI_DTU

  }






}
