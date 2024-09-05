package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers

class ADDhlr16(
    override val registers: Registers,
    internal val target: R16,
) : Instruction {
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
        when (target) {
            R16.BC -> add(registers, registers::bc::get)
            R16.DE -> add(registers, registers::de::get)
            R16.HL -> add(registers, registers::hl::get)
            R16.SP -> add(registers, registers::sp::get)
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
