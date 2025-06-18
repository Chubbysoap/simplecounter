package simplecounter

import chisel3._
import org.chipsalliance.cde.config._
import freechips.rocketchip.tile._

// Simple 8-bit counter module
class SimpleCounter(implicit p: Parameters) extends CoreModule()(p) {
  val io = IO(new Bundle {
    val en = Input(Bool())
    val count = Output(UInt(8.W))
  })

  val countReg = RegInit(0.U(8.W))
  when(io.en) {
    countReg := countReg + 1.U
  }
  io.count := countReg
}

// Top-level module for SimpleCounter
class SimpleCounterTop(implicit p: Parameters) extends Module {
  val io = IO(new Bundle {
    val en = Input(Bool())
    val count = Output(UInt(8.W))
  })

  val counter = Module(new SimpleCounter)
  counter.io.en := io.en
  io.count := counter.io.count
}