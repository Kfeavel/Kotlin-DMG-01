package gameboy.cpu.registers

import gameboy.cpu.instructions.InstructionTarget

enum class Cond(
    override val register: UByte
) : InstructionTarget {
    NZ(0x00u),
    Z(0x01u),
    NC(0x02u),
    C(0x03u),
}
