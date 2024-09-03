package gameboy.cpu

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus

class CPU {
    internal val registers = Registers()
    internal val bus = MemoryBus()

    internal fun step() {
        val opcode = bus[registers.pc]
        Instruction.fromByte(opcode, registers, bus).apply {
            println("${this@apply}")
            execute()
        }
    }
}
