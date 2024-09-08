package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers

class ADDhlr16(
    override val registers: Registers,
    internal val dest: R16,
) : Instruction {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b11001111u
        override val opcode: UByte = 0b00001001u

    }

    /**
     * Overflowing add
     */
    private fun add(registers: Registers, get: () -> UShort) {
        val term = get()
        val value = registers.hl
        val newValue = registers.hl.plus(term).toUShort()

        registers.hl = newValue
        registers.f.apply {
            subtract = false
            halfCarry = isAdditionHalfCarry(value, term)
            carry = (newValue < term)
        }
    }

    override fun execute() {
        when (dest) {
            R16.BC -> add(registers, registers::bc::get)
            R16.DE -> add(registers, registers::de::get)
            R16.HL -> add(registers, registers::hl::get)
            R16.SP -> add(registers, registers::sp::get)
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $dest"
}
