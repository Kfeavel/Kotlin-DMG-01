package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.ADDar8
import gameboy.cpu.instructions.arithmetic.INCr8
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestInstruction {
    @Test
    fun `Opcode Decoding`() {
        val nop = Instruction.fromByte(
            opcode = 0x00u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(nop is NOP)

        val halt = Instruction.fromByte(
            opcode = 0x76u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(halt is HALT)

        val illegal = Instruction.fromByte(
            opcode = 0xD3u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(illegal is HALT)

        val incr8a = Instruction.fromByte(
            opcode = 0x24u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(incr8a is INCr8)
        assertEquals(R8.H, incr8a.dest)

        val addr8a = Instruction.fromByte(
            opcode = 0x80u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(addr8a is ADDar8)
        assertEquals(R8.B, addr8a.dest)
    }
}
