package gameboy.cpu.instructions.etc

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.Registers

class RLCa(
    override val registers: Registers,
) : Instruction {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b00000111u
        override val opcode: UByte = 0b00000111u

    }

    override fun execute() {
        val msb: UByte = registers.a and 0b10000000u
        registers.a = (registers.a.toUInt() shl 1).toUByte()

        registers.f.apply {
            zero = (registers.a.compareTo(0u) == 0)
            subtract = false
            halfCarry = false
            carry = (msb.compareTo(0b10000000u) == 0)
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName}"
}
