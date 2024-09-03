package gameboy.cpu.instructions

import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

sealed class Instruction<T : InstructionTarget> {
    abstract val registers: Registers
    abstract fun execute()

    companion object {
        private fun fromBytePrefix(
            opcode: UByte,
            registers: Registers,
        ): Instruction<*> {
            return when (opcode.toInt()) {
                0x04 -> ADDr8(registers, R8.B)
                else -> throw IllegalStateException("Unknown opcode ($opcode)")
            }
        }

        private fun fromByteNoPrefix(
            opcode: UByte,
            registers: Registers,
        ): Instruction<*> {
            when (opcode) {
                else -> throw IllegalStateException("Unknown opcode ($opcode)")
            }
        }

        fun fromByte(
            opcode: UByte,
            prefixed: Boolean,
            registers: Registers
        ): Instruction<*> = when (prefixed) {
            true -> fromBytePrefix(opcode, registers)
            false -> fromByteNoPrefix(opcode, registers)
        }
    }
}
