package gameboy.cpu.instructions

import gameboy.cpu.registers.Registers

class NOP(
    override val registers: Registers
) : Instruction {
    override fun execute() {
        registers.pc.inc()
    }
}
