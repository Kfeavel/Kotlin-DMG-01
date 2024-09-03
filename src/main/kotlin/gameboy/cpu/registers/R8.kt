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
    HL(0x06u);

    companion object {
        /**
         * Finds the register target by taking the opcode, masking off the top byte and comparing to
         * known register values.
         */
        @OptIn(ExperimentalStdlibApi::class)
        fun fromOpcode(opcode: UByte, mask: UByte): R8 {
            return R8.entries.find { it.register == ((opcode and mask).toUInt() shr 4).toUByte() }
                ?: throw IllegalStateException("Invalid opcode register target (${opcode.toHexString()})")
        }
    }
}
