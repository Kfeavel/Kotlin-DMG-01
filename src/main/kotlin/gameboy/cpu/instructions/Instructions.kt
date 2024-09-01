package gameboy.cpu.instructions

import gameboy.cpu.Registers

sealed class Instruction<T : InstructionTarget> {
    abstract fun execute(registers: Registers)

    companion object {
        private fun fromBytePrefix(opcode: UByte): Instruction<*> {
            when (opcode) {
                else -> throw IllegalStateException("Unknown opcode ($opcode)")
            }
        }

        private fun fromByteNoPrefix(opcode: UByte): Instruction<*> {
            when (opcode) {
                else -> throw IllegalStateException("Unknown opcode ($opcode)")
            }
        }

        fun fromByte(opcode: UByte, prefixed: Boolean): Instruction<*> = when (prefixed) {
            true -> fromBytePrefix(opcode)
            false -> fromByteNoPrefix(opcode)
        }

        fun execute(instruction: Instruction<*>) {
            when (instruction) {
                is ADD -> {

                }
                else -> throw IllegalStateException("Unknown instruction ($instruction)")
            }
        }
    }
}
