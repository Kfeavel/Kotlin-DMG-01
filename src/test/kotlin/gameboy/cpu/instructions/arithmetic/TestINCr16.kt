package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.instructions.Instruction
import gameboy.cpu.registers.R16
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestINCr16 {
    @Test
    fun `Decode Instruction`() {
        val incr16 = Instruction.fromByte(
            opcode = 0b00010011u,
            registers = Registers(),
            bus = MemoryBus(),
        )
        assertTrue(incr16 is INCr16)
        assertEquals(R16.DE, incr16.target)
    }

    @Test
    fun `Test Increment`() {
        val registers = Registers().apply {
            bc = 0xAABBu
            INCr16(registers = this, target = R16.BC).execute()
        }
        assertEquals(0xAABCu, registers.bc)
    }
}
