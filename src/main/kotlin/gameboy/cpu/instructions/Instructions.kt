package gameboy.cpu.instructions

import gameboy.cpu.Registers

sealed class Instruction<T : InstructionTarget> {
    abstract fun execute(registers: Registers)

    companion object {
        fun execute(instruction: Instruction<*>) {
            when (instruction) {
                is ADD -> {

                }
                else -> throw IllegalStateException("Unknown instruction ($instruction)")
            }
        }
    }
}
