package gameboy.cpu.registers

import gameboy.cpu.instructions.InstructionTarget

enum class R16(
    override val register: UByte
) : InstructionTarget {
    BC(0x00u),
    DE(0x01u),
    HL(0x02u),
    SP(0x03u);

    companion object {
        /**
         * Finds the register dest by taking the opcode, masking off the top byte and comparing to
         * known register values.
         */
        @OptIn(ExperimentalStdlibApi::class)
        fun fromOpcode(opcode: UByte, mask: UByte, shift: Int = 0): R16 {
            val register = ((opcode and mask).toUInt() shr shift).toUByte()
            return entries.find { it.register == register }
                ?: throw IllegalStateException("Invalid opcode register dest (${opcode.toHexString()})")
        }
    }
}
