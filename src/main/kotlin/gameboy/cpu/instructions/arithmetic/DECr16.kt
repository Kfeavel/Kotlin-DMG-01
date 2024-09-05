package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers

class DECr16(
    override val registers: Registers,
    internal val target: R16,
) : Instruction {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b11001111u
        override val opcode: UByte = 0b00001011u

    }

    private fun dec(
        get: () -> UShort,
        set: (UShort) -> Unit
    ) {
        set(get().dec())
    }

    override fun execute() {
        when (target) {
            R16.BC -> dec(registers::bc::get, registers::bc::set)
            R16.DE -> dec(registers::de::get, registers::de::set)
            R16.HL -> dec(registers::hl::get, registers::hl::set)
            R16.SP -> dec(registers::sp::get, registers::sp::set)
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $target"
}
