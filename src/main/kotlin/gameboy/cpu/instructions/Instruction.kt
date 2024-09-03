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
                0x76 -> HALT(registers)
                // Unimplemented opcodes that simply hang the CPU when called
                // For our use cases this will simply halt emulation. They could be used in some emulator specific
                // manner which is why these are called out instead of simply letting them fall to the `else` block.
                0xD3,
                0xDB,
                0xDD,
                0xE3,
                0xE4,
                0xEB,
                0xEC,
                0xED,
                0xF4,
                0xFC,
                0xFD -> HALT(registers)
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
