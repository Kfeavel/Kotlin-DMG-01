package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

/**
 * [RLCa] is the same thing as the [RLCr8] but with a constant [dest] of [R8.A]
 */
class RLCa(
    override val registers: Registers,
) : RLCr8(registers, R8.A) {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b00000111u
        override val opcode: UByte = 0b00000111u
    }

    override fun toString() = "${this::class.simpleName}"
}
