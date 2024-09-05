package gameboy.cpu.instructions.load

import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals

class TestLDr16imm16 {
    @Test
    fun `Test u16 Immediate Load`() {
        val registers = Registers()
        val bus = MemoryBus().apply {
            set(0x00u, 0xAAu)
            set(0x01u, 0xBBu)
        }
        LDr16imm16(registers, bus, R16.BC).execute()
        assertEquals(registers.bc, 0xBBAAu)
    }
}
