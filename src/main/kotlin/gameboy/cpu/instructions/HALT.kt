package gameboy.cpu.instructions

import gameboy.cpu.exceptions.HaltException
import gameboy.cpu.registers.Registers

class HALT(
    override val registers: Registers
) : Instruction {
    override fun execute() {
        throw HaltException()
    }

    override fun toString() = "${this::class.simpleName}"
}
