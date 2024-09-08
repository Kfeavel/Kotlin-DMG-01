package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.instructions.InstructionBitmasks
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers

class INCr16(
    override val registers: Registers,
    internal val dest: R16,
) : Instruction {
    companion object : InstructionBitmasks {
        override val mask: UByte = 0b11001111u
        override val opcode: UByte = 0b00000011u

    }

    private fun inc(
        get: () -> UShort,
        set: (UShort) -> Unit
    ) {
        set(get().inc())
    }

    override fun execute() {
        when (dest) {
            R16.BC -> inc(registers::bc::get, registers::bc::set)
            R16.DE -> inc(registers::de::get, registers::de::set)
            R16.HL -> inc(registers::hl::get, registers::hl::set)
            R16.SP -> inc(registers::sp::get, registers::sp::set)
        }

        registers.pc++
    }

    override fun toString() = "${this::class.simpleName} $dest"
}
