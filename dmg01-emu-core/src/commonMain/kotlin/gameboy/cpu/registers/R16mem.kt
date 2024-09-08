package gameboy.cpu.registers

import gameboy.cpu.instructions.InstructionTarget

enum class R16mem(
    override val register: UByte
) : InstructionTarget {
    BC(0x00u),
    DE(0x01u),
    HLI(0x02u), // HL+
    HLD(0x03u), // HL-
}
