package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers

/**
 * [RLa] is the same thing as the [RLr8] but with a constant [dest] of [R8.A]
 */
class RLa(
    override val registers: Registers,
) : RLr8(registers, R8.A) {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b11111111u
        override val opcode: UByte = 0b00010111u
    }

    override fun toString() = "${this::class.simpleName}"
}
