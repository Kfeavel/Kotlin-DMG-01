package gameboy.cpu.instructions

import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

interface Instruction {
    val registers: Registers
    fun execute()

    companion object {
        private fun fromBytePrefix(
            opcode: UByte,
            registers: Registers,
        ): Instruction {
            return when (opcode.toInt()) {
                // Opcodes without register prefix
                else -> when (opcode.toInt() and 0x0F) {
                    // Opcodes with register prefix (most common)
                    else -> throw IllegalStateException("Unknown opcode ($opcode)")
                }
            }
        }

        private fun fromByteNoPrefix(
            opcode: UByte,
            registers: Registers,
        ): Instruction {
            return when (opcode.toInt()) {
                // Opcodes without register prefix
                0x00 -> NOP(registers)
                // Opcodes with register prefix (most common)
                else -> when (opcode.toInt() and 0x0F) {
                    0x04 -> ADDr8(registers, R8.fromOpcode(opcode))
                    else -> throw IllegalStateException("Unknown opcode ($opcode)")
                }
            }
        }

        fun fromByte(
            opcode: UByte,
            prefixed: Boolean,
            registers: Registers
        ): Instruction = when (prefixed) {
            true -> fromBytePrefix(opcode, registers)
            false -> fromByteNoPrefix(opcode, registers)
        }
    }
}
