package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.*
import gameboy.cpu.instructions.etc.RLCa
import gameboy.cpu.instructions.etc.RLCr8
import gameboy.cpu.instructions.load.LDr16imm16
import gameboy.cpu.instructions.load.LDr8imm8
import gameboy.cpu.instructions.load.LDr8r8
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus

/**
 * [Instruction Set Reference #1](https://gbdev.io/pandocs/CPU_Instruction_Set.html)
 *
 * [Instruction Set Reference #2](https://gekkio.fi/files/gb-docs/gbctr.pdf)
 *
 * [Instruction Set Reference #3](https://gbdev.io/gb-opcodes//optables/dark)
 */
interface Instruction {
    val registers: Registers
    fun execute()

    companion object {
        private fun UByte.matchesMask(mask: UByte, result: UByte = mask): Boolean {
            return ((this and mask) == result)
        }

        internal fun fromByteWithPrefix(
            opcode: UByte,
            registers: Registers,
        ): Instruction {
            when {
                opcode.matchesMask(RLCr8.mask, RLCr8.opcode) ->
                    return RLCr8(registers, R8.fromOpcode(opcode, RLCr8.register, 0))
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
                // Block 0
                0x00 -> return NOP(registers)
                // Block 1 (8-bit register-to-register loads)
                0x76 -> return HALT(registers)
                0xCB -> return fromByteWithPrefix(bus[++registers.pc], registers)
                // Block 3
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
                    opcode.matchesMask(LDr16imm16.mask, LDr16imm16.opcode) ->
                        return LDr16imm16(registers, bus, R16.fromOpcode(opcode, LDr16imm16.register, 4))

                    opcode.matchesMask(INCr16.mask, INCr16.opcode) ->
                        return INCr16(registers, R16.fromOpcode(opcode, INCr16.register, 4))
                    opcode.matchesMask(DECr16.mask, DECr16.opcode) ->
                        return DECr16(registers, R16.fromOpcode(opcode, DECr16.register, 4))
                    opcode.matchesMask(LDr8r8.mask, LDr8r8.opcode) ->
                        return LDr8r8(
                            registers,
                            dest = R8.fromOpcode(opcode, LDr8r8.registerTarget, 3),
                            source = R8.fromOpcode(opcode, LDr8r8.registerSource, 0),
                        )
                    opcode.matchesMask(RLCa.mask, RLCa.opcode) ->
                        return RLCa(registers)
                    opcode.matchesMask(ADDhlr16.mask, ADDhlr16.opcode) ->
                        return ADDhlr16(registers, R16.fromOpcode(opcode, ADDhlr16.register, 4))

                    opcode.matchesMask(0b00000111u, 0b00000100u) ->
                        return INCr8(registers, R8.fromOpcode(opcode, 0b00111000u, 3))
                    opcode.matchesMask(0b00000111u, 0b00000101u) ->
                        return DECr8(registers, R8.fromOpcode(opcode, 0b00111000u, 3))
                    opcode.matchesMask(LDr8imm8.mask, LDr8imm8.opcode) ->
                        return LDr8imm8(registers, bus, R8.fromOpcode(opcode, LDr8imm8.register, 3))
                    // Block 2 (8-bit arithmetic)
                    opcode.matchesMask(0b11111000u, 0b10000000u) ->
                        return ADDar8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b1001000u) ->
                        return ADCar8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10010000u) ->
                        return SUBar8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10011000u) ->
                        return SBCar8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10100000u) ->
                        return ANDar8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10101000u) ->
                        return XORar8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10110000u) ->
                        return ORar8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    opcode.matchesMask(0b11111000u, 0b10111000u) ->
                        return CPar8(registers, R8.fromOpcode(opcode, 0b00000111u, 0))
                    else -> throw IllegalStateException("Unknown opcode (0x${opcode.toHexString()})")
                }
            }
        }
    }
}
