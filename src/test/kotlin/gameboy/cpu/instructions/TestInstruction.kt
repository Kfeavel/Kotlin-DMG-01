package gameboy.cpu.instructions

import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test

class TestInstruction {
    @Test
    fun `Opcode Decoding`() {
        val addr8a = Instruction.fromByte(
            opcode = (0x04u or (R8.A.register.toUInt() shl 4)).toUByte(),
            prefixed = false,
            registers = Registers(),
        )
        assert(addr8a is ADDr8)
        assert((addr8a as ADDr8).target == R8.A)
    }
}
