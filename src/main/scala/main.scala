package simplecounter

import chisel3._
import chisel3.stage.ChiselStage
import org.chipsalliance.cde.config._

object Main extends App {
  implicit val p: Parameters = Parameters.empty
  (new ChiselStage).execute(
    Array("--target-dir", "generated"),
    Seq(chisel3.stage.ChiselGeneratorAnnotation(() => new SimpleCounterTop()(p)))
  )
}