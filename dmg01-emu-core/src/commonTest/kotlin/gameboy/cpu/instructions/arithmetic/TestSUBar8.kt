package gameboy.cpu.instructions.arithmetic

import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSUBar8 {
    @Test
    fun `Test Sub`() {
        val registers = Registers(
            a = 0xA0u,
            b = 0x0Bu
        ).apply {
            SUBar8(registers = this, dest = R8.B).execute()
        }
        assertEquals(0x95u, registers.a)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            SUBar8(registers = this, dest = R8.B).execute()
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            SUBar8(registers = this, dest = R8.B).execute()
        }
        assertEquals(true, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers(
            a = 0x10u,
            b = 0x01u
        ).apply {
            SUBar8(registers = this, dest = R8.B).execute()
        }
        assertEquals(true, registers.f.halfCarry)
    }

    @Test
    fun `Test Overflow`() {
        val registers = Registers(
            a = 0x00u,
            c = 0x01u
        ).apply {
            SUBar8(registers = this, dest = R8.C).execute()
        }
        assertEquals(true, registers.f.carry)
    }
}
