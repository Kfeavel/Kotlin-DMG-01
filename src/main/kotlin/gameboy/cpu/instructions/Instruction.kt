package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.*
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus

interface Instruction {
    val registers: Registers
    fun execute()

    companion object {
        private fun UByte.matchesMask(mask: UByte, result: UByte = mask): Boolean {
            return ((this and mask) == result)
        }

        private fun fromByteWithPrefix(
            opcode: UByte,
            registers: Registers,
        ): Instruction {
            return when (opcode.toInt()) {
                else -> throw IllegalStateException("Unknown opcode ($opcode)")
            }
        }

        @OptIn(ExperimentalStdlibApi::class)
        fun fromByte(
            opcode: UByte,
            registers: Registers,
            bus: MemoryBus,
        ): Instruction {
            when (opcode.toInt()) {
                0x00 -> return NOP(registers)
                0x76 -> return HALT(registers)
                0xCB -> return fromByteWithPrefix(bus[++registers.pc], registers)
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
                0xFD -> return HALT(registers)
                // Complex instructions
                else -> when {
                    // Block 0
                    opcode.matchesMask(0b00000111u, 0b00000100u) ->
                        return INCr8(registers, R8.fromOpcode(opcode, 0b00111000u, 3))
                    opcode.matchesMask(0b00000111u, 0b00000101u) ->
                        return DECr8(registers, R8.fromOpcode(opcode, 0b00111000u, 3))
                    // Block 2 (8-bit arithmetic)
                    opcode.matchesMask(0b11111000u, 0b10000000u) ->
                        return ADDr8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b1001000u) ->
                        return ADCr8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10010000u) ->
                        return SUBr8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10011000u) ->
                        return SBCr8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10100000u) ->
                        return ANDr8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10101000u) ->
                        return XORr8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10110000u) ->
                        return ORr8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10111000u) ->
                        return CPr8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    else -> throw IllegalStateException("Unknown opcode (0x${opcode.toHexString()})")
                }
            }
        }
    }
}
