package gameboy.cpu

import gameboy.cpu.instructions.Instruction

class CPU {
    private val registers = Registers()

    private fun execute(instruction: Instruction<*>) {
        instruction.execute(this.registers)
    }
}
