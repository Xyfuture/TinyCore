
import spinal.core._
import spinal.core.sim._
import stage.{ID, IF}

class Top extends Component {
  val ifStage = clockDomain(new IF())
  val idStage = clockDomain(new ID())

  ifStage.io.output >-> idStage.io.input

}



object TB{
  def main(args:Array[String]): Unit ={
    SimConfig.withWave.compile(new IF("E:\\code\\TinyCore\\inst.txt")).doSim{
      dut => {
        dut.clockDomain.forkStimulus(100)
        for (idx <- 1 to 5){
          dut.clockDomain.waitSampling()
        }
      }
    }
  }
}
