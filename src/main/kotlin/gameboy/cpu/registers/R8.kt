package gameboy.cpu.registers

import gameboy.cpu.instructions.InstructionTarget

enum class R8(
    override val register: UByte
) : InstructionTarget {
    A(0x07u),
    B(0x00u),
    C(0x01u),
    D(0x02u),
    E(0x03u),
    H(0x04u),
    L(0x05u),
    HL(0x06u),
}
