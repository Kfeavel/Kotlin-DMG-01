package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.INCr8
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestINCr8 {
    @Test
    fun `Test Increment`() {
        val registers = Registers(
            b = 0x0Bu
        ).apply {
            INCr8(registers = this, target = R8.B).execute()
        }
        assertEquals(0x0Cu, registers.b)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            b = 0xFFu
        ).apply {
            INCr8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers(
            b = 0x00u
        ).apply {
            INCr8(registers = this, target = R8.B).execute()
        }
        assertEquals(false, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers(
            b = 0x0Fu
        ).apply {
            INCr8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.halfCarry)
    }
}
