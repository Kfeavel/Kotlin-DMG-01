package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestDECr16 {
    @Test
    fun `Decode Instruction`() {
        val decr16 = Instruction.fromByte(
            opcode = 0b00011011u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(decr16 is DECr16)
        assertEquals(R16.DE, decr16.dest)
    }

    @Test
    fun `Test Increment`() {
        val registers = Registers().apply {
            bc = 0xAABBu
            DECr16(registers = this, dest = R16.BC).execute()
        }
        assertEquals(0xAABAu, registers.bc)
    }
}
