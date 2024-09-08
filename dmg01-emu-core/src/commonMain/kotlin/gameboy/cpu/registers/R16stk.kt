package gameboy.cpu.registers

import gameboy.cpu.instructions.InstructionTarget

enum class R16stk(
    override val register: UByte
) : InstructionTarget {
    BC(0x00u),
    DE(0x01u),
    HL(0x02u),
    AF(0x03u),
}
